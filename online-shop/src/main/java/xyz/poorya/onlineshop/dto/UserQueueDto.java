package xyz.poorya.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQueueDto {
    @JsonProperty
    private String username;

    @JsonProperty
    private String fName;

    @JsonProperty
    private String lName;

    @JsonProperty
    private String phoneNumber;

    @JsonProperty
    private String email;

}
