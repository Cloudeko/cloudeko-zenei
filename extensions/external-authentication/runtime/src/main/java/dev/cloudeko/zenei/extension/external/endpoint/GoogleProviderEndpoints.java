package dev.cloudeko.zenei.extension.external.endpoint;

public class GoogleProviderEndpoints implements DefaultProviderEndpoints {

    @Override
    public String getAuthorizationEndpoint() {
        return "https://accounts.google.com/o/oauth2/auth";
    }

    @Override
    public String getTokenEndpoint() {
        return "https://oauth2.googleapis.com/token";
    }

    @Override
    public String getBaseEndpoint() {
        return "https://www.googleapis.com";
    }
}
