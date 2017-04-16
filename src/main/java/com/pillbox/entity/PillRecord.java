package com.pillbox.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;

@Entity
public class PillRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pillRecordId;
    private Integer yearDay;
    private Time pillTime;
    private Boolean pillDispensed;

    public Long getPillRecordId() {
        return pillRecordId;
    }

    public void setPillRecordId(Long pillRecordId) {
        this.pillRecordId = pillRecordId;
    }

    public Integer getYearDay() {
        return yearDay;
    }

    public void setYearDay(Integer yearDay) {
        this.yearDay = yearDay;
    }

    public Time getPillTime() {
        return pillTime;
    }

    public void setPillTime(Time pillTime) {
        this.pillTime = pillTime;
    }

    public Boolean getPillDispensed() {
        return pillDispensed;
    }

    public void setPillDispensed(Boolean pillDispensed) {
        this.pillDispensed = pillDispensed;
    }
}
