package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.ProdutoRequest;
import br.com.fiap.Jannos.dto.response.ProdutoResponse;
import br.com.fiap.Jannos.entity.Produto;
import br.com.fiap.Jannos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private LojaService lojaService;

    @Override
    public Produto toEntity(ProdutoRequest r) {

        var anuncio = anuncioService.findById( r.anuncio().id() );
        var loja = lojaService.findById( r.loja().id() );

        return Produto.builder()
                .nome( r.nome() )
                .descricao( r.descricao() )
                .tipo( r.tipo() )
                .anuncio( anuncio )
                .loja( loja )
                .build();

    }

    @Override
    public ProdutoResponse toResponse(Produto e) {

        var anuncio = anuncioService.toResponse( e.getAnuncio() );
        var loja = lojaService.toResponse( e.getLoja() );

        return ProdutoResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .descricao( e.getDescricao() )
                .tipo( e.getTipo() )
                .anuncio( anuncio )
                .loja( loja )
                .build();

    }

    @Override
    public List<Produto> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Produto> findAll(Example<Produto> example) {
        return repo.findAll( example );
    }

    @Override
    public Produto findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Produto save(Produto e) {
        return repo.save( e );
    }

}
