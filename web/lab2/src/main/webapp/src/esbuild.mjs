import {context} from "esbuild";
import {sassPlugin} from "esbuild-sass-plugin";


let ctx = await context({
    entryPoints: ["js/views/MainPage.js", "style/ErrorPage.scss", "style/MainPage.scss"],
    color: true,
    logLevel: "info",
    bundle: true,
    minify: true,
    outdir: "../static/dist",
    plugins: [sassPlugin()],
});

if (process.env.NODE_ENV === "production") {
    await ctx.rebuild();
    await ctx.dispose();
} else {
    await ctx.watch();
}
