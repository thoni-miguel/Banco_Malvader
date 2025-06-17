/**
 * Classe de modelo que representa um funcionário no sistema Banco Malvader.
 *
 * <p>Contém informações pessoais e funcionais, como nome, CPF, cargo e permissões, necessárias para
 * a gestão de funcionários no sistema.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Funcionario extends Usuario {
    private String codigoFuncionario;
    private CargoFuncionario cargo;
    private Endereco endereco;
    private Integer idSupervisor;

    // Construtor Completo
    public Funcionario(
            int idUsuario,
            String nome,
            String cpf,
            java.sql.Date dataNascimento,
            String telefone,
            TipoUsuario tipoUsuario,
            String senhaHash,
            String codigoFuncionario,
            CargoFuncionario cargo,
            Endereco endereco,
            Integer idSupervisor) {
        super(idUsuario, nome, cpf, dataNascimento, telefone, tipoUsuario.name(), senhaHash, null, null);
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
        this.endereco = endereco;
        this.idSupervisor = idSupervisor;
    }

    // Construtor Simples
    public Funcionario(
            String codigoFuncionario,
            CargoFuncionario cargo,
            String nome,
            String cpf,
            Date dataNascimento,
            String telefone,
            Endereco endereco) {
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNascimento(dataNascimento);
        this.setTelefone(telefone);
        this.endereco = endereco;
    }

    // Construtor para Login
    public Funcionario(int idFuncionario, String codigoFuncionario, CargoFuncionario cargo, int idUsuario) {
        super(idUsuario, null, null, null, null, String.valueOf(TipoUsuario.FUNCIONARIO), null, null, null);
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
    }

    // Construtor para Funcionario sem Endereço
    public Funcionario(int idUsuario, String nome, String codigoFuncionario, CargoFuncionario cargo) {
        super(idUsuario, nome, null, null, null, String.valueOf(TipoUsuario.FUNCIONARIO), null, null, null);
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
    }

    public Funcionario(
            int idUsuario,
            String nome,
            String cpf,
            Date date,
            String telefone,
            TipoUsuario tipo,
            String senhaHash,
            String codigoFuncionario,
            CargoFuncionario cargo) {
        super(idUsuario, nome, cpf, date, telefone, tipo.name(), senhaHash, null, null);
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
    }
}
