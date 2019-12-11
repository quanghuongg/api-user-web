package com.api.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Integer id;

    private String username;

    private String display_name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String address;

    private String phone;

    private String avatar;

    private long expired;

    private int status;

    private long created;

    private long updated;

    private double hourly_wage;

    private String description;

    //Add  more
    private int role_id;

    private Role role;

    private List<Skill> skills;

}
