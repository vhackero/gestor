/**
 *  Application sisiGestorWeb AngularJS
 *  @author Pablo César Sánchez Porta
 *  Módulo Página principal
 *  _______________________________________________
 *  casosExitoControlador: controlador que obtiene y despliega elementos del slider principal,
 *  consume una estructura de datos con las siguientes características:
 *  JSON => [
 *  			{
 * 					 "id": Int, 					// Identificador del Caso
 * 					 "href": String, 				// URL de página del enlace
 * 			   		 "target":"_blank | _self ", 	// Ventana objetivo del enlace
 * 					 "titulo": String, 				// Título del Caso
 * 					 "subtitulo": String, 			// Subtítulo del Caso
 * 					 "img":String					// URL de la imagen	
 *  			},
 *  			...
 *  		]
 *  _______________________________________________
 *  cursosPublicoGeneral: Carga elementos para mostrar en el catálogo de cursos, 
 *  consume estructura de datos y filtra los cursos
 *  JSON => [
 *  			{
 *					"id": Int, 
 *					"imagen" : String,
 *					"nombreCurso": String,			// Nombre del Curso
 *					"creadoPor": String,			// Nombre del creador del curso
 *					"publicado": String,			// Fecha de pubicación ej. "YYYY-mm-dd",
 *					"descripcion": String,			// Descripción del curso
 *					"areaConocimiento": Srting,		// Área de conocimiento 
 *					"nivel": String,				// Nivel del curso
 *					"valoracionCurso": Int,			// Valoración del curso (1-5)
 *					"duracion": String,				// Duracion del curso 
 *					"impartidoPor": String,			// Nombre del instructor
 *					"nuevo": boolean,				// Marcar como curso nuevo
 *					"proximanente": boolean			// Maarcar como proximamente
 *				},
 *				...
 *			]
 */
angular.module('sisiGestorWeb', ['ui.bootstrap', 'ngAnimate']).
	controller('casosExitoControlador', ['$scope', '$http', function($scope, $http){
		$scope.intervalo = 10000;
		$scope.active = 0;
		$scope.transition = false;
		$scope.wrapSlides = false;
		$scope.casos = null;
		$http.get('/gestorweb/javax.faces.resource/angularjs/data/casosExito.json.xhtml')
			.success(function(data){
				$scope.casos = data;
			});
	}]).
	filter('rango', function(){
		return function (input, total){
			total = parseInt(total);
			for (var i = 0; i<=total; i++) {
				input.push(i);
			}
			return input;
		}
	}).
	controller('cursosPublicoGeneral', ['$scope', '$http', function($scope, $http){
		$scope.cursos = null;
		$http.get('/gestorweb/javax.faces.resource/angularjs/data/cursosPublicoGeneral.json.xhtml')
			.success(function(data){
				$scope.cursos = data;
			});
	}]);