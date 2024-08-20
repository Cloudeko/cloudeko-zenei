package dev.cloudeko.zenei.extension.external.web.external;

public interface BaseExternalClient<T> {
    T getCurrentlyLoggedInUser(String token);
}
