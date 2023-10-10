import PlotDisplay from "../ui/PlotDisplay.js";

class FigureDisplay extends PlotDisplay {
    #datastore;

    constructor(canvas, datastore, onClick) {
        super(canvas, onClick);

        datastore.on("config", ({r}) => {
            console.log(`New R value = ${r}`)
            this.redraw();
        })
        datastore.on("insert", (point) => this.addPoint(point));
        datastore.on("change", (pts) => {
            console.log("Data changed")
            this.redraw()
            this.addPoints(pts);
        })
        this.#datastore = datastore;
    }

    #preloadData() {
        this.addPoints(this.#datastore.get());
    }

    setup(params) {
        super.setup(params);
    }

    redraw() {
        super.redraw({});

        const R = this.#datastore.getR();
        if (!R) return;
        this.#drawFigure(R);
        this.#preloadData();
    }

    addPoints(pts) {
        pts.forEach(x => this.addPoint(x));
    }

    addPoint({x, y, r, result}) {
        const color =
            Math.abs(r - this.#datastore.getR()) > 1e-5
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
            line(R, 0);
            line(R, R / 2);
            line(0, R / 2);
            line(-R, 0);
            line(-R/ 2, 0);
            c.arc(
                ...this._toCanvas([0, 0]),
                R * this._ratio / 2,
                Math.PI,
                Math.PI / 2,
                true
            );
            c.closePath();
            c.fillStyle = "#DD00AAA0";
            c.fill();
        });
    }
}

export default FigureDisplay;
