package com.bancomalvader.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {
    private int idAuditoria;
    private Usuario usuario;
    private String acao;
    private Timestamp dataHora;
    private String detalhes;
} 