package com.bancomalvader.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaInvestimento {
    private int idContaInvestimento;
    private Conta conta;
    private PerfilRisco perfilRisco;
    private BigDecimal valorMinimo;
    private BigDecimal taxaRendimentoBase;
} 