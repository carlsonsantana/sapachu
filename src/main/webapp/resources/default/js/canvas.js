clicked = false;
travado = false;
var positions = [];
var menorX, maiorX, menorY, maiorY;

// works out the X, Y position of the click inside the canvas from the X, Y position on the page
function getPosition(mouseEvent, sigCanvas) {
	var x, y;
	if ((mouseEvent.offsetX !== undefined) && (mouseEvent.offsetY !== undefined)) {
		x = mouseEvent.offsetX;
		y = mouseEvent.offsetY;
	}
	if (mouseEvent.pageX !== undefined && mouseEvent.pageY !== undefined) {
		x = mouseEvent.pageX - jQuery(sigCanvas).offset().left;
		y = mouseEvent.pageY - jQuery(sigCanvas).offset().top;
	} else {
		x = mouseEvent.clientX + document.body.scrollLeft + document.documentElement.scrollLeft - jQuery(sigCanvas).offset().left;
		y = mouseEvent.clientY + document.body.scrollTop + document.documentElement.scrollTop - jQuery(sigCanvas).offset().top;
	}
	return {'x': x, 'y': y};
}

function rgbToHex(r, g, b) {
	if (r > 255 || g > 255 || b > 255)
		throw "Invalid color component";
	return ((r << 16) | (g << 8) | b).toString(16);
}

function getCanvas() {
	return document.getElementById("canvasSignature");
}

function getContext() {
	return getCanvas().getContext("2d");
}

