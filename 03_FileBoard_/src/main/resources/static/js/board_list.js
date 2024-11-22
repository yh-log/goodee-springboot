pageCall();

function pageCall() {
	$.ajax({
		url: 'board_list.ajax',
		data: {
			'cnt': 10
		},
		dataType: 'json',
		success: function(data) {
			listPrint(data.list);
		},
		error: function(e) {}
	});
}
	
function listPrint(list) {
	var tags = '';
	for (var item of list) {
		tags += '<tr>';
		tags += '<td>'+item.idx+'</td>';
		tags += '<td class="left"><a href="board_detail.go?idx='+item.idx+'">'+item.subject+'</a></td>';
		tags += '<td>'+item.user_name+'</td>';
		tags += '<td>'+item.bHit+'</td>';
		tags += '<td>'+item.reg_date+'</td>';
		tags += '</tr>';
	}
	$('tbody').html(tags);
}