function gerarAreas(loaded) {
	if (loaded) {
		generateAreas();
	} else {
		jQuery('#form_pernas').on('load', generateAreas());
	}
}
function generateAreas() {
	var image = '#form_pernas,#form_pernas2';
	var top;
	top = jQuery(image).offset().top;
	var left;
	left = jQuery(image).offset().left;
	var usemap = jQuery(image).attr('usemap') + 'Id';
	var areas = jQuery(usemap).children('area, a');
	var length = areas.length;
	for (var i = 0; i < length; i++) {
		var area = areas.eq(i);
		var coordenadas = area.attr('coords').split(',');
		var div = '<a href="#" style="display:block;" class="mapareas" onclick="document.getElementById(\'' + area.attr('id') + '\').onclick();"><div style="border:1px solid black; height:' + (coordenadas[3] - coordenadas[1]) + 'px; width:' + (coordenadas[2] - coordenadas[0]) + 'px;position:absolute;top:' + (top + parseInt(coordenadas[1])) + 'px;left:' + (left + parseInt(coordenadas[0])) + 'px;z-index: 10"> </div></a>';
		jQuery(div).insertBefore(jQuery("#after").eq(0));
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
function substituirArea(posx1, posy1, posx2, posy2) {
	var coords = '((' + posx1 + ',' + posy1 + '),(' + posx2 + ',' + posy2 + '))';
	jQuery('#form_pontos').attr("value", coords);
	sumirAreas();
	gerarAreas(true);
	mapaImagem.setOptions({remove: false, disable: false, hide: false});
	mapaImagem.update();
}
function adicionarArea(mapa, coordenadas) {
	var map = document.getElementById(mapa);
	var area = '<area shape="rect" coords="' + coordenadas + '" href="javascript:mostrarUlcera();" />';
	map.innerHTML += area;
	sumirAreas();
	gerarAreas(true);
	mapaImagem.setOptions({remove: false, disable: false, hide: false});
	mapaImagem.update();
}
function mostrarUlcera() {
	document.getElementById("ulcera").style.display = '';
}


//TODO VISUAL
function adicionarExame() {
	jQuery('<div class=\'form-group exames\'><label>Exame</label><input type=\'text\' class=\'form-control\' /></div>').insertAfter(jQuery('.exames').eq(jQuery('.exames').size() - 1));
}

function adicionarMedicamento() {
	jQuery('<div class=\'form-group medicamentos\'><label>Medicamento</label><input type=\'text\' class=\'form-control\' /></div>').insertAfter(jQuery('.medicamentos').eq(jQuery('.medicamentos').size() - 1));
}

function adicionarCirurgia() {
	jQuery('<div class=\'form-group cirurgias\'><label>Cirurgia</label><input type=\'text\' class=\'form-control\' /></div>').insertAfter(jQuery('.cirurgias').eq(jQuery('.cirurgias').size() - 1));
}
function limparAreas() {
	jQuery('.mapareas').remove();
}