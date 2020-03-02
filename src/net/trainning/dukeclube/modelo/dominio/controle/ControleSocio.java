/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.modelo.dominio.controle;

import java.util.List;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Socio;
import net.trainning.dukeclube.persistencia.FactoryGateway;
import net.trainning.dukeclube.persistencia.IFactoryGateway;
import net.trainning.dukeclube.persistencia.igateway.IGatewaySocio;

/**
 *
 * @author trainning
 */
public class ControleSocio {
    public void gravarSocio(Socio socio) throws DukeClubeException{
        IFactoryGateway factoryGateway = new FactoryGateway();
        IGatewaySocio gatewaySocio = factoryGateway.getGatewaySocio();
        gatewaySocio.gravarSocio(socio);
                
    }
    
    public void excluirSocio(Socio socio) throws DukeClubeException{
        IFactoryGateway factoryGateway = new FactoryGateway();
        IGatewaySocio gatewaySocio = factoryGateway.getGatewaySocio();
        gatewaySocio.excluirSocio(socio);
                
    }
    
    public List pesquisar() throws DukeClubeException{
        IFactoryGateway factoryGateway = new FactoryGateway();
        IGatewaySocio gatewaySocio = factoryGateway.getGatewaySocio();
        return gatewaySocio.pesquisar();
                
    }
    public List pesquisar(String nome) throws DukeClubeException{
        IFactoryGateway factoryGateway = new FactoryGateway();
        IGatewaySocio gatewaySocio = factoryGateway.getGatewaySocio();
        return gatewaySocio.pesquisar(nome);
                
    }
    
}
