package br.com.jonss.exceptions;

public class ServerLimitException extends RuntimeException {
    public ServerLimitException() {
        super("server limit is up to limit");
    }
}
