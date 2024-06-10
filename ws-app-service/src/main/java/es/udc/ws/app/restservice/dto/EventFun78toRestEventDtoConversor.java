package es.udc.ws.app.restservice.dto;

import Event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventFun78toRestEventDtoConversor {

    public static RestEventFun78Dto toRestEventFun78Dto(Event event) {
        return new RestEventFun78Dto(event.getEventId(), event.getName(),event.getCelebrationDate(),event.getRuntime(), event.getDescription(),
                event.getEventState(),event.getAttendance(), event.getAttendance() + event.getNot_Attendance());
    }

    public static Event toEvent(RestEventFun78Dto event) {
        return new Event(event.getEventId(), event.getEventName(),event.getEventDescription(), event.getCelebrationDate(),null,event.getRuntime(),
                event.getEventState(),event.getAttendance(),event.getTotalAttendance() - event.getAttendance());
    }

    public static List<RestEventFun78Dto> toRestEventFun78Dtos(List<Event> events) {
        List<RestEventFun78Dto> eventDtos = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            eventDtos.add(toRestEventFun78Dto(event));
        }
        return eventDtos;
    }
}
