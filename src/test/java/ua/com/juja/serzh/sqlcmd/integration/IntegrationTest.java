package ua.com.juja.serzh.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.serzh.sqlcmd.Setup;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.PostgreSQLManager;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

@Ignore
public class IntegrationTest {
    private static DatabaseManager manager;

    private static Setup setup = new Setup();
    private final static String DATABASE = "sqlcmd5hope5never5exist";
    private final static String USER = setup.getUser();
    private final static String PASSWORD = setup.getPassword();

    @BeforeClass
    public static void setup() {
        manager = new PostgreSQLManager();
        setup.setupData(manager, DATABASE);
    }

    @AfterClass
    public static void dropDatabase() {
        setup.dropData(manager);
    }

    @Before
    public void prepare() {
//        setBaseUrl("http://localhost:8080/sqlcmd");
        setBaseUrl("http://localhost:8080/sqlcmd/main#/menu");
        beginAt("/connect");
        setTextField("database", DATABASE);
        setTextField("userName", USER);
        setTextField("password", PASSWORD);
        submit();
    }

    @Test
    public void success() {
        assertTextPresent("Existing commands:");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testAssertLinks() {
        assertLinkPresentWithText("create");
    }

    @Test
    public void testTableData() {
        clickLinkWithText("tables");
        assertLinkPresentWithText("users");
        assertLinkPresentWithText("test1");
        assertLinkPresentWithText("users2");
        assertTextPresent("menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testDeleteRecord() {
        gotoPage("/databases");
        assertTextPresent(DATABASE);
    }
}
