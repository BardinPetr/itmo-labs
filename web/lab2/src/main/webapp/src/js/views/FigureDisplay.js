import PlotDisplay from "../ui/plot/PlotDisplay.js";

class FigureDisplay extends PlotDisplay {
    #datastore;

    constructor(canvas, datastore, onClick) {
        super(canvas, onClick);

        datastore.on("clear", () => this.redraw());
        datastore.on("insert", (data) => this.addPoint(data));
        this.#datastore = datastore;
    }

    #preloadData() {
        this.#datastore.get().forEach((data) => this.addPoint(data));
    }

    setup(params) {
        super.setup(params);
    }

    redraw(params) {
        super.redraw(params);

        const {R} = this._storedDrawParams;
        if (!R) return;
        this.#drawFigure(R);
        this.#preloadData();
    }

    addPoint({x, y, r, result}) {
        const color =
            r !== this._storedDrawParams.R
                ? "#AAAAAADD"
                : result
                    ? "#00FF00"
                    : "#FF0000";
        this.drawCircle([x, y], 0.1, color);
    }

    #drawFigure(R) {
        this._env((c) => {
            if (!R) return;

            const line = (x, y) => c.lineTo(...this._toCanvas([x, y]));

            c.beginPath();
            c.moveTo(...this._toCanvas([0, 0]));
            line(0, R / 2);
            line(R, 0);
            line(R / 2, 0);
            line(-R / 2, 0);
            c.arc(
                ...this._toCanvas([0, 0]),
                R * this._ratio / 2,
                0,
                Math.PI / 2,
                false
            );
            line(-R, -R / 2);
            line(-R, 0);
            c.closePath();
            c.fillStyle = "#DD00AAA0";
            c.fill();
        });
    }
}

export default FigureDisplay;
