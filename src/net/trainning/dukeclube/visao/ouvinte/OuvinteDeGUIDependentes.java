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
import net.trainning.dukeclube.visao.gui.GUIDependentes;

/**
 *
 * @author trainning
 */
public class OuvinteDeGUIDependentes {
    private GUIDependentes guiDependentes;

    public OuvinteDeGUIDependentes(GUIDependentes guiDependentes) {
        this.guiDependentes = guiDependentes;
        guiDependentes.bGravarSocioAddActionListener(new OuvinteGravarSocio());
    }
    
    
    class OuvinteGravarSocio implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Socio socio = guiDependentes.getSocio();
            ControleSocio controle = new ControleSocio();
            try{
                controle.gravarSocio(socio);
                guiDependentes.exibirMensagem("Sócio gravado com sucesso","DukeClube - Dependentes",false);
            }catch(DukeClubeException ex){
                guiDependentes.exibirMensagem(ex.getMessage(),"DukeClube - Dependentes",true);
            }
        }
    }
    
}
