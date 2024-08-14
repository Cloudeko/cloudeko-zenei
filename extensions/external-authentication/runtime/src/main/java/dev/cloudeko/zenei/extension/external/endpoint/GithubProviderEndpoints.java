package dev.cloudeko.zenei.extension.external.endpoint;

public class GithubProviderEndpoints implements DefaultProviderEndpoints {

    @Override
    public String getAuthorizationEndpoint() {
        return "https://github.com/login/oauth/authorize";
    }

    @Override
    public String getTokenEndpoint() {
        return "https://github.com/login/oauth/access_token";
    }

    @Override
    public String getBaseEndpoint() {
        return "https://api.github.com";
    }
}
