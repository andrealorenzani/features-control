package name.lorenzani.andrea.featurescontrol.datasource;

import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.User;

import java.util.List;
import java.util.Optional;

public interface ClientSource {
    Optional<User> getClient(String id);

    User addClient(String id, String name);

    User setCapabilities(String id, List<Capability> capabilities) throws Exception;

    User removeCapabilities(String id, List<Capability> capabilities) throws Exception;

    List<User> getAllUsers();
}
