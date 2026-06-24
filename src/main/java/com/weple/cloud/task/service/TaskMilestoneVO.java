package com.weple.cloud.task.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class TaskMilestoneVO {
	private Long milestoneId;
	private Long projectId;
	private String milestoneTitle;
}
