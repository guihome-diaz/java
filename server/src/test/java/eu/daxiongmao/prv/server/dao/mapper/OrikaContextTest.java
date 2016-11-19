package eu.daxiongmao.prv.server.dao.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import eu.daxiongmao.prv.server.config.OrikaConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(classes = { OrikaConfiguration.class })
public class OrikaContextTest {

    @Autowired
    private OrikaBeanMapper mapper;

    @Autowired
    private ApplicationContext appContext;

    @Test
    public void orikaContextValidation() {
        Assert.assertNotNull(appContext);
        Assert.assertNotNull(mapper);
        Assert.assertNotNull(appContext.getBean("userMapper"));
        Assert.assertNotNull(appContext.getBean("vehicleMapper"));
        Assert.assertNotNull(appContext.getBean("refuelMapper"));
        Assert.assertNotNull(appContext.getBean("userDbToLongConverter"));
    }

}
