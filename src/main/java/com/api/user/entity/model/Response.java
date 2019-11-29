package com.api.user.entity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Response {
    @JsonProperty("code")
    private int code = 0;

    @JsonProperty("message")
    private String message = "success";

    @JsonProperty("data")
    private Object data;
}
