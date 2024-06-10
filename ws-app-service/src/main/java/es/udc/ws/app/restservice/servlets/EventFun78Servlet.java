package es.udc.ws.app.restservice.servlets;
import java.time.LocalDate;
import Event.Event;
import EventService.EventServiceFactory;
import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.NotCanceledOrAlreadyDoneEvent;
import EventService.Exception.OutOfTimeException;
import es.udc.ws.app.restservice.dto.EventFun78toRestEventDtoConversor;
import es.udc.ws.app.restservice.dto.EventToRestEventDtoConversor;
import es.udc.ws.app.restservice.dto.RestEventDto;
import es.udc.ws.app.restservice.dto.RestEventFun78Dto;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestEventDtoConversor;
import es.udc.ws.app.restservice.json.JsonToRestEventFun78Conversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static es.udc.ws.util.servlet.ServletUtils.normalizePath;



public class EventFun78Servlet extends RestHttpServletTemplate {
    @Override

    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException, InstanceNotFoundException {
        String path = normalizePath(req.getPathInfo());
            if ((path) != null) {
                String[] pathdivided = path.split("/");
                try {
                    Long.valueOf(pathdivided[1]);
                } catch (NumberFormatException var6) {
                    throw new InputValidationException("Invalid Request: invalid eventId id '" + pathdivided[1] + "'");
                }
                Long eventId = Long.valueOf(pathdivided[1]);
                try {
                    EventServiceFactory.getService().removeEventCanceledOrAlreadyDone(eventId);
                } catch (NotCanceledOrAlreadyDoneEvent e) {
                    ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN, AppExceptionToJsonConversor.NotCanceledOrAlreadyDoneEvent(e), null);
                }
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT,
                        null, null);
            }

    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, InputValidationException {
        String path = normalizePath(req.getPathInfo());
        if ((path) != null) {
            String startDateParam = req.getParameter("startDate");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate startDate=LocalDate.parse(startDateParam,formatter);
            int attendant = Integer.parseInt(req.getParameter("Minattendant"));
            LocalDate finishDate=startDate.plusDays(90);
            List<Event> events=  EventServiceFactory.getService().findEventByFutureDateWithAttend(startDate,finishDate,attendant);
            List<RestEventFun78Dto> eventDtos = EventFun78toRestEventDtoConversor.toRestEventFun78Dtos(events);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestEventFun78Conversor.toArrayNode(eventDtos), null);
        }
    }

}
