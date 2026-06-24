package com.weple.cloud.file;


import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class TaskFileVO {
	private String fileId;
	private String logicalName;
    private Long fileSize;
    private Long versionNumber;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Date uploadedAt;
}
