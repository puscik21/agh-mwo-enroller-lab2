package com.company.enroller.exception;

import lombok.Getter;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

@Getter
public class ParticipantAlreadyExistsException extends ApiException {

    private final String login;

    public ParticipantAlreadyExistsException(String login) {
        super("Unable to create. A participant with login '%s' already exists.".formatted(login), HttpStatus.CONFLICT, Level.WARN);
        this.login = login;
    }
}
