/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.visao.ouvinte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Socio;
import net.trainning.dukeclube.modelo.dominio.controle.ControleSocio;
import net.trainning.dukeclube.visao.gui.GUISocios;



/**
 *
 * @author trainning
 */
public class OuvinteDeGUISocios {

    public OuvinteDeGUISocios(GUISocios guiSocios) {
        this.guiSocios = guiSocios;
        guiSocios.bExcluirSocioActionListener(new OuvinteExcluirSocio());
        guiSocios.bPesquisarSocioActionListener(new OuvintePesquisarSocio());
    }
    
    
    GUISocios guiSocios;
    
    class OuvintePesquisarSocio implements ActionListener {
        public void actionPeformed(ActionEvent e){
            try{
                ControleSocio controle = new ControleSocio();
                String nome = guiSocios.getNome();
                List socios = controle.pesquisar(nome);
                
                guiSocios.exibirSocios(socios);
            }catch(DukeClubeException exc){
                guiSocios.exibirMensagem(exc.getMessage(), "Mensagem de erro", true);
            }
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
          //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    class OuvinteExcluirSocio implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                Socio socio = guiSocios.getSocio();
                StringBuilder mensagem = new StringBuilder("Confirma a exclusão do sócio abaixo: ");
                mensagem.append("\nCódigo: " + socio.getCodigo());
                mensagem.append("\nNome: " + socio.getNome());
                int resposta = guiSocios.pedirConfirmacao(mensagem.toString(), "Exclusão de registro",JOptionPane.YES_NO_OPTION);
                if(resposta == JOptionPane.OK_OPTION){
                    ControleSocio controle = new ControleSocio();
                    controle.excluirSocio(socio);
                    List socios = controle.pesquisar();
                    guiSocios.exibirSocios(socios);
                }
            }catch(DukeClubeException exc){
                guiSocios.exibirMensagem(exc.getMessage(), "Mensagem de erro", true);
                    
            }
        }
    }
}
