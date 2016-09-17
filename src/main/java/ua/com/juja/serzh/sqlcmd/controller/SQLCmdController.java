package ua.com.juja.serzh.sqlcmd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SQLCmdController {

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String main() {
        return "main";
    }

}
