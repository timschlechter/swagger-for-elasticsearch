package net.itimothy.elasticsearch.plugin.swagger;

import com.spun.util.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.io.File;
import java.io.IOException;

public class ElasticsearchServer {
    private static final String DATA_DIRECTORY = "target/elasticsearch-data";

    private final Node node;

    public ElasticsearchServer() {
        node = NodeBuilder.nodeBuilder().settings(
            ImmutableSettings.settingsBuilder()
                .put("http.port", 9201)
                .put("node.name", "swagger-for-elasticsearch-test-node")
                .put("cluster.name", "swagger-for-elasticsearch-test-cluster")
                .put("path.data", DATA_DIRECTORY)
        ).node();
    }

    public Client getClient() {
        return node.client();
    }

    public void shutdown() {
        node.close();
        deleteDataDirectory();
    }

    private void deleteDataDirectory() {
        try {
            FileUtils.deleteDirectory(new File(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete data directory of embedded elasticsearch server", e);
        }
    }
}
