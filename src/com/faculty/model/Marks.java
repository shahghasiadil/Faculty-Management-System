package com.faculty.model;

import javax.persistence.*;

@Entity
@Table(name = "marks")
public class Marks extends ModelBase<Marks> {

    @Column(name = "mid_term")
    private long mid_term;
    @Column(name = "final_m")
    private long final_m;
    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinColumn(name = "a_id")
    private  Attendence attendence ;
    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Student student ;
    @Transient
    private  Long Total;
    @Transient
    private String Result;

    public String getResult() {
        if(getTotal()>=55){
            return "Pass";
        }
        else
            return "Fail";

    }

    public void setResult(String Total) {
        Result = Total;
    }

    public Long getTotal() {
        return mid_term+final_m;
    }

    public void setTotal(Long total) {
        Total = total;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public long getMid_term() {
        return mid_term;
    }

    public void setMid_term(long mid_term) {
        this.mid_term = mid_term;
    }

    public long getFinal_m() {
        return final_m;
    }

    public void setFinal_m(long final_m) {
        this.final_m = final_m;
    }

    public Attendence getAttendence() {
        return attendence;
    }

    public void setAttendence(Attendence attendence) {
        this.attendence = attendence;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "id=" + id +
                ", mid_term=" + mid_term +
                ", final_m=" + final_m +
                ", attendence=" + attendence +
                ", student=" + student +
                '}';
    }
}
