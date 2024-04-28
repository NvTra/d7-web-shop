package com.tranv.d7shop.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranv.d7shop.models.User;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRepose {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
