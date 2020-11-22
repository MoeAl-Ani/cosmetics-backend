package com.infotamia.cosmetics.base;

import com.infotamia.cosmetics.application.CosmeticTestApplication;
import com.infotamia.cosmetics.servlet.CosmeticsApplicationConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.external.ExternalTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * base jersey test
 * @author Mohammed Al-Ani
 */
public abstract class JerseyBase extends JerseyTest {

    @Override
    protected Application configure() {
        return new CosmeticsApplicationConfig();
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new ExternalTestContainerFactory() {
            @Override
            public TestContainer create(URI baseUri, DeploymentContext context) throws IllegalArgumentException {
                baseUri = UriBuilder.fromUri("http://127.0.0.1/").port(9998).build();
                return super.create(baseUri, context);
            }
        };

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @BeforeClass
    public static void beforeClass() {
        try {
            CosmeticTestApplication.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass() {
        try {
            CosmeticTestApplication.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RestAssuredWrapper.GetBuilder get() {
        return new RestAssuredWrapper.GetBuilder();
    }

    public RestAssuredWrapper.PostBuilder post() {
        return new RestAssuredWrapper.PostBuilder();
    }

    public RestAssuredWrapper.PatchBuilder patch() {
        return new RestAssuredWrapper.PatchBuilder();
    }

    public RestAssuredWrapper.DeleteBuilder delete() {
        return new RestAssuredWrapper.DeleteBuilder();
    }
}
