package xyz.poorya.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.poorya.onlineshop.domain.User.Location;

import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithoutUsernameDto {
    private String fName;

    private String lName;

    private String password;

    private String phoneNumber;

    private String email;

    private Location location;

    public boolean isNull() {
        Field fields[] = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                Object value = f.get(this);
                if (value == null) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return false;

    }

}
