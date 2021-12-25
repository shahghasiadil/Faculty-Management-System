package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecturer")
public class Lecturer extends ModelBase<Lecturer>{

    @Column(name = "first_name")
    private  String first_name;
    @Column(name = "last_name")
    private  String last_name;
    @Column(name = "phone_no")
    private String phone_no;
    @Column(name = "email")
    private String email;
//    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
////    @JoinColumn(name = "user_id")
//    private Users users;
//    @ManyToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "c_id")
//    private  Course course;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Address address;

    @OneToMany(mappedBy = "lecturers",cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourses(Course course){
        courses.add(course);
        course.setLecturers(this);
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }



    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Users getUsers() {
//        return users;
//    }
//
//    public void setUsers(Users users) {
//        this.users = users;
//    }

    @Override
    public String toString() {
        return first_name +" "+last_name ;

    }
}
