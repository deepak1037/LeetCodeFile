package com.leetcode.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavoritesLists {

    @JsonProperty("allFavorites")
    private List<AllFavorites> allFavorites;

	public List<AllFavorites> getAllFavorites() {
		return allFavorites;
	}

	public void setAllFavorites(List<AllFavorites> allFavorites) {
		this.allFavorites = allFavorites;
	}
    
    
}
