package com.weple.cloud.milestone.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weple.cloud.auth.service.LoginUserDetails;
import com.weple.cloud.milestone.service.MilestoneService;
import com.weple.cloud.milestone.service.MilestoneVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project/{projectId}/milestone") // 클래스 레벨에서 projectId를 아예 고정합니다.
public class MilestoneController {

	private final MilestoneService milestoneService;

	// 1. 특정 프로젝트의 마일스톤 전체 조회 (Thymeleaf 화면 반환)
	@GetMapping
	public String milestoneList(@PathVariable("projectId") Integer projectId, Model model) {
		// [추가 고민 필요] 현재 서비스의 selectMilestoneAll()은 전체 마일스톤을 가져옵니다.
		// 실제로는 selectMilestoneByProjectId(projectId) 처럼 해당 프로젝트 것만 필터링하는 것이 좋습니다.
		List<MilestoneVO> list = milestoneService.selectMilestoneAll(); 
		
		model.addAttribute("milestones", list);
		model.addAttribute("projectId", projectId); // 화면단에서 사용하기 위해 기재
		
		return "weple/milestone/list"; 
	}

	// 2. 등록 페이지 조회
	@GetMapping("/insert")
	public String milestoneInsertForm(@PathVariable("projectId") Integer projectId, Model model) {
		// 중요: 등록 폼 화면(HTML)에 현재 어떤 프로젝트 안에서 등록 중인지 projectId를 넘겨줍니다.
		model.addAttribute("projectId", projectId);
		return "weple/milestone/register";
	}

	// 3. 등록하기 (Form Submit 후 리다이렉트)
	@PostMapping("/insert")
	public String milestoneInsert(
			@PathVariable("projectId") Integer projectId,
			@AuthenticationPrincipal LoginUserDetails loginUser, 
			MilestoneVO milestoneVO) {
		
		// 1) 로그인한 사용자의 userCode 세팅 (수정하신 부분 적용)
		String userCode = loginUser.getLoginUser().getUserCode();
		milestoneVO.setUserCode(userCode);
		
		// 2) URL 경로에서 추출한 projectId를 VO에 강제로 주입
		// 이렇게 하면 HTML 폼에서 실수로 다르게 보내더라도 URL 기준으로 안전하게 묶입니다.
		milestoneVO.setProjectId(projectId);
		
		if (milestoneVO.getMilestoneStatus() == null) {
			milestoneVO.setMilestoneStatus("g1"); // 기본값: active (공통코드)
		}
		
		milestoneService.addMilestone(milestoneVO);
		
		// 등록 완료 후 다시 해당 프로젝트의 마일스톤 리스트로 리다이렉트
		return "redirect:/project/" + projectId + "/milestone";
	}

	// 4. 수정 페이지 조회 (기존 데이터 채우기)
	@GetMapping("/update")
	public String milestoneUpdateForm(
			@PathVariable("projectId") Integer projectId,
			@RequestParam("milestoneId") int milestoneId, 
			Model model) {
		
		MilestoneVO milestone = milestoneService.selectMilestoneById(milestoneId);
		model.addAttribute("milestone", milestone);
		model.addAttribute("projectId", projectId);
		
		return "weple/milestone/register";
	}

	// 5. 수정하기 (Form Submit 후 리다이렉트)
	@PostMapping("/update")
	public String milestoneUpdate(@PathVariable("projectId") Integer projectId, MilestoneVO milestoneVO) {
		// 안전장치로 현재 프로젝트 ID 바인딩
		milestoneVO.setProjectId(projectId);
		
		milestoneService.updateMilestone(milestoneVO);
		return "redirect:/project/" + projectId + "/milestone";
	}

	// 6. 삭제하기 (화면 새로고침 없이 AJAX로 처리)
	@PostMapping("/delete/{milestoneId}")
	@ResponseBody
	public ResponseEntity<String> milestoneDelete(@PathVariable("milestoneId") int milestoneId) {
		int result = milestoneService.deleteMilestone(milestoneId);
		if (result > 0) {
			return ResponseEntity.ok("SUCCESS");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAIL");
		}
	}
}
