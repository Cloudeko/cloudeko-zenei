package dev.cloudeko.zenei.extension.external.endpoint;

public class ProviderEndpoints {
    public static final DefaultProviderEndpoints GITHUB = new GithubProviderEndpoints();
    public static final DefaultProviderEndpoints DISCORD = new DiscordProviderEndpoints();
    public static final DefaultProviderEndpoints GOOGLE = new GoogleProviderEndpoints();
}
