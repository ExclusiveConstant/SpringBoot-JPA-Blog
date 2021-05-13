let index = {
		//let _this = this; //functionを使うとしたら、このようにthisを参照できるようにする必要がある
		init : function(){
			$("#btn-save").on("click", ()=>{ //function(){}, () => {} thisをバインディングするために, 単純にFunctionを使うとthisがwindowを指す
				this.save();
			});
			$("#btn-login").on("click", ()=>{ //function(){}, () => {} thisをバインディングするために, 単純にFunctionを使うとthisがwindowを指す
				this.login();
			});
		},
		save: function(){
			//alert('userのsaveが呼び出された');
			let data = {
				username: $("#username").val(),
				password: $("#password").val(),
				email: $("#email").val(),
			}
			
			//console.log(data);
			
			//ajaxを呼び出すとdefaultが非同期である
			//ajax通信で三つのdataをjsonに変えてinsertを要請する。
			//ajaxが通信を成功してjsonをreturnするとServerが自動でJavaObjectに変換する
			$.ajax({
				//会員登録の遂行を要請
				type: "POST",
				url: "/blog/api/user",
				data: JSON.stringify(data), //http body data
				contentType: "application/json; charset=urf-8" //body dataのタイプについて(MIME)
				//dataType: "json" //responseは基本的にString, ただ形がjsonなら->javaScriptObjectに変換 -> 自動でやるので、記載しなくてよい
			}).done(function(resp){		
				alert("会員登録が完了しました。");
				location.href="/blog";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
			
		},
		
		login: function(){
			//alert('userのsaveが呼び出された');
			let data = {
				username: $("#username").val(),
				password: $("#password").val(),
				email: $("#email").val(),
			}
			
			//console.log(data);
			
			//ajaxを呼び出すとdefaultが非同期である
			//ajax通信で三つのdataをjsonに変えてinsertを要請する。
			//ajaxが通信を成功してjsonをreturnするとServerが自動でJavaObjectに変換する
			$.ajax({
				//会員登録の遂行を要請
				type: "POST",
				url: "/blog/api/user/login",
				data: JSON.stringify(data), //http body data
				contentType: "application/json; charset=urf-8" //body dataのタイプについて(MIME)
				//dataType: "json" //responseは基本的にString, ただ形がjsonなら->javaScriptObjectに変換 -> 自動でやるので、記載しなくてよい
			}).done(function(resp){		
				alert("ログインができました。");
				location.href="/blog";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
			
		}
}

index.init();