
package com.example.irfan.suksesbelajar.mDataObject;

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
    "id_owner",
    "kota"
})
public class Owner {

    @JsonProperty("id_owner")
    private Integer idOwner;
    @JsonProperty("kota")
    private String kota;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id_owner")
    public Integer getIdOwner() {
        return idOwner;
    }

    @JsonProperty("id_owner")
    public void setIdOwner(Integer idOwner) {
        this.idOwner = idOwner;
    }

    @JsonProperty("kota")
    public String getKota() {
        return kota;
    }

    @JsonProperty("kota")
    public void setKota(String kota) {
        this.kota = kota;
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
