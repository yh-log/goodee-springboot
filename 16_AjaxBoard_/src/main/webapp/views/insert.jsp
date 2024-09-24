<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table,td,th{
		border : 1px solid black;
		border-collapse: collapse;
		padding :5px 10px;
	}
	div.career{
		border: 1px solid lightgray;
		margin-top: 10px;
		width: 645px;
		padding: 10px;
	}
	ul li{
		list-style-type: none;
		margin-left: -40px;
		border-bottom: 1px solid gray;
		width: 645px;
		padding: 10px;
	}
	ul li:nth_child(odd){ /*홀수만*/
		background-color: lightgray;
	}
	.row2{
		height: 45px;
		width: 60px;
	}
</style>
</head>
<body>

</body>
<script>
	// jquery에서는 다른 페이지를 불러오는 기능을 제공 한다.
	// $('표현할 위치').load(불러올페이지경로 셀렉터, [콜백함수]);
	// 불러올 경로가 views에 있으면 못불러온다. (controller 에서만 불러올 수 있어서)
	$('body').load('html/card.html #career_card', function(res,stat){
		console.log(res); // 가져온 전체 소스를 보여준다.
		console.log(stat); // 상태를 알려준다. success
	});
	
/* 	function append(){
		var content = '';

		content += '<li>'
		content += '<input type="text" name="co_name" placeholder="회사명"/>'
		content += '<input type="date" name="start_date"/>'
		content += '<input type="date" name="end_date"/>'
		content += '<input type="text" name="desc" placeholder="업무내용"/>'
		content += '<button onclick="append()">추가</button>'
		content += '</li>'
		console.log(content);
		$('div.career').append(content);
	} */
	
	function addList(elem){ 
		var html = $(elem).parent().html(); // elem=this=button 의 한단계 위에 있는 html을 가져온다
		//console.log(html);
		$('ul.career').append('<li>'+html+'</li>');
	}
	
	function regiest(){
	
//		var career = {'name' : $('input[name=name]').val(), 'gender' : $('input[name=gender]').val(), 'birth_date' : $('input[name=birth_date]').val(), 'hire_date' : $('input[name=hire_date]').val()};
//		var com = [{'co_name' : $('input[name=co_name]').val(), 'start_date' : $('input[name=start_date]').val(), 'end_date' : $('input[name=end_datd]').val(), 'desc' : $('input[name=desc]').val()}];
//		var arr = [career['com']= {'co_name' : $('input[name=co_name]').val(), 'start_date' : $('input[name=start_date]').val(), 'end_date' : $('input[name=end_datd]').val(), 'desc' : $('input[name=desc]').val()}];
/* 
		var i = 0;
		var arr = [];
		while(i < $('input[name=co_name]').length){
			arr.push($('inputpname=co_name').val());
			arr.push($('inputpname=start_date').val());
			arr.push($('inputpname=end_datd').val());
			arr.push($('inputpname=desc').val());
			i++;
		}
		career['com'] = arr; */
//		console.log(career);
		
		// 기본 정보 ▼
		var name = $('input[name=name]').val();
		var gender = $('input[name=gender]:checked').val();
		var birth_date = $('input[name=birth_date]').val();
		var hire_date = $('input[name=hire_date]').val();
		
		console.log(name, gender, birth_date, hire_date);
		console.log($('ul.career li'));
		
		// 커리어 정보 ▼ (회사별 정보를 objcet에 담고, list에 추가)
		var list = [];
		
		// 이중 for문을 활용해서..!
		$('ul.career li').each(function(idx,item){ // foreach (반복)
			var career = {};
			$(item).find('input').each(function(idx,elem){ // li 요소 밑에 input 요소들을 찾는다.
				
/* 				career.key = value; // 변수 활용 불가
				career[key] = value; // 변수 활용 가능 */
				career[$(elem).attr('name')] = $(elem).val();
			});
		//	console.log(career);
			3
		});
		console.log(list);
		
		var param = {}; // 서버로 전송할 파라메터 (object)
		param.name = name;
		param.gender = gender;
		param.birth_date = birth_date;
		param.hire_date = hire_date;
		param.list = list;
		console.log('서버에 전송할 데이터 : ' , param); // object는 + 가 아닌 , 로 해줘야 함
		
		// {'key' : 'value'} 형태가 아닌 복잡한 형태의 경우 이전에 사용한 방식으로는 전송이 불가능하다.
		// 복잡한 형태란? {key : value, key:[{key:value}, {}...]} → key:value 방식이 아닌 value가 한 단계 더 포장되어 있으면 복잡한거다.
		// 복잡한 JSON 형태는 어떻게 전송하나?
		
		$.ajax({
			type:'POST', // 1. post로 전송해야 한다 (get방식은 끊어져버릴 수 있다.)
			url: 'insert.ajax',
			// 2. 보내는 파라미터는 json 형태의 문자열 이어야 할 것
			data: JSON.stringify(param), // 원래 {} 로 넣었으니까 오브젝트를 직접 넣어줘도 된다.
			dataType: 'JSON',
			// application/json; : 보내는 json에 한글이 없을 경우 그냥 이것만 작성해줘도 됨
			contentType: 'application/json; charset=UTF-8', // content-type 이 json 이라고 명시해줘야 한다.
			success: function(data){
				console.log(data);
			}, error: function(e){
				console.log(e);
			}
		});
		
	}
	
</script>
</html>