package ua.com.juja.serzh.sqlcmd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
// converter to Json objects :)
public class RestService {

    @Autowired
    private Service service;

    /*@RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<String> menuItems() {
        return service.commandsList();
    }*/

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<Description> menuItems() {
        return service.commandsDescription();
    }

    @RequestMapping(value = "/help/content", method = RequestMethod.GET)
    public List<Description> helpItems() {
        return service.commandsDescription();
    }

    @RequestMapping(value = "/tables/content", method = RequestMethod.GET)
    public Set<String> tables(HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());
        if (manager == null) {
            return new HashSet<>();
        }
        return service.tables(manager);
    }

   /* @RequestMapping(value = "/actions/{userName}/content", method = RequestMethod.GET)
    public List<UserAction> actions(@PathVariable(value = "userName") String userName) {
        if (userName == null) {
            return new LinkedList<>();
        }
        return service.getAllFor(userName);
    }*/

    @RequestMapping(value = "/actions/content", method = RequestMethod.GET)
    public List<UserActionLog> actions(HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());
        if (manager == null) {
            return new ArrayList<>();
        }
        return service.getAll();
//        return Arrays.asList(new UserAction("Proba", new DatabaseConnection("Name", "db")));
    }
/*

    @RequestMapping(value = "/actions/{userName}/content", method = RequestMethod.GET)
    public List<UserActionLog> actions(@PathVariable(value = "userName") String userName) {
        return service.getAllFor(userName);
    }
*/

    @RequestMapping(value = "/table/{table}/content", method = RequestMethod.GET)
    public List<List<String>> table(@PathVariable(value = "table") String table,
                                     HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());

        if (manager == null) {
            return new LinkedList<>();
        }

        return service.find(manager, table);
    }

    @RequestMapping(value = "/connected", method = RequestMethod.GET)
    public boolean isConnected(HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());
        return manager != null;
    }
/*
    @RequestMapping(value = {"/connect"}, method = RequestMethod.POST)
    public String login(HttpSession session, @ModelAttribute("connection") Connection connection) {
        try {
            DatabaseManager manager = service.connect(connection.getDbName(),
                    connection.getUserName(), connection.getPassword());
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }*/

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }
}
