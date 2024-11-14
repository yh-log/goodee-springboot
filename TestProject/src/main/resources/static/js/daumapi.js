/*duam map api*/

 	var roadAddr = '';
 	var sigungu = '';
 	var sido = '';
 	var shotsido = '';

	
	function search(){
		var themeObj = {
			postcodeTextColor: "#FB7E3A", //우편번호 글자색 수정
		};	
		new daum.Postcode({
			theme: themeObj,
		    oncomplete: function(data) {
		        
		        // 도로명 주소
		        roadAddr = data.roadAddress;
		        $('#sample4_roadAddress').val(roadAddr); // 사용자에게 보여지는 값
		        
		        // 구, 동
		        sigungu = data.sigungu;
		        
		        // 시/도
		        sido = data.sido; // 나중에 앞에 2글자만 짤라서 사용
		        
		        // 시/도 앞 2글자만 사용
		        shotsido = sido.substring(0, 2);
		    }
		}).open();
	};