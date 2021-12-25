package com.faculty.model;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule  extends  ModelBase<Schedule>{

    @Column(name = "date")
    private  String date;
    @Column(name = "day")
    private  String day;
    @Column(name = "start_time")
    private  String start_time;
    @Column(name = "end_time")
    private  String end_time;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Classes classes;
//    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Course> courses= new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Course courses;

    public void setCourses(Course courses) {
        this.courses = courses;
    }
//
//    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL,orphanRemoval = true)
//    private  List<Exam_Schedule> exam_schedules = new ArrayList<>();

    public Course getCourses() {
        return courses;
    }
//
//    public List<Exam_Schedule> getExam_schedules() {
//        return exam_schedules;
//    }
//
//
//
//    public void setExam_schedules(List<Exam_Schedule> exam_schedules) {
//        this.exam_schedules = exam_schedules;
//    }
//
//
//    public  void addExamSchedule(Exam_Schedule exam_schedule){
//
//
//        exam_schedules.add(exam_schedule);
//        exam_schedule.setSchedule(this);
//    }



//    public void addCourses (Course course){
//
//        courses.add(course);
//        course.setSchedule(this);
//    }
//
//    public List<Course> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Schedule{" +

                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", classes=" + classes +
                ", courses=" + courses +

                '}';
    }
}
