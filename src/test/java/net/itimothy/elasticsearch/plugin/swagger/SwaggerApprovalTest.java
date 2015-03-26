package net.itimothy.elasticsearch.plugin.swagger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.approvaltests.Approvals;
import org.elasticsearch.client.Client;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public class SwaggerApprovalTest {
    static final String PLUGIN_BASE_URI = "http://localhost:9201/_swagger/";

    protected ElasticsearchServer server;
    protected Client client;
    protected CloseableHttpClient httpclient;


    @Before
    public void before() {
        server = new ElasticsearchServer();
        client = server.getClient();
        httpclient = HttpClients.createDefault();
    }

    @After
    public void after() throws Exception {
        httpclient.close();
        server.shutdown();
    }

    public String httpGet(String relativeUri) {
        try {
            return httpclient.execute(
                new HttpGet(PLUGIN_BASE_URI + relativeUri),
                new ResponseHandler<String>() {
                    @Override
                    public String handleResponse(HttpResponse response) throws IOException {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    }
                }
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void verify(String relativeUri) throws Exception {
        String result = httpGet(relativeUri);
        Approvals.verify(normalizeResult(result));
    }

    protected String normalizeResult(String result) {
        return result;
    }
}
