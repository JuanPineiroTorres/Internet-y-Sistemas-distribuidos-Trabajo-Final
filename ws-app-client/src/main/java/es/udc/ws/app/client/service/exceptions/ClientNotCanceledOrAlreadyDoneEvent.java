package es.udc.ws.app.client.service.exceptions;

public class ClientNotCanceledOrAlreadyDoneEvent extends Exception{
        private Long eventId;
        public ClientNotCanceledOrAlreadyDoneEvent(Long eventId) {
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
