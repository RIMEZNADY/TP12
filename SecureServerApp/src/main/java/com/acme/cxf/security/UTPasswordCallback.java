package com.acme.cxf.security;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.Map;

public final class UTPasswordCallback implements CallbackHandler {
    private final Map<String, String> users;

    private UTPasswordCallback(Map<String, String> users) {
        this.users = Map.copyOf(users);
    }

    public static UTPasswordCallback withSingleUser(String username, String password) {
        return new UTPasswordCallback(Map.of(username, password));
    }

    public static UTPasswordCallback withUsers(Map<String, String> users) {
        return new UTPasswordCallback(users);
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback passwordCallback) {
                users.computeIfPresent(passwordCallback.getIdentifier(), (key, value) -> {
                    passwordCallback.setPassword(value);
                    return value;
                });
            }
        }
    }
}