package es.udc.ws.app.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.restservice.dto.RestEventDto;
import es.udc.ws.app.restservice.dto.RestEventFun78Dto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class JsonToRestEventFun78Conversor {
    public static ObjectNode toObjectNode(RestEventFun78Dto event) {

        ObjectNode eventObject = JsonNodeFactory.instance.objectNode();

        if (event.getEventId() != null) {
            eventObject.put("eventId", event.getEventId());
        }
        eventObject.put("eventName", event.getEventName()).
                put("celebrationDate", event.getCelebrationDate().toString());
        if(event.getAttendance()!=null){
            eventObject.put("attendance", event.getAttendance());
        }
        if(event.getAffirmativePorcent()!= null){
            eventObject.put("AffirmativePorcent",event.getAffirmativePorcent());
        }

        return eventObject;
    }

    public static ArrayNode toArrayNode(List<RestEventFun78Dto> events) {
        ArrayNode eventsNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < events.size(); i++) {
            RestEventFun78Dto eventDto = events.get(i);
            ObjectNode eventObject = toObjectNode(eventDto);
            eventsNode.add(eventObject);
        }
        return eventsNode;
    }

    public static RestEventFun78Dto toRestEventFun78Dto(InputStream jsonEvent) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonEvent);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode eventObject = (ObjectNode) rootNode;
                JsonNode eventIdNode = eventObject.get("eventId");
                Long eventId = (eventIdNode != null) ? eventIdNode.longValue() : null;
                String eventName = eventObject.get("eventName").textValue().trim();
                LocalDateTime celebrationDate = LocalDateTime.parse(eventObject.get("celebrationDate").textValue().trim());
                int runtime =  eventObject.get("runtime").intValue();
                String description = eventObject.get("description").textValue().trim();
                JsonNode eventstateNode = eventObject.get("eventState");
                Boolean state = eventstateNode == null || eventstateNode.booleanValue();
                JsonNode eventattendanceNode = eventObject.get("attendance");
                Integer attendance = (eventattendanceNode != null) ? eventattendanceNode.intValue() : 0;
                JsonNode totalAttendanceNode = eventObject.get("totalAttendance");
                Integer totalAttendance = (totalAttendanceNode != null) ? totalAttendanceNode.intValue() : 0;


                return new RestEventFun78Dto(eventId, eventName,celebrationDate, runtime, description, state,attendance, totalAttendance);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
}
