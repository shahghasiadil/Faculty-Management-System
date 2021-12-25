package com.faculty.model;

import javax.persistence.*;

@Entity
@Table(name = "exam_schedule")
public class Exam_Schedule extends  ModelBase<Exam_Schedule>{


    @Column(name = "start_time")
    private  String start_time;
    @Column(name = "end_time")
    private  String end_time;
    @Column(name = "date")
    private String date;

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

//    @ManyToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "id")
//    private  Schedule schedule;

    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Staff staff;


    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }




    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

//    public Schedule getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(Schedule schedule) {
//        this.schedule = schedule;
//    }


    @Override
    public String toString() {
        return "Exam_Schedule{" +

                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +

                ", staff=" + staff +
                '}';
    }
}
