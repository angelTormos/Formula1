package jpaswing.repository;

import jpaswing.entity.Piloto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface EscuderiaRepository extends CrudRepository<Piloto, Long> {

    Piloto findFirstByOrderByIdAsc();
}
