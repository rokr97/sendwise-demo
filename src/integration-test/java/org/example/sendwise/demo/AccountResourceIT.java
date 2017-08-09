package org.example.sendwise.demo;

import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.wire.VerboseWire;
import io.bootique.jetty.JettyModule;
import io.bootique.test.TestIO;
import io.bootique.BQRuntime;
import io.bootique.jetty.test.junit.JettyTestFactory;
import io.bootique.test.junit.BQDaemonTestFactory;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.*;

public class AccountResourceIT {
    @Rule
    public JettyTestFactory jettyTestFactory = new JettyTestFactory();
    @Rule
    public BQDaemonTestFactory testFactory = new BQDaemonTestFactory();
    private String baseUri;

    @Before
    public void before() {
        TestIO io = TestIO.noTrace();
        BQRuntime runtime = testFactory
                .app("-c", "sendwise-dev.yml", "--server")
                .bootLogger(io.getBootLogger())
                .autoLoadModules()
                .start();
        Server server = runtime.getInstance(Server.class);
        NetworkConnector connector = (NetworkConnector) server.getConnectors()[0];
        baseUri = "http://" + server.getURI().getHost() + ":" + connector.getPort() + server.getURI().getPath();

        sanityCheck(); // without it first jcabi request results in 'Connection refused: connect'

/*
        CayenneTestDataManager testDataManager = new CayenneTestDataManager(runtime, true, Account.class, Game.class);

        testDataManager.getTable(Account.class)
                .insertColumns("id", "name")
                .values(1, "New Jersey Devils")
                .values(2, "New York Rangers")
                .values(3, "Detroit Red Wings")
                .values(4, "LA Kings")
                .exec();
*/
    }

    private void sanityCheck() {
        Client client = ClientBuilder.newClient();
        Response r = client.target(baseUri).request().get();
//        Response r = client.target("http://localhost:1337/accounts").request().get();

    }

    @Test
    public void testGetWithJcabi() throws IOException, InterruptedException {
        com.jcabi.http.Response response = fetch("accounts/");
//                .as(JsonResponse.class)
//                .json()
//                .readObject()
//                .getString("login");
        assertThat(response.status()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    private com.jcabi.http.Response fetch(String path) throws IOException {
        return new JdkRequest(baseUri + path)
//                .uri().path(path).back()
                .method(Request.GET)
                .through(VerboseWire.class)
                .header("User-Agent", "My Super HTTP Client")
                .fetch();
    }
}