package ua.com.juja.serzh.sqlcmd;

import ua.com.juja.serzh.sqlcmd.dao.databaseManager.PostgreSQLManager;

import java.util.Set;

/**
 * Created by Serzh on 9/12/16.
 */
public class Temp {

    public static void main(String[] args) {
        PostgreSQLManager manager = new PostgreSQLManager();
        manager.connect("", "postgres", "postgres");
        Set<String> databases = manager.getDatabases();
        databases.forEach(System.out::println);
    }
}