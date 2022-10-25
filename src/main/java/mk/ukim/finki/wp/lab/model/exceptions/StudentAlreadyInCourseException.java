package mk.ukim.finki.wp.lab.model.exceptions;

public class StudentAlreadyInCourseException extends RuntimeException {
    public StudentAlreadyInCourseException() {
        super("That student is already enrolled in the course");
    }
}

