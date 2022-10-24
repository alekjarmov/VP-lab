package mk.ukim.finki.wp.lab.model.exceptions;

public class NoSuchUsernameException extends RuntimeException {
    public NoSuchUsernameException() {
        super("A user with the given username does not exist");
    }
}

