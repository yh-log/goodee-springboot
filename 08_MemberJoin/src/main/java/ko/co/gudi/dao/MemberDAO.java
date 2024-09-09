package ko.co.gudi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

// java와 xml을 연결해주는 interface
// 그 역할을 수행한다는 뜻으로 표시를 해줘야 한다.

@Mapper
public interface MemberDAO {
	
	int join(Map<String, String> param);

}
