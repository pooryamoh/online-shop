package xyz.poorya.Payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
