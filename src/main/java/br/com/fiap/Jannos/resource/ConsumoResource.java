package br.com.fiap.Jannos.resource;


import br.com.fiap.Jannos.dto.request.ConsumoRequest;
import br.com.fiap.Jannos.dto.response.ConsumoResponse;
import br.com.fiap.Jannos.entity.Consumo;
import br.com.fiap.Jannos.repository.AnuncioRepository;
import br.com.fiap.Jannos.repository.ClienteRepository;
import br.com.fiap.Jannos.service.ConsumoService;
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
@RequestMapping(value = "/consumo")
public class ConsumoResource implements ResourceDTO<ConsumoRequest, ConsumoResponse> {

    @Autowired
    private ConsumoService service;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @GetMapping
    public ResponseEntity<Collection<ConsumoResponse>> findAll(
            @RequestParam(name = "quantidade", required = false) String quantidade,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "cliente", required = false) Long idCliente,
            @RequestParam(name = "anuncio", required = false) Long idAnuncio
    ) {

        var cliente = Objects.nonNull( idCliente ) ? clienteRepository
                .findById( idCliente )
                .orElse( null ) : null;

        var anuncio = Objects.nonNull( idAnuncio ) ? anuncioRepository
                .findById( idAnuncio )
                .orElse( null ) : null;

        var consumo = Consumo.builder()
                .quantidade( quantidade )
                .descricao( descricao )
                .cliente( cliente )
                .anuncio( anuncio )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Consumo> example = Example.of( consumo, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsumoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<ConsumoResponse> save(@RequestBody @Valid ConsumoRequest r) {
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
