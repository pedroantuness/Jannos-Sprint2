package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.LojaRequest;
import br.com.fiap.Jannos.dto.response.LojaResponse;
import br.com.fiap.Jannos.entity.Loja;
import br.com.fiap.Jannos.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaService implements ServiceDTO<Loja, LojaRequest, LojaResponse> {

    @Autowired
    private LojaRepository repo;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private NotaService notaService;


    @Override
    public Loja toEntity(LojaRequest r) {

        var endereco = enderecoService.findById( r.endereco().id() );
        var nota = notaService.findById( r.nota().id() );

        return Loja.builder()
                .nome( r.nome() )
                .tipo( r.tipo() )
                .descricao( r.descricao() )
                .endereco( endereco )
                .nota( nota )
                .build();

    }

    @Override
    public LojaResponse toResponse(Loja e) {

        var endereco = enderecoService.toResponse( e.getEndereco() );
        var nota = notaService.toResponse( e.getNota() );

        return LojaResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .tipo( e.getTipo() )
                .descricao( e.getDescricao() )
                .endereco( endereco )
                .nota( nota )
                .build();

    }

    @Override
    public List<Loja> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Loja> findAll(Example<Loja> example) {
        return repo.findAll( example );
    }

    @Override
    public Loja findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Loja save(Loja e) {
        return repo.save( e );
    }

}
