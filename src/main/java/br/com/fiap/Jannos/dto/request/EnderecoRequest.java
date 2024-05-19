package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(

        @Pattern(regexp = "([0-9]{5}-?[0-9]{3} | [0-9]{8})")
        @NotNull(message = "CEP é obrigatório!")
        String cep,

        @Size(max = 4)
        @NotNull(message = "Número é obrigatório!")
        Integer numero,

        @Size(max = 255)
        String complemento,

        String latitude,

        String longitude

) {
}
