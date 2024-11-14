
// loading 시 
function loading(){
	$('body').append('<div class="loading-bg"><div class="lds-spinner"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div>');
}
// loading 완료시
function loadingComplete(){
	$('.loading-bg').remove();
}


// 태그 체크박스 클릭시 label에 클래스 추가

if($('#tagFilters').length > 0){
	document.querySelector('#tagFilters').addEventListener('click', function(event) {
	  if (event.target.type === 'checkbox') {  // 클릭된 요소가 체크박스인지 확인
	    if (event.target.checked) {
	      event.target.parentElement.classList.add('checked');  // 체크되면 클래스 추가
	    } else {
	      event.target.parentElement.classList.remove('checked');  // 체크 해제되면 클래스 제거
	    }
	  }
});
 }