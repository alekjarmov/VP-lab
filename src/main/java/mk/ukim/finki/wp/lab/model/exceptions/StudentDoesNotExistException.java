package mk.ukim.finki.wp.lab.model.exceptions;

public class StudentDoesNotExistException extends RuntimeException {
    public StudentDoesNotExistException() {
        super("Student does not exist");
    }
}

