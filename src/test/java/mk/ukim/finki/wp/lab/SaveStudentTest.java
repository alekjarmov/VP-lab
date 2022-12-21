package mk.ukim.finki.wp.lab;


import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidFormParameters;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameTakenException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveStudentTest {
    //unit test for save student from StudentService
    @Mock
    private StudentRepository studentRepository;


    private StudentServiceImpl studentService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Student student = new Student("username", "password", "name", "surname");
        Mockito.when(this.studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        this.studentService = Mockito.spy(new StudentServiceImpl(this.studentRepository));
    }

    @Test
    public void testEmptyUsername(){
        String username = "";
        String password = "password";
        String name = "name";
        String surname = "surname";
        Assert.assertThrows("Invalid form parameters", InvalidFormParameters.class, () -> {
            this.studentService.save(username, password, name, surname);
        });
        Mockito.verify(this.studentService, Mockito.times(1)).save("", "password", "name", "surname");
    }

    @Test
    public void testSuccessfulSave(){
        String username = "username";
        String password = "password";
        String name = "name";
        String surname = "surname";
        Student student = this.studentService.save(username, password, name, surname);

        Assert.assertNotNull("Student is null",student);
        Assert.assertEquals("username does not match","username", student.getUsername());
        Assert.assertEquals("password does not match","password", student.getPassword());
        Assert.assertEquals("name does not match","name", student.getName());
        Assert.assertEquals("surname does not match","surname", student.getSurname());
    }

    @Test
    public void testDuplicateUsername(){
        String username = "username";
        String password = "password";
        String name = "name";
        String surname = "surname";
        Student student = new Student("username", "password", "name", "surname");
        Mockito.when(this.studentRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(student));
        Assert.assertThrows("Username taken", UsernameTakenException.class, () -> {
            this.studentService.save(username, password, name, surname);
        });
        Mockito.verify(this.studentService, Mockito.times(1)).save("username", "password", "name", "surname");
    }
}
