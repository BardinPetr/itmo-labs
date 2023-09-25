import PointResult from "./PointResult.js";

class PointResultStorage {
    #callbacks = {
        clear: [],
        insert: [],
    };

    constructor() {
        let data = this.get();
        this.#notify("insert", data)
    }

    on(type, callback) {
        this.#callbacks[type].push(callback);
    }

    #notify(type, data) {
        this.#callbacks[type].forEach((cb) => cb(data));
    }

    #store(data) {
    }

    clear() {
    }

    get() {
        return window.lab.history.map(i => new PointResult({
            id: i[0],
            x: i[1],
            y: i[2],
            r: i[3],
            result: i[4],
            timestamp: i[5],
            executionTime: i[6]
        }))
    }

    add(data) {
    }
}

export default PointResultStorage;
