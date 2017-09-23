package name.lorenzani.andrea.featurescontrol.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Capability> capabilities;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.capabilities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }
}
