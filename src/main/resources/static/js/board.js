let index={
	init: function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
		$("#btn-delete").on("click",()=>{
			this.deleteById();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});
		$("#btn-reply-save").on("click",()=>{
			this.replySave();
		});
		$("#btn-reply-delete").on("click",()=>{
			this.replyDelete();
		});
	},
	
	save: function(){
		/*alert('user의 save함수 호출됨');*/
		let data={
			title: $("#title").val(),
			content: $("#content").val(),
		};

		$.ajax({
			type:"POST",
			url:"/api/board",
			data:JSON.stringify(data),//http body데이터
			contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
			dataType:"json"
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다");
			//console.log(resp);
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},
	deleteById: function(){
		let id=$("#id").text();
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id,
			dataType:"json"
		}).done(function(resp){
			alert("삭제가 완료되었습니다");
			//console.log(resp);
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},
	update: function(){
		let id=$("#id").val();
		
		let data={
			title: $("#title").val(),
			content: $("#content").val(),
		};

		$.ajax({
			type:"PUT",
			url:"/api/board/"+id,
			data:JSON.stringify(data),//http body데이터
			contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
			dataType:"json"
		}).done(function(resp){
			alert("글 수정이 완료되었습니다");
			//console.log(resp);
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},
	replySave: function(){
		/*alert('user의 save함수 호출됨');*/
		let data={
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val(),
		};

		$.ajax({
			type:"POST",
			url:`/api/board/${data.boardId}/reply`,
			data:JSON.stringify(data),//http body데이터
			contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
			dataType:"json"
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다");
			//console.log(resp);
			location.href=`/board/${data.boardId}`;
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},
		replyDelete: function(){
		/*alert('user의 save함수 호출됨');*/
		let id=$("#id").text();
		let replyId=$("#replyId").val();
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id+"/reply/+"+replyId,
			dataType:"json"
		}).done(function(resp){
			alert("댓글 삭제가 완료되었습니다");
			//console.log(resp);
			location.href="/board/"+id;
		}).fail(function(){
			alert(JSON.stringify(error));
		});
	},
		
	
}
index.init();