
package com.leetcode.bean;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "totalAccepted",
    "totalSubmission",
    "totalAcceptedRaw",
    "totalSubmissionRaw",
    "acRate"
})
public class Stats {

    @JsonProperty("totalAccepted")
    private String totalAccepted;
    @JsonProperty("totalSubmission")
    private String totalSubmission;
    @JsonProperty("totalAcceptedRaw")
    private Integer totalAcceptedRaw;
    @JsonProperty("totalSubmissionRaw")
    private Integer totalSubmissionRaw;
    @JsonProperty("acRate")
    private String acRate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("totalAccepted")
    public String getTotalAccepted() {
        return totalAccepted;
    }

    @JsonProperty("totalAccepted")
    public void setTotalAccepted(String totalAccepted) {
        this.totalAccepted = totalAccepted;
    }

    @JsonProperty("totalSubmission")
    public String getTotalSubmission() {
        return totalSubmission;
    }

    @JsonProperty("totalSubmission")
    public void setTotalSubmission(String totalSubmission) {
        this.totalSubmission = totalSubmission;
    }

    @JsonProperty("totalAcceptedRaw")
    public Integer getTotalAcceptedRaw() {
        return totalAcceptedRaw;
    }

    @JsonProperty("totalAcceptedRaw")
    public void setTotalAcceptedRaw(Integer totalAcceptedRaw) {
        this.totalAcceptedRaw = totalAcceptedRaw;
    }

    @JsonProperty("totalSubmissionRaw")
    public Integer getTotalSubmissionRaw() {
        return totalSubmissionRaw;
    }

    @JsonProperty("totalSubmissionRaw")
    public void setTotalSubmissionRaw(Integer totalSubmissionRaw) {
        this.totalSubmissionRaw = totalSubmissionRaw;
    }

    @JsonProperty("acRate")
    public String getAcRate() {
        return acRate;
    }

    @JsonProperty("acRate")
    public void setAcRate(String acRate) {
        this.acRate = acRate;
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
