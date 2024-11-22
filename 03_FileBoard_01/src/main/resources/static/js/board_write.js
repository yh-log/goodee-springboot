function save() {
	var form = new FormData($('form')[0]);
	var user_name = $('input[name="user_name"]').val();
	var subject = $('input[name="subject"]').val();
	var context = $('textarea[name="context"]').val();
	var checkNull = false;
	
	if (user_name != '' && subject != '' && context != '') {
		checkNull = true;
	}
	
	if (!checkNull) {
		$('.msg p').html('입력하지 않은 내용이 있습니다.');
		$('.msg').show();
	} else {
		$('form')[0].submit();
	}
}