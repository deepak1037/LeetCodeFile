
package com.leetcode.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "56",
    "185",
    "295"
})
public class Frequencies {

    @JsonProperty("56")
    private List<Integer> _56 = null;
    @JsonProperty("185")
    private List<Integer> _185 = null;
    @JsonProperty("295")
    private List<Integer> _295 = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("56")
    public List<Integer> get56() {
        return _56;
    }

    @JsonProperty("56")
    public void set56(List<Integer> _56) {
        this._56 = _56;
    }

    @JsonProperty("185")
    public List<Integer> get185() {
        return _185;
    }

    @JsonProperty("185")
    public void set185(List<Integer> _185) {
        this._185 = _185;
    }

    @JsonProperty("295")
    public List<Integer> get295() {
        return _295;
    }

    @JsonProperty("295")
    public void set295(List<Integer> _295) {
        this._295 = _295;
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
