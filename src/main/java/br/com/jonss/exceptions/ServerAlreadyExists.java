package br.com.jonss.exceptions;

public class ServerAlreadyExists extends RuntimeException {
    public ServerAlreadyExists() {
        super("server already exists");
    }
}
