package org.cdf.ddd.runtime.registry;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PartnerLoaderTest {

    @Test
    @Ignore
    public void start() throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("ddd-example-test.xml");
        context.start();
        PartnerLoader fooJar = new PartnerLoader("../kunbase-example/order-center-bp-isv/target/order-center-bp-isv-0.0.1.jar", "org.example.bp");
        fooJar.load();
        fooJar.unload();
        context.stop();
    }

    @Test
    public void jarPath() {
        try {
            new PartnerLoader("a/b", null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertEquals("Invalid jarPath: a/b", expected.getMessage());
        }
    }

    @Test
    public void jarName() {
        PartnerLoader loader = new PartnerLoader("../kunbase-example/order-center-bp-isv/target/order-center-bp-isv-0.0.1.jar", null);
        assertEquals("order-center-bp-isv-0.0.1.jar", loader.jarName());

        loader = new PartnerLoader("a.jar", null);
        assertEquals("a.jar", loader.jarName());
    }

    @Test
    public void label() {
        PartnerLoader foo = new PartnerLoader("a/b/c.jar", "");
        assertEquals("PartnerLoader(jarPath=a/b/c.jar)", foo.label());
    }

}
