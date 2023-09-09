import PlotDisplay from "../ui/plot/PlotDisplay.js";

class FigureDisplay extends PlotDisplay {
  constructor(canvas, onClick) {
    super(canvas, onClick);
  }

  setup(params) {
    super.setup(params);
  }

  redraw(params) {
    super.redraw(params);

    const { R } = params;
    this.#drawFigure(R);
  }

  #drawFigure(R) {
    if (!R) return;

    const line = (x, y) => this._ctx.lineTo(...this._toCanvas([x, y]));

    this._ctx.beginPath();
    this._ctx.moveTo(...this._toCanvas([0, 0]));
    line(R, 0);
    line(0, -R / 2);
    line(0, -R);
    line(-R / 2, -R);
    line(-R / 2, 0);
    line(-R, 0);
    this._ctx.arc(
      ...this._toCanvas([0, 0]),
      R * this._ratio,
      -Math.PI / 2,
      Math.PI,
      true
    );
    line(0, R);
    this._ctx.closePath();
    this._ctx.fillStyle = "#DD00AAA0";
    this._ctx.fill();
  }
}

export default FigureDisplay;
