package br.com.fiap.Jannos.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoRequest(

        @Size(min = 3, max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String nome,

        @Size(max = 255)
        @NotNull(message = "Descrição é obrigatória!")
        String descricao,

        @Size(max = 255)
        @NotNull(message = "Tipo é obrigatório!")
        String tipo,

        @NotNull(message = "Anuncio é obrigatório!")
        AbstractRequest anuncio,

        @NotNull(message = "Loja é obrigatória!")
        AbstractRequest loja

) {
}
