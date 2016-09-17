package ua.com.juja.serzh.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.service.Service;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private Service service;

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String main() {
        return "main";
    }

   /* @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session, Model model,
                          @RequestParam(required = false, value = "fromPage") String fromPage) {
//        String page = (String) session.getAttribute("from-page");
//        session.removeAttribute("from-page");
//        Connection connection = new Connection(page);
        Connection connection = new Connection(fromPage);
        if (fromPage != null) {
            connection.setFromPage(fromPage);
        } else {
            connection.setFromPage("/menu");
        }
        model.addAttribute("connection", connection);

       *//* if (getManager(session) == null) {
            return "connect";
        } else {
            return "menu";
        }*//*
        return "connect";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute("connection") Connection connection, HttpSession session, Model model) {
        try {
            DatabaseManager manager = service.connect(connection.getDatabase(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("db_manager", manager);
            return "redirect:" + connection.getFromPage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }
*/
    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }
}
