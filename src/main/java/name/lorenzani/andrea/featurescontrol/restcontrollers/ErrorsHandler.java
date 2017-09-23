package name.lorenzani.andrea.featurescontrol.restcontrollers;

import name.lorenzani.andrea.featurescontrol.exceptions.DataFillerException;
import name.lorenzani.andrea.featurescontrol.exceptions.DataRetrieverException;
import name.lorenzani.andrea.featurescontrol.exceptions.NotAvailable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class FoursquareExceptionHandler {

    @ResponseBody
    @ExceptionHandler(DataFillerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> dataFillerExceptionHandler(DataFillerException ex) {
        Map<String, String> res = new HashMap<>();
        res.put("errorCode", "1");
        res.put("requestId", ex.getRequestId());
        res.put("message", ex.getMessage());
        res.put("cause", ex.getCause().getMessage());
        return res;
    }

    @ResponseBody
    @ExceptionHandler(DataRetrieverException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> dataRetrieverExceptionHandler(DataRetrieverException ex) {
        Map<String, String> res = new HashMap<>();
        res.put("errorCode", "2");
        res.put("requestId", ex.getRequestId());
        res.put("message", ex.getMessage());
        res.put("cause", ex.getCause().getMessage());
        return res;
    }

    @ResponseBody
    @ExceptionHandler(NotAvailable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> notAvailableExceptionHandler(NotAvailable ex) {
        Map<String, String> res = new HashMap<>();
        res.put("errorCode", "3");
        res.put("requestId", ex.getRequestId());
        res.put("message", ex.getMessage());
        res.put("cause", ex.getCause().getMessage());
        return res;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, String> genericExceptionHandler(Exception ex) {
        Map<String, String> res = new HashMap<>();
        res.put("errorCode", "999");
        res.put("message", ex.getMessage());
        res.put("cause", ex.getCause().getMessage());
        return res;
    }
}
