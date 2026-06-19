package com.weple.cloud.system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weple.cloud.system.service.SystemGroupVO;
import com.weple.cloud.system.service.SystemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SystemController {
	
	private final SystemService systemService;
	
	//관리 내 일감유형
	
	// ---------------------------- 그룹 종류 --------------------------
	//전체조회
	@GetMapping("groupList")
	public String systemGroupList(@RequestParam(required = false) String keyword,
								  Model model) {
		List<SystemGroupVO> list = systemService.findGroupAll(keyword);
		model.addAttribute("systemGroupList", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("menu", "group");
		return "weple/admin/group/list";
	}
	
	//등록
	@GetMapping("groupInsert")
	public String groupInsertForm() {
		return "weple/admin/group/insert";
	}
		
	@PostMapping("groupInsert")
	public String groupInsertProcess(SystemGroupVO systemGroupVO) {
		systemGroupVO.setCompanyId(1);
		int gno = systemService.addGroup(systemGroupVO);
		return "redirect:groupList";
	}
		
	//삭제
	@GetMapping("groupDelete")
	public String groupDelete(Integer groupId) {
		systemService.removeGroup(groupId);
		return "redirect:groupList";
	}
	
	// ---------------------------- 그룹 내 사용자 --------------------------
	//전체조회
	
	//등록
	
	//수정
	
	//삭제
	
}
