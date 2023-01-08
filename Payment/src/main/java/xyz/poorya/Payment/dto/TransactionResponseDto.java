package xyz.poorya.Payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
    private boolean paid;

    private String message;

    private String id;

    @Override
    public String toString() {
        return "TransactionResponseDto{" +
                "paid=" + paid +
                ", message='" + message + '\'' +
                '}';
    }
}
