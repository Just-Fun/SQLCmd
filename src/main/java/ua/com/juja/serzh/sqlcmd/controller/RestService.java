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

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<Description> menuItems() {
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

    @RequestMapping(value = "/actions/content", method = RequestMethod.GET)
    public List<UserActionLog> actions(HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());
        if (manager == null) {
            return new ArrayList<>();
        }
        return service.getAll();
    }

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
    public String isConnected(HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());
        return (manager != null) ? manager.getUserName() : null;
    }

    @RequestMapping(value = "/connect", method = RequestMethod.PUT)
    public String connecting(HttpServletRequest request, @ModelAttribute("connection") Connection connection) {
        try {
            DatabaseManager manager = service.connect(connection.getDatabase(),
                    connection.getUserName(), connection.getPassword());
            HttpSession session = request.getSession();
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