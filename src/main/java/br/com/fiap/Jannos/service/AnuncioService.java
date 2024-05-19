package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.AnuncioRequest;
import br.com.fiap.Jannos.dto.response.AnuncioResponse;
import br.com.fiap.Jannos.entity.Anuncio;
import br.com.fiap.Jannos.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioService implements ServiceDTO<Anuncio, AnuncioRequest, AnuncioResponse> {

    @Autowired
    private AnuncioRepository repo;

    @Override
    public Anuncio toEntity(AnuncioRequest r) {

        return Anuncio.builder()
                .valor( r.valor() )
                .quantidade( r.quantidade() )
                .descricao( r.descricao() )
                .prazo( r.prazo() )
                .inicio( r.inicio() )
                .fim( r.fim() )
                .build();

    }

    @Override
    public AnuncioResponse toResponse(Anuncio e) {

        return AnuncioResponse.builder()
                .id( e.getId() )
                .valor( e.getValor() )
                .quantidade( e.getQuantidade() )
                .descricao( e.getDescricao() )
                .prazo( e.getPrazo() )
                .inicio( e.getInicio() )
                .fim( e.getFim() )
                .build();

    }

    @Override
    public List<Anuncio> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Anuncio> findAll(Example<Anuncio> example) {
        return repo.findAll( example );
    }

    @Override
    public Anuncio findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Anuncio save(Anuncio e) {
        return repo.save( e );
    }

}
