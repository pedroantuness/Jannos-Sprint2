package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record EnderecoResponse(

        Long id,
        String cep,
        Integer numero,
        String complemento,
        String latitude,
        String longitude

) {
}
