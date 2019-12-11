package com.api.user.entity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestInfo {
    int page;

    int size;

    int type;

    String keyword;

    @JsonProperty("sort_by")
    String sortBy;

    @JsonProperty("order_by")
    String orderBy;

    @JsonProperty("skill_ids")
    private List<Integer> skillIds;

}
