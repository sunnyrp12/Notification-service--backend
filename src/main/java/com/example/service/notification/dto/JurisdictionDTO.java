package com.example.service.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JurisdictionDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("identificationNumber")
    private String identificationNumber;

    @JsonProperty("jurisdiction")
    private String jurisdiction;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("firstName")
    private String firstName;

}
