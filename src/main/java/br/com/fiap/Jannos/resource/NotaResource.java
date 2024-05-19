package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.NotaRequest;
import br.com.fiap.Jannos.dto.response.NotaResponse;
import br.com.fiap.Jannos.entity.Nota;
import br.com.fiap.Jannos.service.NotaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/nota")
public class NotaResource implements ResourceDTO<NotaRequest, NotaResponse> {

    @Autowired
    private NotaService service;

    @GetMapping
    public ResponseEntity<Collection<NotaResponse>> findAll(
            @RequestParam(name = "valor", required = false) Double valor,
            @RequestParam(name = "media", required = false) String media,
            @RequestParam(name = "descricao", required = false) String descricao
    ) {

        var nota = Nota.builder()
                .valor( valor )
                .media( media )
                .descricao( descricao )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Nota> example = Example.of( nota, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<NotaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<NotaResponse> save(@RequestBody @Valid NotaRequest r) {
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
