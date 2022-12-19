let index={
	init: function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});

	},
	
	save: function(){
		/*alert('user의 save함수 호출됨');*/
		let data={
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		/*console.log(data);*/
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax호출시 default가 비동기 호출
		$.ajax({
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data),//http body데이터
			contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
			dataType:"json"//요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열(생긴게 json이라면)=>javascript
			//회원가입 수행 요청(100초 가정)
		}).done(function(resp){
			if(resp.status===500){
				alert("회원가입이 실패하였습니다");
			}
			else{
			alert("회원가입이 완료되었습니다");
			//console.log(resp);
			location.href="/";}
			
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},
		update: function(){
		/*alert('user의 save함수 호출됨');*/
		let data={
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type:"PUT",
			url:"/user",
			data:JSON.stringify(data),//http body데이터
			contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
			dataType:"json"//요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열(생긴게 json이라면)=>javascript
			//회원가입 수행 요청(100초 가정)
		}).done(function(resp){
			alert("회원수정이 완료되었습니다");
			//console.log(resp);
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},

		
	
}
index.init();