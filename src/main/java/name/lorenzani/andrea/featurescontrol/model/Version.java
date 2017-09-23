package name.lorenzani.andrea.featurescontrol.model;

import java.util.ArrayList;
import java.util.List;

public class Version {
    private String id;
    private List<Capability> capabilities;

    public Version() {
    }

    public Version(String id) {
        this.id = id;
        this.capabilities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }
}
