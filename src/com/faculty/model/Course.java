package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course extends ModelBase<Course> {

    @Column(name = "c_name")
    private  String Course_name;
    @Column(name = "c_credits")
    private  long C_Credits;
    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();
//    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
//    private  List<Lecturer> lecturers = new ArrayList<>();
    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Lecturer lecturers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "course")
    private  List<Exam_Schedule> exam_schedules = new ArrayList<>();

    public void addExamSchedule(Exam_Schedule exam_schedule){

        exam_schedules.add(exam_schedule);
        exam_schedule.setCourse(this);
    }

    public Lecturer getLecturers() {
        return lecturers;
    }

    public void setLecturers(Lecturer lecturers) {
        this.lecturers = lecturers;
    }

    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinColumn(name = "s_id")
    private  Semester semester;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courses")
    private  List<Schedule> schedules= new ArrayList<>();

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
        schedule.setCourses(this);
    }

    //    @ManyToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "id")
//    private  Schedule schedule;
//
//    public Schedule getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(Schedule schedule) {
//        this.schedule = schedule;
//    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;

    }


//    public  void addLecturers(Lecturer lecturer){
//
//        lecturers.add(lecturer);
//        lecturer.setCourse(this);
//    }
//    public  void  removeLecturer(Lecturer lecturer){
//        lecturers.remove(lecturer);
//        lecturer.setCourse(null);
//
//        DBUtil.delete(lecturer);
//    }




    public  void  addRegistration (Registration registration){

        registrations.add(registration);
        registration.setCourse(this);
    }

    public String getCourse_name() {
        return Course_name;
    }

    public void setCourse_name(String course_name) {
        Course_name = course_name;
    }

//    public List<Lecturer> getLecturers() {
//        return lecturers;
//    }
//
//    public void setLecturers(List<Lecturer> lecturers) {
//        this.lecturers = lecturers;
//    }





    public long getC_Credits() {
        return C_Credits;
    }

    public void setC_Credits(long c_Credits) {
        C_Credits = c_Credits;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    @Override
    public String toString() {
        return Course_name;
    }
}
