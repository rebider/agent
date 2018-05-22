package com.ryx.credit.activity.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ActIdUser implements Serializable{
    private Object id;

    private BigDecimal rev;

    private Object first;

    private Object last;

    private Object email;

    private Object pwd;

    private Object pictureId;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public BigDecimal getRev() {
        return rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public Object getFirst() {
        return first;
    }

    public void setFirst(Object first) {
        this.first = first;
    }

    public Object getLast() {
        return last;
    }

    public void setLast(Object last) {
        this.last = last;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPwd() {
        return pwd;
    }

    public void setPwd(Object pwd) {
        this.pwd = pwd;
    }

    public Object getPictureId() {
        return pictureId;
    }

    public void setPictureId(Object pictureId) {
        this.pictureId = pictureId;
    }
}