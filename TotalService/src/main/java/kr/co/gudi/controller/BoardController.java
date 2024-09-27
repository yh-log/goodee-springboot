package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/boardListView")
	public String boardListView(HttpSession session) {
		
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			return "boardList";
		}
		return "redirect:/" + page;
	}
	
	@GetMapping(value="/boardList")
	@ResponseBody
	public Map<String, Object> boardList(int page, int cnt){
		logger.info("페이지 : "+page, cnt);
		
		return board_service.boardList(page,cnt);
	}
	
	@PostMapping(value="/write")
	public String write(MultipartFile[] files, BoardDTO boardDto) {
		// 글에 입력된 idx 에 상세보기로 이동
		
		logger.info("DTO : {}", boardDto.toString());
		logger.info("file : {}", files);
		
		if(board_service.write(files, boardDto)) {
			return "redirect:/boardDetail/"+boardDto.getIdx();
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value="/writeView")
	public String writeView() {
		return "write";
	}
	
	@GetMapping(value="/boardDetail/{idx}")
	public String boardDetailView(@PathVariable("idx") int idx, HttpSession session, Model model) {
		
		if(board_service.boardDetail(idx, model)){
			return "boardDetail";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/download.do")
	public ResponseEntity<Resource> download(String new_filename, String ori_filename){
		logger.info("들어온 파일" + new_filename, ori_filename);
		
		return board_service.download(new_filename, ori_filename);
	}
	
	
	@RequestMapping(value="/updateView/{idx}")
	public String uadateView(@PathVariable("idx") int idx, HttpSession session, Model model) {
		logger.info("들어온 번호"+idx);
		board_service.boardDetail(idx, model);
		return "update";
	}
	
	@PostMapping(value="/boardUpdate")
//	@ResponseBody
	public Map<String, Object> boardUpdate(MultipartFile[] files, 
			@RequestParam BoardDTO boardDto){
		Map<String, Object> resultMap = new HashMap<>();
		
		logger.info("DTO : {}", boardDto.toString());
		logger.info("files : {} ", files.toString());
		
		return resultMap;
	}
	
}
