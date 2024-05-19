package br.com.fiap.Jannos.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequest(

        @Size(min = 3, max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String nome,

        @Email(message = "Email inválido!")
        @NotNull(message = "Email é obrigatório!")
        String email,

        @CPF(message = "CPF invávlido!")
        @NotNull(message = "CPF é obrigatório!")
        String cpf,

        @NotNull(message = "Endereço é obrigatório!")
        AbstractRequest endereco,

        @NotNull(message = "Nota é obrigatória!")
        AbstractRequest nota

) {
}
