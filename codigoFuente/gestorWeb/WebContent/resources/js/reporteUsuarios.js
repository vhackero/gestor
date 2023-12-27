$(function() {

	cargaGraficasUsuarios = function(xhr, status, args) {

		
		
		var usuarios = jQuery.parseJSON(args.estadisticas);
		console.log(usuarios);
		console.log(usuarios.totalUsuarios);
		console.log("Estadisticas: " + args.estadisticas);
		// var dato1 = parseInt(args.progsBorrador);
		// var dato2 = parseInt(args.progsFinal);
		// var dato3 = parseInt(args.progsCancel);
	
		/* Paleta de colores */
		var tresColores = [ 'rgb(84, 143, 203)', 'rgb(0, 0, 0)',
				'rgb(199, 198, 198)' ];
		var dosColores = [ 'rgb(84, 143, 203)', 'rgb(199, 198, 198)' ];
		DevExpress.viz.registerPalette('paletaTresColores', tresColores);
		DevExpress.viz.registerPalette('paletaDosColores', dosColores);

		/* TOTAL USUARIOS */
		$('.gTotalUsuarios').text(usuarios.totalUsuarios);
		/* TOTAL USUARIOS EXTERNOS */
		$('.gTotalExternos').text(usuarios.usuariosExternos);
		/* TOTAL USUARIOS INTERNOS */
		$('.gTotalInternos').text(usuarios.usuariosInternos);

		/* DATOS GENERO */
		var datosUsuariosGeneros = [ {
			tipo : "Hombres",
			cantidad : usuarios.numeroHombres
		}, {
			tipo : "Mujeres",
			cantidad : usuarios.numeroMujeres
		} ];

		/* CARGA DATOS GENERO */
		$("#datosUsuariosGeneros").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaDosColores",
					dataSource : datosUsuariosGeneros,
					series : [ {
						argumentField : "tipo",
						valueField : "cantidad",
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
					onPointClick : function(e) {
						var point = e.target;

						toggleVisibility(point);
					},
					onLegendClick : function(e) {
						var arg = e.target;

						toggleVisibility(this.getAllSeries()[0]
								.getPointsByArg(arg)[0]);
					}
				});

		/* DATOS ESTATUS */
		var datosUsuariosEstatus = [ {
			tipo : "Activos",
			cantidad : usuarios.numeroActivos
		}, {
			tipo : "Inactivos",
			cantidad : usuarios.numeroInactivos
		} ];

		/* CARGA DATOS ESTATUS */
		$("#datosUsuariosEstatus").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaDosColores",
					dataSource : datosUsuariosEstatus,
					series : [ {
						argumentField : "tipo",
						valueField : "cantidad",
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
					onPointClick : function(e) {
						var point = e.target;

						toggleVisibility(point);
					},
					onLegendClick : function(e) {
						var arg = e.target;

						toggleVisibility(this.getAllSeries()[0]
								.getPointsByArg(arg)[0]);
					}
				});

		/* DATOS POR ORDEN DE GOBIERNO */
		var datosUsuariosOrdenGobierno = [ {
			tipo : "Federal",
			cantidad : usuarios.ordenFederal
		}, {
			tipo : "Estatal",
			cantidad : usuarios.ordenEstatal
		}, {
			tipo : "Municipal",
			cantidad : usuarios.ordenMunicipal
		} ];

		/* CARGA DATOS POR ORDEN DE GOBIERNO */
		$("#datosUsuariosOrdenGobierno").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosUsuariosOrdenGobierno,
					series : [ {
						argumentField : "tipo",
						valueField : "cantidad",
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
					onPointClick : function(e) {
						var point = e.target;

						toggleVisibility(point);
					},
					onLegendClick : function(e) {
						var arg = e.target;

						toggleVisibility(this.getAllSeries()[0]
								.getPointsByArg(arg)[0]);
					}
				});

		/* DATOS POR PUESTO */
		var datosUsuariosPuesto = [ {
			tipo : usuarios.nombrePuesto1,
			cantidad : usuarios.cantidadPuesto1
		}, {
			tipo : usuarios.nombrePuesto2,
			cantidad : usuarios.cantidadPuesto2
		}, {
			tipo : usuarios.nombrePuesto3,
			cantidad : usuarios.cantidadPuesto3
		} ];

		/* CARGA DATOS POR ORDEN DE GOBIERNO */
		$("#datosUsuariosPuesto").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosUsuariosPuesto,
					series : [ {
						argumentField : "tipo",
						valueField : "cantidad",
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
					onPointClick : function(e) {
						var point = e.target;

						toggleVisibility(point);
					},
					onLegendClick : function(e) {
						var arg = e.target;

						toggleVisibility(this.getAllSeries()[0]
								.getPointsByArg(arg)[0]);
					}
				});

		/* GRAFICA BARRAS USUARIOS POR RANGO DE EDAD */
		/* DATOS POR PUESTO */
		var datosRangoEdades = [ {
			argumento : "18 a 29 años",
			valor : usuarios.rangoEdad18
		}, {
			argumento : "30 a 39 años",
			valor : usuarios.rangoEdad30
		}, {
			argumento : "40 a 49 años",
			valor : usuarios.rangoEdad40
		}, {
			argumento : "50 a 65 años",
			valor : usuarios.rangoEdad50
		} ];

		$("#rangoEdades").dxChart({
			dataSource : datosRangoEdades,
			barWidth : 0.5,
			series : {
				argumentField : "argumento",
				valueField : "valor",
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
			legend : {
				visible : false
			},
			size : {
				height : 200,
				width : 800
			},
			valueAxis : {
				title : {
					text : "Cantidad de usuarios"
				}
			},
		});

	}

});
