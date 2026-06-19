package com.weple.cloud.system.mapper;

import java.util.List;

import com.weple.cloud.system.service.SystemGroupVO;
import com.weple.cloud.system.service.TaskTypeVO;

public interface SystemMapper {
	// ---------------------------- 일감유형 --------------------------
	// 전체 조회
	public List<TaskTypeVO> selectTaskTypeAll();
	
	// 등록
	public int insertTaskType(TaskTypeVO taskType);
	
	// 편집
	
	// 삭제
	
	// ---------------------------- 그룹 종류 --------------------------
	//전체조회
	public List<SystemGroupVO> selectGroupAll(String keyword);
		
	//등록
	public int insertGroup(SystemGroupVO systemGroupVO);
		
	//삭제
	public int deleteGroup(int groupId);
	
	// ---------------------------- 그룹 내 사용자 --------------------------
	//전체조회
	
	//등록
	
	//수정
	
	//삭제
}
