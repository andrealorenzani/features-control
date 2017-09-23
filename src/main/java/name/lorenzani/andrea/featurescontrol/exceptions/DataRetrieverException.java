package name.lorenzani.andrea.featurescontrol.exceptions;

public class DataRetrieverException extends RuntimeException {

    private final String requestId;

    public DataRetrieverException(String reqid, String message, Throwable cause) {
        super(message, cause);
        this.requestId = reqid;
    }

    public String getRequestId() {
        return requestId;
    }
}
