package com.api.user.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RequestInfo {
    int page;
    int size;
    int type;
    String keyword;
    String sortBy;
    int orderBy;
    private List<Integer> skillIds;

}
