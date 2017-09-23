package name.lorenzani.andrea.featurescontrol.restcontrollers.datapopulator;

import name.lorenzani.andrea.featurescontrol.datasource.CapabilitySource;
import name.lorenzani.andrea.featurescontrol.exceptions.DataFillerException;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping(value = "/capability")
public class CapabilityDataFiller {

    private final CapabilitySource ds;
    private final AtomicInteger idGenerator;

    @Autowired
    public CapabilityDataFiller(CapabilitySource ds) {
        idGenerator = new AtomicInteger(0);
        this.ds = ds;
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public Capability addElementApi(@PathVariable String id) {
        String requestId = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(id)) {
            throw new DataFillerException(requestId, "Unable to create Capability without id", null);
        }
        try {
            return ds.addCapability(id, idGenerator.getAndIncrement());
        } catch (Throwable t) {
            throw new DataFillerException(requestId, String.format("Unable to create Capability: %s", t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Capability> list() {
        String requestId = UUID.randomUUID().toString();
        List<Capability> res = ds.getAllCapabilities();
        res.sort(Comparator.comparingInt(Capability::getPos));
        return res;
    }
}