package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.SendCreateConfirmationEmail;
import dev.cloudeko.zenei.domain.model.mail.CreateConfirmationEmailInput;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class SendCreateConfirmationEmailImpl implements SendCreateConfirmationEmail {

    private final Mailer mailer;

    @Override
    public boolean handle(CreateConfirmationEmailInput input) {
        mailer.send(mailer.createMessage()
                .to(input.getEmail())
                .subject("Welcome to Zenei!")
                .html("<h1>Thank you for signing up to Zenei!</h1>"
                        + "<p>Click <a href=\"http://localhost:8080/confirm-email?email=" + input.getEmail() + "\">here</a> to confirm your email.</p>"));
        return true;
    }
}
