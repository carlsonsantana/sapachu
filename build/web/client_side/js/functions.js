function gerarAreas(loaded) {
	if (loaded) {
		generateAreas();
	} else {
		jQuery("#form_pernas").on("load", generateAreas());
	}
}
function generateAreas() {
	var image = "#form_pernas";
	var top;
	top = jQuery(image).offset().top;
	var left;
	left = jQuery(image).offset().left;
	var usemap = jQuery("#form_pernas").attr("usemap") + "Id";
	var areas = jQuery(usemap).children("area");
	var length = areas.length;
	for (var i = 0; i < length; i++) {
		var area = areas.eq(i);
		var coordenadas = area.attr("coords").split(",");
		var div = '<a href="' + area.attr('href') + '" style="display:block;" class="mapareas"><div style="border:1px solid black; height:' + coordenadas[3] + 'px; width:' + coordenadas[2] + 'px;position:absolute;top:' + (top + parseInt(coordenadas[0])) + 'px;left:' + (left + parseInt(coordenadas[1])) + 'px; z-index: 3000"> </div></a>';
		jQuery(div).insertBefore(jQuery("div").eq(0));
	}
}
function sumirAreas() {
	jQuery('.mapareas').remove();
	if (jQuery("#form_pernas").size() > 0) {
		//generateAreas();
	}
}