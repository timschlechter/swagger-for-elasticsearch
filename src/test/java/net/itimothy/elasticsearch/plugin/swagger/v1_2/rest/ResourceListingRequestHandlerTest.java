package net.itimothy.elasticsearch.plugin.swagger.v1_2.rest;

import net.itimothy.elasticsearch.plugin.swagger.SwaggerApprovalTest;
import org.approvaltests.reporters.JunitReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

@UseReporter(JunitReporter.class)
public class ResourceListingRequestHandlerTest extends SwaggerApprovalTest {
    /*
    @Test
    public void get_cleanInstance_approves() throws Exception {
        verify("v1.2");
    }

    @Test
    public void getIndex_indexCreated_approves() throws Exception {
        client.admin().indices().prepareCreate("some-index").execute().actionGet();
        verify("some-index/v1.2");
    }
    */
}
