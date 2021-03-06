function gerarAreas(loaded) {
	if (loaded) {
		generateAreas();
	} else {
		jQuery('#form_pernas').on('load', generateAreas());
	}
}
function generateAreas() {
	var image = '#form_pernas,#form_pernas2';
	
	var top = jQuery(image).offset().top;
	var left = jQuery(image).offset().left;
	
	var usemap = jQuery(image).attr('usemap') + 'Id';
	
	var areas = jQuery(usemap).children('area, a');
	
	var place = jQuery("#after").eq(0);
	place.html('');
	
	var length = areas.length;
	for (var i = 0; i < length; i++) {
		var area = areas.eq(i);
		
		var coordenadas = area.attr('coords').split(',');
		var avaliado = parseInt(area.attr('data-avaliado'));
		var div = '<a href="#" style="display:block;';
		if (avaliado == 0) {
			div += 'background-color: rgb(240, 0, 0);background-color: rgba(240, 0, 0, 0.2);';
		} else if (avaliado == 1) {
			div += 'background-color: rgb(0, 240, 0);background-color: rgba(0, 240, 0, 0.2);';
		}
		
		div += '" class="mapareas" onclick="document.getElementById(\'' + area.attr('id') + '\').onclick();"><div style="border:1px solid black; height:' + (coordenadas[3] - coordenadas[1]) + 'px; width:' + (coordenadas[2] - coordenadas[0]) + 'px;position:absolute;top:' + (top + parseInt(coordenadas[1])) + 'px;left:' + (left + parseInt(coordenadas[0])) + 'px;z-index: 10;';
		if (avaliado == 0) {
			div += 'background-color: rgb(240, 0, 0);background-color: rgba(240, 0, 0, 0.2);';
		} else if (avaliado == 1) {
			div += 'background-color: rgb(0, 240, 0);background-color: rgba(0, 240, 0, 0.2);';
		}
		div += '"> </div></a>';
		place.html(place.html() + div);
	}
}
function sumirAreas() {
	jQuery('.mapareas').remove();
	if (jQuery("#form_pernas").size() > 0) {
		if (typeof mapaImagem != typeof undefined) {
			mapaImagem.setOptions({remove: true, disable: true, hide: true});
			mapaImagem.update();
		}
		//generateAreas();
	}
}
function cancelar() {
	var mensagem = 'Deseja realmente cancelar esta operação, cancelando ' +
			'todos os dados informados serão perdidos.';
	return confirm(mensagem);
}
function substituirArea(x1, y1, x2, y2) {
	var pontos = [];
	pontos.push({
		'x' : x1,
		'y' : y1
	});
	pontos.push({
		'x' : x2,
		'y' : y2
	});
	
	jQuery('#form_pontos').attr("value", convertArrayToPoints(pontos));
	
	sumirAreas();
	gerarAreas(true);
	
	mapaImagem.setOptions({remove: false, disable: false, hide: false});
	mapaImagem.update();
}

function limparAreas() {
	jQuery('.mapareas').remove();
}

function convertPointsToArray(polygon) {
	return parser.parse(polygon);
}

function convertArrayToPoints(array) {
	var polygon = '(';
	var length = array.length;
	for (var i = 0; i < length; i++) {
		var point = array[i];
		polygon += '(' + point.x + ',' + point.y + ')';
		if ((i + 1) != length) {
			polygon += ',';
		}
	}
	polygon += ')';
	return polygon;
}

function convertPointsToHTMLCoords(polygon) {
	var retorno = polygon;
	while ((retorno.indexOf('(') > -1) || (retorno.indexOf(')') > -1)) {
		retorno = retorno.replace('(', '').replace(')', '');
	}
	return retorno;
}

function convertArrayToHTMLCoords(array) {
	return convertPointsToHTMLCoords(convertArrayToPoints(array));
}