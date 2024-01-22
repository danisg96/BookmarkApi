package com.segnalibri.api.Segnalibri.exception;

public class BookmarkNotFoundException extends Exception {
    public BookmarkNotFoundException() {
    }

    public BookmarkNotFoundException(String message) {
        super(message);
    }
}
