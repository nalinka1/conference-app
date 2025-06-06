package com.example.conference.core;

import javax.security.auth.Subject;
import java.security.Principal;

public record User(String username, String apiKey) implements Principal {

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
