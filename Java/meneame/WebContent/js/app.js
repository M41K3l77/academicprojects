/**
 * Configuracion de rutas parciales para cargar en la pagina
 */
var meneameApp= angular.module('meneameApp', [
	'ngRoute',
	'angularCSS'
]).

config(['$routeProvider', '$httpProvider', function($routeProvider,$httpProvider) {
	$routeProvider.when('/home', {controller: 'homeCtrl', controllerAs: "hc",css: '../css/stylesheet.css' ,templateUrl: 'Home.html'});
	$routeProvider.when('/noticia/:noticeid', {controller: 'noticiaCtrl', controllerAs: "nc",css: '../css/stylesheet.css' ,templateUrl: 'Noticia.html'});
	$routeProvider.when('/login', {controller: 'loginCtrl', controllerAs: "lc",css: '../css/stylesheetloginregister.css',templateUrl: 'Login.html'});
	$routeProvider.when('/usernewslist', {controller: 'userNewsListCtrl', controllerAs: "ulc",css: '../css/stylesheet.css' ,templateUrl: 'NoticiaUserList.html'});
	$routeProvider.when('/usercommentslist', {controller: 'userCommentsListCtrl', controllerAs: "ucc",css: '../css/stylesheet.css' ,templateUrl: 'ComentariosUserList.html'});
	$routeProvider.when('/usersendnews', {controller: 'userSendNewsCtrl', controllerAs: "usnc",css: '../css/stylesheet.css' ,templateUrl: 'EnviarNoticia.html'});
	$routeProvider.when('/editcomment/:commentid', {controller: 'editcommentCtrl', controllerAs: "ecc",css: '../css/stylesheet.css' ,templateUrl: 'EditarComentario.html'});
	$routeProvider.when('/editnews/:newsid', {controller: 'editnewsCtrl', controllerAs: "enc",css: '../css/stylesheet.css' ,templateUrl: 'EditarNoticia.html'});
	$routeProvider.when('/usersendcomment/:noticeid', {controller: 'userSendCommentCtrl', controllerAs: "uscc",css: '../css/stylesheet.css' ,templateUrl: 'EnviarComentario.html'});
	$routeProvider.when('/userprofile', {controller: 'userprofileCtrl', controllerAs: "upc",css: '../css/stylesheetloginregister.css',templateUrl: 'UserProfile.html'});
	$routeProvider.when('/register', {controller: 'registerCtrl', controllerAs: "rc",css: '../css/stylesheetloginregister.css',templateUrl: 'Register.html'});
	$routeProvider.when('/error/:errormessage', {controller: 'errorCtrl', controllerAs: "ec",css: '../css/errorangular.css',templateUrl: 'Error.html'});
	$routeProvider.otherwise({redirectTo:'/home'});
	$httpProvider.useApplyAsync(true);
	//$locationProvider.html5Mode(true); 
}]);