package ua.com.juja.serzh.sqlcmd.controller;


import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

public class UserActionLog {

    private String username;
    private String action;
    private String database;
    private String date;

    public UserActionLog() {
        //do nothing
    }

    public UserActionLog(UserAction action) {
        this.username = action.getConnection().getUserName();
        this.action = action.getAction();
        this.database = action.getConnection().getDbName();
        this.date = action.getDate();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
