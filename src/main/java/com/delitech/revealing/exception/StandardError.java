package com.delitech.revealing.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StandardError {
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestapm;
	private Integer status;
	private String error;
	private String message;
	private String path;

	public StandardError() {
		super();
	}

	public StandardError(LocalDateTime timestapm, Integer status, String error, String message, String path) {
		this.timestapm = timestapm;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
}
