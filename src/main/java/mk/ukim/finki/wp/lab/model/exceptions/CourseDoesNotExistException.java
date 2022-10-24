package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseDoesNotExistException extends RuntimeException {
    public CourseDoesNotExistException() {
        super("Course does not exist");
    }
}

