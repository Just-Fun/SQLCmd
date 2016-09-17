package ua.com.juja.serzh.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.service.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
// converter to Json objects :)
public class SQLCmdRestController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<Description> menuItems() {
        return service.commandsDescription();
    }

    @RequestMapping(value = "/tables/content", method = RequestMethod.GET)
    public Set<String> tables(HttpSession session) {
        DatabaseManager manager = getManager(session);
        return service.tables(manager);
    }

    @RequestMapping(value = "/actions/content", method = RequestMethod.GET)
    public List<UserActionLog> actions(HttpSession session) {
        DatabaseManager manager = getManager(session);
        return service.getAll();
    }

    @RequestMapping(value = "/table/{table}/content", method = RequestMethod.GET)
    public List<List<String>> table(@PathVariable(value = "table") String table, HttpSession session) {
        DatabaseManager manager = getManager(session);
        return service.getTableData(manager, table);
    }

    @RequestMapping(value = "/connected", method = RequestMethod.GET)
    public String isConnected(HttpSession session) {
        DatabaseManager manager = getManager(session);
        return (manager != null) ? manager.getUserName() : null;
    }

    @RequestMapping(value = "/connect", method = RequestMethod.PUT)
    public String connecting(HttpSession session, @ModelAttribute("connection") Connection connection) {
        try {
            DatabaseManager manager = service.connect(connection.getDatabase(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("db_manager", manager);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }
}