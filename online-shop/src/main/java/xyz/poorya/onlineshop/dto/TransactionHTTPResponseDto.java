package xyz.poorya.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHTTPResponseDto {
    private String callbackUrl;
}
