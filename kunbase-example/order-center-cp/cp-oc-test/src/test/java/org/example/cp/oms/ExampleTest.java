package org.example.cp.oms;

import lombok.extern.slf4j.Slf4j;
import org.cdf.ddd.runtime.DDD;
import org.example.cp.oms.domain.model.OrderModel;
import org.example.cp.oms.domain.model.OrderModelCreator;
import org.example.cp.oms.domain.service.SubmitOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

@Slf4j
@Ignore
public class ExampleTest {

    @Test
    public void dynamicLoadPatternAndPartner() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-test.xml");
        applicationContext.start();

        DDD.getContainer().loadPartnerPlugin(new URL("https://github.com/kunbucha/kunbase-framework/raw/loader/doc/assets/jar/order-center-bp-isv-0.0.1.jar"), "org.example.bp");
        DDD.getContainer().loadPartnerPlugin(new URL("https://github.com/kunbucha/kunbase-framework/raw/loader/doc/assets/jar/order-center-bp-ka-0.0.1.jar"), "org.example.bp");

        DDD.getContainer().loadPatternPlugin(new URL("https://github.com/kunbucha/kunbase-framework/raw/loader/doc/assets/jar/order-center-pattern-0.0.1.jar"), "org.example.cp");

        DDD.getContainer().unloadPatternPlugin("hair");

        // prepare the domain model
        OrderModelCreator creator = new OrderModelCreator();
        creator.setSource("ISV");
        creator.setCustomerNo("hair");
        OrderModel orderModel = OrderModel.createWith(creator);

        // call the domain service
        SubmitOrder submitOrder = (SubmitOrder) applicationContext.getBean("submitOrder");
        submitOrder.submit(orderModel);

        applicationContext.stop();
    }
}
