package name.lorenzani.andrea.featurescontrol.restcontrollers.datapopulator;

import name.lorenzani.andrea.featurescontrol.datasource.CapabilitySource;
import name.lorenzani.andrea.featurescontrol.datasource.VersionSource;
import name.lorenzani.andrea.featurescontrol.exceptions.DataFillerException;
import name.lorenzani.andrea.featurescontrol.exceptions.NotAvailable;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/version")
public class VersionDataFiller extends DataFiller<Version> {

    private final VersionSource ds;
    private final CapabilitySource cs;

    @Autowired
    public VersionDataFiller(VersionSource ds, CapabilitySource cs) {
        super(Version.class);
        this.ds = ds;
        this.cs = cs;
    }

    @Override
    public Version addElementApi(@PathVariable String id, @PathVariable String name) {
        String requestId = UUID.randomUUID().toString();
        throw new NotAvailable(requestId, "Not found");
    }

    @Override
    protected Version addElement(String... arguments) throws Exception {
        String requestId = UUID.randomUUID().toString();
        if (arguments.length == 0)
            throw new DataFillerException(requestId, "Unable to create Version without id", null);
        return ds.addVersion(arguments[0]);
    }

    @Override
    protected Version addCapabilities(String id, List<String> capabilities) throws Exception {
        List<Capability> caps =
                capabilities.stream()
                        .map(cap -> cs.getCapability(cap)
                                .orElseThrow(() -> new IllegalArgumentException(String.format("Capability with id %s does not exist", cap)))
                        ).collect(Collectors.toList());
        return ds.setCapabilities(id, caps);
    }

    @Override
    protected Version removeCapabilities(String id, List<String> capabilities) throws Exception {
        List<Capability> caps =
                capabilities.stream()
                        .map(cap -> cs.getCapability(cap)
                                .orElseThrow(() -> new IllegalArgumentException(String.format("Capability with id %s does not exist", cap)))
                        ).collect(Collectors.toList());
        return ds.removeCapabilities(id, caps);
    }

    @Override
    protected List<Version> listAllElements() {
        return ds.getAllVersions();
    }
}