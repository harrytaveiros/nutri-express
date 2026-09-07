package br.dtos.prato;

import java.math.BigDecimal;

public record PratoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal valor,
        String categoria,
        Integer calorias,
        Double quantidade,
        String unidadeMedida
) {
}