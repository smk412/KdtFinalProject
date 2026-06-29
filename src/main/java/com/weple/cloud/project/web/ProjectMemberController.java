package com.weple.cloud.project.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weple.cloud.project.service.ProjectMemberRoleVO;
import com.weple.cloud.project.service.ProjectMemberService;
import com.weple.cloud.project.service.ProjectMemberVO;
import com.weple.cloud.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService memberService;
    private final ProjectService       projectService;

    //  설정 > 구성원 탭  GET /project/settings/members
    @GetMapping("/project/settings/members")
    public String membersPage(
            @RequestParam Long projectId,
            Model model) {

        List<ProjectMemberVO> memberList = memberService.findMemberList(projectId);
        List<ProjectMemberRoleVO>          roleList   = memberService.findRoleList();

        model.addAttribute("project",     projectService.findById(String.valueOf(projectId)));
        model.addAttribute("moduleNames", projectService.findActiveModuleNames(projectId));
        model.addAttribute("sidebarMenu", "project");
        model.addAttribute("currentMenu", "settings");
        model.addAttribute("projectId",   projectId);
        model.addAttribute("memberList",  memberList);
        model.addAttribute("roleList",    roleList);
        model.addAttribute("activeTab",   "members");
        model.addAttribute("settingMenu", "member");

        return "weple/project/members";
    }

    //  구성원 추가 모달 - 사용자 검색 AJAX
    //  GET /project/settings/members/search
    @GetMapping("/project/settings/members/search")
    @ResponseBody
    public ResponseEntity<List<ProjectMemberVO>> searchUsers(
            @RequestParam Long   projectId,
            @RequestParam(required = false, defaultValue = "") String keyword) {

        List<ProjectMemberVO> result = memberService.searchUsersForAdd(projectId, keyword);
        return ResponseEntity.ok(result);
    }

    //  구성원 추가  POST /project/settings/members/add
    @PostMapping("/project/settings/members/add")
    @ResponseBody
    public ResponseEntity<String> addMember(
            @RequestParam Long   projectId,
            @RequestParam String userCode,
            @RequestParam Long   roleId) {

        ProjectMemberVO vo = new ProjectMemberVO();
        vo.setProjectId(projectId);
        vo.setUserCode(userCode);
        vo.setRoleId(roleId);

        try {
            memberService.addMember(vo);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    //  구성원 삭제  POST /project/settings/members/delete
    @PostMapping("/project/settings/members/delete")
    @ResponseBody
    public ResponseEntity<String> deleteMember(
            @RequestParam String memberId,
            @RequestParam Long   projectId) {

        try {
            memberService.removeMember(memberId, projectId);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}