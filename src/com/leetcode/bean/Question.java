
package com.leetcode.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.leetcode.util.JsonService;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "questionId",
    "questionFrontendId",
    "title",
    "titleSlug",
    "translatedTitle",
    "stats",
    "difficulty",
    "isPaidOnly",
    "topicTags",
    "frequencyTimePeriod",
    "__typename"
})
public class Question {
	
	public static void main(String[] args) {
		int[] a= {1, 2, 2, 2, 3, 3, 4, 5, 5, 5, 5, 5};
		//Collections.binarySearch(list, key);
		System.out.println(Arrays.binarySearch(a, 5));
	}

    @JsonProperty("status")
    private String status;
    @JsonProperty("questionId")
    private Integer questionId;
    @JsonProperty("questionFrontendId")
    private Integer questionFrontendId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("titleSlug")
    private String titleSlug;
    @JsonProperty("translatedTitle")
    private Object translatedTitle;
    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("difficulty")
    private String difficulty;
    @JsonProperty("isPaidOnly")
    private Boolean isPaidOnly;
    @JsonProperty("topicTags")
    private List<TopicTag> topicTags = null;
    @JsonProperty("frequencyTimePeriod")
    private Integer frequencyTimePeriod;
    @JsonProperty("__typename")
    private String typename;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("questionId")
    public Integer getQuestionId() {
        return questionId;
    }

    @JsonProperty("questionId")
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("questionFrontendId")
    public Integer getQuestionFrontendId() {
    	
        return questionFrontendId;
    }

    @JsonProperty("questionFrontendId")
    public void setQuestionFrontendId(Integer questionFrontendId) {
        this.questionFrontendId = questionFrontendId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("titleSlug")
    public String getTitleSlug() {
        return titleSlug;
    }

    @JsonProperty("titleSlug")
    public void setTitleSlug(String titleSlug) {
        this.titleSlug = titleSlug;
    }

    @JsonProperty("translatedTitle")
    public Object getTranslatedTitle() {
        return translatedTitle;
    }

    @JsonProperty("translatedTitle")
    public void setTranslatedTitle(Object translatedTitle) {
        this.translatedTitle = translatedTitle;
    }

    @JsonProperty("stats")
    public Stats getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(String sstats) throws Exception{
    	Stats stats = JsonService.getObjectFromJson(sstats, Stats.class);
        this.stats = stats;
    }

    @JsonProperty("difficulty")
    public String getDifficulty() {
        return difficulty;
    }

    @JsonProperty("difficulty")
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @JsonProperty("isPaidOnly")
    public Boolean getIsPaidOnly() {
        return isPaidOnly;
    }

    @JsonProperty("isPaidOnly")
    public void setIsPaidOnly(Boolean isPaidOnly) {
        this.isPaidOnly = isPaidOnly;
    }

    @JsonProperty("paidOnly")
    public void setPaidOnly(Boolean isPaidOnly) {
        this.isPaidOnly = isPaidOnly;
    }
    
    @JsonProperty("topicTags")
    public List<TopicTag> getTopicTags() {
        return topicTags;
    }

    @JsonProperty("topicTags")
    public void setTopicTags(List<TopicTag> topicTags) {
        this.topicTags = topicTags;
    }

    @JsonProperty("frequencyTimePeriod")
    public Integer getFrequencyTimePeriod() {
        return frequencyTimePeriod;
    }

    @JsonProperty("frequencyTimePeriod")
    public void setFrequencyTimePeriod(Integer frequencyTimePeriod) {
        this.frequencyTimePeriod = frequencyTimePeriod;
    }

    @JsonProperty("__typename")
    public String getTypename() {
        return typename;
    }

    @JsonProperty("__typename")
    public void setTypename(String typename) {
        this.typename = typename;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
