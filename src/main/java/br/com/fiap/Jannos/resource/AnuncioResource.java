package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.AnuncioRequest;
import br.com.fiap.Jannos.dto.response.AnuncioResponse;
import br.com.fiap.Jannos.entity.Anuncio;
import br.com.fiap.Jannos.service.AnuncioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/anuncio")
public class AnuncioResource implements ResourceDTO<AnuncioRequest, AnuncioResponse> {

    @Autowired
    private AnuncioService service;

    @GetMapping
    public ResponseEntity<Collection<AnuncioResponse>> findAll(
            @RequestParam(name = "valor", required = false) Double valor,
            @RequestParam(name = "quantidade", required = false) Integer quantidade,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "prazo", required = false) String prazo,
            @RequestParam(name = "inicio", required = false) Date inicio,
            @RequestParam(name = "fim", required = false) Date fim
    ) {

        var anuncio = Anuncio.builder()
                .valor( valor )
                .quantidade( quantidade )
                .descricao( descricao )
                .prazo( prazo )
                .inicio( inicio )
                .fim( fim )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Anuncio> example = Example.of( anuncio, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<AnuncioResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<AnuncioResponse> save(@RequestBody @Valid AnuncioRequest r) {
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
