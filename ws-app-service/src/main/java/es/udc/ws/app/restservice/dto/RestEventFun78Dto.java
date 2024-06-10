package es.udc.ws.app.restservice.dto;

import java.time.LocalDateTime;

public class RestEventFun78Dto {
    private Long eventId;
    private String eventName;
    private LocalDateTime celebrationDate;
    private int runtime;
    private String eventDescription;

    private Boolean eventState;

    private Integer attendance;
    private Integer totalAttendance;

    private Integer AffirativePorcent;

    public RestEventFun78Dto() {
    }

    public RestEventFun78Dto(Long eventId, String eventName, LocalDateTime celebrationDate,
                             int runtime, String eventDescription, Boolean eventState, Integer attendance, Integer totalAttendance) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.celebrationDate = celebrationDate;
        this.runtime = runtime;
        this.eventDescription = eventDescription;
        this.eventState = eventState;
        this.attendance = attendance;
        this.totalAttendance = totalAttendance;

        if (totalAttendance != 0) {
            this.AffirativePorcent = (int) (((float) attendance / (float) totalAttendance) * 100);
        } else {
            this.AffirativePorcent = 0;
        }

        System.out.println(attendance + " " + totalAttendance + " " + AffirativePorcent);
    }


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long saleId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(Long movieId) {
        this.eventName = eventName;
    }

    public LocalDateTime getCelebrationDate() {
        return celebrationDate;
    }

    public void setCelebrationDate(LocalDateTime celebrationDate) {
        this.celebrationDate = celebrationDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Boolean getEventState() {
        return eventState;
    }

    public void setEventState(Boolean eventState) {
        this.eventState = eventState;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(Integer totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public Integer getAffirmativePorcent(){return AffirativePorcent;}
    @Override
    public String toString() {
        return "RestEventDto{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", celebrationDate=" + celebrationDate +
                ", attendance=" + attendance +
                ", AffirmativePorcent=" + AffirativePorcent +
                '}';
    }
}
