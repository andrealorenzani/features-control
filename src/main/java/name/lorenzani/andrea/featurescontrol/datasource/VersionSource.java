package name.lorenzani.andrea.featurescontrol.datasource;

import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.Version;

import java.util.List;
import java.util.Optional;

public interface VersionSource {
    Optional<Version> getVersion(String id);

    Version addVersion(String id);

    Version setCapabilities(String id, List<Capability> capabilities) throws Exception;

    Version removeCapabilities(String id, List<Capability> capabilities) throws Exception;

    List<Version> getAllVersions();
}
