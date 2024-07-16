package ai.swarmsolver.backend.app.keys;

import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class KeysBean {

    private  final DirectoryStructure directoryStructure;

    public KeysBean(DirectoryStructure directoryStructure) {
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
        return directoryStructure.getConfigDir() + File.separator + "keys.properties";
    }

}
