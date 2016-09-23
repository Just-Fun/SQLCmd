package ua.com.juja.serzh.sqlcmd.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ua.com.juja.serzh.sqlcmd.dao.entity.DatabaseConnection;

public interface DatabaseConnectionRepository extends CrudRepository<DatabaseConnection, Integer> {
    DatabaseConnection findByUserNameAndDbName(String userName, String dbName);
}
