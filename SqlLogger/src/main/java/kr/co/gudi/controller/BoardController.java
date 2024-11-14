package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService boardService;
	
	@RequestMapping(value="/")
	public String listView() {
		return "list";
	}

	@RequestMapping(value="/list.ajax")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "20") String cnt) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("list", boardService.getList(page, cnt));
		return result;
	}
	
	@RequestMapping(value="/write.go")
	public String writeView() {
		return "write";
	}

	@RequestMapping(value="/write.ajax")
	@ResponseBody
	public Map<String, Object> write(@ModelAttribute BoardDTO boardDto, MultipartFile[] files) {
		logger.info("받아온 값 => " + boardDto.toString());
		logger.info("파일은 => " + files.toString());
		Map<String, Object> resultMap = new HashMap<>();
		if(boardService.write(boardDto, files)) {
			resultMap.put("success", true);
		}
		return resultMap;
	}
	
}
