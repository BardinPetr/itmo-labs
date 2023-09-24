export const range = (start, end, step = 1) =>
    [...Array(Math.ceil((end - start) / step) + 1).keys()].map(
        (i) => start + i * step
    );

export const isFloat = (x) => /^\-?\d+(\.\d+)?$/.test(x);

export const id = (el) => el.prop("id");

export const absMax = (...arr) => Math.max(...arr.map((i) => Math.abs(i)));
