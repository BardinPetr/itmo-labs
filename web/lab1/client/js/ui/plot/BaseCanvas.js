class BaseCanvas {
  constructor(canvas, onClick) {
    this._onClickCallback = onClick;

    this._canvas = $(canvas);
    this._canvas.unbind("click").on("click", (evt) => this._onClick(evt));

    this._ctx = canvas.getContext("2d");
  }

  _onClick({ offsetX, offsetY }) {
    this._onClickCallback([offsetX, offsetY]);
  }

  setup(params) {}

  redraw(params) {
    this._ctx.reset();
    this._ctx.font = "12px arial";

    this._width = this._canvas.width();
    this._height = this._canvas.height();
  }

  drawLine(start, end, width = 1) {
    this._ctx.beginPath();
    this._ctx.lineWidth = width;
    this._ctx.moveTo(...start);
    this._ctx.lineTo(...end);
    this._ctx.stroke();
  }

  drawArrow([x0, y0], [x1, y1], width = 1, size = 10) {
    const angle = Math.atan2(y1 - y0, x1 - x0);
    const delta = (isSin, angleDelta) =>
      size * (isSin ? Math.sin : Math.cos)(angle + angleDelta);

    this._ctx.beginPath();
    this._ctx.lineWidth = width;
    this._ctx.moveTo(x0, y0);
    this._ctx.lineTo(x1, y1);
    this._ctx.moveTo(
      x1 - delta(false, -Math.PI / 6),
      y1 - delta(true, -Math.PI / 6)
    );
    this._ctx.lineTo(x1, y1);
    this._ctx.lineTo(
      x1 - delta(false, Math.PI / 6),
      y1 - delta(true, Math.PI / 6)
    );
    this._ctx.stroke();
  }
}

export default BaseCanvas;
