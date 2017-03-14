/**
 * Controladores para el proyecto
 */
meneameApp.controller('homeCtrl', function(NewsFactory,UserFactory,CommentFactory,$route,$templateCache,$scope,$location) {
	var hc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();
	hc.mainnewslist=[];
	hc.newsmosthitted={};
	hc.newsmostliked={};
	hc.userconf={
			category:'todas',
			cambiacategoria:false,
			filtro:'fecha',
			actualpage: 0,
			offsetnews: 0
	}

	hc.funciones = {
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			goUsercommentslist : function (){
				$location.url('/usercommentslist');
			},
			goUsersendnews : function (){
				$location.url('/usersendnews');
			},
			obtenerMainNewsList : function(category,filtro,offsetnews){	
				
				NewsFactory.getNewsCategorySorted(category,filtro,offsetnews)
				.then(function(respuesta) {
					//
					
						auxlist=[];
						auxlist=angular.copy(hc.mainnewslist);
						
						hc.mainnewslist=[];
						if(hc.userconf.cambiacategoria==true){/*si ha habido cambio de categoria*/
							angular.forEach(respuesta.data,function(data){
								var notice={
										listitem:data,
										usernews:{},
										commentscount:{}
								};
								CommentFactory.getCommentCountByNews(data.id)
								.then(function(respuestanumcomments) {
									notice.commentscount=respuestanumcomments;
								}, function(reason) {
									console.log("Error: mumero de comentarios no obtenidos");
								});
								UserFactory.getUserById(data)
								.then(function(respuestauser) {
									notice.usernews=respuestauser;
								}, function(reason) {
									console.log("Error: usuario no obtenido");
								});
								hc.mainnewslist.push(notice);
							});
							hc.userconf.cambiacategoria=false;
						}else{/*no cambio de categoria, es paginacion*/
							if(respuesta.data.length>0){
								angular.forEach(respuesta.data,function(data){
									var notice={
											listitem:data,
											usernews:{},
											commentscount:{}
									};
									CommentFactory.getCommentCountByNews(data.id)
									.then(function(respuestanumcomments) {
										notice.commentscount=respuestanumcomments;
									}, function(reason) {
										console.log("Error: mumero de comentarios no obtenidos");
									});
									UserFactory.getUserById(data)
									.then(function(respuestauser) {
										notice.usernews=respuestauser;
									}, function(reason) {
										console.log("Error: usuario no obtenido");
									});
									hc.mainnewslist.push(notice);
								});							
							}else{/*la respuesta es vacia por lo que hay que volver a la pagina anterior*/
								if(hc.userconf.actualpage>0){/*si es la primera pagina no hay que volver para atras*/
									hc.userconf.actualpage=hc.userconf.actualpage-1;
									hc.userconf.offsetnews=hc.userconf.actualpage*10;
									hc.mainnewslist=auxlist;
								}
								
							}
						}
					
					
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticia no obtenidos");
				})
			},
			obtenerNewsMostHitted : function(){
				NewsFactory.getNewsMostHitted()
				.then(function(respuesta) {
					hc.newsmosthitted = respuesta;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de orden no obtenidos");
				})

			},
			obtenerNewsMostLiked : function(){
				NewsFactory.getNewsMostLiked()
				.then(function(respuesta) {
					hc.newsmostliked = respuesta;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de orden no obtenidos");
				})

			},
			seleccionarCategory : function(category){
				hc.userconf.category=category;
				hc.userconf.cambiacategoria=true,
				hc.userconf.filtro='fecha';
				hc.userconf.actualpage=0;
				hc.userconf.offsetnews=0;
				hc.funciones.obtenerMainNewsList(hc.userconf.category,hc.userconf.filtro,hc.userconf.offsetnews);
			},
			seleccionarPagina : function(actualpage){
				
				if(actualpage>=0){
					hc.userconf.actualpage=actualpage;
					hc.userconf.offsetnews=hc.userconf.actualpage*10;
					hc.funciones.obtenerMainNewsList(hc.userconf.category,hc.userconf.filtro,hc.userconf.offsetnews);
				}
			},
			filtrarnoticias : function(){
				hc.funciones.obtenerMainNewsList(hc.userconf.category,hc.userconf.filtro,hc.userconf.offsetnews);
			},
			menearnoticia : function(notice){
				news=angular.copy(notice);
				news.dateStamp=null;
				news.timeStamp=null;
				news.hits=notice.hits+1;
				NewsFactory.menear(news)
				.then(function(response) {
					if(response < 400){
						hc.funciones.obtenerMainNewsList(hc.userconf.category,hc.userconf.filtro,hc.userconf.offsetnews);
						hc.funciones.obtenerNewsMostHitted();
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}
				}, function(response) {
					console.log("error noticia meneada");
				})
			}
	}
	hc.funciones.obtenerMainNewsList(hc.userconf.category,hc.userconf.filtro,hc.userconf.offsetnews);
	hc.funciones.obtenerNewsMostHitted();
	hc.funciones.obtenerNewsMostLiked();
	
}).controller('loginCtrl', function($route,$scope,$location,UserFactory) {
	var lc = this;
	$scope.erroruser='';
	$scope.errorformatopassword='';
	$scope.errorformatonombre='';
	lc.funciones = {
			loginUser : function(){
				console.log("logueando");
				user ={
						name:'',
						password:''
				};
				user.name=$scope.user.name;
				user.password=$scope.user.password;
				$scope.erroruser='';
				$scope.errorformatopassword='';
				$scope.errorformatonombre='';
				pedirlogin=true;
				if(!$scope.user.name.match('[a-zA-Z][a-zA-Z0-9]{2,12}')){
					$scope.errorformatonombre='el nombre de almenos tres caracteres y empezar por una letra';
					pedirlogin=false;
				}
				if(!$scope.user.password.match('[a-zA-Z0-9_]{6,12}')){
					$scope.errorformatopassword='contraseña minimo 6 y máximo 12';
					pedirlogin=false;
				}
				if(pedirlogin){
					UserFactory.loginUser(user.name,user.password)
					.then(function(respuesta) {
						if( respuesta.ok == "true" ){
							$location.url('/home');
						}else{
							$scope.erroruser='usuario o contraseña incorrecta';						
							console.log("Usuario o constraseña incorrecto/a");

						}
					}, function(reason) {
						console.log("Error: peticion de login");
					});
				}
				
			}
	}
}).controller('headerCtrl', function($location,$scope,$route,UserFactory) {
	var headc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();
	headc.funciones = {
			goLogin : function (){
				$location.url('/login');
			},
			goRegister : function (){
				$location.url('/register');
			},
			goProfile : function (){
				$location.url('/userprofile');
			},
			logout : function (){
				UserFactory.logoutUser($scope.userloged.name)
				.then(function(response) {
					if(response < 400){							
						$location.url('/home');
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}
				}, function(response) {
					console.log("error logout");
				});
			}
	}
}).controller('noticiaCtrl', function(NewsFactory,CommentFactory,UserFactory,$routeParams,$location,$scope) {
	var nc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();
	nc.newsmosthitted={};
	nc.newsmostliked={};
	nc.notice={
			info:{},
			usernews:'',
			comments:[],
			commentscount:{}
	};
	nc.userconf={
			actualpage: 0,
			offsetcommets: 0
	}
	nc.voto='0';
	nc.funciones = {
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			goUsersendnews : function (){
				$location.url('/usersendnews');
			},
			obtenerNoticia : function(noticeid){
				NewsFactory.getNewsById(noticeid)
				.then(function(respuesta) {
					UserFactory.getUserById(respuesta)
					.then(function(respuestauser) {
						nc.notice.usernews=respuestauser;
					}, function(reason) {
						console.log("Error: usuario de noticia no obtenido");
					});
					nc.notice.info=respuesta;
					CommentFactory.getCommentCountByNews(nc.notice.info.id)
					.then(function(response) {
						nc.notice.commentscount=response;
					}, function(response) {
						console.log("Error recoger numero de comentarios de la noticia "+respuesta.status);
					});
					CommentFactory.getCommentsByNews(nc.notice.info,nc.userconf.offsetcommets)
					.then(function(comentarios) {
						// lista de comentarios
						nc.notice.comments=[];//comentarios;
						var i=0;
						angular.forEach(comentarios,function(comentario){
							var comment={
									infoc: comentario,
									ncomm: {},
									usercomment: ''
							};
							i++;
							comment.ncomm='#'+i;							
							UserFactory.getUserCommentById(comentario)
							.then(function(response) {
								comment.usercomment=response;								
								//nc.notice.comments.push(comment);
							}, function(response) {
								console.log("Error recoger usuario del comentarios de la noticia "+response.status);
							});
							nc.notice.comments.push(comment);
						});
						
					}, function(respuesta) {
						console.log("Error recoger lista comentarios de la noticia "+respuesta.status);
					});
				}, function(respuesta) {
					console.log("Error al recoger Noticia");
				});
			},
			obtenerNewsMostHitted : function(){
				NewsFactory.getNewsMostHitted()
				.then(function(respuesta) {
					nc.newsmosthitted = respuesta;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticias no obtenidos");
				})

			},
			obtenerNewsMostLiked : function(){
				NewsFactory.getNewsMostLiked()
				.then(function(respuesta) {
					nc.newsmostliked = respuesta;
					angular.forEach(respuesta,function(dato){
					});
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticias no obtenidos");
				})

			},
			seleccionarPagina : function(actualpage){
				auxlist=[];
				auxlist=angular.copy(nc.notice.comments);
				if(actualpage>=0){
					nc.userconf.actualpage=actualpage;
					nc.userconf.offsetcommets=nc.userconf.actualpage*10;
						CommentFactory.getCommentsByNews(nc.notice.info,nc.userconf.offsetcommets)
						.then(function(comentarios) {
							if(comentarios.length>0){
								// lista de comentarios
								nc.notice.comments=[];//comentarios;
								var i=0;
								angular.forEach(comentarios,function(comentario){
									var comment={
											infoc: comentario,
											ncomm: {},
											usercomment: ''
									};
									i++;
									comment.ncomm='#'+i;							
									UserFactory.getUserCommentById(comentario)
									.then(function(response) {
										comment.usercomment=response;								
									}, function(response) {
										console.log("Error recoger usuario del comentarios de la noticia "+response.status);
									});
									nc.notice.comments.push(comment);
								});
							}else{
								if(nc.userconf.actualpage>0){/*si es la primera pagina no hay que volver para atras*/
									nc.userconf.actualpage=nc.userconf.actualpage-1;
									nc.userconf.offsetnews=nc.userconf.actualpage*10;
									nc.notice.comments=auxlist;
								}
							}							
						}, function(respuesta) {
							console.log("Error recoger lista comentarios de la noticia");
						
						});
				}
			},
			menearnoticia : function(notice){
				news=angular.copy(notice);
				news.dateStamp=null;
				news.timeStamp=null;
				news.hits=notice.hits+1;
				NewsFactory.menear(news)
				.then(function(response) {
					if(response < 400){
						nc.funciones.obtenerNoticia($routeParams.noticeid);
						nc.funciones.obtenerNewsMostHitted();
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}					
				}, function(response) {
					console.log("error noticia meneada");
				})
			},
			votarComentario : function (comment){
				if((parseInt(nc.voto) > -4) && (parseInt(nc.voto) < 4)){
					var commentvote={
							id:comment.id,
							owner:comment.owner,
							news:comment.news,
							text:comment.text,
							likes:comment.likes
					};
					var sumalikes=parseInt(comment.likes)+parseInt(nc.voto);
					nc.voto='0';
					commentvote.dateStamp=null;
					commentvote.timeStamp=null;
					commentvote.likes=sumalikes;
					CommentFactory.votarComment(commentvote)
					.then(function(response) {
						if(response < 400){							
							nc.funciones.obtenerNoticia($routeParams.noticeid);
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}
						
					}, function(response) {
						console.log("Error editar comentario usuario");
					});
				}else{// no hay que trucar el voto ;)
					console.log("no hay que trucar el voto ;)");
					nc.voto='0';
				}
			},
			goUsersendcomment : function(notice){
				$location.url('/usersendcomment/'+notice.id);
			}
	}
	nc.funciones.obtenerNewsMostHitted();
	nc.funciones.obtenerNewsMostLiked();
	if (!($routeParams.noticeid==undefined)){
		nc.funciones.obtenerNoticia($routeParams.noticeid);
	}
}).controller('userNewsListCtrl', function(NewsFactory,UserFactory,CommentFactory,$route,$templateCache,$scope,$location) {
	var ulc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();

	var ownerid=UserFactory.getUserLoggedPrivado().id;
	ulc.usernewslist=[];
	ulc.newsmosthitted={};
	ulc.newsmostliked={};
	ulc.userconf={
			filtro:'fecha',
			actualpage: 0,
			offsetnews: 0
	}

	ulc.funciones = {
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			goUsercommentslist : function (){
				$location.url('/usercommentslist');
			},
			goUsersendnews : function (){
				$location.url('/usersendnews');
			},
			obtenerNewsMostHitted : function(){
				NewsFactory.getNewsMostHitted()
				.then(function(respuesta) {
					ulc.newsmosthitted = respuesta;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticias no obtenidos");
				})

			},
			obtenerNewsMostLiked : function(){
				NewsFactory.getNewsMostLiked()
				.then(function(respuesta) {
					ulc.newsmostliked = respuesta;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticias no obtenidos");
				})

			},
			obtenerUserNewsList :function(owner,filtro,offsetnews){
				NewsFactory.getAllNewsByOwnerSorted(owner, filtro, offsetnews)
				.then(function(response) {
					//
					if(response.status == 200){
						auxlist=[];
						auxlist=angular.copy(ulc.usernewslist);
						ulc.usernewslist=[];
						if(response.data.length>0){
							angular.forEach(response.data,function(data){
								var notice={
										listitem:data,
										usernews:$scope.userloged.name,
										commentscount:{}
								};
								CommentFactory.getCommentCountByNews(data.id)
								.then(function(respuestanumcomments) {
									notice.commentscount=respuestanumcomments;
								}, function(reason) {
									console.log("Error: mumero de comentarios no obtenidos");
								});
								ulc.usernewslist.push(notice);
							});						
						}else{/*la respuesta es vacia por lo que hay que volver a la pagina anterior*/
							if(ulc.userconf.actualpage>0){/*si es la primera pagina no hay que volver para atras*/
								ulc.userconf.actualpage=ulc.userconf.actualpage-1;
								ulc.userconf.offsetnews=ulc.userconf.actualpage*10;
								ulc.usernewslist=auxlist;
							}						
						}
					}else if(response.status != 200 && response.status < 400){							
						console.log("problemas para cargar las noticias");
					}else{
						errormessage=response.status;
						$location.url('/error/'+errormessage);
					}
				}, function(response) {
					console.log("Error: datos de noticias de usuario no obtenidos");
				});
			},
			filtrarnoticias : function(){
				ulc.funciones.obtenerUserNewsList(ownerid,ulc.userconf.filtro,ulc.userconf.offsetnews);
			},
			seleccionarPagina : function(actualpage){
				if(actualpage>=0){
					ulc.userconf.actualpage=actualpage;
					ulc.userconf.offsetnews=ulc.userconf.actualpage*10;
					ulc.funciones.obtenerUserNewsList(ownerid,ulc.userconf.filtro,ulc.userconf.offsetnews);
				}
			},
			menearnoticia : function(notice){
				news=angular.copy(notice);
				news.dateStamp=null;
				news.timeStamp=null;
				news.hits=notice.hits+1;
				NewsFactory.menear(news)
				.then(function(response) {
					if(response < 400){
						ulc.funciones.obtenerUserNewsList(ownerid,ulc.userconf.filtro,ulc.userconf.offsetnews);
						ulc.funciones.obtenerNewsMostHitted();
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}
					
				}, function(response) {
					console.log("error noticia meneada");
				})
			},
			editarNoticia : function(newsid){
				$location.url('/editnews/'+newsid);
			},
			borrarNoticia : function(news){
				NewsFactory.eliminarNoticia(news)
				.then(function(response) {
					if(response < 400){							
						ulc.funciones.obtenerUserNewsList(ownerid,ulc.userconf.filtro,ulc.userconf.offsetnews);
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}					
				}, function(response) {
					console.log("Error borrado noticia");
				});
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}else{
		ulc.funciones.obtenerUserNewsList(ownerid,ulc.userconf.filtro,ulc.userconf.offsetnews);
		ulc.funciones.obtenerNewsMostHitted();
		ulc.funciones.obtenerNewsMostLiked();
	}
	/** */
	
}).controller('userCommentsListCtrl', function(NewsFactory,UserFactory,CommentFactory,$route,$templateCache,$scope,$location) {
	var ucc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();

	var ownerid=UserFactory.getUserLoggedPrivado().id;
	ucc.usercommentslist=[];
	ucc.newsmosthitted={};
	ucc.newsmostliked={};
	ucc.userconf={
			actualpage: 0,
			offsetcomments: 0
	}
	ucc.funciones = {
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			borrarComentario : function(comment){
				CommentFactory.eliminarComentario(comment)
				.then(function(response) {
					if(response < 400){							
						ucc.funciones.obtenerCommentsByUser(ownerid,ucc.userconf.offsetcomments);
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}					
				}, function(response) {
					console.log("Error borrado noticia");
				});
			},
			editarComentario : function(commentid){
				$location.url('/editcomment/'+commentid);
			},
			obtenerNewsMostHitted : function(){
				NewsFactory.getNewsMostHitted()
				.then(function(respuesta) {
					ucc.newsmosthitted = respuesta;
					
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticias no obtenidos");
				});
			},
			obtenerNewsMostLiked : function(){
				NewsFactory.getNewsMostLiked()
				.then(function(respuesta) {
					ucc.newsmostliked = respuesta;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("Error: datos de noticias no obtenidos");
				});
			},
			obtenerCommentsByUser : function(owner,offsetcomments){
				CommentFactory.getCommentsByUser(owner,offsetcomments)
				.then(function(response) {
					//
					if(response.status == 200){
						auxlist=[];
						auxlist=angular.copy(ucc.usercommentslist);
						ucc.usercommentslist=[];
						if(response.data.length>0){
							angular.forEach(response.data,function(data){
								var comentario={
										notice:{
											noticeObj:{},
											usernews:{},
											commentscount:{}
										},
										comment:{
											commentObj:data,
											usercomment:$scope.userloged.name
										}
								};
								NewsFactory.getNewsById(data.news)
								.then(function(response) {
									comentario.notice.noticeObj=response;
									CommentFactory.getCommentCountByNews(comentario.notice.noticeObj.id)
									.then(function(respuestanumcomments) {
										comentario.notice.commentscount=respuestanumcomments;
									}, function(reason) {
										console.log("Error: mumero de comentarios no obtenidos");
									});
									UserFactory.getUserById(comentario.notice.noticeObj)
									.then(function(respuestauser) {
										comentario.notice.usernews=respuestauser;
									}, function(reason) {
										console.log("Error: usuario no obtenido");
									});
									
								}, function(response) {
									console.log("Error: datos de noticia no obtenido");
								});						
								ucc.usercommentslist.push(comentario);
							});
						}else{
							if(ucc.userconf.actualpage>0){/*si es la primera pagina no hay que volver para atras*/
								ucc.userconf.actualpage=ucc.userconf.actualpage-1;
								ucc.userconf.offsetcomments=ucc.userconf.actualpage*10;
								ucc.usercommentslist=auxlist;
							}
						}
					}else if(response.status != 200 && response.status < 400){							
						console.log("problemas para cargar los comentarios del usuario");
					}else{
						errormessage=response.status;
						$location.url('/error/'+errormessage);
					}
					//
					
					
				}, function(response) {
					console.log("Error: datos de comentarios no obtenidos");
				});
			},
			seleccionarPagina : function(actualpage){
				if(actualpage>=0){
					ucc.userconf.actualpage=actualpage;
					ucc.userconf.offsetcomments=ucc.userconf.actualpage*10;
					ucc.funciones.obtenerCommentsByUser(ownerid,ucc.userconf.offsetcomments);
				}
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}else{
		ucc.funciones.obtenerNewsMostHitted();
		ucc.funciones.obtenerNewsMostLiked();
		ucc.funciones.obtenerCommentsByUser(ownerid,ucc.userconf.offsetcomments);
	}
}).controller('userSendNewsCtrl', function(NewsFactory,UserFactory,$route,$scope,$location) {
	var usnc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();
	ownerid=UserFactory.getUserLoggedPrivado().id;
	$scope.news={
			url:'',
			title:'',
			text:'',
			category:'actualidad'
	}
	
	$scope.errorurl='';
	$scope.errortitulo='';
	$scope.errordescripcion='';
	$scope.errorcategory='';
	usnc.funciones={
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			sendNews : function (){
				//
				$scope.errorurl='';
				$scope.errortitulo='';
				$scope.errordescripcion='';
				$scope.errorcategory='';
				pedirsendnews=true;
				if(!$scope.news.url.match('^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*')){
					$scope.errorurl='url mal formada';
					pedirsendnews=false;
				}			
				if(!($scope.news.title.length > 0 &&  $scope.news.title.length <= 150)){
					$scope.errortitulo='error longitud titulo';
					pedirsendnews=false;
				}			
				if(!($scope.news.text.length > 0 && $scope.news.text.length <= 400)){
					console.log("longitud descripcion: "+$scope.news.text);
					$scope.errordescripcion='error longitud descripcion';
					pedirsendnews=false;
				}
				if(!$scope.news.category.match('(ocio)|(actualidad)|(cultura)|(deporte)|(tecnologia)')){
					$scope.errorcategory='la categoria no existe';
					pedirsendnews=false;
				}
				if(pedirsendnews){
					var news={
							owner:ownerid,
							url:'',
							title:'',
							text:'',
							category:'',
							dateStamp:'',
							timeStamp:''
					};
					news.url=$scope.news.url;
					news.title=$scope.news.title;
					news.text=$scope.news.text;
					news.category=$scope.news.category;
					news.dateStamp=null;
					news.timeStamp=null;
					NewsFactory.postUserNews(news)
					.then(function(response) {
						if(response < 400){							
							$location.url('/usernewslist');
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}
					}, function(response) {
						console.log("Error: no se pudo realizar el post de la noticia del usuario");						
					});
				}
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}
	/** */
}).controller('editnewsCtrl', function(NewsFactory,UserFactory,$route,$scope,$location,$routeParams) {
	var enc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();

	var ownerid=UserFactory.getUserLoggedPrivado().id;
	enc.news={
			id:'',
			owner:'',
			url:'',
			title:'',
			text:'',
			category:'',
			dateStamp:'',
			timeStamp:''
	};
	$scope.errorurl='';
	$scope.errortitulo='';
	$scope.errordescripcion='';
	$scope.errorcategory='';
	
	enc.funciones={
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			obtenerNoticia : function(noticeid){
				NewsFactory.getNewsById(noticeid)
				.then(function(response) {
					if(ownerid == response.owner){
						enc.news.id=response.id;
						enc.news.owner=ownerid;
						enc.news.url=response.url;
						enc.news.title=response.title;
						enc.news.text=response.text;
						enc.news.category=response.category;	
					}
				}, function(response) {
					console.log("Error: recuperar noticia usuario");
				});
			},
			editnews : function(){
				$scope.errorurl='';
				$scope.errortitulo='';
				$scope.errordescripcion='';
				$scope.errorcategory='';
				pedirupdatenews=true;
				if(!enc.news.url.match('^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*')){
					$scope.errorurl='url mal formada';
					pedirupdatenews=false;
				}			
				if(!(enc.news.title.length > 0 &&  enc.news.title.length <= 150)){
					$scope.errortitulo="error longitud titulo";
					pedirupdatenews=false;
				}			
				if(!(enc.news.text.length > 0 && enc.news.text.length <= 400)){
					$scope.errordescripcion="error longitud descripcion";
					pedirupdatenews=false;
				}
				if(!enc.news.category.match('(ocio)|(actualidad)|(cultura)|(deporte)|(tecnologia)')){
					$scope.errorcategory="la categoria no existe";
					pedirupdatenews=false;
				}
				if(pedirupdatenews){
					enc.news.dateStamp=null;
					enc.news.timeStamp=null;
					
					NewsFactory.putUserNews(enc.news)
					.then(function(response) {						
						if(response < 400){							
							$location.url('/usernewslist');
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}
					}, function(response) {
						console.log("Error editar noticia usuario");
						/*UserFactory.setNoUserLogged();*/
						$location.url('/home');
					});
				}
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}
	/** */
	if (!($routeParams.newsid==undefined)){
		enc.funciones.obtenerNoticia($routeParams.newsid);
	}else{
		$location.url('/usernewslist');
	}
}).controller('userSendCommentCtrl', function(NewsFactory,UserFactory,CommentFactory,$route,$scope,$location,$routeParams) {
	var uscc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();

	var ownerid=UserFactory.getUserLoggedPrivado().id;
	$scope.comment={
			text:''
	}
	$scope.errordescripcion='';
	
	uscc.funciones={
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			goUsersendnews : function (){
				$location.url('/usersendnews');
			},
			sendComment : function (){
				//
				$scope.errordescripcion='';
				pedirsendcoment=true;			
				if(!($scope.comment.text.length > 0 && $scope.comment.text.length <= 400)){
					$scope.errordescripcion='error longitud descripcion';
					pedirsendcoment=false;
				}
				if(pedirsendcoment){
					var comment={
							owner:ownerid,
							news:$routeParams.noticeid,
							text:'',
							dateStamp:'',
							timeStamp:''
					};
					comment.text=$scope.comment.text;
					comment.dateStamp=null;
					comment.timeStamp=null;
					CommentFactory.postUserComment(comment)
					.then(function(response) {
						if(response < 400){							
							$location.url('/noticia/'+$routeParams.noticeid);
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}
					}, function(response) {
						console.log("Error: no se pudo realizar el post del comentario del usuario");
					});
				}				
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}
	/** */
	if (($routeParams.noticeid==undefined)){
		$location.url('/home');
	}
}).controller('userprofileCtrl', function(UserFactory,$route,$scope,$location) {
	var upc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	
	$scope.userloged=UserFactory.getUserLoggedPublico();

	var ownerid=UserFactory.getUserLoggedPrivado().id;
	upc.ownerpassword=UserFactory.getUserLoggedPrivado().password;
	upc.owneremail=UserFactory.getUserLoggedPrivado().email;
	$scope.user={
			email:upc.owneremail,
			password1:upc.ownerpassword,
			password2:upc.ownerpassword
	}
	$scope.errorpassword='';
	$scope.errorformatoemail='';
	$scope.errorformatopassword='';
	upc.funciones={
			updateUserProfile : function(){
				//
				$scope.errorpassword='';
				$scope.errorformatoemail='';
				$scope.errorformatopassword='';
				actualizaruser=true;
				if($scope.user.password1 != $scope.user.password2){
					// error en confirmacion de la contraseña
					$scope.errorpassword='error al confirmar contraseña';
					actualizaruser=false;
				}			
				if(!$scope.user.email.match('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')){
					$scope.errorformatoemail="email no reconicido";
					actualizaruser=false;
				}
				if(!$scope.user.password1.match('[a-zA-Z0-9_]{6,12}') || !$scope.user.password2.match('[a-zA-Z0-9_]{6,12}')){
					$scope.errorformatopassword="contraseña minimo 6 y máximo 12";
					actualizaruser=false;
				}
				if(actualizaruser){
					var user={
							id:ownerid,
							name:$scope.userloged.name,
							email:$scope.user.email,
							password:$scope.user.password1
					};
					UserFactory.actualizarUser(user)
					.then(function(response) {
						if(response < 400){							
							UserFactory.setUserEmailPassword($scope.user.email,$scope.user.password1);
							$location.url('/home');
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}						
					}, function(response) {
						console.log("Error: no se pudo actualizar el usuario");
					});
				}				
			},
			darBajaUserProfile : function (){				
				// desloguear y eliminar usuario y su sesion en el servidor
				UserFactory.darDebajaUser(ownerid)
				.then(function(response) {
					if(response < 400){							
						UserFactory.setNoUserLogged();
						$location.url('/home');
					}else{
						errormessage=response;
						$location.url('/error/'+errormessage);
					}
				}, function(response) {
					console.log("Error: al darse de baja");
				});
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}
	/** */
}).controller('editcommentCtrl', function(UserFactory,CommentFactory,$route,$scope,$location,$routeParams) {
	var ecc = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();
	ownerid=UserFactory.getUserLoggedPrivado().id;
	ecc.comment={
			id:-1,
			owner:-1,
			news:-1,
			dateStamp:null,
			timeStamp:null,
			text:'',
			likes:-1
	};
	$scope.errordescripcion='';
	ecc.funciones={
			goUsernewslist : function (){
				$location.url('/usernewslist');
			},
			goUsersendnews : function (){
				$location.url('/usersendnews');
			},
			obtenerComentario : function (commentid){
				CommentFactory.getCommentUserByCommentid(commentid)
				.then(function(response) {
						ecc.comment.id=response.data.id;
						ecc.comment.owner=response.data.owner;
						ecc.comment.news=response.data.news;
						ecc.comment.text=response.data.text;
						ecc.comment.likes=response.data.likes;
				}, function(response) {
					console.log("Error: recuperar noticia usuario");
				});
			},
			updateComentario : function(){
				//
				$scope.errordescripcion='';
				pedirupdatecoment=true;			
				if(!(ecc.comment.text.length > 0 && ecc.comment.text.length <= 400)){
					$scope.errordescripcion='error longitud descripcion';
					pedirupdatecoment=false;
				}
				if(pedirupdatecoment){
					CommentFactory.putUserComment(ecc.comment)
					.then(function(response) {
						if(response < 400){							
							$location.url('/usercommentslist');
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}
						
					}, function(response) {
						console.log("Error editar comentario usuario, usuario no logueado en el servidor");
						UserFactory.setNoUserLogged();
						$location.url('/home');
					});
				}
			}
	}
	/**
	 * controlar f5 (ya que no se usan cookies o storage para el usuario) y si intentamos acceder a una ruta con acceso restringido
	 */
	if($scope.userloged.visible == false){
		$location.url('/home');
	}
	/** */
	if (!($routeParams.commentid==undefined)){
		ecc.funciones.obtenerComentario($routeParams.commentid);
	}else{
		$location.url('/usercommentslist');
	}
}).controller('registerCtrl', function(UserFactory,$scope,$location) {
	var rc = this;
	$scope.errorpassword='';
	$scope.errorformatonombre='';
	$scope.errorformatoemail='';
	$scope.errorformatopassword='';
	rc.funciones={
			registerUser : function(){				
				var user={
						id:-1,
						name:$scope.user.name,
						email:$scope.user.email,
						password:$scope.user.password1
				}
				//
				$scope.errorpassword='';
				$scope.errorformatonombre='';
				$scope.errorformatoemail='';
				$scope.errorformatopassword='';
				pedirregistro=true;
				if($scope.user.password1 != $scope.user.password2){
					// error en confirmacion de la contraseña
					$scope.errorpassword='error al confirmar contraseña';
					pedirregistro=false;
				}			
				if(!$scope.user.name.match('[a-zA-Z][a-zA-Z0-9]{2,12}')){
					$scope.errorformatonombre="el nombre de almenos tres caracteres y empezar por una letra";
					pedirregistro=false;
				}			
				if(!$scope.user.email.match('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')){
					$scope.errorformatoemail="email no reconicido";
					pedirregistro=false;
				}
				if(!$scope.user.password1.match('[a-zA-Z0-9_]{6,12}') || !$scope.user.password2.match('[a-zA-Z0-9_]{6,12}')){
					$scope.errorformatopassword="contraseña minimo 6 y máximo 12";
					pedirregistro=false;
				}
				//
				if(pedirregistro){
					UserFactory.postRegisterUser(user)
					.then(function(response) {
						if(response == 201){
							$location.url('/home');
						}else if(response != 201 && response < 400){							
							console.log("no se pudo realizar el registro");
						}else{
							errormessage=response;
							$location.url('/error/'+errormessage);
						}
					}, function(response) {
						errormessage=response.status;
						$location.url('/error/'+errormessage);
					});
				}
			}
	}
}).controller('errorCtrl', function(NewsFactory,UserFactory,CommentFactory,$scope,$routeParams) {
	var ec = this;
	$scope.userloged={
			name:'',
			visible:false
	}
	$scope.userloged=UserFactory.getUserLoggedPublico();
	$scope.errormessage='';
	if (!($routeParams.errormessage==undefined)){
		var numberstring = $routeParams.errormessage.toString();
		var nuevostring='';
		for (i = 0; i < numberstring.length; i++) {
			if(i == numberstring.length-1){
				nuevostring=nuevostring+numberstring[i];
			}else{
				nuevostring=nuevostring+numberstring[i]+' ';
			}
		}
		$scope.errormessage=nuevostring;		
	}else{
		$scope.errormessage='error indefinido';
	}
});
