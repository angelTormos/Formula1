package jpaswing.repository;

import jpaswing.entity.Piloto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface EscuderiaRepositoryPagination extends PagingAndSortingRepository<Piloto, Long> {
    @Query("SELECT COUNT(*) FROM Piloto")
    public int countAllRecords();

}