package com.program.tech.youtubeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private String id;
    @JsonProperty("sub")
    private String sub;
//    @JsonProperty("given_name");
//    private String givenname;
//    @JsonProperty("familyName");
//    private String familyName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String picture;

    private String email;
}
