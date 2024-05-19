package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.ProdutoRequest;
import br.com.fiap.Jannos.dto.response.ProdutoResponse;
import br.com.fiap.Jannos.entity.Produto;
import br.com.fiap.Jannos.repository.AnuncioRepository;
import br.com.fiap.Jannos.repository.LojaRepository;
import br.com.fiap.Jannos.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoService service;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @GetMapping
    public ResponseEntity<Collection<ProdutoResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "anuncio", required = false) Long idAnuncio,
            @RequestParam(name = "loja", required = false) Long idLoja
    ) {

        var anuncio = Objects.nonNull( idAnuncio ) ? anuncioRepository
                .findById( idAnuncio )
                .orElse( null ) : null;

        var loja = Objects.nonNull( idLoja ) ? lojaRepository
                .findById( idLoja )
                .orElse( null ) : null;


        var produto = Produto.builder()
                .nome( nome )
                .descricao( descricao )
                .tipo( tipo )
                .anuncio( anuncio )
                .loja( loja )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Produto> example = Example.of( produto, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }

}
