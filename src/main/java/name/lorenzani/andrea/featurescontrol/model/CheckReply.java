package name.lorenzani.andrea.featurescontrol.model;

public class CheckReply {
    private final long[] data;

    public CheckReply(long[] data) {
        this.data = data;
    }

    public long[] getData() {
        return data;
    }
}
