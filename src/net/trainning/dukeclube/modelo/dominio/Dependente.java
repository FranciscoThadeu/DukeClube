/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.modelo.dominio;

import java.util.Date;

/**
 *
 * @author trainning
 */
public class Dependente {
    private String nome;
    private int sexo;
    private java.util.Date dataNascimento;
    private int parentesco;

    public Dependente(String nome, int sexo, Date dataNascimento, int parentesco) {
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.parentesco = parentesco;
    }

    public Dependente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * @return the parentesco
     */
    public int getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco the parentesco to set
     */
    public void setParentesco(int parentesco) {
        this.parentesco = parentesco;
    }
    
    
}
