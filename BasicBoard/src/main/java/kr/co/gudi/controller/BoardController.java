package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.CommentDTO;
import kr.co.gudi.dto.FileDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@GetMapping(value="/boardList")
	@ResponseBody
	public Map<String, Object> boardList(int page, int cnt, HttpSession session){
//		Map<String, Object> resultMap = board_service.boardList(page, cnt);
		
		logger.info("page : "+page);
		logger.info("cnt : "+cnt);
		
		return board_service.boardList(page, cnt);
	}
	
	@GetMapping(value="/writeForm")
	public String writeForm() {
		return "write";
	}
	
	@PostMapping(value="/write")
	@ResponseBody
	public Map<String, Object> write(MultipartFile[] files, @ModelAttribute BoardDTO boardDto){
		
		logger.info("dto : {}", boardDto.toString());
		logger.info("files : {}", files.toString());
		
		boolean success = false;
		String page = "";
		
		int idx = board_service.write(files, boardDto);
		if(idx > 0) {
			logger.info("idx : "+idx);
			success = true;
			page = "/boardDetail/"+idx;
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", success);
		resultMap.put("page", page);
		
		return resultMap;
	}
	
	
	@GetMapping(value="/boardDetail/{idx}")
	public String boardDetail(@PathVariable int idx, Model model) {
		
		logger.info("받아온 idx: "+idx);
		board_service.boardDetail(idx, model);
		
		return "boardDetail";
	}
	
	@PostMapping(value="/addComment")
	@ResponseBody
	public Map<String, Object> addComment(@ModelAttribute CommentDTO commentDto){
		
		logger.info("comment : " + commentDto.toString());
		
		Map<String, Object> resultMap = new HashMap<>();
		
		boolean success = false;
		
		int com_idx = board_service.addComment(commentDto);
		
		if(com_idx>0) {
			success = true;
		}
		
		resultMap.put("success", success);
	    return resultMap;
	}
	
	// int board_idx
	@GetMapping(value="/commentList")
	@ResponseBody
	public Map<String, Object> commentList(CommentDTO commentDto){
		
		logger.info("CommentDTO: " + commentDto.toString());
//		logger.info("board_idx : " + board_idx);
		
		Map<String , Object> resultMap = new HashMap<>();
		resultMap.put("comment", board_service.commentList(commentDto));
		
		return resultMap;
	}
	
	@GetMapping(value="/download")
	public ResponseEntity<Resource> download(FileDTO fileDto){
		
		logger.info("file download : " +  fileDto.toString());
		
//		return null; ▶ 에러가 나면 return 값을 null로 해서 받아보기!!!
		return board_service.download(fileDto);
	}
	
	@GetMapping(value="/updateView/{idx}")
	public String updateView(@PathVariable int idx, Model model) {
		
		logger.info("idx : " + idx);		
		board_service.boardDetail(idx, model);
		
		return "boardUpdate";
	}
	
	@PutMapping(value="/boardUpdate")
	@ResponseBody
	public Map<String, Object> boardUpdate(MultipartFile[] files, @ModelAttribute BoardDTO boardDto){

		Map<String, Object> resultMap = new HashMap<>();
		
		boolean success = false;
		String page = "";
		
		if(board_service.boardUpdate(files, boardDto)) {
			success = true;
			page = "/boardDetail/"+boardDto.getIdx();;
		}
		
		resultMap.put("success", success);
		resultMap.put("page", page);
		
		return resultMap;
	}

	
	@DeleteMapping(value="/deleteFile")
	@ResponseBody
	public Map<String, Object> deleteFile(@ModelAttribute FileDTO fileDto){
		
		logger.info("delete filename : " + fileDto.toString());
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", board_service.deleteFile(fileDto));
		
		return resultMap;
	}
	
	@GetMapping(value="/delete/{idx}")
	public String delete(@PathVariable int idx) {
		
		logger.info("idx : " + idx);
		
		board_service.delete(idx);
		
		return "boardList";
	}
	
	
}
