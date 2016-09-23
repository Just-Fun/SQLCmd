package ua.com.juja.serzh.sqlcmd.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.juja.serzh.sqlcmd.dao.entity.UserAction;

public interface UserActionRepository extends PagingAndSortingRepository<UserAction, Integer>, UserActionRepositoryCustom {

    @Query(value = "SELECT ua FROM UserAction ua ORDER BY date DESC")
    Page<UserAction> findAll(Pageable pageable);
}
