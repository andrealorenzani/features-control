package name.lorenzani.andrea.featurescontrol.datasource.impl;

import name.lorenzani.andrea.featurescontrol.datasource.ClientSource;
import name.lorenzani.andrea.featurescontrol.datasource.GenericDatasource;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientSourceImpl extends GenericDatasource<User> implements ClientSource {

    public ClientSourceImpl() {
        super(new ConcurrentHashMap<>(), User.class);
    }

    @Override
    public Optional<User> getClient(String id) {
        return get(id);
    }

    @Override
    public User addClient(String id, String name) {
        return add(id, new User(id, name));
    }

    @Override
    public User setCapabilities(String id, List<Capability> capabilities) throws Exception {
        User user = get(id).orElse(new User(id, ""));
        user.getCapabilities().addAll(capabilities);
        return update(id, user);
    }

    @Override
    public User removeCapabilities(String id, List<Capability> capabilities) throws Exception {
        User user = get(id).orElse(new User(id, ""));
        user.getCapabilities().removeAll(capabilities);
        return update(id, user);
    }

    @Override
    public List<User> getAllUsers() {
        return getAllElements();
    }
}
