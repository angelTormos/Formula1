package jpaswing.controller;


import jpaswing.entity.Piloto;
import jpaswing.repository.PilotoRepository;
import jpaswing.repository.PilotoRepositoryPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EscuderiaController {
    private final PilotoRepository pilotoRepository;
    private final PilotoRepositoryPagination piltosRepositoryPagination;
    private int currentPage = 0;
    private int count;
    private Optional<Piloto> currentPiloto;
    @Autowired
    public EscuderiaController(PilotoRepository pilotoRepository, PilotoRepositoryPagination pilotoRepositoryPagination){
        this.pilotoRepository = pilotoRepository;
        this.piltosRepositoryPagination = pilotoRepositoryPagination;
        this.count = piltosRepositoryPagination.countAllRecords();
    }
    public Optional<Piloto> getPiloto(){
        PageRequest pr = PageRequest.of(currentPage, 1);
        List<Piloto> pilotos = piltosRepositoryPagination.findAll(pr).getContent();
        if (!pilotos.isEmpty()) {
            currentPiloto = Optional.of(pilotos.get(0));
        } else {
            currentPiloto = Optional.empty();
        }
        return currentPiloto;
    }

    public Optional<Piloto> next(){
        this.count = piltosRepositoryPagination.countAllRecords();
        if (currentPage == this.count -1 ) return currentPiloto;

        currentPage++;
        return getPiloto();
    }

    public Optional<Piloto> previous(){
        if (currentPage == 0) return currentPiloto;

        currentPage--;
        return getPiloto();
    }

    public Optional<Piloto> first(){
        currentPage = 0;
        return getPiloto();
    }
    public Optional<Piloto> last(){
        this.count = piltosRepositoryPagination.countAllRecords();
        currentPage = count - 1;
        return getPiloto();
    }

}