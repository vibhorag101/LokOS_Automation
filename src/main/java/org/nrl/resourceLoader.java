package org.nrl;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class resourceLoader {
    public URI getResourcePath(String fileName) throws URISyntaxException {
        // for fileName return the classpath of the file
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException("file is not found!!!!!");
        } else {
            return url.toURI();
        }
    }

}
