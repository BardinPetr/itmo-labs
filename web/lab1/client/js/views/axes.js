function drawLine(ctx, width, start, end) {
  ctx.beginPath();
  ctx.lineWidth = width;
  ctx.moveTo(...start);
  ctx.lineTo(...end);
  ctx.stroke();
}

function drawArrow(ctx, width, [x0, y0], [x1, y1], size = 10) {
  const angle = Math.atan2(y1 - y0, x1 - x0);
  const delta = (isSin, angleDelta) =>
    size * (isSin ? Math.sin : Math.cos)(angle + angleDelta);

  ctx.beginPath();
  ctx.lineWidth = width;
  ctx.moveTo(x0, y0);
  ctx.lineTo(x1, y1);
  ctx.moveTo(x1 - delta(false, -Math.PI / 6), y1 - delta(true, -Math.PI / 6));
  ctx.lineTo(x1, y1);
  ctx.lineTo(x1 - delta(false, Math.PI / 6), y1 - delta(true, Math.PI / 6));
  ctx.stroke();
}

function drawAxes(
  { ctx, toCanvas, ratio, ptsWidth, ptsHeight },
  tickPeriod = 1,
  tickSize = 5
) {
  ctx.font = "12px arial";

  drawArrow(
    ctx,
    2,
    toCanvas([0, -ptsHeight / 2]),
    toCanvas([0, ptsHeight / 2])
  );
  drawArrow(ctx, 2, toCanvas([-ptsWidth / 2, 0]), toCanvas([ptsWidth / 2, 0]));

  tickSize = 5 / ratio;
  for (var i = -ptsHeight / 2 + 1; i <= ptsHeight / 2 - 1; i += tickPeriod) {
    if (i === 0) continue;
    ctx.textBaseline = "middle";
    drawLine(ctx, 1, toCanvas([-tickSize, i]), toCanvas([tickSize, i]));
    ctx.fillText(i, ...toCanvas([tickSize + tickSize / 4, i]));
  }
  for (var i = -ptsWidth / 2 + 1; i <= ptsWidth / 2 - 1; i += tickPeriod) {
    if (i === 0) continue;
    ctx.textAlign = "center";
    drawLine(ctx, 1, toCanvas([i, -tickSize]), toCanvas([i, tickSize]));
    ctx.fillText(i, ...toCanvas([i, 2 * tickSize]));
  }
}

function drawFigure({ ctx, toCanvas, ratio }, R) {
  if (!R) return;

  const line = (x, y) => ctx.lineTo(...toCanvas([x, y]));

  ctx.beginPath();
  ctx.moveTo(...toCanvas([0, 0]));
  line(R, 0);
  line(0, -R / 2);
  line(0, -R);
  line(-R / 2, -R);
  line(-R / 2, 0);
  line(-R, 0);
  ctx.arc(...toCanvas([0, 0]), R * ratio, -Math.PI / 2, Math.PI, true);
  line(0, R);
  ctx.closePath();
  ctx.fillStyle = "#DD00AAA0";
  ctx.fill();
}

function prepareAxes(ctx, minPtsDimension) {
  const [width, height] = [canvas.width, canvas.height];
  const ratio = Math.round(Math.min(width, height) / minPtsDimension);

  const [ptsWidth, ptsHeight] = [
    Math.floor(width / ratio),
    Math.floor(height / ratio),
  ];

  const toCanvas = ([x, y]) => [width / 2 + x * ratio, height / 2 - y * ratio];
  const fromCanvas = ([x, y]) => [
    (x - width / 2) / ratio,
    (height / 2 - y) / ratio,
  ];

  return { ctx, toCanvas, fromCanvas, ratio, ptsWidth, ptsHeight };
}

function renderPlot(size, R, onClick) {
  var canvas = document.getElementById("canvas");
  var ctx = canvas.getContext("2d");
  ctx.reset();

  const axesCtx = prepareAxes(ctx, size);

  drawAxes(axesCtx);

  if (R) {
    drawFigure(axesCtx, R);
    $(canvas)
      .unbind("click")
      .on("click", ({ offsetX, offsetY }) =>
        onClick(axesCtx.fromCanvas([offsetX, offsetY]))
      );
  }

  return axesCtx;
}

export default renderPlot;
