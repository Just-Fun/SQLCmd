package ua.com.juja.serzh.sqlcmd.controller;


public class Connection {

    private String userName;
    private String password;
    private String database;
    private String fromPage;

    public Connection() {
        // do nothing
    }

    public Connection(String page) {
        this.fromPage = page;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getFromPage() {
        return fromPage;
    }

    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }
}
