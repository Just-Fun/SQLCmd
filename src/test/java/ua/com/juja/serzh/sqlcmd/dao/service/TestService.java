package ua.com.juja.serzh.sqlcmd.dao.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.BeforeMethod;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.dao.repository.UserActionRepository;
import ua.com.juja.serzh.sqlcmd.service.Service;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Serzh on 9/22/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestService {

//    @InjectMocks
    @Mock
//    private DatabaseService service;
    private Service service;

    //    @Autowired
    @Mock
    DatabaseManager manager;

    @Mock
    private UserActionRepository userActions;


    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAutowireDependencies() {
        assertNotNull(manager);
        assertNotNull(service);
        assertNotNull(userActions);
    }

}
