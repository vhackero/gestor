/**
 * Application AngularJS
 * Gestor Web Zona Privada
 * @author Pablo César Sánchez Porta 
 */

var app = angular.module('sisiGestorWeb',[]);
app.controller('mensajesControlador', function($scope, $interval, $timeout, mensajesREST){
	//Actualización de número de mensajes
	function obtenerNumeroMensajes(){
		mensajesREST.numero.then(function(numero){
			$scope.contadorMensajes = numero.data.numeroMensajes;
			$('.badge').removeClass('hide');
		});
	}
	$timeout(function(){ obtenerNumeroMensajes(); }, 500);
	$interval(function(){ obtenerNumeroMensajes();}, 30000);
	
	//LLenado panel de mensajes
	mensajesREST.mensajes.then(function(mensajes){
		//console.log(mensajes.data);
		$scope.mensajes = mensajes.data;
	});
	
});
app.factory('mensajesREST', function ($http, $q, $interval, $timeout){
	var url = '/gestorweb/ws/mensajes/';
	var numero = $q.defer();
	var mensajes = $q.defer();
	var actualizaNumero = function(){
		$http({method:'GET', url:url+'numero/'+userId, json:true})
		.then(function(nmr){
			numero.resolve({data:nmr.data});
		});
	};
	$timeout(function(){ actualizaNumero();}, 100);
	$interval(function(){ actualizaNumero();},30000);
	$http({method:'GET', url:url+'primeros/'+userId, json:true})
		.then(function(msj){
			mensajes.resolve({data:msj.data})
		});
	return {
		numero: numero.promise,
		mensajes: mensajes.promise 
	}
});
/**
 * Filtros
 */
app.filter('htmlATexto', function(){
	return function(text){
		return text ? String(text).replace(/<[^>]+>/gm, ' ') : '';
	}
});