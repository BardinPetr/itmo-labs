const {XRange, YRange, RRange, XInclusive, YInclusive, RInclusive} = window.lab.constraints;

export const xInputMinValue = XRange[0];
export const xInputMaxValue = XRange[1];
export const xInclusive = XInclusive.every(i => i);

export const rInputMinValue = RRange[0];
export const rInputMaxValue = RRange[1];
export const rInclusive = RInclusive.every(i => i);

export const yInputMinValue = YRange[0];
export const yInputMaxValue = YRange[1];
export const yInclusive = YInclusive.every(i => i);
