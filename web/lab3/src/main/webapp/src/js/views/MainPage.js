import "../utils/jquery.js";
import FigureDisplay from "../views/FigureDisplay.js";
import PointResultStorage from "../data/PointResultStorage.js";
import {checkPointRequest, getConstraints} from "../data/api.js";
import {absMax} from "../utils/utils.js";
import ClientRangeValidator from "../validation/ClientRangeValidator.js";

class MainPage {
    #window;
    #store;
    #plot;
    #C;

    constructor() {
        console.info("STARTED");

        PrimeFaces.validator['RangeExternalValidated'] = new ClientRangeValidator();

        this.#window = $(window);

        this.#store = new PointResultStorage();
        this.#plot = new FigureDisplay(
            document.getElementById("plot-canvas"),
            this.#store,
            (coord) => this.#pointSelected(coord)
        );

        this.#setup();
    }

    async #setup() {
        this.#C = await getConstraints();

        this.#plot.setup({
            targetDimension: [
                2 * absMax(this.#C.x.min, this.#C.x.max),
                2 * absMax(this.#C.y.min, this.#C.y.max)
            ],
        });

        await this.#store.start();

        this.#window.on("resize", () => this.onResize());
        this.updateTableSize();
    }

    onResize() {
        this.updateTableSize();
    }

    updateTableSize() {
        $(".ui-datatable-scrollable-body").css("max-height", this.#window.height() * 0.5)
    }

    async #pointSelected([x, y]) {
        const r = this.#store.getR();
        if (!r) {
            alert("Please select R first");
            return;
        }

        console.log(`Point R=${r} (${x}, ${y})`);

        try {
            const res = await checkPointRequest(x, y, r);
            if (res == null) {
                alert("Invalid request");
                return;
            }
            this.#store.add(res);
        } catch (err) {
            console.error(err);
            alert("Invalid request");
        }
    }
}

$(() => new MainPage())

