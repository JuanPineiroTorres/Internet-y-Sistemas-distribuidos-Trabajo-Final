package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.app.client.service.dto.ClientEventFun78Dto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientEventFun78Conversor {
    public static ObjectNode toObjectNode(ClientEventFun78Dto event) throws IOException {

        ObjectNode eventObject = JsonNodeFactory.instance.objectNode();

        if (event.getEventId() != null) {
            eventObject.put("eventId", event.getEventId());
        }
        eventObject.put("eventName", event.getEventName())
                .put("celebrationDate", event.getCelebrationDate().toString())
                .put("runtime", ChronoUnit.HOURS.between(event.getCelebrationDate(),event.getEndDate()))
                .put("description", event.getEventDescription())
                .put("eventDescription", event.getEventDescription())
                .put("eventState", event.getEventState())
                .put("attendance", event.getAttendance())
                .put("AffirmativePorcent",event.getAffirmativePorcent())
                .put("NegativePorcent",event.getNegativePorcent())
                .put("totalAttendance", event.getTotalAttendance());


        return eventObject;
    }

    public static ClientEventFun78Dto toClientEventFun78Dto(InputStream jsonEvent) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonEvent);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientEventFun78Dto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientEventFun78Dto> toClientEventFun78Dtos(InputStream jsonMovies) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonMovies);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode eventsArray = (ArrayNode) rootNode;
                List<ClientEventFun78Dto> eventDtos = new ArrayList<>(eventsArray.size());
                for (JsonNode eventNode : eventsArray) {
                    eventDtos.add(toClientEventFun78Dto(eventNode));
                }

                return eventDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientEventFun78Dto toClientEventFun78Dto(JsonNode eventNode) throws ParsingException {
        if (eventNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode eventObject = (ObjectNode) eventNode;
            System.out.println(eventObject.get("AffirmativePorcent"));
            JsonNode eventIdNode = eventObject.get("eventId");
            Long movieId = (eventIdNode != null) ? eventIdNode.longValue() : null;
            String eventName = eventObject.get("eventName").textValue().trim();
            LocalDateTime celebrationDate = LocalDateTime.parse(eventObject.get("celebrationDate").textValue().trim());
            JsonNode eventattendanceNode = eventObject.get("attendance");
            Integer attendance = (eventattendanceNode != null) ? eventattendanceNode.intValue() : 0;
            JsonNode AffirmativePorcentNode = eventObject.get("AffirmativePorcent");
            Integer AffirmativePorcent = (AffirmativePorcentNode != null) ? AffirmativePorcentNode.intValue() : 0;
            return new ClientEventFun78Dto(movieId, eventName, celebrationDate ,attendance,AffirmativePorcent);
        }
    }
}
