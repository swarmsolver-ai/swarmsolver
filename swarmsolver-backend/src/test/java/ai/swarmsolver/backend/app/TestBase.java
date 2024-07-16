package ai.swarmsolver.backend.app;

import ai.swarmsolver.backend.app.testinfra.DirectoryStructurePrinter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
abstract public class TestBase {

    @Rule
    public TemporaryFolder temporaryFolder = TemporaryFolder.builder().build();

    @Autowired
    public ApplicationProperties applicationProperties;

    @Before
    public void setUp() throws IOException {
        File dataFolder = temporaryFolder.newFolder("data");

        File configFolder = new File(dataFolder, "config");
        configFolder.mkdir();

        File workspaceFolder = new File(dataFolder, "workspace");
        workspaceFolder.mkdir();

        applicationProperties.setDataDir(dataFolder.getAbsolutePath());
        log.info("======> data dir set to: " + applicationProperties.getDataDir());
    }

    protected void printDirStructure(String stateDescription) {
        DirectoryStructurePrinter.dumpWorkspaceState(new File(this.applicationProperties.getDataDir()), stateDescription);
    }

}
