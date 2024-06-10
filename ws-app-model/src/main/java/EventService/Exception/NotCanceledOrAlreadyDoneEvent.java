package EventService.Exception;

public class NotCanceledOrAlreadyDoneEvent extends Exception{
        private Long eventId;
        public NotCanceledOrAlreadyDoneEvent(Long eventId) {
            super(" Error, this event isnt canceled or Already done: "+ eventId);
            this.eventId= eventId;
        }

        public Long getEventId() {
            return eventId;
        }

        public void setEventId(Long eventId) {
            this.eventId = eventId;
        }

}
