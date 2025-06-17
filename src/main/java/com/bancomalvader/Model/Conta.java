/**
 * Classe de modelo que representa uma conta bancária no sistema Banco Malvader.
 *
 * <p>Fornece atributos e métodos básicos para manipular informações como número da conta, saldo,
 * titular, e operações financeiras. Serve como base para contas específicas como corrente e
 * poupança.
 */
package com.bancomalvader.Model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
    private int idConta;
    private String numeroConta;
    private Agencia agencia;
    private BigDecimal saldo;
    private String tipoConta; // POUPANCA, CORRENTE, INVESTIMENTO
    private Cliente cliente;
    private Timestamp dataAbertura;
    private StatusConta status;

    private String nomeCliente;
    private String cpfCliente;

    private Double limite;
    private String dataVencimento;

    public Conta(
            int idConta, 
            String numeroConta, 
            String codigoAgencia, 
            double saldo, 
            String tipoConta, 
            int idCliente) {
        this.idConta = idConta;
        this.numeroConta = numeroConta;
        this.agencia = new Agencia();
        this.agencia.setCodigoAgencia(codigoAgencia);
        this.saldo = BigDecimal.valueOf(saldo);
        this.tipoConta = tipoConta;
        this.cliente = new Cliente();
        this.cliente.setId(idCliente);
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite.doubleValue();
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
