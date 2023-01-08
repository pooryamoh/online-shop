package xyz.poorya.onlineshop.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNameExistsException extends AuthenticationException {

    public UserNameExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNameExistsException(String msg) {
        super(msg);
    }
}
