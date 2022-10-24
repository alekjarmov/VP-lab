package mk.ukim.finki.wp.lab.model.exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException() {
        super("Username is already taken");
    }
}

