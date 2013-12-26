jQuery(function(){
                var derivers = jQuery.pivotUtilities.derivers;
				/*var renderers = jQuery.extend(jQuery.pivotUtilities.renderers, 
                    jQuery.pivotUtilities.gchart_renderers);*/
                jQuery.getJSON(contexto + "mps.json", function(mps) {
                    jQuery("#output").pivotUI(mps, {
						//renderers: renderers,
						//cols: ["Média Área"],
						//rendererName: "Area Chart",
                        derivedAttributes: {
                            "Média Área": derivers.bin("Área", 10)
                        }
                    });
                });
             });