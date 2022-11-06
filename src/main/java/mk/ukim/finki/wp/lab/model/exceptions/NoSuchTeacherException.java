package mk.ukim.finki.wp.lab.model.exceptions;

public class NoSuchTeacherException extends RuntimeException{
    public NoSuchTeacherException() {
        super("A teacher with the given id does not exist");
    }
}
