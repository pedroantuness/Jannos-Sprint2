package br.com.fiap.Jannos.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LojaRequest(

        @Size(min = 3, max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String nome,

        @Size(max = 255)
        @NotNull(message = "Tipo é obrigatório!")
        String tipo,

        @Size(max = 255)
        String descricao,

        @NotNull(message = "Endereço é obrigatório!")
        AbstractRequest endereco,

        @NotNull(message = "Nota é obrigatória!")
        AbstractRequest nota

) {
}
