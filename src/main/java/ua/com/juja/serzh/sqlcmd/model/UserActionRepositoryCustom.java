package ua.com.juja.serzh.sqlcmd.model;

public interface UserActionRepositoryCustom {

    void createAction(String userName, String databaseName, String action);

}
