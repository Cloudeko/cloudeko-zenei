package dev.cloudeko.zenei.domain.provider;

public interface MailTemplateProvider {

    String format(String template, Object... args);

    default String defaultCreateConfirmationMailTemplate(String baseUrl, String token) {
        return format(
                """
                        <html>
                            <body>
                                <h1>Welcome to Zenei</h1>
                                <p>
                                    You have successfully created an account on Zenei. To activate your account, please click the link below.
                                </p>
                                <a href="%s?token=%s">Verify Email</a>
                            </body>
                        </html>
                        """,
                baseUrl,
                token
        );
    }
}
