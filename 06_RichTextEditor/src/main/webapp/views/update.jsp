<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="/richtexteditor/res/style.css" />
	<link rel="stylesheet" href="/richtexteditor/rte_theme_default.css" />
	
	<script type="text/javascript" src="/richtexteditor/rte.js"></script>
	<script type="text/javascript" src='/richtexteditor/plugins/all_plugins.js'></script>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	
	<style>
		table, th, td{
			margin-left: 45px;
			margin-top: 15px;
			padding: 5px 10px;
			border: 1px solid lightgray;
			border-collapse: collapse;
			width : 70%;
		}
		
		input[type="text"]{
			width: 99%;
			border: 1px solid lightgray;
			height: 30px;
			margin: 5px 0px;
			border-radius: 7px;
		}
		
		input[type="button"]{
			padding: 5px 20px;
	        height: 35px;
	        margin: 0 6px;
	        border: 1px solid gray;
	        border-radius: 10px;
	        background: #fff;
	        color: gray;
	        cursor: pointer;
		}
	</style>
	
</head>
<body>
	<form action="update.do" method="post">
		<input type="hidden" name="idx" value="${info.idx}"/>
		<table>
			<tr>
				<td><input type="text" name="subject" value="${info.subject}" placeholder="제목을 입력하세요"/></td>
			</tr>
			<tr>
				<td><input type="text" name="user_name" value="${info.user_name}" placeholder="사용자"/></td>
			</tr>
			<tr>
				<td><div id="div_editor"></div></td>
				<input type="hidden" name="content"/>
				
			</tr>
			<tr>
				<!-- js 에서 처리 후 저장해야 하기 때문에 submit도 button도 아닌 것으로 사용 -->
				<th>
					<input type="button" value="취소" onclick="location.href='/detail.go?idx=${info.idx}'"/>
					<input type="button" value="수정" onclick="update()"/>
				</th>
			</tr>
		</table>
		<div id="content" style="display:none">${info.content}</div>
	</form>
</body>
<script>
 	var config = {}
 	// basic도 너무 많아서 simple로 변경 -> simple 에서는 넣고싶은 것만 넣을 수 있다.
 	// html 저장, 출력, pdf 저장, 코드보기 만 노출
	config.toolbar = "basic";
	config.editorResizeMode = "none";
	
	config.file_upload_handler = function(file, pathReplace){ // 파일객체, 경로 변경 함수
		console.log('file => ', file);
		if(file.size > (2*1024*1024)){
			alert('2MB 이상의 파일은 올릴 수 없습니다.');
			pathReplace('/img/noimage.png'); // 엑박처리
		}
	}
	
	var editor = new RichTextEditor("#div_editor", config); 
	
	// 에디터에 가져온 데이터 넣기 (가져올땐 getHTMLCode)
	// id가 content인 요소의 태그와 태그 사이 값을 가져와서 editor에 추가
	//	input 태그에 value로 넣을 때에는 " ' 이 혼동되서 사용되면서 문제가 생긴다.
	editor.setHTMLCode($('#content').html());
	
	function update(){
		var content = editor.getHTMLCode();
		// 콘솔로그로 찍는 것도 로딩 시간이 길어지는 이유 중 하나!!
		//console.log(content);
		//console.log('전체 문서의 크기 => ', (content.length/1024/1024) + 'MB');
		
		if(content.length > 100*1024*1024){
			alert('100MB 이상 크기는 전송이 불가능합니다.');
		}else{
			$('input[name="content"]').val(content);
			$('form').submit();
		}
	}
	
</script>
</html>