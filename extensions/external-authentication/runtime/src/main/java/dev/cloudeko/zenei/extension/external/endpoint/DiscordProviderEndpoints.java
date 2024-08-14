package dev.cloudeko.zenei.extension.external.endpoint;

public class DiscordProviderEndpoints implements DefaultProviderEndpoints {

    @Override
    public String getAuthorizationEndpoint() {
        return "https://discord.com/api/oauth2/authorize";
    }

    @Override
    public String getTokenEndpoint() {
        return "https://discord.com/api/oauth2/token";
    }

    @Override
    public String getBaseEndpoint() {
        return "https://discord.com/api";
    }
}
