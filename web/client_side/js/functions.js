function gerarAreas(loaded) {
	if (loaded) {
		generateAreas();
	} else {
		jQuery('#form_pernas').on('load', generateAreas());
	}
}
function generateAreas() {
	var image = '#form_pernas';
	var top;
	top = jQuery(image).offset().top;
	var left;
	left = jQuery(image).offset().left;
	var usemap = jQuery('#form_pernas').attr('usemap') + 'Id';
	var areas = jQuery(usemap).children('area');
	var length = areas.length;
	for (var i = 0; i < length; i++) {
		var area = areas.eq(i);
		var coordenadas = area.attr('coords').split(',');
		var div = '<a href="' + area.attr('href') + '" style="display:block;" class="mapareas"><div style="border:1px solid black; height:' + (coordenadas[3] - coordenadas[1]) + 'px; width:' + (coordenadas[2] - coordenadas[0]) + 'px;position:absolute;top:' + (top + parseInt(coordenadas[1])) + 'px;left:' + (left + parseInt(coordenadas[0])) + 'px; z-index: 3000"> </div></a>';
		jQuery(div).insertBefore(jQuery("div").eq(0));
	}
}
function sumirAreas() {
	jQuery('.mapareas').remove();
	if (jQuery("#form_pernas").size() > 0) {
		mapaImagem.setOptions({remove: true, disable: true, hide: true});
		mapaImagem.update();
		//generateAreas();
	}
}
function cancelar() {
	var mensagem = 'Deseja realmente cancelar esta operação, cancelando ' +
			'todos os dados informados serão perdidos.';
	return confirm(mensagem);
}
function adicionarArea(mapa, posx1, posy1, posx2, posy2) {
	var map = document.getElementById(mapa);
	var area = '<area shape="rect" coords="' + posx1 + ',' + posy1 + ',' +
			posx2 + ',' + posy2 + '" href="javascript:mostrarUlcera();" />';
	map.innerHTML += area;
	sumirAreas();
	gerarAreas(true);
	mapaImagem.setOptions({remove: false, disable: false, hide: false});
	mapaImagem.update();
}
function mostrarUlcera() {
	document.getElementById("ulcera").style.display = '';
}