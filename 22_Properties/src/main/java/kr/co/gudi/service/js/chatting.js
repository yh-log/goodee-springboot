
	
	// 1대1 채팅방 열기
	function chat(id,unlikeId){
		$.ajax({
			type:'GET',
			url:'/chat/'+id+'/'+unlikeId,
			data:{},
			dataType:'JSON',
			success:function(data){
				console.log(data.roomNum);
				openChat(data.roomNum);
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	
	function openChat(roomNum) {
	    // 새 창의 URL
	    var url = '/chat/'+roomNum;
	
	    // 새 창의 크기와 위치 설정
	    var width = 400;
	    var height = 700;
	    var left = (screen.width - width) / 2;
	    var top = (screen.height - height) / 2;
	
	    // 새 창을 열고, 크기와 위치 설정
	    window.open(url, '_blank', `width=${width},height=${height},left=${left},top=${top},resizable=no,scrollbars=no,status=no,menubar=no,location=no`);
	}
  
  
  
  function openCrewChat(){
    	$.ajax({
    		type: 'GET',
    		url: '/crewChat/'+crew_idx,
    		dataType: 'JSON',
    		success: function(data){
    			openCrewChatWindow(crew_idx,data.roomNum);
    		},
    		error: function(e){
    			console.log(e);
    		}
    	});
    }
    
    function openCrewChatWindow(crew_idx,roomNum) {
	    // 새 창의 URL
	    var url = '/crewChat/open/'+crew_idx+'/'+roomNum;
	    chatWindowSet(url);
	}
	
	function openCrewLeaderChat(){
		$.ajax({
			type:'GET',
			url:'/crewLdchat/'+crew_idx,
			data:{},
			dataType:'JSON',
			success:function(data){
				console.log(data.roomNum);
				openLeaderChat(data.roomNum);
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	
	function openCrewManagerChat(userId){
		console.log("userId: ",userId);
	
		$.ajax({
			type:'GET',
			url:'/crewMgchat/'+crew_idx+'/'+userId,
			data:{},
			dataType:'JSON',
			success:function(data){
				console.log(data.roomNum);
				openLeaderChat(data.roomNum);
			},
			error:function(e){
				console.log(e);
			}
		});
		
		
	}
	
	function openLeaderChat(roomNum){
		 // 새 창의 URL
	    var url = '/crewLdchat/open/'+roomNum;
	    chatWindowSet(url);
	}
	
	function chatWindowSet(url){
		 // 새 창의 크기와 위치 설정
	    var width = 400;
	    var height = 700;
	    var left = (screen.width - width) / 2;
	    var top = (screen.height - height) / 2;
	    // 새 창을 열고, 크기와 위치 설정
	    window.open(url, '_blank', 'width='+width+',height='+height+',left='+left+',top='+top+',resizable=no,scrollbars=no,status=no,menubar=no,location=no');
	}
	