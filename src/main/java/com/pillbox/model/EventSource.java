package com.pillbox.model;

import com.pillbox.entity.PillRecord;
import com.pillbox.entity.Schedule;

import java.util.*;

public class EventSource {

    private Event[] events;
    private String color;
    private String textColor;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public EventSource(String title, String textColor, String color, List<PillRecord> pillRecords) {
        this.color = color;
        this.textColor = textColor;
        List<Event> eventList = new ArrayList<>();
        for(PillRecord pillRecord : pillRecords){
            if(pillRecord.getPillDispensed()){
                String time = pillRecord.getPillTime().toString();
                String year = String.valueOf(pillRecord.getYearDay()).substring(0,4);
                int dayOfYear = Integer.parseInt(String.valueOf(pillRecord.getYearDay()).substring(4,7));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
                int month = calendar.get(Calendar.MONTH)+1;
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                Event e = new Event();
                e.setStart(year + "-" + (month<10?"0":"") + month + "-" + (dayOfMonth<10?"0":"") + dayOfMonth + "T" + time);
                e.setTitle(title);
                eventList.add(e);
            }
        }
        this.events = eventList.toArray(new Event[eventList.size()]);
    }

    public EventSource(String title, String textColor, String color, Schedule schedule) {
        this.color = color;
        this.textColor = textColor;
        Set<Integer> daysMedicationIsDispensed = addDaysToSet(schedule);
        Calendar calendar = Calendar.getInstance();
        List<Event> eventList = new ArrayList<>();
        for(int i = calendar.get(Calendar.DAY_OF_YEAR); i <= 365; i++){
            calendar.set(Calendar.DAY_OF_YEAR, i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if(daysMedicationIsDispensed.contains(dayOfWeek)){
                int month = calendar.get(Calendar.MONTH)+1;
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                Event e = new Event();
                e.setStart(calendar.get(Calendar.YEAR) + "-" + (month<10?"0":"") + month + "-" + (dayOfMonth<10?"0":"") + dayOfMonth + "T" + schedule.getTime().toString());
                e.setTitle(title);
                eventList.add(e);
            }
        }
        this.events = eventList.toArray(new Event[eventList.size()]);
    }

    public HashSet<Integer> addDaysToSet(Schedule schedule){
        HashSet<Integer> daysMedicationIsDispensed = new HashSet<>();
        if(schedule.getSunday()){
            daysMedicationIsDispensed.add(1);
        }
        if(schedule.getMonday()){
            daysMedicationIsDispensed.add(2);
        }
        if(schedule.getTuesday()){
            daysMedicationIsDispensed.add(3);
        }
        if(schedule.getWednesday()){
            daysMedicationIsDispensed.add(4);
        }
        if(schedule.getThursday()){
            daysMedicationIsDispensed.add(5);
        }
        if(schedule.getFriday()){
            daysMedicationIsDispensed.add(6);
        }
        if(schedule.getSaturday()){
            daysMedicationIsDispensed.add(7);
        }
        return daysMedicationIsDispensed;
    }

    class Event {
        private String title;
        private String start;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }
    }

}