checkLogin();

function checkLogin(){
		var url = window.location.pathname;
		
		if(url.indexOf('/admin/login')!==-1){
			if(getCookie("jwt")!=""){
				alert('이미 로그인된 상태입니다.');
				location.replace('/admin/home');
			}
		}else{
			if(getCookie("jwt")==""){
				alert('로그인을 하십시오.');
				location.replace('/admin/login');
			}
		}
		
	}