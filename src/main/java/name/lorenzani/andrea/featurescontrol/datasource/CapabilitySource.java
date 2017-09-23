package name.lorenzani.andrea.featurescontrol.datasource;

import name.lorenzani.andrea.featurescontrol.model.Capability;

import java.util.List;
import java.util.Optional;

public interface CapabilitySource {
    Optional<Capability> getCapability(String id);

    Capability addCapability(String id, int pos);

    List<Capability> getAllCapabilities();
}
