package net.itimothy.elasticsearch.restapispec;

import com.google.gson.Gson;
import net.itimothy.elasticsearch.restapispec.model.Api;
import net.itimothy.elasticsearch.restapispec.model.RestApiSpecData;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OfficialRestApiSpecDataProvider {

    // TODO: caching

    public Map<String, Api> getData(Version version) {
        final HashMap<String, Api> data = new HashMap<>();

        try {
            final Gson gson = new Gson();
            final String path = String.format(
                "rest-api-spec/%s.%s",
                version.major,
                version.minor
            );
            for (InputStream stream : getFiles(path)) {
                final String json = IOUtils.toString(stream, "UTF-8");
                try {
                    AccessController.doPrivileged(
                        new PrivilegedAction<Void>() {
                            public Void run() {
                                RestApiSpecData restApiSpecData = gson.fromJson(json, RestApiSpecData.class);
                                Map.Entry<String, Api> entry = restApiSpecData.entrySet().iterator().next();
                                data.put(entry.getKey(), entry.getValue());
                                return null; // nothing to return
                            }
                        }
                    );

                } finally {
                    IOUtils.closeQuietly(stream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private List<InputStream> getFiles(String path) throws IOException, URISyntaxException {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        List<InputStream> result = new ArrayList<>();

        if (jarFile.isFile()) {  // Run with JAR file
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();

                if (name.startsWith(path + "/") && !name.equals(path + "/")) { //filter according to the path
                    result.add(getClass().getClassLoader().getResourceAsStream(name));
                }
            }
            jar.close();

        } else { // Run with IDE
            final URL url = getClass().getResource("/" + path);
            if (url != null) {
                final File apps = new File(url.toURI());
                for (File file : apps.listFiles()) {

                    result.add(new FileInputStream(file.getPath()));
                }

            }
        }

        return result;
    }
}
