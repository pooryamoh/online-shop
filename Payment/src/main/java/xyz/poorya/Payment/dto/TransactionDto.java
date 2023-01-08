package xyz.poorya.Payment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String id;
    private String username;

    private int amount;


    @Override
    public String toString() {
        return "TransactionDto{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                '}';
    }
}
