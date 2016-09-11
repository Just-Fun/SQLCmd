package ua.com.juja.serzh.sqlcmd.model;

public interface UserActionRepositoryCustom {

    void createAction(String databaseName, String userName, String action);

}
