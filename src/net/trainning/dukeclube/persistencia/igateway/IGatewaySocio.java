/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.persistencia.igateway;

import java.util.List;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Socio;
/**
 *
 * @author trainning
 */
public interface IGatewaySocio {
    public abstract void gravarSocio(Socio socio) throws DukeClubeException;
    public abstract void excluirSocio (Socio socio) throws DukeClubeException;
    public abstract List pesquisar() throws DukeClubeException;
    public abstract List pesquisar(String nome) throws DukeClubeException;
}
