package com.pillbox.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class PillBox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="pillBoxRecordList", joinColumns={@JoinColumn(name="pillBoxId", referencedColumnName="id")}, inverseJoinColumns={@JoinColumn(name="pillRecordId", referencedColumnName="pillRecordId")})
    @OrderBy(value = "yearDay ASC")
    private List<PillRecord> pillRecordList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<PillRecord> getPillRecordList() {
        return pillRecordList;
    }

    public void setPillRecordList(List<PillRecord> pillRecordList) {
        this.pillRecordList = pillRecordList;
    }
}
