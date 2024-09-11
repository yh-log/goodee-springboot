package ko.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ko.co.gudi.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	int join(Map<String, String> param);

	String login(String id, String pw);

	List<BoardDTO> list();

	BoardDTO detail(String id);

	void del(String id);

}
