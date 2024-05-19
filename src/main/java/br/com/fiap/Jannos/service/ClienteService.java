package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.ClienteRequest;
import br.com.fiap.Jannos.dto.response.ClienteResponse;
import br.com.fiap.Jannos.entity.Cliente;
import br.com.fiap.Jannos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements ServiceDTO<Cliente, ClienteRequest, ClienteResponse> {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private NotaService notaService;

    @Override
    public Cliente toEntity(ClienteRequest r) {

        var endereco = enderecoService.findById( r.endereco().id() );
        var nota = notaService.findById( r.nota().id() );

        return Cliente.builder()
                .nome( r.nome() )
                .email( r.email() )
                .cpf( r.cpf() )
                .endereco( endereco )
                .nota( nota )
                .build();

    }

    @Override
    public ClienteResponse toResponse(Cliente e) {

        var endereco = enderecoService.toResponse( e.getEndereco() );
        var nota = notaService.toResponse( e.getNota() );

        return ClienteResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .email( e.getEmail() )
                .cpf( e.getCpf() )
                .endereco( endereco )
                .nota( nota )
                .build();

    }

    @Override
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Cliente> findAll(Example<Cliente> example) {
        return repo.findAll( example );
    }

    @Override
    public Cliente findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Cliente save(Cliente e) {
        return repo.save( e );
    }

}
