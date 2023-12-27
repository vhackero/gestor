$(function() {
	/**
	 * 2023/12/22
	 * Falta aplicar las reglas de necogio para generar las graficas
	 */
	cargaGraficasReservaciones = function(xhr, status, args) {
		
		var reservaciones = jQuery.parseJSON(args.estadisticas);		
		console.log(reservaciones);	
		
	}

	cargaGraficasEventos = function(xhr, status, args) {

		var eventos = jQuery.parseJSON(args.estadisticas);
		console.log(eventos);
		cargarPaletaColores();
		cargarEventosTotales(eventos);
		graficarCompEventos(eventos);
		graficarEjeCapEventos(eventos);
		graficarProgramasEventos(eventos);
		graficarEntidadesEventos(eventos);

		/* Graficas de pastel */
		crearGraficaPastel($("#modalidadEventos").attr("id"),
				eventos.nombreModalidadCantidad);
		crearGraficaPastel($("#privadosEventos").attr("id"),
				eventos.nombreClasificacionCantidad);
		crearGraficaPastel($("#tiposEventos").attr("id"),
				eventos.nombreTipoCantidad);
		crearGraficaPastel($("#dirigidoEventos").attr("id"),
				eventos.nombreDirigidoCantidad);
	}

	mostrarListaProgramas = function() {
		$("#contenedorNombresProgramas").fadeToggle();
	}
	/* Carga la cantidad total de eventos */
	cargarEventosTotales = function(eventos) {
		$('.gTotalEventos').text(eventos.totalEventos);
	}

	graficarCompEventos = function(eventos) {
		var arregloJsonCompetencias = [];
		var contenedorGrafica = $("#competenciaEventos");
		var arregloClaves = Object.keys(eventos.nombreCompetenciaCantidad);
		$.each(arregloClaves, function(indice, valor) {
			arregloJsonCompetencias.push({
				argumento : valor,
				valor : eventos.nombreCompetenciaCantidad[valor]
			});

		});

		var id = contenedorGrafica.attr("id");
		var argumento = (Object.keys(arregloJsonCompetencias[0]))[0];
		var valor = (Object.keys(arregloJsonCompetencias[0]))[1];
		graficar(id, arregloJsonCompetencias, argumento, valor, false);
	}

	graficarEjeCapEventos = function(eventos) {
		var arregloJsonEjeCap = [];
		var contenedorGrafica = $("#ejeCapEventos");
		var arregloClaves = Object.keys(eventos.nombreEjeCapCantidad);
		$.each(arregloClaves, function(indice, valor) {
			arregloJsonEjeCap.push({
				argumento : valor,
				valor : eventos.nombreEjeCapCantidad[valor]
			});

		});

		var id = contenedorGrafica.attr("id");
		var argumento = (Object.keys(arregloJsonEjeCap[0]))[0];
		var valor = (Object.keys(arregloJsonEjeCap[0]))[1];
		graficar(id, arregloJsonEjeCap, argumento, valor, false);
	}

	graficarProgramasEventos = function(eventos) {
		var arregloJsonProgramas = [];
		var contenedorNombreProgramas = $("#contenedorNombresProgramas");
		var contenedorGrafica = $("#programaEventos");
		var ul = $("<ul style='list-style:none;padding-left:0'>");
		var arregloClaves = Object.keys(eventos.nombreProgCantidad);
		$.each(arregloClaves, function(indice, valor) {
			arregloJsonProgramas.push({
				argumento : "Prog. " + (indice + 1),
				valor : eventos.nombreProgCantidad[valor]
			});
			if (contenedorNombreProgramas.children().length == 0) {
				ul.append("<li>" + "<b>Prog. " + (indice + 1) + ":</b> "
						+ valor + "</li>");
			}

		});
		if (contenedorNombreProgramas.children().length == 0) {
			contenedorNombreProgramas.append(ul);
		}
		var id = contenedorGrafica.attr("id");
		var argumento = (Object.keys(arregloJsonProgramas[0]))[0];
		var valor = (Object.keys(arregloJsonProgramas[0]))[1];
		graficar(id, arregloJsonProgramas, argumento, valor, false);
	}

	graficarEntidadesEventos = function(eventos) {
		if(eventos.nombreEntidadCantidad != null){
			console.log("tamaño json entidad: " + eventos.nombreEntidadCantidad.length);
			var arregloJsonEntidades = [];
			var contenedorGrafica = $("#entidadEventos");
			var arregloClaves = Object.keys(eventos.nombreEntidadCantidad);
			$.each(arregloClaves, function(indice, valor) {
				arregloJsonEntidades.push({
					argumento : valor,
					valor : eventos.nombreEntidadCantidad[valor]
				});

			});

			var id = contenedorGrafica.attr("id");
			var argumento = (Object.keys(arregloJsonEntidades[0]))[0];
			var valor = (Object.keys(arregloJsonEntidades[0]))[1];
			graficar(id, arregloJsonEntidades, argumento, valor, true);
		}else{
			$(".gTituloEntidad").css("display","none");
		}
		
	}

	crearGraficaPastel = function(idContenedor, datos) {
		var arregloDatos = [];
		var contenedorGrafica = $("#" + idContenedor);
		var arregloClaves = Object.keys(datos);
		$.each(arregloClaves, function(indice, valor) {
			arregloDatos.push({
				argumento : valor,
				valor : datos[valor]
			});

		});

		var id = contenedorGrafica.attr("id");
		var argumento = (Object.keys(arregloDatos[0]))[0];
		var valor = (Object.keys(arregloDatos[0]))[1];
		graficarPastel(id, arregloDatos, argumento, valor);
	}

	graficar = function(id, datos, argumento, valor, graficaHorizontal) {

		$('#' + id).dxChart({
			dataSource : datos,
			barWidth : 0.5,
			series : {
				argumentField : argumento,
				valueField : valor,
				type : "bar",
				color : "#3498db",
				label : {
					visible : true,
					connector : {
						visible : true,
						width : 1
					}
				}
			},
			commonAxisSettings: {
	            grid: { visible: true }
	        },
			legend : {
				visible : false
			},
			size : {
				height : 200,
				width : 800
			},
			rotated : graficaHorizontal,
			tooltip : {
				enabled : true,
				customizeTooltip : function(arg) {
					return {
						text : arg.valueText
					};
				}
			},
			valueAxis : {
				type : 'logarithmic',
				logarithmBase : 2,
				title : {
					text : "Cantidad"
				},
// minorTickInterval:1
				tickInterval : 1,
// min: 0,
				showZero: true,
				visible:true, 
				tick: {
	                visible: true,
	                opacity: 0.75
	            }
			}
		});
	}

	graficarPastel = function(id, datos, argumento, valor) {
		$("#" + id).dxPieChart({
			margin : {
				top : 0,
				bottom : 0,
				left : 0,
				right : 0
			},
			diameter : .5,
			palette : "paletaTresColores",
			dataSource : datos,
			series : [ {
				argumentField : argumento,
				valueField : valor,
				label : {
					visible : true,
					connector : {
						visible : true,
						width : 1
					}
				}
			} ],
			legend : {
				margin : {
					top : 70
				}
			},
			tooltip : {
				enabled : true,
				customizeTooltip : function(arg) {
					return {
						text : arg.valueText
					};
				}
			}
		});
	}

	cargarPaletaColores = function() {
		/* Paleta de colores */
		tresColores = [ 'rgb(84, 143, 203)', 'rgb(0, 0, 0)',
				'rgb(199, 198, 198)', 'rgb(85, 198, 153)', 'rgb(98, 85, 153)' ];
		dosColores = [ 'rgb(84, 143, 203)', 'rgb(199, 198, 198)' ];
		DevExpress.viz.registerPalette('paletaTresColores', tresColores);
		DevExpress.viz.registerPalette('paletaDosColores', dosColores);
	}

});
