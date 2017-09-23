package name.lorenzani.andrea.featurescontrol.datasource.impl;

import name.lorenzani.andrea.featurescontrol.datasource.CapabilitySource;
import name.lorenzani.andrea.featurescontrol.datasource.GenericDatasource;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CapabilitySourceImpl extends GenericDatasource<Capability>
        implements CapabilitySource {
    public CapabilitySourceImpl() {
        super(new ConcurrentHashMap<>(), Capability.class);
    }

    @Override
    public Optional<Capability> getCapability(String id) {
        return get(id);
    }

    @Override
    public Capability addCapability(String id, int pos) {
        return add(id, new Capability(id, pos));
    }

    @Override
    public List<Capability> getAllCapabilities() {
        return getAllElements();
    }
}
