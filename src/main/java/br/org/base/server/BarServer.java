package br.org.base.server;

import br.org.base.log.SFLogger;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.jersey.grizzly2.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.json.JsonJacksonModule;
import org.glassfish.jersey.server.Application;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.UnknownHostException;

public class BarServer {

    public static DB mongoDB;

    public static String user, pass, db;

    public static String contentUrl;

    private static final String CONTENT_PATH = "/content";

    public static void main(String[] args){

        final int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8082;
        final URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(port).build();


        final Application server = Application.builder(ResourceConfig.builder().packages("br.org.base.resource").build()).build();

        server.addModules(new JsonJacksonModule());

        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, server);
        //httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/main/webapp"), CONTENT_PATH);


        for (NetworkListener networkListener : httpServer.getListeners()) {
            if (System.getenv("FILE_CACHE_ENABLED") == null) {
                networkListener.getFileCache().setEnabled(false);


            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                httpServer.stop();

            }
        });

        user = "base";
        pass = "1q2w3e4r";

        db = "producao";

        MongoURI mongolabUri = new MongoURI(System.getenv("MONGOLAB_URI") != null ? System.getenv("MONGOLAB_URI") : "mongodb://127.0.0.1:27017/producao");


        Mongo m = null;
        try {
            m = new Mongo(mongolabUri);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            SFLogger.log(e.getMessage(), BarServer.class);
        }
        mongoDB = m.getDB(mongolabUri.getDatabase());
        if ((mongolabUri.getUsername() != null) && (mongolabUri.getPassword() != null)) {
            mongoDB.authenticate(mongolabUri.getUsername(), mongolabUri.getPassword());
        }

        contentUrl = CONTENT_PATH;

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            SFLogger.log(e.getMessage(), BarServer.class);
        }
    }
}
