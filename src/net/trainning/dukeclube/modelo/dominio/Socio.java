/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.modelo.dominio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.trainning.dukeclube.excecao.DukeClubeException;

/**
 *
 * @author trainning
 */
public class Socio {
    private long codigo;
    private int categoria;
    private String nome;
    private int sexo;
    private java.util.Date dataNascimento;
    private String telefoneFixo;
    private String telefoneCelular;
    private String email;
    private List dependentes;

    public Socio(long codigo, int categoria, String nome, int sexo, Date dataNascimento, String telefoneFixo, String telefoneCelular, String email) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.email = email;
        
        dependentes = new ArrayList();
    }

    public Socio() {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the categoria
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        if(nome == null || nome.length() == 0){
            throw new IllegalArgumentException("O nome do sócio não foi informado!");
        }
        this.nome = nome;
    }

    /**
     * @return the sexo
     */
    public int getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the dataNascimento
     */
    public java.util.Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(java.util.Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the telefoneFixo
     */
    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    /**
     * @param telefoneFixo the telefoneFixo to set
     */
    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    /**
     * @return the telefoneCelular
     */
    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    /**
     * @param telefoneCelular the telefoneCelular to set
     */
    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void adicionarDependente(Dependente dependente){
        this.dependentes.add(dependente);
    }
    
    public void removerDependente(Dependente dependente){
        this.dependentes.remove(dependente);
    }
    
    public void removerDependente(int indice){
        this.dependentes.remove(indice);
    }
    
    public List getDependentes(){
        return Collections.unmodifiableList(dependentes);
    }
    
}
