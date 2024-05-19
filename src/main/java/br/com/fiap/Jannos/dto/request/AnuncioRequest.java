package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record AnuncioRequest(

        @Size(max = 6)
        @NotNull(message = "Valor é obrigatório!")
        Double valor,

        @Size(max = 4)
        @NotNull(message = "Quantidade é obrigatório!")
        Integer quantidade,

        @Size(max = 255)
        String descricao,

        @Size(max = 255)
        @NotNull(message = "Prazo é obrigatório!")
        String prazo,


        @NotNull(message = "A data de inicio é obrigatória!")
        Date inicio,

        Date fim

) {
}
