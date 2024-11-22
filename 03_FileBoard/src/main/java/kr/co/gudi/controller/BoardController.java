package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;
import kr.co.gudi.service.BoardService;

@RestController
public class BoardController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService board_service;
	
	/**
	 * author yh.kim (24.11.14)
	 * 리스트 화면 이동
	 */
	@GetMapping(value="/list.go")
	public ModelAndView listView() {
		return new ModelAndView("list");
	}
	
	/*
	 * author yh.kim (24.11.14)
	 * 리스트 조회
	 * */
	@PostMapping(value="/list.ajax")
	public Map<String, Object> list(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", board_service.list());
		return resultMap;
	}
	
	/**
	 * author yh.kim (24.11.14)
	 * 게시글 삭제
	 */
	@DeleteMapping(value="/delete.ajax")
	public Map<String, Object> boardDelete(@RequestParam int idx) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", board_service.boardDelete(idx));
		return resultMap;
	}

	/**
	 * author yh.kim (24.11.14)
	 * 글쓰기 화면 이동
	 */
	@GetMapping(value="/write.go")
	public ModelAndView write() {
		return new ModelAndView("write");
	}
	
	/**
	 * author yh.kim (24.11.14)
	 * 게시글 등록
	 */
	@PostMapping(value="/write.ajax")
	public Map<String, Object> write(MultipartFile[] files, @ModelAttribute BoardDTO boardDto){
		
		logger.info("dto => " + boardDto.toString());
		logger.info("file => " + files.toString());
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", board_service.write(boardDto, files));
		resultMap.put("page", "/detail/" + boardDto.getIdx());
		return resultMap;
	}
	
	/**
	 * author yh.kim (24.11.14)
	 * 상세 화면 이동
	 */
	@GetMapping(value="/detail/{idx}")
	public ModelAndView detail(@PathVariable int idx, Model model){
		model.addAttribute("idx", idx);
		return new ModelAndView("detail");
	}
	
	/**
	 * author yh.kim (24.11.14)
	 * 상세 조회
	 */
	@GetMapping(value="/detail.ajax")
	public Map<String, Object> detail(@RequestParam int idx) {
	    logger.info("idx => " + idx);
	    Map<String, Object> resultMap = new HashMap<>();
	
	    if(idx <=  0) {
	        resultMap.put("success", false);
	        resultMap.put("error", "idx parameter is missing");
	        return resultMap;
	    }
	
	    try {
	    	int idxs = idx;
	    	List<BoardDTO> dto = board_service.detail(idxs);
	
	        if (dto != null) {
	            resultMap.put("success", true);
	            resultMap.put("result", dto);
	        } else {
	            resultMap.put("success", false);
	        }
	    } catch (NumberFormatException e) {
	        logger.error("Invalid idx format: " + idx, e);
	        System.out.println(e.getMessage());
	        resultMap.put("success", false);
	        resultMap.put("error", "Invalid index format");
	    } catch (Exception e) {
	    	System.out.println(e);
	    }
	
	    return resultMap;
	}
	
	/**
	 * author yh.kim (24.11.15)
	 * 파일 다운로드
	 */
//	@GetMapping(value=".downloadImg")
//	public Map<String, Object> downloadImg(@RequestParam String file){
//	
//		
//		return null;
//	}
}
