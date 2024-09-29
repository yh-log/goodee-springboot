package kr.co.gudi.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
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
	public String write(MultipartFile[] files, BoardDTO boardDto, HttpSession session) {
		// 글에 입력된 idx 에 상세보기로 이동
		
		if(session.getAttribute("loginId") == null) {
			return "redirect:/";
		}
		
		logger.info("DTO : {}", boardDto.toString());
		logger.info("file : {}", files.toString());
		
		if(board_service.write(files, boardDto)) {
		}
		return "redirect:/boardDetail.go?idx="+boardDto.getIdx();
		
//		return "redirect:/";
	}
	
	@GetMapping(value="/writeView")
	public String writeView(HttpSession session) {
		
		if(session.getAttribute("loginId") == null) {
			return "redirect:/";
		}
		return "write";
	}
	
	@GetMapping(value="/boardDetail.go")
	public String boardDetailView(int idx, HttpSession session, Model model) {
		
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
	
	
	@RequestMapping(value="/update.go")
	public String uadateView(@RequestParam int idx, HttpSession session, Model model) {
		logger.info("들어온 번호"+idx);
		
		if(session.getAttribute("loginId") == null) {
			return "redirect:/";
		}
		
		board_service.boardDetail(idx, model);
		return "update";
	}
	
	@PostMapping(value="/boardUpdate")
	public String boardUpdate(MultipartFile[] files, 
			@RequestParam Map<String, Object> param, Model model, HttpSession session){
//		Map<String, Object> resultMap = new HashMap<>();
		
		if(session.getAttribute("loginId") == null) {
			return "redirect:/";
		}
		
		String idxStr = String.valueOf(param.get("idx"));
		int idx = Integer.parseInt(idxStr);
		
		logger.info("DTO : {}", param);
		logger.info("files : {} ", files.toString());
		
		model.addAttribute("result", board_service.boardUpdate(param, files));
		
		return  "redirect:/boardDetail.go?idx=" + idx;
	}
	
	@RequestMapping(value="/boarddelete.go")
	public String boarddelete(int idx, HttpSession session) {
		
		if(session.getAttribute("loginId") == null) {
			return "redirect:/";
		}
		
		board_service.boarddelete(idx);
		
		return "boardList";
	}
	
	
}
