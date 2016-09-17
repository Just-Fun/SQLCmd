package ua.com.juja.serzh.sqlcmd.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import java.util.LinkedList;
import java.util.List;

public interface UserActionRepository extends PagingAndSortingRepository<UserAction, Integer>, UserActionRepositoryCustom {

    @Query(value = "SELECT ua FROM UserAction ua ORDER BY date DESC")
    List<UserAction> findAll();
}
