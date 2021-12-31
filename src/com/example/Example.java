
package com.example;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user_name",
    "num_solved",
    "num_total",
    "ac_easy",
    "ac_medium",
    "ac_hard",
    "stat_status_pairs",
    "frequency_high",
    "frequency_mid"
})
public class Example {

    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("num_solved")
    private Integer numSolved;
    @JsonProperty("num_total")
    private Integer numTotal;
    @JsonProperty("ac_easy")
    private Integer acEasy;
    @JsonProperty("ac_medium")
    private Integer acMedium;
    @JsonProperty("ac_hard")
    private Integer acHard;
    @JsonProperty("stat_status_pairs")
    private List<StatStatusPair> statStatusPairs = null;
    @JsonProperty("frequency_high")
    private Integer frequencyHigh;
    @JsonProperty("frequency_mid")
    private Integer frequencyMid;

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("num_solved")
    public Integer getNumSolved() {
        return numSolved;
    }

    @JsonProperty("num_solved")
    public void setNumSolved(Integer numSolved) {
        this.numSolved = numSolved;
    }

    @JsonProperty("num_total")
    public Integer getNumTotal() {
        return numTotal;
    }

    @JsonProperty("num_total")
    public void setNumTotal(Integer numTotal) {
        this.numTotal = numTotal;
    }

    @JsonProperty("ac_easy")
    public Integer getAcEasy() {
        return acEasy;
    }

    @JsonProperty("ac_easy")
    public void setAcEasy(Integer acEasy) {
        this.acEasy = acEasy;
    }

    @JsonProperty("ac_medium")
    public Integer getAcMedium() {
        return acMedium;
    }

    @JsonProperty("ac_medium")
    public void setAcMedium(Integer acMedium) {
        this.acMedium = acMedium;
    }

    @JsonProperty("ac_hard")
    public Integer getAcHard() {
        return acHard;
    }

    @JsonProperty("ac_hard")
    public void setAcHard(Integer acHard) {
        this.acHard = acHard;
    }

    @JsonProperty("stat_status_pairs")
    public List<StatStatusPair> getStatStatusPairs() {
        return statStatusPairs;
    }

    @JsonProperty("stat_status_pairs")
    public void setStatStatusPairs(List<StatStatusPair> statStatusPairs) {
        this.statStatusPairs = statStatusPairs;
    }

    @JsonProperty("frequency_high")
    public Integer getFrequencyHigh() {
        return frequencyHigh;
    }

    @JsonProperty("frequency_high")
    public void setFrequencyHigh(Integer frequencyHigh) {
        this.frequencyHigh = frequencyHigh;
    }

    @JsonProperty("frequency_mid")
    public Integer getFrequencyMid() {
        return frequencyMid;
    }

    @JsonProperty("frequency_mid")
    public void setFrequencyMid(Integer frequencyMid) {
        this.frequencyMid = frequencyMid;
    }

}
