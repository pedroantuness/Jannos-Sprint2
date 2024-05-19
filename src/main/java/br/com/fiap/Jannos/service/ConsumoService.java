package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.ConsumoRequest;
import br.com.fiap.Jannos.dto.response.ConsumoResponse;
import br.com.fiap.Jannos.entity.Consumo;
import br.com.fiap.Jannos.repository.ConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumoService implements ServiceDTO<Consumo, ConsumoRequest, ConsumoResponse> {

    @Autowired
    private ConsumoRepository repo;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AnuncioService anuncioService;

    @Override
    public Consumo toEntity(ConsumoRequest r) {

        var cliente = clienteService.findById( r.cliente().id() );
        var anuncio = anuncioService.findById( r.anuncio().id() );

        return Consumo.builder()
                .quantidade( r.quantidade() )
                .descricao( r.descricao() )
                .cliente( cliente )
                .anuncio( anuncio )
                .build();

    }

    @Override
    public ConsumoResponse toResponse(Consumo e) {

        var cliente = clienteService.toResponse( e.getCliente() );
        var anuncio = anuncioService.toResponse( e.getAnuncio() );

        return ConsumoResponse.builder()
                .id( e.getId() )
                .quantidade( e.getQuantidade() )
                .descricao( e.getDescricao() )
                .cliente( cliente )
                .anuncio( anuncio )
                .build();

    }

    @Override
    public List<Consumo> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Consumo> findAll(Example<Consumo> example) {
        return repo.findAll( example );
    }

    @Override
    public Consumo findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Consumo save(Consumo e) {
        return repo.save( e );
    }

}

