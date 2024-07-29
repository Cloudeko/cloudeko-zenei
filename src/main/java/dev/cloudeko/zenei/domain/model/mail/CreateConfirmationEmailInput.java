package dev.cloudeko.zenei.domain.model.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateConfirmationEmailInput {
    private String email;
}
