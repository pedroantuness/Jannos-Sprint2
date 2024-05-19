package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotaRequest(

        @Size(max = 3)
        @NotNull(message = "Nome é obrigatório!")
        Double valor,

        @Size(max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String media,

        @Size(max = 255)
        String descricao

) {
}
