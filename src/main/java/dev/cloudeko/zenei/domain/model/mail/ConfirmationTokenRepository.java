package dev.cloudeko.zenei.domain.model.mail;

public interface ConfirmationTokenRepository {

    ConfirmationToken createConfirmationToken(String email);
}
