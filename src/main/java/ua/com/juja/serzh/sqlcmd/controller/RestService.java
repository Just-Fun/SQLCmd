package ua.com.juja.serzh.sqlcmd.controller;

/**
 * Created by oleksandr.baglai on 11.12.2015.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
// преобразователь в Json объекты :)
public class RestService {

    @Autowired
    private Service service;

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<String> menuItems() {
        return service.commandsList();
    }

    @RequestMapping(value = "/help/content", method = RequestMethod.GET)
    public List<Description> helpItems() {
        return service.commandsDescription();
    }

    @RequestMapping(value = "/list/content", method = RequestMethod.GET)
    public Set<String> list(HttpServletRequest request) {
        DatabaseManager manager = getManager(request.getSession());

        if (manager == null) {
            return new HashSet<>();
        }

        return service.tables(manager);
    }

    @RequestMapping(value = "/tables/{table}/content", method = RequestMethod.GET)
    public List<List<String>> tables(@PathVariable(value = "table") String table,
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

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }
}
