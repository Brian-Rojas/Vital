package com.pillbox.controller;

import com.pillbox.entity.PillBox;
import com.pillbox.entity.PillRecord;
import com.pillbox.entity.Schedule;
import com.pillbox.model.EventSource;
import com.pillbox.repository.PillBoxRepository;
import com.pillbox.repository.PillRecordRepository;
import com.pillbox.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AppController {

    private PillBoxRepository pillBoxRepository;
    private PillRecordRepository pillRecordRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public AppController(PillBoxRepository pillBoxRepository, PillRecordRepository pillRecordRepository, ScheduleRepository scheduleRepository) {
        this.pillBoxRepository = pillBoxRepository;
        this.pillRecordRepository = pillRecordRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/schedule/{id}")
    public Schedule getSchedule(@PathVariable("id") Long id){
        PillBox pillBox = pillBoxRepository.findById(id);
        return pillBox.getSchedule();
    }

    @PostMapping("/schedule/{id}/day/{day}")
    public Schedule flipMonday(@PathVariable("id") Long id, @PathVariable("day") String day){
        PillBox pillBox = pillBoxRepository.findById(id);
        pillBox.getSchedule().flipDay(day.toLowerCase());
        return pillBoxRepository.save(pillBox).getSchedule();
    }

    @PostMapping("/schedule/{id}/time/{time}")
    public Schedule setScheduleTime(@PathVariable("id") Long id, @PathVariable("time") String time){
        PillBox pillBox = pillBoxRepository.findById(id);
        String[] hourMinSec = time.split(":");
        int hour = Integer.parseInt(hourMinSec[0]);
        int min = Integer.parseInt(hourMinSec[1]);
        int sec = Integer.parseInt(hourMinSec[2]);
        pillBox.getSchedule().setTime(new Time(hour, min, sec));
        return pillBoxRepository.save(pillBox).getSchedule();
    }

    @GetMapping("/schedule/{id}/events")
    public EventSource getScheduleAsEvents(@PathVariable("id") Long id){
        Schedule schedule = pillBoxRepository.findById(id).getSchedule();
        Set<String> daysMedicationIsDispensed = new HashSet<>();
        return new EventSource("Planned Medication", "#FFF", "#ff7f50", schedule);
    }

    @GetMapping("/pillbox/{id}")
    public PillBox getPillBox(@PathVariable("id") Long id){
        return pillBoxRepository.findById(id);
    }

    @GetMapping("/pillbox/{id}/pillrecords")
    public PillRecord[] getPillBoxPillRecords(@PathVariable("id") Long id){
        List<PillRecord> pillRecords = pillBoxRepository.findById(id).getPillRecordList();
        return pillRecords.toArray(new PillRecord[pillRecords.size()]);
    }

    @GetMapping("/pillrecords/{id}/events")
    public EventSource getPillBoxPillRecordsAsEvents(@PathVariable("id") Long id){
        List<PillRecord> pillRecords = pillBoxRepository.findById(id).getPillRecordList();
        return new EventSource("Medication Taken", "#FFF", "#3a87ad", pillRecords);
    }

    @PostMapping("pillrecords/{id}/events")
    public Boolean savePillBoxPillRecord(@PathVariable("id") Long id){
        PillBox pillBox = pillBoxRepository.findById(id);
        PillRecord pillRecord = new PillRecord();
        pillRecord.setPillTime(pillBox.getSchedule().getTime());
        Calendar calendar = Calendar.getInstance();
        DecimalFormat format = new DecimalFormat("000");
        String yearDay = String.valueOf(calendar.get(Calendar.YEAR)) + format.format(Calendar.DAY_OF_YEAR);
        pillRecord.setYearDay(Integer.parseInt(yearDay));
        pillRecord.setPillDispensed(true);
        pillBox.getPillRecordList().add(pillRecord);
        pillBoxRepository.save(pillBox);
        return true;
    }

    @PostConstruct
    public void createPillBox(){
        PillRecord pillRecord = new PillRecord();
        String yearDay = String.valueOf(Calendar.YEAR) + String.valueOf(Calendar.DAY_OF_YEAR);
        Schedule schedule = new Schedule();
        schedule.setTime(new Time(4, 30, 0));
        schedule.setTuesday(false);
        schedule.setFriday(false);
        PillBox pillBox = new PillBox();
        pillBox.setSchedule(schedule);
        pillBoxRepository.save(pillBox);
    }

    @PostConstruct
    public void createPillRecord(){
        PillBox pillBox = pillBoxRepository.findById((long) 1);
        for(int i = 1; i <= 104; i++) {
            PillRecord pillRecord = new PillRecord();
            Calendar calendar = Calendar.getInstance();
            DecimalFormat format = new DecimalFormat("000");
            String yearDay = String.valueOf(calendar.get(Calendar.YEAR)) + format.format(i);
            pillRecord.setYearDay(Integer.parseInt(yearDay));
            pillRecord.setPillTime(pillBox.getSchedule().getTime());
            pillRecord.setPillDispensed(Math.random() < .5 ? true : false);
            pillBox.getPillRecordList().add(pillRecord);
        }
        pillBoxRepository.save(pillBox);
    }


}
