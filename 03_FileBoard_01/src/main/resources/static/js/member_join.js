if ($('.msg p').html() != "") {
	$('.msg').show();
}

var checkId = false;

var pw = '';
var name = '';
var age = '';
var email = '';
var checkNull = false;

$('#checkId').click(function() {
	var id = $('input[name="id"]').val();
	
	$.ajax({
		type: 'post',
		url: 'checkId.ajax',
		data: {
			"id": id
		},
		success: function(data) {
			if (!data.checkId) {
				$('.checkResult').html('사용할 수 있는 아이디입니다.').css({'color': '#000'});
				checkId = true;
			} else {
				$('.checkResult').html('이미 사용중인 아이디입니다.').css({'color': 'red'});
			}
		},
		error: function(e) {
			console.log('Error occured. ', e);
		}
	});
});

function join() {
	pw = $('input[name="pw"]').val();
	name = $('input[name="name"]').val();
	age = $('input[name="age"]').val();
	email = $('input[name="email"]').val();
	
	if (pw != '' && name != '' && age != '' && email != '') {
		checkNull = true;
	}
	
	if (!checkId) {
		console.log("checkId: ",checkId," / checkNull: ", checkNull);
		$('.msg p').html('아이디 중복 여부를 체크하지 않으셨습니다.');
		$('.msg').show();
	} else if(!checkNull) {
		console.log("checkId: ",checkId," / checkNull: ", checkNull);
		$('.msg p').html('입력하지 않은 내용이 있습니다.');
		$('.msg').show();
	} else {
		console.log("checkId: ",checkId," / checkNull: ", checkNull);
		$('form').submit();
	}
}