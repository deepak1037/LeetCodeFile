package com.leetcode.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Submissions {
	
    @JsonProperty("statusDisplay")
    private String statusDisplay;
    

    private String timestamp;


	public String getStatusDisplay() {
		return statusDisplay;
	}


	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(Long timestamp) {		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MMM/dd");
		this.timestamp = sdf.format(new Date(timestamp*1000));
	}
    
    
}
