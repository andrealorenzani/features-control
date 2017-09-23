package name.lorenzani.andrea.featurescontrol.model;

public class Capability {
    private String name;
    private int pos;

    public Capability() {
    }

    public Capability(String name, int pos) {
        this.name = name;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public int getPos() {
        return pos;
    }
}
