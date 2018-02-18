package com.danieleautizi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404 not found exception.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractStatusCodeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(final String msg) {
        super(msg);
    }

    public NotFoundException(final String msg, final Throwable trbl) {
        super(msg, trbl);
    }

}
