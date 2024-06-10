package es.udc.ws.app.client.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientEventFun78Dto {

        private Long eventId;
        private String eventName;
        private LocalDateTime celebrationDate;
        private int runtime;
        private String eventDescription;

        private Boolean eventState;

        private Integer attendance;
        private Integer totalAttendance;

        private Integer AffirativePorcent;

        private  Integer NegativePorcent;

        private LocalDateTime endDate;
        public ClientEventFun78Dto() {
        }

    public ClientEventFun78Dto(Long eventId, String eventName, LocalDateTime celebrationDate,
                               Integer attendance, Integer AffirativePorcent) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.celebrationDate = celebrationDate;
        this.attendance = attendance;
        this.AffirativePorcent = AffirativePorcent;

        if (AffirativePorcent != 0) {
            this.totalAttendance = (int) ((float) attendance * 100 / (float) AffirativePorcent);
        } else {
            this.totalAttendance = attendance;
        }

        System.out.println(totalAttendance + " " + attendance);

        if (totalAttendance != 0) {
            this.NegativePorcent = (int) (((float) (totalAttendance - attendance) / (float) totalAttendance) * 100);
        } else {
            this.NegativePorcent = 100;
        }
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

        public LocalDateTime getEndDate() {
            return endDate;
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

        public Integer getNegativePorcent(){return  NegativePorcent;}

        public Integer getRuntime(){
            return runtime;
        }
        @Override
        public String toString() {
            return "ClientEventDto{" +
                    "eventId=" + eventId +
                    ", eventName='" + eventName + '\'' +
                    ", celebrationDate=" + celebrationDate +
                    ", attendance=" + attendance +
                    ", AffirmativePorcent=" + AffirativePorcent +
                    ", NegativePorcent= "+NegativePorcent+
                    '}';
        }
}


