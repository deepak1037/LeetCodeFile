package com.leetcode.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdNamePair {

	@JsonProperty("idHash")
	private String idHash;
	
	@JsonProperty("name")
	private String name;

	public String getIdHash() {
		return idHash;
	}

	public void setIdHash(String idHash) {
		this.idHash = idHash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
