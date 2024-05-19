package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.LojaRequest;
import br.com.fiap.Jannos.dto.response.LojaResponse;
import br.com.fiap.Jannos.entity.Loja;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import br.com.fiap.Jannos.repository.NotaRepository;
import br.com.fiap.Jannos.service.LojaService;
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
@RequestMapping(value = "/loja")
public class LojaResource implements ResourceDTO<LojaRequest, LojaResponse> {

    @Autowired
    private LojaService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public ResponseEntity<Collection<LojaResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "endereco", required = false) Long idEndereco,
            @RequestParam(name = "nota", required = false) Long idNota
    ) {

        var endereco = Objects.nonNull( idEndereco ) ? enderecoRepository
                .findById( idEndereco )
                .orElse( null ) : null;

        var nota = Objects.nonNull( idNota ) ? notaRepository
                .findById( idNota )
                .orElse( null ) : null;

        var loja = Loja.builder()
                .nome( nome )
                .tipo( tipo )
                .descricao( descricao )
                .endereco( endereco )
                .nota( nota )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Loja> example = Example.of( loja, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<LojaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<LojaResponse> save(@RequestBody @Valid LojaRequest r) {
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
