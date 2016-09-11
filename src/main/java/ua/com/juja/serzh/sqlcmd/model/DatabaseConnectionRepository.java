package ua.com.juja.serzh.sqlcmd.model;

import org.springframework.data.repository.CrudRepository;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;

/**
 * Created by oleksandr.baglai on 19.12.2015.
 */
public interface DatabaseConnectionRepository extends CrudRepository<DatabaseConnection, Integer> {
    DatabaseConnection findByUserNameAndDbName(String userName, String dbName);
}
