import { context } from "esbuild";
import { compile as sassCompile } from "sass";
import { readFile, writeFile, readdir, mkdir, copyFile } from "fs/promises";
import { resolve, basename } from "path";
import { dirSync as tmpDir } from "tmp";
import { spawn } from "child_process";
import { cwd } from "process";

const serverDir = "../server";
const distDir = "./dist";

const cssInjectPlugin = {
  name: "cssinject",
  setup(build) {
    const cssRegex = /<link[^>]+href\s*=\s*["']([\.\/\w]*)["'][^>]+\/>/g;

    build.onResolve({ filter: /\.html$/ }, async ({ resolveDir, path }) => {
      let html = await readFile(resolve(resolveDir, path), "utf8");

      let watchFiles = [];
      for (const [matchText, cssPath] of html.matchAll(cssRegex)) {
        const source = resolve(resolveDir, path, "../", cssPath);
        const { css, loadedUrls } = sassCompile(source);
        watchFiles.push(...loadedUrls.map((i) => i.pathname));
        html = html.replace(matchText, `<style>\n${css}\n</style>\n`);
      }

      const dist = resolve(tmpDir().name, basename(path));
      await writeFile(dist, html);

      return {
        path: dist,
        watchFiles,
      };
    });
  },
};

let ctx = await context({
  entryPoints: ["js/views/MainPage.js", "static/index.html"],
  color: true,
  logLevel: "info",
  bundle: true,
  minify: true,
  outdir: "dist/",
  plugins: [cssInjectPlugin],
  loader: {
    ".html": "copy",
  },
});

if (process.env.NODE_ENV === "production") {
  await ctx.rebuild();
  await ctx.dispose();
} else {
  try {
    await mkdir(distDir);
  } catch (e) {}

  const phpFiles = await readdir(serverDir);
  await Promise.all(
    phpFiles.map((path) =>
      copyFile(resolve(serverDir, path), resolve(distDir, path))
    )
  );

  spawn("php", ["-S", "localhost:5000", "-t", distDir]);

  await ctx.watch();
}
