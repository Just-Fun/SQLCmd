package ua.com.juja.serzh.sqlcmd.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import java.util.List;

public interface UserActionRepository extends CrudRepository<UserAction, Integer>, UserActionRepositoryCustom {

  /*  @Query(value = "SELECT ua FROM UserAction ua WHERE ua.connection.userName = :userName")
    List<UserAction> findByUserName(@Param("userName") String userName);*/

//    @Query(value = "SELECT ua FROM UserAction ua")
    List<UserAction> findAll();
}