function initialize() {
	// get references to the canvas element as well as the 2D drawing context
	var sigCanvas = getCanvas();
	var context = getContext();
	context.strokeStyle = 'Black';
	// This will be defined on a TOUCH device such as iPad or Android, etc.
	/*var is_touch_device = 'ontouchstart' in document.documentElement;
	 if (is_touch_device) {
	 // create a drawer which tracks touch movements
	 var drawer = {
	 isDrawing: false,
	 touchstart: function(coors) {
	 context.beginPath();
	 context.moveTo(coors.x, coors.y);
	 this.isDrawing = true;
	 },
	 touchmove: function(coors) {
	 if (this.isDrawing) {
	 context.lineTo(coors.x, coors.y);
	 context.stroke();
	 }
	 },
	 touchend: function(coors) {
	 if (this.isDrawing) {
	 this.touchmove(coors);
	 this.isDrawing = false;
	 }
	 }
	 };
	 
	 // create a function to pass touch events and coordinates to drawer
	 function draw(event) {
	 
	 // get the touch coordinates.  Using the first touch in case of multi-touch
	 var coors = {
	 x: event.targetTouches[0].pageX,
	 y: event.targetTouches[0].pageY
	 };
	 
	 // Now we need to get the offset of the canvas location
	 var obj = sigCanvas;
	 
	 if (obj.offsetParent) {
	 // Every time we find a new object, we add its offsetLeft and offsetTop to curleft and curtop.
	 do {
	 coors.x -= obj.offsetLeft;
	 coors.y -= obj.offsetTop;
	 }
	 // The while loop can be "while (obj = obj.offsetParent)" only, which does return null
	 // when null is passed back, but that creates a warning in some editors (i.e. VS2010).
	 while ((obj = obj.offsetParent) !== null);
	 }
	 
	 // pass the coordinates to the appropriate handler
	 drawer[event.type](coors);
	 }
	 
	 
	 // attach the touchstart, touchmove, touchend event listeners.
	 sigCanvas.addEventListener('touchstart', draw, false);
	 sigCanvas.addEventListener('touchmove', draw, false);
	 sigCanvas.addEventListener('touchend', draw, false);
	 
	 // prevent elastic scrolling
	 sigCanvas.addEventListener('touchmove', function(event) {
	 event.preventDefault();
	 }, false);
	 } else {*/
	$("#remove").mousedown(function() {
		while (positions.pop() !== undefined)
			;
		context.clearRect(0, 0, 500, 500);
		document.getElementById("form_valorarea").value = "0";
		alturaReal = null;
		larguraReal = null;
	});
	$("#complete").mousedown(function() {
		if (positions.length >= 6) {
			positions.push(positions[3]);
			var numero = document.getElementById("form_valorarea");
			var cont = 0;
			var pontosUlcera = [];
			var baseComparativosUlcera = [];
			numero.value = "";
			for (var i = 3; i < positions.length; i++) {
				if (i !== 3) {
					cont = cont + ((positions[i].x * larguraReal) * (positions[i - 1].y * alturaReal));
				}
				if ((i + 1) !== positions.length) {
					cont = cont - ((positions[i].x * larguraReal) * (positions[i + 1].y * alturaReal));
				}
				pontosUlcera.push(positions[i]);
			}
			for (var i = 0; i < 3; i++) {
				baseComparativosUlcera.push(positions[i]);
			}
			document.getElementById("form_pontosulcera").value = convertArrayToPoints(pontosUlcera);
			document.getElementById("form_basecomparativosulcera").value = convertArrayToPoints(baseComparativosUlcera);
			document.getElementById("form_alturarealcomparativoulcera").value = alturaReal.toString();
			document.getElementById("form_largurarealcomparativoulcera").value = larguraReal.toString();
			if (cont < 0) {
				cont = -cont;
			}
			cont = cont / 2;
			numero.value = cont;

			context.clearRect(0, 0, context.canvas.width, context.canvas.heigth);
			for (var i = 3, length = positions.length; i < length; i++) {
				if ((i + 1) === length) {
					context.strokeStyle = 'red';
				} else {
					context.strokeStyle = 'black';
				}
				context.lineTo(positions[i].x, positions[i].y);
				context.stroke();
			}
		} else {
			alert("É necessário pelo menos que se selecione dois pontos para completar o polígono.");
		}
	});
	// start drawing when the mousedown event fires, and attach handlers to
	// draw a line to wherever the mouse moves to
	$("#canvasSignature").mousedown(function(mouseEvent) {
		var position = getPosition(mouseEvent, sigCanvas);

		context.moveTo(position.x, position.y);
		context.beginPath();
		context.strokeStyle = 'Black';
		if (!clicked) {
			positions.push(position);
		}
		// attach event handlers
		$(this).mousemove(function(mouseEvent) {
			drawLine(mouseEvent, sigCanvas, context, positions);
		}).mouseup(function(mouseEvent) {
			finishDrawing(mouseEvent, sigCanvas, context);
		}).mouseout(function(mouseEvent) {
			finishDrawing(mouseEvent, sigCanvas, context);
		});
		clicked = true;
	});
	//}
}
var casos = 0;
// draws a line to the x and y coordinates of the mouse event inside
// the specified element using the specified context
function drawLine(mouseEvent, sigCanvas, context, points) {
	if (!travado) {
		context.clearRect(0, 0, context.canvas.width, context.canvas.heigth);
		for (var i = 0, length = points.length; i < length; i++) {
			if ((i === 0) || (i === 3)) {
				context.moveTo(points[i].x, points[i].y);
			} else if (i < 3) {
				context.strokeStyle = 'blue';
				context.lineTo(points[i].x, points[i].y);
				context.stroke();
				context.moveTo(points[0].x, points[0].y);
			} else {
				if ((i + 1) === length) {
					context.strokeStyle = 'red';
				} else {
					context.strokeStyle = 'black';
				}
				context.lineTo(points[i].x, points[i].y);
				context.stroke();
			}
			if (i === 2) {
				while (!alturaReal) {
					try {
						alturaReal = parseFloat(prompt("Altura real:", ""))/(Math.abs(points[i - 2].y - points[i - 1].y));
					} catch (e) {
						console.error(e.message);
					}
				}
				while (!larguraReal) {
					try {
						larguraReal = parseFloat(prompt("Largura real:", ""))/(Math.abs(points[i - 2].x - points[i].x));
					} catch (e) {
						console.error(e.message);
					}
				}
			}
		}
	}
}

// draws a line from the last coordiantes in the path to the finishing
// coordinates and unbind any event handlers which need to be preceded
// by the mouse down event
function finishDrawing(mouseEvent, sigCanvas, context) {
	// draw the line to the finishing coordinates
	drawLine(mouseEvent, sigCanvas, context, positions);
	clicked = false;
	context.closePath();

	// unbind any events which could draw
	$(sigCanvas).unbind("mousemove")
			.unbind("mouseup")
			.unbind("mouseout");
}
function close() {
	travado = !travado;
}