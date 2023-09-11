import { build } from "esbuild";
import { compile as sassCompile } from "sass";
import { readFile, writeFile } from "fs/promises";
import { resolve, basename } from "path";
import { dirSync as tmpDir } from "tmp";

const cssInjectPlugin = {
  name: "cssinject",
  setup(build) {
    const cssRegex = /<link[^>]+href\s*=\s*["']([\.\/\w]*)["'][^>]+\/>/g;

    build.onResolve({ filter: /\.html$/ }, async ({ resolveDir, path }) => {
      let html = await readFile(resolve(resolveDir, path), "utf8");

      for (const [matchText, cssPath] of html.matchAll(cssRegex)) {
        const source = resolve(resolveDir, path, "../", cssPath);
        const { css } = sassCompile(source);
        html = html.replace(matchText, `<style>\n${css}\n</style>\n`);
      }

      const dist = resolve(tmpDir().name, basename(path));
      await writeFile(dist, html);

      return { path: dist };
    });
  },
};

await build({
  entryPoints: ["js/views/MainPage.js", "static/index.html"],
  bundle: true,
  minify: true,
  outdir: "dist/",
  plugins: [cssInjectPlugin],
  loader: {
    ".html": "copy",
  },
});
