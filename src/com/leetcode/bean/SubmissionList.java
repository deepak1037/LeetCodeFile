package com.leetcode.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmissionList {
    @JsonProperty("submissions")
    private List<Submissions> submissions;

	public List<Submissions> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submissions> submissions) {
		this.submissions = submissions;
	}
    
    
}
