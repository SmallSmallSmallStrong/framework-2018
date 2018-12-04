package com.sdyijia.myclass;

import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    private Logger logger = Logger.getLogger(MyClassLoader.class);

    @Override
    protected Class<?> findClass(String name) {
        String myPath = "file://" + MyClassLoader.class.getResource("/").getPath().replace("test-classes", "classes") + name.replaceAll("\\.", "/") + ".class";
        logger.debug(String.format("class file path：%s", myPath));
        byte[] cLassBytes = null;
        Path path = null;
        try {
            path = Paths.get(new URI(myPath));
            cLassBytes = Files.readAllBytes(path);
            return defineClass(name, cLassBytes, 0, cLassBytes.length);
        } catch (IOException | URISyntaxException e) {
            logger.error(String.format("错误：%s", e.getMessage()));
        }
        return null;
    }
}
