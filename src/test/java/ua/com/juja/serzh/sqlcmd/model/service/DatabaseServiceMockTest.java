package ua.com.juja.serzh.sqlcmd.model.service;

import org.hibernate.service.spi.ServiceException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.PostgreSQLManager;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;
import ua.com.juja.serzh.sqlcmd.model.entity.UserActionLog;
import ua.com.juja.serzh.sqlcmd.model.repository.DatabaseConnectionRepository;
import ua.com.juja.serzh.sqlcmd.model.repository.UserActionRepository;
import ua.com.juja.serzh.sqlcmd.service.DatabaseService;
import ua.com.juja.serzh.sqlcmd.service.Service;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:test-application-context.xml"))
//@RunWith(MockitoJUnitRunner.class)
public class DatabaseServiceMockTest {

    @Autowired
    Service service;

    @Autowired
    DatabaseManager manager;

    @Test
    public void shouldAutowireDependencies() {
        assertNotNull(manager);
        assertNotNull(service);
    }

    @Test
    public void testConnectToServer() {
        //given
        String database = "test";
        String username = "test";
        String password = "password";

        //when
        service.connect(database, username, password);

        //then
        verify(manager).connect(database, username, password);
    }

    /*@Test
    public void testConnectToServer() {
        //given
        String username = "postgres";
        String password = "postgres";
        String database = "postgres";

        //when
        service.connect(database ,username, password);

        //then
        verify(mockManager).connect(database, username, password);
    }*/

/*    @Test
    public void testCreateEntry() {
        //given
        String table = "test";
        String[] values = {"John", "Smith"};
        when(manager.getTableColumns(table))
                .thenReturn(new LinkedHashSet<String>(Arrays.asList("username", "password")));

        Map<String, Object> expected = new LinkedHashMap<>();
        expected.put("username", "John");
        expected.put("password", "Smith");

      *//*  //when
        service.createEntry(manager, table, values);

        //then
        verify(manager).insert(table, expected);*//*
    }

    @Test
    public void testGetTableData() {
        //given
        String table = "test";

        List<Object> row1 = new ArrayList<>();
        row1.add("hello");
        row1.add("world");
        List<Object> row2 = new ArrayList<>();
        row2.add("user");
        row2.add("qwerty");
        List<List<Object>> expected = Arrays.asList(row1, row2);

        Map<String, Object> tableData1 = new LinkedHashMap<>();
        tableData1.put("user", "hello");
        tableData1.put("pass", "world");
        Map<String, Object>  tableData2 = new LinkedHashMap<>();
        tableData2.put("user", "user");
        tableData2.put("pass", "qwerty");

        when(manager.getTableColumns(table))
                .thenReturn(new LinkedHashSet<String>(Arrays.asList("user", "pass")));
        when(manager.getTableData(table))
                .thenReturn(Arrays.asList(tableData1, tableData2));

        //when
        List<List<String>> actual = service.getTableData(manager, table);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getManager() throws Exception {

    }

    @Test
    public void commandsDescription() throws Exception {

    }

    @Test
    public void connect() throws Exception {

    }

    @Test
    public void getTableData() throws Exception {

    }

    @Test
    public void tables() throws Exception {

    }

    @Test
    public void databases() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }*/

}
