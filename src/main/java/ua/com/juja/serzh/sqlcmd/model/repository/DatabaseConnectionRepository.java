package ua.com.juja.serzh.sqlcmd.model.repository;

import org.springframework.data.repository.CrudRepository;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;

public interface DatabaseConnectionRepository extends CrudRepository<DatabaseConnection, Integer> {
    DatabaseConnection findByUserNameAndDbName(String userName, String dbName);
}
