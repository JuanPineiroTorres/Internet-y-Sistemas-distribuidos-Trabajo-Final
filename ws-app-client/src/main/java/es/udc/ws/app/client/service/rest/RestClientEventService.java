package es.udc.ws.app.client.service.rest;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.udc.ws.app.client.service.ClientEventService;
import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.app.client.service.dto.ClientEventFun78Dto;
import es.udc.ws.app.client.service.dto.ClientResponseDto;
import es.udc.ws.app.client.service.exceptions.ClientAlreadyCanceledException;
import es.udc.ws.app.client.service.exceptions.ClientAlreadyResponseException;
import es.udc.ws.app.client.service.exceptions.ClientOutOfTimeException;
import es.udc.ws.app.client.service.rest.json.JsonToClientEventDtoConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientEventFun78Conversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientExceptionConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientResponseDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RestClientEventService implements ClientEventService {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientEventService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long addEvent(ClientEventDto event) throws InputValidationException {
        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "event").
                    bodyStream(toEventInputStream(event), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientEventDtoConversor.toClientEventDto(response.getEntity().getContent()).getEventId();

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientEventDto findEvent(Long eventId) throws InstanceNotFoundException {
        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "event/"
                            + URLEncoder.encode(String.valueOf(eventId), "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientEventDtoConversor.toClientEventDto(response.getEntity()
                    .getContent());
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelEvent(Long eventId) throws InstanceNotFoundException, ClientOutOfTimeException, ClientAlreadyCanceledException, InputValidationException {
        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "event/" + eventId + "/cancel").
                    execute().returnResponse();
            System.out.println(getEndpointAddress() + "event/"
                    + URLEncoder.encode(eventId + "/cancel", "UTF-8"));
            System.out.println(response);
            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);
        } catch (InputValidationException | InstanceNotFoundException | ClientAlreadyCanceledException |
                 ClientOutOfTimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientEventDto> findEvents(LocalDate finalDate, String keywords) {

        try {
            HttpResponse response;
            if(keywords==null){
                response = Request.Get(getEndpointAddress() + "event?finalDate="
                                + URLEncoder.encode(finalDate.toString(), "UTF-8")).
                        execute().returnResponse();
            }else{
                response = Request.Get(getEndpointAddress() + "event?finalDate="
                                + URLEncoder.encode(finalDate.toString(), "UTF-8") + "&keywords=" + URLEncoder.encode(keywords, "UTF-8")).
                        execute().returnResponse();
            }

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientEventDtoConversor.toClientEventDtos(response.getEntity()
                    .getContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ClientEventFun78Dto> findEventByFutureDateWithAttend(LocalDate afterFirstDate, int attent){
        try{
            HttpResponse response;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = afterFirstDate.format(formatter);
            response=Request.Get(getEndpointAddress()+"EventFun78/?startDate=" + URLEncoder.encode((formattedDate), "UTF-8") + "&Minattendant="
            + attent).execute().returnResponse();
            validateStatusCode(HttpStatus.SC_OK,response);
            return JsonToClientEventFun78Conversor.toClientEventFun78Dtos(response.getEntity().getContent());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeEventCanceledOrAlreadyDone(long eventId) {
        try{
            HttpResponse response;
            response=Request.Post(getEndpointAddress()+"EventFun78/"+eventId).execute().returnResponse();
            validateStatusCode(HttpStatus.SC_NO_CONTENT,response);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public Long addResponse(ClientResponseDto responseDto) throws InputValidationException, InstanceNotFoundException, ClientOutOfTimeException, ClientAlreadyCanceledException, ClientAlreadyResponseException {
        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "response").
                    bodyStream(toResponseInputStream(responseDto), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientResponseDtoConversor.toClientResponseDto(response.getEntity().getContent()).getResponseId();

        } catch (InputValidationException | InstanceNotFoundException | ClientAlreadyCanceledException |
                 ClientOutOfTimeException | ClientAlreadyResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientResponseDto> findResponses(String userEmail, boolean asistence) throws InputValidationException {

        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "response?userEmail="
                            + URLEncoder.encode(userEmail, "UTF-8") + "&asistence=" + URLEncoder.encode(String.valueOf(asistence), "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientResponseDtoConversor.toClientResponseDtos(response.getEntity()
                    .getContent());
        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toEventInputStream(ClientEventDto event) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientEventDtoConversor.toObjectNode(event));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private InputStream toEventFun78InputStream(ClientEventFun78Dto event){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientEventFun78Conversor.toObjectNode(event));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream toResponseInputStream(ClientResponseDto response) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientResponseDtoConversor.toObjectNode(response));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private void validateStatusCode(int successCode, HttpResponse response) throws Exception {

        try {

            int statusCode = response.getStatusLine().getStatusCode();

            /* Success? */
            if (statusCode == successCode) {
                return;
            }

            /* Handler error. */
            switch (statusCode) {
                case HttpStatus.SC_NOT_FOUND -> throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                        response.getEntity().getContent());
                case HttpStatus.SC_BAD_REQUEST -> throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                        response.getEntity().getContent());
                case HttpStatus.SC_FORBIDDEN -> throw JsonToClientExceptionConversor.fromForbiddenErrorCode(
                        response.getEntity().getContent());
                case HttpStatus.SC_GONE -> throw JsonToClientExceptionConversor.fromGoneErrorCode(
                        response.getEntity().getContent());
                default -> throw new RuntimeException("HTTP error; status code = "
                        + statusCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
