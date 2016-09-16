package ua.com.juja.serzh.sqlcmd.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserActionRepositoryImpl implements UserActionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseConnectionRepository databaseConnections;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createAction(String userName, String databaseName, String action) {
        DatabaseConnection databaseConnection = databaseConnections.findByUserNameAndDbName(userName, databaseName);
        if (databaseConnection == null) {
            databaseConnection = databaseConnections.save(new DatabaseConnection(userName, databaseName));
        }
       /* UserAction userAction = new UserAction(action, databaseConnection);
        Session session = (Session) entityManager.getDelegate();
        session.persist(userAction);*/
        entityManager.persist(new UserAction(action, System.currentTimeMillis(), databaseConnection));
    }
}