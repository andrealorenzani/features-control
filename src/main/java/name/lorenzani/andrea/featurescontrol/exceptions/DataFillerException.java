package name.lorenzani.andrea.featurescontrol.exceptions;

public class DataFillerException extends RuntimeException {

    private final String requestId;

    public DataFillerException(String reqid, String message, Throwable cause) {
        super(message, cause);
        this.requestId = reqid;
    }

    public String getRequestId() {
        return requestId;
    }
}
