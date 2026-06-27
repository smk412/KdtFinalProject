package com.weple.cloud.history.worklog.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weple.cloud.admin.service.UserService;
import com.weple.cloud.admin.service.UserVO;
import com.weple.cloud.history.worklog.service.WorkLogService;
import com.weple.cloud.history.worklog.service.WorkLogVO;
import com.weple.cloud.project.service.ProjectService;
import com.weple.cloud.project.service.ProjectVO;

@Controller
public class WorkLogController {
	private final WorkLogService workLogService;
	private final ProjectService projectService;
	private final UserService userService;
	
	@Autowired
	public WorkLogController(
			WorkLogService workLogService,
			ProjectService projectService,
			UserService userService) {
		this.workLogService = workLogService;
		this.projectService = projectService;
		this.userService = userService;
	}
	
	// 작업내역 조회
	@GetMapping("/worklog")
	public String workLogList(
			@RequestParam(required = false) String projectId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String userCode,
            @RequestParam(required = false) List<String> typeNames,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "search", required = false) String search,
			Model model) {
		
		 // 날짜 기본값: 최근 5일
		if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
		    LocalDate today = LocalDate.now();
		    StringBuilder redirectUrl = new StringBuilder("redirect:/worklog");
		    redirectUrl.append("?startDate=").append(today.minusDays(4).format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		    redirectUrl.append("&endDate=").append(today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		    if (projectId != null && !projectId.isEmpty())
		        redirectUrl.append("&projectId=").append(projectId);
		    if (userCode != null && !userCode.isEmpty())
		        redirectUrl.append("&userCode=").append(userCode);
		    if (typeNames != null)
		        for (String t : typeNames) redirectUrl.append("&typeNames=").append(t);
		    return redirectUrl.toString();
		}
		
		List<WorkLogVO> list = null;
	    int totalPages = 0;
	    Double totalSpentHour = null;

	 // 검색 버튼 클릭 시에만 데이터 조회
        if ("true".equals(search)) {
            int pageSize = 10;
            int offset = (page - 1) * pageSize;
 
            list = workLogService.findAll(
                    projectId, startDate, endDate, userCode, typeNames, offset, pageSize);
 
            int totalCount = workLogService.countAll(
                    projectId, startDate, endDate, userCode, typeNames);
 
            totalPages = (int) Math.ceil((double) totalCount / pageSize);
 
            totalSpentHour = workLogService.sumSpentHour(
                    projectId, startDate, endDate, userCode, typeNames);
        }
		
        // 프로젝트 목록 (필터 셀렉박스용)
		List<ProjectVO> projectList = projectService.findAll("");
		model.addAttribute("projects", projectList);
		
		// 사용자 목록 (필터 셀렉박스용)
		List<UserVO> userList = userService.findAllActiveUsers();
		model.addAttribute("users", userList);
		
		model.addAttribute("workLogList", list);
		model.addAttribute("searched", "true".equals(search));
		model.addAttribute("projectId", projectId);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("userCode", userCode);
		model.addAttribute("typeNames", typeNames);
		model.addAttribute("currentPage", page);      
		model.addAttribute("totalPages", totalPages); 
		model.addAttribute("totalSpentHour", totalSpentHour);
		
		model.addAttribute("sidebarMenu", "work-history");
		model.addAttribute("currentMenu", "none");
		
		return "weple/history/worklog";
	}
}
