package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Student {
    @Id
    private String username;
    private  String password;
    private  String name;
    private  String surname;

    public Student() {
    }

    public Student(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
    public String getFullName(){
        return String.format("%s %s", this.name, this.surname);
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", this.name, this.surname, this.username);
    }


}
