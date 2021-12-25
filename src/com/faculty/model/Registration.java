package com.faculty.model;

import javax.persistence.*;

@Entity
@Table(name = "registration")
public class Registration extends ModelBase<Registration> {

    @Column(name = "date")
    private  String date;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Student student ;
    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinColumn(name = "c_id")
    private  Course course ;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Student getStudents() {
        return student;
    }

    public void setStudents(Student students) {
        this.student = students;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
