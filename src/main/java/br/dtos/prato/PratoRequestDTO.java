package br.dtos.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PratoRequestDTO(
        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotNull
        @Positive
        BigDecimal valor,

        @NotBlank
        String categoria,

        @NotNull
        @Positive
        Integer calorias,

        @NotNull
        @Positive
        Double quantidade,

        @NotBlank
        String unidadeMedida
) {
}