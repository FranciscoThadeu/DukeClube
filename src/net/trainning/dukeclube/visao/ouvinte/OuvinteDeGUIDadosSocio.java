/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.visao.ouvinte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Socio;
import net.trainning.dukeclube.modelo.dominio.controle.ControleSocio;
import net.trainning.dukeclube.visao.gui.GUIDadosSocio;

/**
 *
 * @author trainning
 */
public class OuvinteDeGUIDadosSocio {
    GUIDadosSocio guiDadosSocio;

    public OuvinteDeGUIDadosSocio(GUIDadosSocio gui) {
        guiDadosSocio = gui;
        guiDadosSocio.bGravarSocioAddActionListener(new OuvinteGravarSocio());
    }
    
    class OuvinteGravarSocio implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Socio socio;
            try{
                socio = guiDadosSocio.getSocio();
                ControleSocio controle = new ControleSocio();
                controle.gravarSocio(socio);
                guiDadosSocio.showMensagem("Sócio gravado com sucesso!", false);
                guiDadosSocio.limparCampos();
            }catch(DukeClubeException ex){
                guiDadosSocio.showMensagem(ex.getMessage(), true);
            }
        }
    }
}
