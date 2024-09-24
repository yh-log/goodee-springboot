package kr.co.gudi.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.BoardService;
import kr.co.gudi.vo.BoardVO;

@Controller
public class BoardController {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService boardService;
	
	
	@RequestMapping(value="/")
	public String listForm() {
		return "list";
	}
	
	@GetMapping(value="/list.ajax")
	@ResponseBody
	public List<BoardVO> list() {
		List<BoardVO> list = boardService.list();
		return list;
	}
	
}
