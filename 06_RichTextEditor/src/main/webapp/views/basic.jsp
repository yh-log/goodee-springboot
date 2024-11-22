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
		table{
			margin-left: 45px;
			margin-top: 15px;
		}
		
		input[type="text"]{
			width: 99%;
			border: 1px solid lightgray;
			height: 30px;
			margin-bottom: 10px;
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
	<form action="write.do" method="post">
		<table>
			<tr>
				<td><input type="text" name="subject" value="테스트" placeholder="제목을 입력하세요"/></td>
			</tr>
			<tr>
				<td><input type="text" name="user_name" value="test" placeholder="사용자"/></td>
			</tr>
			<tr>
				<td><div id="div_editor"></div></td>
				<input type="hidden" name="content"/>
			</tr>
			<tr>
				<!-- js 에서 처리 후 저장해야 하기 때문에 submit도 button도 아닌 것으로 사용 -->
				<th><input type="button" value="저장" onclick="save()"/></th>
			</tr>
		</table>
	
	</form>
</body>
<script>
	var config = {}
	config.toolbar = "basic";
	
	// 에더터 크기 조절 x
	config.editorResizeMode = "none";
	
	// data:image = 이미지를 base64 형태로 문자열화 한 것이다.
	// 장점 : 별도의 파일 처리 없이 파일을 다룰 수 있다. (서버에 안가도 o) , 사용이 간단하다,
	// 단점 : 용량제어가 안되며, 기존 파일보다 용량이 커진다. (16진수라서 16비트씩 먹는다. 2개만 있어도 14개의 공백이 생김)
	// 	* 그러나 내용이 파일이기 때문에 db에 그대로 저장할 수 있다. (longtext)
	
	// 파일 업로드 시 설정
	config.file_upload_handler = function(file, pathReplace){ // 파일객체, 경로 변경 함수
		console.log('file => ', file);
		if(file.size > (2*1024*1024)){
			alert('2MB 이상의 파일은 올릴 수 없습니다.');
			pathReplace('/img/noimage.png'); // 엑박처리
		}
	}
	
	var editor = new RichTextEditor("#div_editor", config);
	
	
	function save(){
		
		var content = editor.getHTMLCode();
		console.log(content);
		console.log('전체 문서의 크기 => ', (content.length/1024/1024) + 'MB');
		
		if(content.length > 100*1024*1024){
			alert('100MB 이상 크기는 전송이 불가능합니다.');
		}else{
			$('input[name="content"]').val(content);
			$('form').submit();
		}
		
		// tomcat에서 받을 수 있는 post 방식의 텍스트 데이터는 2MB 이다.
		// 		파일은 enctype 에 multipart 를 선언해줘서 파일과 텍스트를 분리해서 인식했기 때문에 가능했다.
		// 그래서 수용할 수 있는 텍스트의 용량을 늘려 준다.
		// server.xml 설정에 <Connector maxPostSize=""/> 를 추가해 준다.
		// -1 : 무제한 / 바이트 단위로 설정 (xml은 계산이 안되기 때문에 직접 값을 넣어줘야 함)
		// 서버 설정할 때에는 -1을 잡지 않는게 좋다! (보통 5MB정도!)
		
		
	}
	
</script>
</html>