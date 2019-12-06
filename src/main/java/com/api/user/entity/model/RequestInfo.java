package com.api.user.entity.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestInfo {
    int page;
    int size;
    int type;
}
