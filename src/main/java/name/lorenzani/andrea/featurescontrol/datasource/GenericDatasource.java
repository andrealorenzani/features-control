package name.lorenzani.andrea.featurescontrol.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GenericDatasource<T> {
    private final ConcurrentHashMap<String, T> datastore;
    private final Class<T> clazz;

    public GenericDatasource(ConcurrentHashMap<String, T> datastore, Class<T> clazz) {
        this.datastore = datastore;
        this.clazz = clazz;
    }

    protected Optional<T> get(String id) {
        System.out.println("Asking for " + id + " in (" + (datastore.get(id) != null) + "): " + datastore.values());
        return Optional.ofNullable(datastore.getOrDefault(id, null));
    }

    protected T update(String id, T newValue) {
        if (datastore.containsKey(id)) {
            datastore.put(id, newValue);
            return newValue;
        } else {
            throw new IllegalArgumentException(String.format("No %s with id %s", clazz.getSimpleName(), id));
        }
    }

    protected T add(String id, T newValue) {
        if (datastore.containsKey(id)) {
            throw new IllegalArgumentException(String.format("A %s with id %s already exists", clazz.getSimpleName(), id));
        } else {
            datastore.put(id, newValue);
            return newValue;
        }
    }

    protected List<T> getAllElements() {
        return new ArrayList<T>(datastore.values());
    }
}
