package name.lorenzani.andrea.featurescontrol.datasource.impl;

import name.lorenzani.andrea.featurescontrol.datasource.GenericDatasource;
import name.lorenzani.andrea.featurescontrol.datasource.VersionSource;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.Version;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VersionSourceImpl extends GenericDatasource<Version> implements VersionSource {

    public VersionSourceImpl() {
        super(new ConcurrentHashMap<>(), Version.class);
    }

    @Override
    public Optional<Version> getVersion(String id) {
        return get(id);
    }

    @Override
    public Version addVersion(String id) {
        return add(id, new Version(id));
    }

    @Override
    public Version setCapabilities(String id, List<Capability> capabilities) throws Exception {
        Version Version = get(id).orElse(new Version(id));
        Version.getCapabilities().addAll(capabilities);
        return update(id, Version);
    }

    @Override
    public Version removeCapabilities(String id, List<Capability> capabilities) throws Exception {
        Version Version = get(id).orElse(new Version(id));
        Version.getCapabilities().removeAll(capabilities);
        return update(id, Version);
    }

    @Override
    public List<Version> getAllVersions() {
        return getAllElements();
    }
}
