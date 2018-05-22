package com.ryx.credit.activity.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ActIdUser implements Serializable{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActIdUser)) return false;

        ActIdUser actIdUser = (ActIdUser) o;

        if (getId() != null ? !getId().equals(actIdUser.getId()) : actIdUser.getId() != null) return false;
        if (getRev() != null ? !getRev().equals(actIdUser.getRev()) : actIdUser.getRev() != null) return false;
        if (getFirst() != null ? !getFirst().equals(actIdUser.getFirst()) : actIdUser.getFirst() != null) return false;
        if (getLast() != null ? !getLast().equals(actIdUser.getLast()) : actIdUser.getLast() != null) return false;
        if (getEmail() != null ? !getEmail().equals(actIdUser.getEmail()) : actIdUser.getEmail() != null) return false;
        if (getPwd() != null ? !getPwd().equals(actIdUser.getPwd()) : actIdUser.getPwd() != null) return false;
        return !(getPictureId() != null ? !getPictureId().equals(actIdUser.getPictureId()) : actIdUser.getPictureId() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getRev() != null ? getRev().hashCode() : 0);
        result = 31 * result + (getFirst() != null ? getFirst().hashCode() : 0);
        result = 31 * result + (getLast() != null ? getLast().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPwd() != null ? getPwd().hashCode() : 0);
        result = 31 * result + (getPictureId() != null ? getPictureId().hashCode() : 0);
        return result;
    }

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