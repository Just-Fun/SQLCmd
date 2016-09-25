package ua.com.juja.serzh.sqlcmd.integration;

import org.hibernate.service.spi.ServiceException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.serzh.sqlcmd.service.Service;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManagerException;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.PostgreSQLManager;
import ua.com.juja.serzh.sqlcmd.Setup;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("file:src/main/webapp/WEB-INF/application-context.xml"))
public class ServiceTest {

    @Autowired
    private Service service;
    private static DatabaseManager manager;
    private static Setup login;

    @BeforeClass
    public static void setup() {
        manager = new PostgreSQLManager();
        login = new Setup();
    }

    @Test(expected = DatabaseManagerException.class)
    public void testConnect_WithIncorrectData() throws ServiceException {
        service.connect("qwe", "qwe", "qwe");
    }

    @Test
    public void testConnect() throws ServiceException {
        service.connect(login.getDatabase(), login.getUser(), login.getPassword());
        assertNotNull(manager);
    }
}
