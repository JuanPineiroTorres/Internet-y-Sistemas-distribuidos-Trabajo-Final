package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientEventService;
import es.udc.ws.app.client.service.ClientEventServiceFactory;
import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.app.client.service.dto.ClientEventFun78Dto;
import es.udc.ws.app.client.service.dto.ClientResponseDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.w3c.dom.events.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppServiceClient {
    public static void main(String[] args) {

        if (args.length == 0) {
            printUsageAndExit();
        }
        ClientEventService clientEventService =
                ClientEventServiceFactory.getService();
        if ("-addEvent".equalsIgnoreCase(args[0])) {
            validateArgs(args, 5, new int[]{});

            try {
                Long eventId = clientEventService.addEvent(new ClientEventDto(null,
                        args[1], args[2], LocalDateTime.parse(args[3]),
                        LocalDateTime.parse(args[4]), true, 0, 0));

                System.out.println("Event " + eventId + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-findEvent".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[]{1});

            ClientEventDto eventDto;
            try {
                eventDto = clientEventService.findEvent(Long.parseLong(args[1]));

                System.out.println("Los datos del evento son: " + eventDto);

            } catch (NumberFormatException | InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-cancel".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[]{1});

            try {
                clientEventService.cancelEvent(Long.parseLong(args[1]));

                System.out.println("Evento con id =  " + Long.parseLong(args[1]) + " cancelado");

            } catch (NumberFormatException | InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-findEvents".equalsIgnoreCase(args[0])) {
            List<ClientEventDto> listEvents = new ArrayList<>();
            try {
                if(args.length==3){
                    validateArgs(args, 3, new int[]{});
                    listEvents = clientEventService.findEvents(LocalDate.parse(args[1]), args[2]);
                }else{
                    listEvents = clientEventService.findEvents(LocalDate.parse(args[1]), null);
                }
                System.out.println("La lista de los eventos anteriores a la fecha =  " + args[1] + " es: " + listEvents);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }


        } else if ("-respond".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[]{1});

            try {
                Long responseId = clientEventService.addResponse(new ClientResponseDto(null,
                        Long.valueOf(args[1]), args[2], Boolean.valueOf(args[3])));
                System.out.println("Respuesta con id =  " + responseId + " ha sido creada");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if ("-findResponses".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[]{});

            try {
                List<ClientResponseDto> responses = clientEventService.findResponses(args[1], Boolean.parseBoolean(args[2]));
                if(Boolean.parseBoolean(args[2])){
                    System.out.println("Las respuestas para el usuario "+ args[1] + " con respuestas afirmativas serían: "+responses);
                }else{
                    System.out.println("Las respuestas totales para el usuario "+ args[1] + " serían: " + responses);
                }

            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-findPopularEvents".equalsIgnoreCase(args[0])){
            validateArgs(args,3,new int[]{});
            try {
                List<ClientEventFun78Dto> responses=clientEventService.findEventByFutureDateWithAttend(LocalDate.parse(args[2]),Integer.parseInt(args[1]));
                System.out.println("La lista de los eventos  a la fecha =  " + args[2] + " con mas de "+args[1]+" respuestas es: " + responses);
            } catch (Exception ex){
                ex.printStackTrace(System.err);
            }
        } else if("-removeEvent".equalsIgnoreCase(args[0])){
            validateArgs(args,2,new int[]{});
            try {
                clientEventService.removeEventCanceledOrAlreadyDone(Long.parseLong(args[1]));
                System.out.println("Evento Borrado con id: "+ args[1]);
            } catch (Exception ex){
                ex.printStackTrace(System.err);
            }
    }
    }

    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if (expectedArgs != args.length) {
            printUsageAndExit();
        }
        for (int i = 0; i < numericArguments.length; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch (NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [addEvent]    EventServiceClient -addEvent <name> <description> <start_date> <end_date> \n" +
                "    [cancel]    EventServiceClient -cancel <eventId> \n" +
                "    [findEvent]    EventServiceClient -findEvent <eventId> \n" +
                "    [findEvents]    EventServiceClient -findEvents <untilDate> <keyword> \n" +
                "    [respond]    EventServiceClient -respond <eventId> <userEmail> <response> \n" +
                "    [findResponses]    EventServiceClient -findResponses <userEmail> <onlyAffirmative> \n");
    }

}