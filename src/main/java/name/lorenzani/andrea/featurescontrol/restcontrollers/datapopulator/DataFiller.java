package name.lorenzani.andrea.featurescontrol.restcontrollers.datapopulator;

import name.lorenzani.andrea.featurescontrol.exceptions.DataFillerException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

/***
 * These REST API are meant to be invoked only for providing data
 * during test. Data source SHOULD be a database
 */
public abstract class DataFiller<T> {
    private Class<T> clazz;

    public DataFiller(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected abstract T addElement(String... arguments) throws Exception;

    protected abstract T addCapabilities(String id, List<String> capabilities) throws Exception;

    protected abstract T removeCapabilities(String id, List<String> capabilities) throws Exception;

    protected abstract List<T> listAllElements();

    @RequestMapping(value = "/add/{id}/{name}", method = RequestMethod.POST)
    public T addElementApi(@PathVariable String id, @PathVariable String name) {
        String requestId = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(id)) {
            throw new DataFillerException(requestId, String.format("Unable to create object %s without id", clazz.getSimpleName()), null);
        }
        if (StringUtils.isEmpty(name)) {
            throw new DataFillerException(requestId, String.format("Unable to create object %s without name", clazz.getSimpleName()), null);
        }
        try {
            return addElement(id, name);
        } catch (Throwable t) {
            throw new DataFillerException(requestId, String.format("Unable to create object %s: %s", clazz.getSimpleName(), t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public T addElementApi(@PathVariable String id) {
        String requestId = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(id)) {
            throw new DataFillerException(requestId, String.format("Unable to create object %s without id", clazz.getSimpleName()), null);
        }
        try {
            return addElement(id);
        } catch (Throwable t) {
            throw new DataFillerException(requestId, String.format("Unable to create object %s: %s", clazz.getSimpleName(), t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/add/capabilities/{id}/{caps}", method = RequestMethod.PUT)
    public T addCapabilitiesApi(@PathVariable String id, @PathVariable List<String> caps) {
        String requestId = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(id)) {
            throw new DataFillerException(requestId, String.format("Unable to add capabilities to object %s without id", clazz.getSimpleName()), null);
        }
        try {
            return addCapabilities(id, caps);
        } catch (Throwable t) {
            throw new DataFillerException(requestId, String.format("Unable to update object %s with new capabilities: %s", clazz.getSimpleName(), t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/del/capabilities/{id}/{caps}", method = RequestMethod.PUT)
    public T removeCapabilitiesApi(@PathVariable String id, @PathVariable List<String> caps) {
        String requestId = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(id)) {
            throw new DataFillerException(requestId, String.format("Unable to remove capabilities to object %s without id", clazz.getSimpleName()), null);
        }
        try {
            return removeCapabilities(id, caps);
        } catch (Throwable t) {
            throw new DataFillerException(requestId, String.format("Unable to remove capabilities to object %s: %s", clazz.getSimpleName(), t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<T> list() {
        String requestId = UUID.randomUUID().toString();
        try {
            return listAllElements();
        } catch (Throwable t) {
            throw new DataFillerException(requestId, String.format("Unable to remove capabilities to object %s: %s", clazz.getSimpleName(), t.getMessage()), t);
        }
    }
}
