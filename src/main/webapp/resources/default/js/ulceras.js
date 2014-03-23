mapaImagem = undefined;
coordenadas = undefined;

jQuery(document).ready(function() {
	var areas = jQuery("#mapaImagemId area, #mapaImagemId a, #mapaImagem2Id area, #mapaImagem2Id a");
	for (var i = 0, length = areas.length; i < length; i++) {
		var area = areas.eq(i);
		
		area.attr("coords", convertPointsToHTMLCoords(area.attr("coords")));
	}
	
	try {
		mapaImagem = jQuery("#form_pernas").imgAreaSelect({
			handles: true,
			instance: true,
			onSelectEnd: function(img, selection) {
				/**
				 * selection.x1 e x2
				 * selection.y1 e y2
				 * selection.width e selection.height
				 */
				coordenadas = selection;

				//document.getElementById("form_area").value = selection.x1 +
				//	"," + selection.y1;
			}
		});
	} catch (e) {
	}

	//mapaImagem.setOptions({remove: true});
	gerarAreas(false);
	
	generateAreas();
	
	if (typeof poligonoUlcera != typeof undefined) {
		drawLine(undefined, undefined, getContext(), polygonToArrayPositions(poligonoUlcera));
	}
});

function polygonToArrayPositions(polygon) {
	var points = polygon;
	while ((points.indexOf("(") > -1) || (points.indexOf(")") > -1)) {
		points = points.replace("(", "").replace(")", "");
	}
	var array = points.split(",");
	var objects = [];
	for (var i = 0, length = array.length; i < length; i = i + 2) {
		objects.push({"x": array[i], "y": array[i + 1]});
	}
	return objects;
}