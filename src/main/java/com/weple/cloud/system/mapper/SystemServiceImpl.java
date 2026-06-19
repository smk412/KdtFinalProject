package com.weple.cloud.system.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.weple.cloud.system.service.SystemGroupVO;
import com.weple.cloud.system.service.SystemService;
import com.weple.cloud.system.service.TaskTypeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// ----------------일감유형-------------
public class SystemServiceImpl implements SystemService {

	private final SystemMapper systemMapper;
	@Override
	public List<TaskTypeVO> findAll() {
		return systemMapper.selectTaskTypeAll();
	}

	@Override
	public int addTaskType(TaskTypeVO taskTypeVO) {
		int result = systemMapper.insertTaskType(taskTypeVO);
		return result == 1 ? taskTypeVO.getTypeId() : -1;
	}

// =---------------그룹-------------
	@Override
	public List<SystemGroupVO> findGroupAll(String keyword) {
		return systemMapper.selectGroupAll(keyword);
	}

	@Override
	public int addGroup(SystemGroupVO systemGroupVO) {
		// 임시 테스트용 회사 ID
	    systemGroupVO.setCompanyId(1);
	    
		int result = systemMapper.insertGroup(systemGroupVO);
		return result == 1 ? systemGroupVO.getGroupId() : -1;
	}

	@Override
	public Map<String, Object> removeGroup(int groupId) {
		Map<String, Object> map = new HashMap<>();
		int result = systemMapper.deleteGroup(groupId);
		if(result >= 1) {
			map.put("groupId", groupId);
		}
		return map;
	}
	
	

}
