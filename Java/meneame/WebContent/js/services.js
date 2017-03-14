/**
 * Factories News
 */
meneameApp.factory("NewsFactory", function($http,$location, CommentFactory){
	
	var url;
	if($location.host()=='localhost'){
		url = 'https://localhost:8443/meneame/rest/news/';
	}else{
		url = $location.protocol()+'://'+$location.host()+'/rest/news/';
	}
	
	var interfaz = {

			getNewsCategorySorted : function(category,filtro,offsetnews){
				var urlNews = url+'allnewsbycategorysorted?category='+category+'&filtro='+filtro+'&offsetnews='+offsetnews;
				return $http.get(urlNews)
				.then(function(response){
					return response;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("then Error getNewsCategorySorted: "+respuesta);
					return respuesta;
					});
			},
			getNewsMostHitted : function(){
				var urlNews = url+'newshittedorliked?mosthittedorliked=hits';
				return $http.get(urlNews)
				.then(function(response){
					return response.data;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("then Error getNewsMostHitted: "+respuesta.status);					
					return respuesta.status;
					});
			},
			getNewsMostLiked : function(){
				var urlNews = url+'newshittedorliked?mosthittedorliked=likes';
				return $http.get(urlNews)
				.then(function(response){
					return response.data;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("then Error getNewsMostLiked: "+respuesta.status);					
					return respuesta.status;
					});
			},
			menear : function(notice){
				var urlmenear = url+notice.id+"/menearnoticia";
				return $http.put(urlmenear, notice)
                .then(function(response){
    				 return response.status;
				 }, function(respuesta) {
						console.log("then Error menear: "+respuesta.status);
						return respuesta.status;
				});
			},
			getNewsById : function(noticeid){
				var urlNotice = url+noticeid;
				return $http.get(urlNotice)
				.then(function(response) {
					return response.data;
				}, function(respuesta) {
					console.log("then Error getNewsById: "+respuesta.status);					
					return respuesta.status;
				});
			},
			getAllNewsByOwnerSorted : function(owner,filtro,offsetnews){
				var urlNews = url+"newsrestricted/allnewsbyownersorted/"+owner+"?filtro="+filtro+"&offsetnews="+offsetnews;
				return $http.get(urlNews)
				.then(function(response) {
					return response;
				}, function(respuesta) {
					console.log("Error getAllNewsByOwnerSorted: "+respuesta.status);
					return respuesta;
				});
			},
			postUserNews : function(news){
				var urlNuevaNews = url+'newsrestricted/';
				return $http.post(urlNuevaNews,news)
				.then(function(response) {
					return response.status;
				}, function(respuesta) {
					console.log("Error postUserNews: "+respuesta.status);
					return respuesta.status;
				});
			},
			putUserNews : function(news){
				var urlactualizar = url+'newsrestricted/'+news.id;
				return $http.put(urlactualizar, news)
                .then(function(response){
    				 return response.status;
				 }, function(respuesta) {
						console.log("Error putUserNews: "+respuesta.status);
						return respuesta.status;
				});
			},
			eliminarNoticia : function(news){
				var urlNews = url+'newsrestricted/'+news.id;
                return $http.delete(urlNews)
                .then(function(response){
     				 return response.status;
 				 }, function(respuesta) {
						console.log("Error eliminarNoticia: "+respuesta.status);
						return respuesta.status;
				});
              }
	}
	return interfaz;
}).factory("CommentFactory", function($http,$location) {	
	
	var url;
	if($location.host()=='localhost'){
		url = 'https://localhost:8443/meneame/rest/comments/';
	}else{
		url = $location.protocol()+'://'+$location.host()+'/rest/comments/';
	}
	
	var interfaz = {
			getCommentCountByNews : function(newsid){
				var urlComment = url+'commentcount/'+newsid;
				return $http.get(urlComment)
				.then(function(response){
					return response.data;
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("then Error getCommentCountByNews: "+respuesta.status);					
					return respuesta.status;
					});
			},
			getCommentsByNews : function(news,offsetcommets){
				var urlComments = url+'notice/'+news.id+'?offsetcomments='+offsetcommets;
				return $http.get(urlComments)
				.then(function(response) {
					return response.data;
				}, function(respuesta) {
					console.log("Error lista comentarios: "+respuesta.status);					
					return respuesta.status;
				});
			},
			getCommentsByUser : function(commentuser,offsetcomments){
				var urlComments = url+'newsrestricted/user/'+commentuser+'?offsetcomments='+offsetcomments;
				return $http.get(urlComments)
				.then(function(response) {
					return response;
				}, function(respuesta) {
					console.log("Error lista comentarios: "+respuesta.status);					
					return respuesta;
				});
			},
			postUserComment : function(comment){
				var urlNuevocomentario = url+'newsrestricted/';
				return $http.post(urlNuevocomentario,comment)
				.then(function(response) {
					return response.status;
				}, function(respuesta) {
					console.log("Error postUserComment: "+respuesta.status);
					return respuesta.status;
				});
			},
			putUserComment : function(comment){
				var urlactualizar = url+'newsrestricted/comment/'+comment.id;
				return $http.put(urlactualizar, comment)
                .then(function(response){
    				 return response.status;
				 }, function(respuesta) {
						console.log("Error putUserComment: "+respuesta.status);
						return respuesta.status;
					});
			},
			votarComment : function(comment){
				var urlactualizar = url+'newsrestricted/comment/'+comment.id+'/votecomment';
				return $http.put(urlactualizar, comment)
                .then(function(response){
    				 return response.status;
				 }, function(respuesta) {
						console.log("Error votarComment: "+respuesta.status);
						return respuesta.status;
					});
			},
			eliminarComentario : function(comment){
				var urlBorrarComentario = url+'newsrestricted/'+comment.id;
                return $http.delete(urlBorrarComentario)
                .then(function(response){
     				 return response.status;
 				 }, function(respuesta) {
						console.log("Error eliminarComentario: "+respuesta.status);
						return respuesta.status;
					});
              },
			getCommentUserByCommentid : function(commentid){
				var urlComments = url+'newsrestricted/comment/'+commentid;
				return $http.get(urlComments)
				.then(function(response) {
					return response;
				}, function(respuesta) {
					console.log("Error comentario: "+respuesta.status);					
					return respuesta;
				});
			}
	}
	return interfaz;
}).factory("UserFactory", function($http,$location) {
	// datos publicos
	var userlogedpublico={
			name:'',
			visible:false
	}
	// datos privados
	var userlogedprivado={
			id:-1,
			password:'',
			email:''
	}

	var url;
	if($location.host()=='localhost'){
		url = 'https://localhost:8443/meneame/rest/users/';
	}else{
		url = $location.protocol()+'://'+$location.host()+'/rest/users/';
	}
	
	var interfaz = {
			getUserById : function(news){
				var urlUser = url+news.owner;
				return $http.get(urlUser)
				.then(function(response){
					if( response.data.ok == "true" ){
						return response.data.username;
					}else{
						return '';
					}
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("then Error: "+respuesta.status);					
					return respuesta.status;
					});
			},
			getUserCommentById : function(comment){
				var urlUser = url+comment.owner;
				return $http.get(urlUser)
				.then(function(response){
					if( response.data.ok == "true" ){
						return response.data.username;
					}else{
						return '';
					}
				},function(respuesta){
					// acciones a realizar cuando se recibe una respuesta de error
					console.log("then Error: "+respuesta.status);					
					return respuesta.status;
					});
			},
			loginUser : function(username,password){			
			var urlUser = url+'login?username='+username+'&password='+password;
			return $http.get(urlUser)
			.then(function(response){
				if( response.data.ok == "true" ){
					userlogedpublico.name=response.data.username;
					userlogedpublico.visible=true;
					userlogedprivado.password=response.data.password;
					userlogedprivado.email=response.data.email;
					userlogedprivado.id=response.data.useroid;
					console.log("then Exito login user: "+response.status);
				}
				return response.data;
			},function(respuesta){
				// acciones a realizar cuando se recibe una respuesta de error
				console.log("then Error: "+respuesta.status);				
				return respuesta.status;
				});
		},
		logoutUser : function(username){
			var urlUserLogout = url+'newsrestricted/logout/'+username;
			return $http.post(urlUserLogout, username)
            .then(function(response){
            	userlogedpublico.name='';
				userlogedpublico.visible=false;
				userlogedprivado.password='';
				userlogedprivado.email='';
				userlogedprivado.id=-1;
				return response.status;
			 }, function(respuesta) {
					console.log("Error logoutUser: "+respuesta.status);					
					return respuesta.status;
				});
		},
		actualizarUser : function(user){
			var urlactualizar = url+'newsrestricted/'+user.id;
			return $http.put(urlactualizar, user)
            .then(function(response){
				 return response.status;
			 }, function(respuesta) {
					console.log("Error actualizarUser: "+respuesta.status);					
					return respuesta.status;
				});
		},
		darDebajaUser : function(userid){
			var urlDarDeBajaUser = url+'newsrestricted/'+userid;
			return $http.delete(urlDarDeBajaUser)
			.then(function(response){
				return response.status;
			}, function(respuesta) {
				console.log("Error darDebajaUser: "+respuesta.status);
				return respuesta.status;
			});
		},
		postRegisterUser : function(user){
			var urlNuevoUsuario = url+'register';
			return $http.post(urlNuevoUsuario,user)
			.then(function(response) {
				var header=response.headers('Location');
				if(header != null){
					userlogedpublico.name=user.name;
					userlogedpublico.visible=true;
					userlogedprivado.password=user.password;
					userlogedprivado.email=user.email;				
					var index=header.lastIndexOf('/');
					var id=header.slice(index+1);
					userlogedprivado.id=parseInt(id);
				}
				
				return response.status;
			}, function(respuesta) {
				console.log("Error postRegisterUser: "+respuesta.status);
				return respuesta.status;
			});
		},
		getUserLoggedPublico : function(){
			return userlogedpublico;
		},
		getUserLoggedPrivado : function(){
			return userlogedprivado;
		},
		setUserEmailPassword : function(email,password){
			userlogedprivado.password=password;
			userlogedprivado.email=email;
		} ,
		setNoUserLogged : function(){
			userlogedpublico.name='';
			userlogedpublico.visible=false;
			userlogedprivado.password='';
			userlogedprivado.email='';
			userlogedprivado.id=-1;
			return 'notuserloged';
		}
	}
	return interfaz;
});
