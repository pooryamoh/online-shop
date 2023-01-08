package xyz.poorya.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {
    private String id;
    private String username;

    private int amount;
}
