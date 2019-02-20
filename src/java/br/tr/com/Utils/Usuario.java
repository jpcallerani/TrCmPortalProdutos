package br.tr.com.Utils;

import java.io.Serializable;
import java.math.BigDecimal;

public class Usuario implements Serializable {

    private String login_func;
    private BigDecimal cod_func;
    private String nome_func;
    private String email;
    private String ativo;
    private String senha;
    private String nivel_acesso;
    private boolean adm;

    /**
     *
     */
    public Usuario() {

    }

    public String getLogin_func() {
        return login_func;
    }

    public void setLogin_func(String login_func) {
        this.login_func = login_func;
    }

    public BigDecimal getCod_func() {
        return cod_func;
    }

    public void setCod_func(BigDecimal cod_func) {
        this.cod_func = cod_func;
    }

    public String getNome_func() {
        return nome_func;
    }

    public void setNome_func(String nome_func) {
        this.nome_func = nome_func;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivel_acesso() {
        return nivel_acesso;
    }

    public void setNivel_acesso(String nivel_acesso) {
        this.nivel_acesso = nivel_acesso;
        if (this.nivel_acesso.equalsIgnoreCase("AD")) {
            this.adm = true;
        } else {
            this.adm = false;
        }
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }
}
