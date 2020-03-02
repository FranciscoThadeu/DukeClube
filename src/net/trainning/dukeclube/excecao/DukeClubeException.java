/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.excecao;

/**
 *
 * @author trainning
 */
public class DukeClubeException extends Exception{

    public DukeClubeException() {
        super("Causa de erro: Desconhecida");
        
    }

    public DukeClubeException(String message) {
        super(message);
    }
    
    
}
