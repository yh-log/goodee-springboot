if ($('.msg p').html() != "") {
	$('.msg').show();
}

$('#login').click(function() {
	var id = $('input[name="id"]').val();
	var pw = $('input[name="pw"]').val();
	
	$.ajax({
		type: 'post',
		url: 'member_login.ajax',
		data: {
			"id": id,
			"pw": pw
		},
		dataType: 'json',
		success: function(data) {
			if (data.login) {
				console.log(data.login);
				alert(id+'님, 반갑습니다.');
				location.href='board_list.go';
			} else {
				console.log(data.login);
				$('.msg p').html('아이디 혹은 비밀번호를 확인하세요.');
				$('.msg').show();
			}
		},
		error: function(e) {}
	});
});