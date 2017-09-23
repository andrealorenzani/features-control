package name.lorenzani.andrea.featurescontrol.exceptions;

public class NotAvailable extends RuntimeException {

    private final String requestId;

    public NotAvailable(String reqId, String s) {
        super(s);
        this.requestId = reqId;
    }

    public String getRequestId() {
        return requestId;
    }
}
