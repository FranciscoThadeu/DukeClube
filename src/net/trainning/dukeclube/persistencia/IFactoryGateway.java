/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.persistencia;

import net.trainning.dukeclube.persistencia.igateway.IGatewaySocio;

/**
 *
 * @author trainning
 */
public interface IFactoryGateway {
    public IGatewaySocio getGatewaySocio();
}
