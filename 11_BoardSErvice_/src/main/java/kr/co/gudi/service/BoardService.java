package kr.co.gudi.service;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO board_dao;

	public List<BoardDTO> list() {
		return board_dao.list();
	}

	// detail 이라는 작업 안에 2개의 쿼리가 있지만
	// 사실상 쪼갤 수 없는 업무 단위가 된다.
	// rollbackFor = Exception.class : 모든 Exception에 대해서 롤백 수행
	// noRollbackFor = SQLSyntaxErrorException.class : 해당 Exception 에 대해서는 롤백을 수행하지 않는다. (잘 사용하지 않음)
	// isolation : 격리 수준
	// isolation = Isolation.READ_COMMITTED(디폴트와 동일)
	// isolation = Isolation.READ_UNCOMMITTED
	// isolation = Isolation.REPEATABLE_READ
	// isolation = Isolation.SERIALIZABLE
	@Transactional(rollbackFor = Exception.class)
	public BoardDTO detail(int idx) {
		board_dao.upHit(idx); // 조회수를 위해 별도의 메서드를 추가 생성
		// try-catch 를 하면 rollback이 먹지 않는다. (이때는 수동으로 처리해줘야 한다)
		return board_dao.detail(idx);
	}

	public void del(int idx) {
		board_dao.del(idx);
	}

	public void write(Map<String, String> params) {
		int row = board_dao.write(params);
		logger.info("insert data : " + row);
	}

	public BoardDTO updateForm(int idx) {
		return board_dao.detail(idx);
	}

	public void update(Map<String, String> params) {
		int row = board_dao.update(params);
		logger.info("update row : " + row);
	}

//	public String upload(Map<String, String> param) {
//		
//		String msg = "업로드에 실패했습니다";
//		if(board_dao.upload(param) == 1) {
//			msg = "업로드에 성공했습니다";
//		}
//		
//		return null;
//	}

//	public int hit(int idx) {
//		return board_dao.hit(idx);
//	}
}
