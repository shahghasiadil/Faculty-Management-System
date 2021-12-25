package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Attendence extends ModelBase <Attendence> {

    @Column(name = "date")
    private  String date;
    @Column(name = "status")
    private  String status;
    @OneToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Course course;
//    @OneToMany(mappedBy = "attendence",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Student> students = new ArrayList<>();

    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Student students;
    @OneToMany(mappedBy = "attendence",cascade = CascadeType.ALL)
    private  List<Marks> marks = new ArrayList<>();

    public void addMarks(Marks mark){

        marks.add(mark);
        mark.setAttendence(this);

    }

//    public  void addStudents(Student student){
//
//        students.add(student);
//        student.setAttendence(this);
//    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
//
//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(List<Student> students) {
//        this.students = students;
//    }


    public Student getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students = students;
    }

    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return   course.getCourse_name();
    }
}
