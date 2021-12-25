package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semester")
public class Semester  extends  ModelBase<Semester>{
    @Column(name = "semester_name")
    private  String semester_name;
    @Column(name = "start_date")
    private  String start_date;
    @Column(name = "end_date")
    private  String end_date;
    @OneToMany(mappedBy = "semester",cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();


    @OneToMany(mappedBy = "semester",cascade = CascadeType.ALL)
    private  List<Instructer> instructers = new ArrayList<>();
    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Classes classes ;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public List<Instructer> getInstructers() {
        return instructers;
    }

    public void setInstructers(List<Instructer> instructers) {
        this.instructers = instructers;
    }

    public String getSemester_name() {
        return semester_name;
    }

    public void setSemester_name(String semester_name) {
        this.semester_name = semester_name;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void  addInstructer(Instructer instructer){

        instructer.setSemester(this);
        this.instructers.clear();
        this.instructers.addAll(instructers);
    }

    public void addCourses(Course course){
   courses.add(course);
   course.setSemester(this);


    }


    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public List<Course> getCourses() {
        return courses;
    }
//
//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }


    @Override
    public String toString() {
        return semester_name ;
    }
}
