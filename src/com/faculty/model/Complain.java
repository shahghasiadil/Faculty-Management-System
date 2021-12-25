package com.faculty.model;

import javax.persistence.*;


@Entity
@Table(name = "complain")
public class Complain extends  ModelBase<Complain> {

    @Column(name = "complain")
    private String complain;
    @Column(name = "reply")
    private  String reply;
    @Column(name = "enter_date")
    private  String enterDate;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinColumn(name = "std_id")
    private  Student students;

//    @ManyToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "staff_id")
//    private  Staff staffs ;

//    public Staff getStaffs() {
//        return staffs;
//    }
//
//    public void setStaffs(Staff staffs) {
//        this.staffs = staffs;
//    }


    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public Student getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Complain{" +
                "id=" + id +
                ", complain='" + complain + '\'' +
                ", reply='" + reply + '\'' +
                ", enterDate='" + enterDate + '\'' +
                ", students=" + students +
                '}';
    }
}
