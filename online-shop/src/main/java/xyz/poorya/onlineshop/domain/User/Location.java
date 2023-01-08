package xyz.poorya.onlineshop.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String city;

    private String country;

    private Double X;

    private Double y;
}
