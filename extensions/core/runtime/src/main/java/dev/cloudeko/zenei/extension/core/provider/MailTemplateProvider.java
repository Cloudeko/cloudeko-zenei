package dev.cloudeko.zenei.extension.core.provider;

public interface MailTemplateProvider {

    String format(String template, Object... args);

    default String defaultCreateConfirmationMailTemplate(String baseUrl, String sessionToken) {
        return format(
                """
                        <html>
                            <body>
                                <h1>Welcome to Zenei</h1>
                                <p>
                                    You have successfully created an externalAccount on Zenei. To activate your externalAccount, please click the link below.
                                </p>
                                <a href="%s?token=%s">Verify Email</a>
                            </body>
                        </html>
                        """,
                baseUrl,
                sessionToken
        );
    }
}
