package ai.swarmsolver.backend.app.keys;

import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class KeysBean {

    private final String workSpaceName;

    private  final DirectoryStructure directoryStructure;

    public KeysBean(String workSpaceName, DirectoryStructure directoryStructure) {
        this.workSpaceName = workSpaceName;
        this.directoryStructure = directoryStructure;
    }

    @SneakyThrows
    public String getKey(String keyName) {
        try (FileInputStream fileInputStream = new FileInputStream(getKeyProperties());) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(keyName);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private  String getKeyProperties() {
        return directoryStructure.getConfigDir(workSpaceName) + File.separator + "keys.properties";
    }

}
