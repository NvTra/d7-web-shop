package com.tranv.d7shop.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRepose {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;
}
