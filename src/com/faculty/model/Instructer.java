package com.faculty.model;

import javax.persistence.*;

@Entity
@Table(name = "instructer")
public class Instructer  extends  ModelBase<Instructer>{

    @Column(name = "first_name")
    private  String first_name;
    @Column(name = "last_name")
    private  String last_name;
    @Column(name = "phone_no")
    private  String phone_no;
    @Column(name = "Email")
    private  String Email;

    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinColumn(name = "s_id")
    private  Semester semester;



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
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Instructer{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", Email='" + Email + '\'' +
                ", semester=" + semester +
                '}';
    }
}
