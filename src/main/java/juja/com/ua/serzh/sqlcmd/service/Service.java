package juja.com.ua.serzh.sqlcmd.service;

import java.util.List;
import java.util.Set;

public interface Service {

    List<String> commandsList();

    void connect(String databaseName, String userName, String password);

    Set<String> getTables();
}
