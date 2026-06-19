package com.weple.cloud.system.service;

import java.util.List;
import java.util.Map;

public interface SystemService {
	//관리 내 일감유형
	// 일감유형 전체조회
	public List<TaskTypeVO> findAll();
	
	// 일감유형 등록
	public int addTaskType(TaskTypeVO taskTypeVO);
	
	// 일감유형 편집
	
	
	// 일감유형 삭제
	
	
	//관리 내 그룹 종류
	//전체조회
	public List<SystemGroupVO> findGroupAll(String keyword);
			
	//등록
	public int addGroup(SystemGroupVO systemGroupVO);
			
	//삭제
	public Map<String, Object> removeGroup(int groupId);
	
	// ---------------------------- 그룹 내 사용자 --------------------------
	//전체조회
	
	//등록
	
	//수정
	
	//삭제
	
}
