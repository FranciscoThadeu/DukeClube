/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.persistencia;

import net.trainning.dukeclube.persistencia.gateway.GatewaySocio;
import net.trainning.dukeclube.persistencia.igateway.IGatewaySocio;

/**
 *
 * @author trainning
 */
public class FactoryGateway implements IFactoryGateway{

    @Override
    public IGatewaySocio getGatewaySocio() {
        return new GatewaySocio();
    }
    
}
