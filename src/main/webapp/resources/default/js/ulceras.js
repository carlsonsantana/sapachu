jQuery(document).ready(function() {
	var areas = jQuery("#mapaImagemId area, #mapaImagemId a, #mapaImagem2Id area, #mapaImagem2Id a");
	for (var i = 0, length = areas.length; i < length; i++) {
		var area = areas.eq(i);
		
		area.attr("coords", convertPointsToHTMLCoords(area.attr("coords")));
	}
	
	try {
		var coordenadas;
		var mapaImagem = jQuery("#form_pernas").imgAreaSelect({
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
	
	if ((document.getElementById('form_adicionarulcera') != undefined) && (document.getElementById('form_adicionarulcera') != null)) {
		var funcaoAnterior = document.getElementById('form_adicionarulcera').onclick;
		document.getElementById('form_adicionarulcera').onclick = function() {
			substituirArea(coordenadas.x1, coordenadas.y1, coordenadas.x2, coordenadas.y2);
			if (typeof funcaoAnterior != typeof undefined) {
				return funcaoAnterior;
			} else {
				return true;
			}
		}
	}
});