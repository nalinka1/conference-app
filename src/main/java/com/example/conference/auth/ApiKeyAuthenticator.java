package com.example.conference.auth;

import com.example.conference.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ApiKeyAuthenticator implements Authenticator<BasicCredentials, User> {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyAuthenticator.class);
    // this is hardcoded for the sake of simplicity. In a real application, we  can use auto-generated API keys as session tokens
    private static final String API_KEY_FOR_AUTHENTICATION = "test";


    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if (API_KEY_FOR_AUTHENTICATION.equals(basicCredentials.getPassword())) {
            return Optional.of(new User(basicCredentials.getUsername(), basicCredentials.getPassword()));
        } else {
            logger.warn("Authentication failed for user: {}", basicCredentials.getUsername());
        }
        return Optional.empty();
    }
}
