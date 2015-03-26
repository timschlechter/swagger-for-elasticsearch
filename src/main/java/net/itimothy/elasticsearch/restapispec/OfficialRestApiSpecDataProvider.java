package net.itimothy.elasticsearch.restapispec;

import com.google.gson.Gson;
import net.itimothy.elasticsearch.restapispec.model.Api;
import net.itimothy.elasticsearch.restapispec.model.RestApiSpecData;
import org.elasticsearch.Version;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class OfficialRestApiSpecDataProvider {

    // TODO: caching

    public Map<String, Api> getData(Version version) {
        HashMap<String, Api> data = new HashMap<>();

        URL url = OfficialRestApiSpecDataProvider.class.getResource(
            String.format(
                "/rest-api-spec/%s.%s",
                version.major,
                version.minor
            )
        );

        try {
            File dir = new File(url.toURI());
            Gson gson = new Gson();

            for (File file : dir.listFiles()) {
                String json = new String(readAllBytes(get(file.getPath())));

                RestApiSpecData restApiSpecData = gson.fromJson(json, RestApiSpecData.class);
                Map.Entry<String, Api> entry = restApiSpecData.entrySet().iterator().next();
                data.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
