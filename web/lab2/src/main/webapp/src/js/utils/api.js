import {toFixedString} from "./utils";

const BASE_URL = `${location.origin}/app`;

export async function checkPointRequest(x, y, r) {
    return $.get({
        url: `${BASE_URL}/check`,
        data: {
            r: toFixedString(r, 9),
            x: toFixedString(x, 9),
            y: toFixedString(y, 9)
        },
    });
}
