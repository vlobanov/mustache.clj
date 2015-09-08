package me.vlobanov.mustache;

import me.vlobanov.mustache.Token;

public class ParserException extends Exception {

    private static final long serialVersionUID = 1L;
    Token token;

    public ParserException(String msg) {
        super(msg);
    }

    public ParserException(String msg, Token token) {
        super(msg);
        this.token = token;
    }
}
