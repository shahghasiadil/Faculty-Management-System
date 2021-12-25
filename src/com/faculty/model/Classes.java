package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Classes")
public class Classes extends  ModelBase<Classes>{

    @Column(name="name")
    private  String name;
    @Column(name = "room_no")
    private String room_no;
    @Column(name = "time")
    private String time;
    @OneToMany(mappedBy = "classes",cascade = CascadeType.ALL)
    private  List<Semester> semesters = new ArrayList<>();
    @OneToMany(mappedBy = "classes",cascade = CascadeType.ALL)
    private List<Schedule> schedules= new ArrayList<>();

    public List<Semester> getSemesters() {
        return semesters;
    }


    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public  void addSchedule(Schedule schedule){

        schedules.add(schedule);
        schedule.setClasses(this);
    }

    public  void addSemester(Semester semester) {

        semesters.add(semester);
        semester.setClasses(this);


    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String  getTime() {
        return String.valueOf(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name;
    }
}
