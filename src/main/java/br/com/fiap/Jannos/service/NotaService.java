package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.NotaRequest;
import br.com.fiap.Jannos.dto.response.NotaResponse;
import br.com.fiap.Jannos.entity.Nota;
import br.com.fiap.Jannos.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService implements ServiceDTO<Nota, NotaRequest, NotaResponse> {

    @Autowired
    private NotaRepository repo;

    @Override
    public Nota toEntity(NotaRequest r) {

        return Nota.builder()
                .valor( r.valor() )
                .media( r.media() )
                .descricao( r.descricao() )
                .build();

    }

    @Override
    public NotaResponse toResponse(Nota e) {

        return NotaResponse.builder()
                .id( e.getId() )
                .valor( e.getValor() )
                .media( e.getMedia() )
                .descricao( e.getDescricao() )
                .build();

    }

    @Override
    public List<Nota> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Nota> findAll(Example<Nota> example) {
        return repo.findAll( example );
    }

    @Override
    public Nota findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Nota save(Nota e) {
        return repo.save( e );
    }

}
