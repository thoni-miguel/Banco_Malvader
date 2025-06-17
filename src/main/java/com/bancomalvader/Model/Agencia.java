package com.bancomalvader.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agencia {
    private int idAgencia;
    private String nome;
    private String codigoAgencia;
    private Endereco endereco;
} 