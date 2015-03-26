package net.itimothy.elasticsearch.description;

import com.google.gson.Gson;
import net.itimothy.elasticsearch.restApiSpecs.Data;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;

import java.io.File;
import java.net.URL;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class ApiSpecsRoutesProvider extends RoutesProvider {
    public ApiSpecsRoutesProvider(Client client) {
        super("Alias management", client, new ModelsCatalog(client));
    }

    public ApiSpecsRoutesProvider(String defaultGroup, Client client, ModelsCatalog modelsCatalog) {
        super(defaultGroup, client, modelsCatalog);
    }

    @Override
    protected List<Route> getRoutesInternal() {
        try {
            URL url = ApiSpecsRoutesProvider.class.getResource("/rest-api-spec/1.5");
            File dir = new File(url.toURI());
            for (File file : dir.listFiles()) {
                String json = new String(readAllBytes(get(file.getPath())));

                Gson gson = new Gson() ;
                gson.fromJson(json, Data.class) ;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
