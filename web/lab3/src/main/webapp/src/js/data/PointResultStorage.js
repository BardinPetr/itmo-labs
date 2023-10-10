import {getPoints, getR} from "./api";

class PointResultStorage {
    #callbacks = {
        clear: [],
        insert: [],
        config: [],
        change: []
    };
    #points = [];
    #config = {};

    constructor() {
    }

    async start() {
        $(document).on('pfAjaxUpdated', (xhr, settings) => {
            const source = settings.pfSettings.source;
            console.log(`Update from ${source}`)
            if (source.includes("point-check-form") || source.includes("clear-form"))
                this.#fetch();
        });

        await this.#fetch();
    }

    async #fetch() {
        this.#config.r = await getR();
        this.#notify("config", this.#config);

        this.#points = await getPoints();
        this.#notify("change", this.#points);
    }

    on(type, callback) {
        this.#callbacks[type].push(callback);
    }

    #notify(type, data) {
        this.#callbacks[type].forEach((cb) => cb(data));
    }

    add(point) {
        this.#points.push(point)
        this.#notify("insert", point);
    }

    get() {
        return this.#points;
    }

    getR() {
        return this.#config.r
    }
}

export default PointResultStorage;
