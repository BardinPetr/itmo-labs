import {toFixedString} from "../utils/utils";
import PointResult from "../data/PointResult";

const extractReturn = x => x.jqXHR.pfArgs;

const pointResultFromServer =
    ({x, y, r, inside}) =>
        new PointResult({x, y, r, result: inside});

export const checkPointRequest = async (x, y, r) => {
    const {result} = extractReturn(await remoteSendPoint([
        {name: 'x', value: toFixedString(x, 8)},
        {name: 'y', value: toFixedString(y, 8)},
        {name: 'r', value: toFixedString(r, 8)}
    ]));
    return result ? pointResultFromServer(result) : null;
}

export const getR = async () => extractReturn(await remoteGetR()).r;

export const getPoints = async () => {
    const {points} = extractReturn(await remoteGetPoints());
    return points.map(pointResultFromServer);
}

export const getConstraints = async () => {
    const {constraints: [r, x, y]} = extractReturn(await remoteGetConstraints());
    return {r, x, y}
}


