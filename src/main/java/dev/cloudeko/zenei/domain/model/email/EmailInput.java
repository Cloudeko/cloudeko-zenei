package dev.cloudeko.zenei.domain.model.email;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EmailInput {
    private String emailAddress;
}
