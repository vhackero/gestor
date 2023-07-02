$(function() {
	
	cargaGraficasProgramas = function(xhr, status, args) {
		var estatusFinal = parseInt(args.progsFinal);
		var estatusBloq = parseInt(args.progsBloq);
		var totalPrograma = parseInt(args.progsTotal);
		var estrategicos = parseInt(args.progsEst);
		var gestion = parseInt(args.progsGest);
		var insti = parseInt(args.progsInst);
		var reg = parseInt(args.progsReg);
		var prod = parseInt(args.progsProd);
		var estruc = parseInt(args.progsEstr);
		var context = parseInt(args.progsContext); 
		var linea = parseInt(args.progsLinea); 
		var presencial = parseInt(args.progsPresencial); 
		var actualizacion = parseInt(args.progsAct); 
		var induccion = parseInt(args.progsInd); 
		var formacion = parseInt(args.progsForm); 
		var basico = parseInt(args.progsBasico); 
		var intermedio = parseInt(args.progsIntermedio); 
		var avanzado = parseInt(args.progsAvanzado); 
		var curso = parseInt(args.progsCurso); 
		var taller = parseInt(args.progsTaller); 
		var seminario = parseInt(args.progsSeminario); 
		
		
		//Colocar el total de programas 
		var tdate = document.getElementById('form:totalProgramas');
		tdate.innerHTML = totalPrograma;
		
		//Total de programas estrategicos
		var tdate = document.getElementById('form:totalEstrategicos');
		tdate.innerHTML = estrategicos;
		
		//Total de programas de gestión
		var tdate = document.getElementById('form:totalGestion');
		tdate.innerHTML = gestion;

		/* Paleta de colores */
		var tresColores = [ 'rgb(84, 143, 203)', 'rgb(0, 0, 0)',
				'rgb(199, 198, 198)' ];
		var dosColores = [ 'rgb(84, 143, 203)', 'rgb(199, 198, 198)' ];
		DevExpress.viz.registerPalette('paletaTresColores', tresColores);
		DevExpress.viz.registerPalette('paletaDosColores', dosColores);

		/* Programas de tipo estrategico */
		var datosProgTipoEstrategico = [ {
			estatus : "Institucionales",
			cantidad : insti
		}, {
			estatus : "Regulatorias",
			cantidad : reg
		}, {
			estatus : "Productos y/o Servicios",
			cantidad : prod
		} ];

		/* Programas de tipo de Gestión */
		var datosProgTipoDeGestion = [ {
			estatus : "Procesos y estructura",
			cantidad : estruc
		}, {
			estatus : "Contexto humano",
			cantidad : context
		} ];

		/* Programas por área responsable */
		var datosProgAreaResponsable = [ {
			estatus : "DGAIP",
			cantidad : estatusFinal
		}, {
			estatus : "DGAAE",
			cantidad : estatusFinal
		}, {
			estatus : "DGAE",
			cantidad : estatusFinal
		} ];

		/* Programas por tipo de evento de capacitación */
		var datosProgTipoEventoCapacitacion = [ {
			estatus : "CURSO",
			cantidad : curso
		}, {
			estatus : "CURSO TALLER",
			cantidad : taller
		}, {
			estatus : "TALLER",
			cantidad : seminario
		} ];
		
		/* Nivel de conocimiento */
		var datosNivelConocimiento = [ {
			estatus : "INTRODUCTORIO",
			cantidad : basico
		}, {
			estatus : "INTERMEDIO",
			cantidad : intermedio
		}, {
			estatus : "AVANZADO",
			cantidad : avanzado
		} ];

		/* Nivel de enseñanza */
		var datosNivelEnsenanza = [ {
			estatus : "ACTUALIZACIÓN",
			cantidad : actualizacion
		}, {
			estatus : "INDUCCIÓN",
			cantidad : induccion
		}, {
			estatus : "FORMACIÓN",
			cantidad : formacion
		} ];
		
		/* Modalidad */
		var datosModalidad = [ {
			estatus : "EN LÍNEA",
			cantidad : linea
		}, {
			estatus : "PRESENCIAL",
			cantidad : presencial
		} ];
		
		/* Estatus */
		var datosEstatus = [ {
			estatus : "FINAL",
			cantidad : estatusFinal
		}, {
			estatus : "BLOQUEADO",
			cantidad : estatusBloq
		} ];
		
		
		
		
		
		/* Programas de tipo estrategico */
		$("#progTipoEstrategico").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosProgTipoEstrategico,
					series : [ {
						argumentField : "estatus",
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

		/* Programas de tipo de Gestión */
		$("#progTipoDeGestion").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaDosColores",
					dataSource : datosProgTipoDeGestion,
					series : [ {
						argumentField : "estatus",
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

		/* Programas por área responsable */
		$("#progAreaResponsable").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosProgAreaResponsable,
					series : [ {
						argumentField : "estatus",
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

		/* Programas por tipo de evento de capacitación */
		$("#progTipoEventoCapacitacion").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosProgTipoEventoCapacitacion,
					series : [ {
						argumentField : "estatus",
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
		
		/* Nivel de conocimiento */
		$("#nivelDeConocimiento").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosNivelConocimiento,
					series : [ {
						argumentField : "estatus",
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
		/* Nivel de enseñanza */
		$("#nivelDeEnsenanza").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaTresColores",
					dataSource : datosNivelEnsenanza,
					series : [ {
						argumentField : "estatus",
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
		
		/*Modalidad*/
		$("#modalidad").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaDosColores",
					dataSource : datosModalidad,
					series : [ {
						argumentField : "estatus",
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
		/*Estatus*/
		$("#estatus").dxPieChart(
				{
					margin : {
						top : 0,
						bottom : 0,
						left : 0,
						right : 0
					},
					diameter : .5,
					palette : "paletaDosColores",
					dataSource : datosEstatus,
					series : [ {
						argumentField : "estatus",
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

		function toggleVisibility(item) {
			if (item.isVisible()) {
				item.hide();
			} else {
				item.show();
			}
		}
		
		
		
	}
	ajustaColorLabel = function(){
		var rect = $('rect');
		$.each(rect,function(i,val){
			if($(this).attr('fill') == 'rgb(199, 198, 198)'){
				
				if($(this).next().css('fill') != 'rgb(118, 118, 118)'){
					$(this).next().css('fill','black');
				}	
				
			}
		});
		
		
//		if($('rect').prop('fill') == 'rgb(199, 198, 198)'){
//			console.log("si entre");
//			//$('rect').next().css('fill','black');
//		}
		
	}
	
});

