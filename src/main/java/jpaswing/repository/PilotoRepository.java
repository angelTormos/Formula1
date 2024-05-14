package jpaswing.repository;

import jpaswing.entity.Piloto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PilotoRepository extends CrudRepository<Piloto, String> {

    Piloto findFirstByOrderByNombreAsc();
}
