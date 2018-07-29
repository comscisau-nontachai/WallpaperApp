  var config = {
  apiKey: "AIzaSyBI0JbWZbcaX_XQ1NnDWmOwwv3gOyWJ7Hw",
    authDomain: "wallpaperapp-cbc53.firebaseapp.com",
    databaseURL: "https://wallpaperapp-cbc53.firebaseio.com",
    projectId: "wallpaperapp-cbc53",
    storageBucket: "wallpaperapp-cbc53.appspot.com",
    messagingSenderId: "360147911749"
  };

  firebase.initializeApp(config);

    firebase.auth.Auth.Persistence.LOCAL; 

    $("#btn-login").click(function(){
        
        var email = $("#email").val();
        var password = $("#password").val(); 

        var result = firebase.auth().signInWithEmailAndPassword(email, password);
    
        result.catch(function(error){
            var errorCode = error.code; 
            var errorMessage = error.message; 

            console.log(errorCode);
            console.log(errorMessage);
        });

    });

    function switchView(view){
        $.get({
            url:view,
            cache: false,  
        }).then(function(data){
            $("#container").html(data);
        });
    }