package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.ClienteRequest;
import br.com.fiap.Jannos.dto.response.ClienteResponse;
import br.com.fiap.Jannos.entity.Cliente;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import br.com.fiap.Jannos.repository.NotaRepository;
import br.com.fiap.Jannos.service.ClienteService;
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
@RequestMapping(value = "/cliente")
public class ClienteResource implements ResourceDTO<ClienteRequest, ClienteResponse> {

    @Autowired
    private ClienteService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public ResponseEntity<Collection<ClienteResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "cpf", required = false) String cpf,
            @RequestParam(name = "endereco", required = false) Long idEndereco,
            @RequestParam(name = "nota", required = false) Long idNota
    ) {

        var endereco = Objects.nonNull( idEndereco ) ? enderecoRepository
                .findById( idEndereco )
                .orElse( null ) : null;

        var nota = Objects.nonNull( idNota ) ? notaRepository
                .findById( idNota )
                .orElse( null ) : null;

        var cliente = Cliente.builder()
                .nome( nome )
                .email( email )
                .cpf( cpf )
                .endereco( endereco )
                .nota( nota )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Cliente> example = Example.of( cliente, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody @Valid ClienteRequest r) {
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
