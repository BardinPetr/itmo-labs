const BASE_URL = `${location.origin}/app`;

export async function checkPointRequest(x, y, r) {
    console.log(x.toFixed(9))
    return $.get({
        url: `${BASE_URL}/check`,
        data: {
            r: r.toFixed(9),
            x: x.toFixed(9),
            y: y.toFixed(9)
        },
    });
}
