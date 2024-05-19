package br.com.fiap.Jannos.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsumoRequest(

        @Size(max = 255)
        @NotNull(message = "Quantidade é obrigatório!")
        String quantidade,

        String descricao,

        @NotNull(message = "Cliente é obrigatório!")
        AbstractRequest cliente,

        @NotNull(message = "Anuncio é obrigatório!")
        AbstractRequest anuncio

) {
}
