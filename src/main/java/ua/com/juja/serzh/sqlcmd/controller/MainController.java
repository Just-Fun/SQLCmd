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

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String main() {
        return "main";
    }

}
