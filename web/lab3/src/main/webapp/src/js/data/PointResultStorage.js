import {getPoints, getR} from "./api";

const EVENT_SOURCES = ["point-check-form:sendBtn", "point-check-form:rInput", "point-check-form:rSlider", "clear-form"]

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
            const source = settings.pfSettings.source.trim();
            console.log(`Update from ${source}`)
            if (EVENT_SOURCES.includes(source))
                this.#fetch();
        });

        await this.#fetch();
    }

    async #fetch() {
        console.log("Fetching updates")

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
