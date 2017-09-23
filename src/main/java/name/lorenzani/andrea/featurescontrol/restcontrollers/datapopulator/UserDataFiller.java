package name.lorenzani.andrea.featurescontrol.restcontrollers.datapopulator;

import name.lorenzani.andrea.featurescontrol.datasource.CapabilitySource;
import name.lorenzani.andrea.featurescontrol.datasource.ClientSource;
import name.lorenzani.andrea.featurescontrol.exceptions.DataFillerException;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserDataFiller extends DataFiller<User> {

    private final ClientSource ds;
    private final CapabilitySource cs;

    @Autowired
    public UserDataFiller(ClientSource ds, CapabilitySource cs) {
        super(User.class);
        this.ds = ds;
        this.cs = cs;
    }

    @Override
    public User addElementApi(@PathVariable String id) {
        String requestId = UUID.randomUUID().toString();
        throw new DataFillerException(requestId, "Unable to create User without name", null);
    }

    @Override
    protected User addElement(String... arguments) throws Exception {
        String requestId = UUID.randomUUID().toString();
        if (arguments.length == 0)
            throw new DataFillerException(requestId, "Unable to create User without id and name", null);
        if (arguments.length == 1) throw new DataFillerException(requestId, "Unable to create User without name", null);
        return ds.addClient(arguments[0], arguments[1]);
    }

    @Override
    protected User addCapabilities(String id, List<String> capabilities) throws Exception {
        List<Capability> caps =
                capabilities.stream()
                        .map(cap -> cs.getCapability(cap)
                                .orElseThrow(() -> new IllegalArgumentException(String.format("Capability with id %s does not exist", cap)))
                        ).collect(Collectors.toList());
        return ds.setCapabilities(id, caps);
    }

    @Override
    protected User removeCapabilities(String id, List<String> capabilities) throws Exception {
        List<Capability> caps =
                capabilities.stream()
                        .map(cap -> cs.getCapability(cap)
                                .orElseThrow(() -> new IllegalArgumentException(String.format("Capability with id %s does not exist", cap)))
                        ).collect(Collectors.toList());
        return ds.removeCapabilities(id, caps);
    }

    @Override
    protected List<User> listAllElements() {
        return ds.getAllUsers();
    }
}
