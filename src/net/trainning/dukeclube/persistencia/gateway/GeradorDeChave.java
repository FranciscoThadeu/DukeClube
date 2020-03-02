/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.persistencia.gateway;

import com.sun.jdi.connect.spi.Connection;
import net.trainning.dukeclube.excecao.DukeClubeException;

/**
 *
 * @author trainning
 */
public class GeradorDeChave {
    private static final byte INCREMENTO = 1;
    private Connection con;
    private String tabela;
    private long proximoCodigo;
    private long maximoCodigo;

    public GeradorDeChave(String tabela)throws DukeClubeException {
        this.con = GerenciadorDeConexao.getConexao();
        this.tabela = tabela;
        this.proximoCodigo = 0;
        this.maximoCodigo = 0;
        try{
            con.setAutoCommit(false);
        }
        catch(SQLException exc){
            StringBuilder mensagem = new StringBuilder("Não foi possível desligar a confirmação automática.");
            mensagem.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(mensagem.toString());
        }
    }
    
    public synchronized long getProximoCodigo() throws DukeClubeException{
        if(proximoCodigo == maximoCodigo){
            reservarCodigo();
        }
        return proximoCodigo;
    }
    
    private void reservarCodigo() throws DukeClubeException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long proximoCodigoNovo;
        
        String sql = "SELECT proximoCodigo FROM chaves WHERE tabela = ? FOR UPDATE";
        try{
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tabela);
            rs = stmt.executeQuery();
            rs.next();
            proximoCodigoNovo = rs.getLong("proximoCodigo");
            
        }
        catch (SQLException exc){
            StringBuilder mensagem = new StringBuilder("Não foi possível gerar o próximo código.");
            mensagem.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(mensagem.toString());
        }
        
        long maximoCodigoNovo = proximoCodigoNovo + INCREMENTO;
        stmt = null;
        rs = null;
        
        try{
            sql = "UPDATE chaves SET proximoCodigo = ? WHERE tabela = ?";
            stmt = con.prepareStatement(sql);
            stmt.setLong(1, maximoCodigoNovo);
            stmt.setString(2, tabela);
            stmt.executeUpdate();
            con.commit();
            proximoCodigo = proximoCodigoNovo;
            maximoCodigo = maximoCodigoNovo;
        }
        catch(SQLException exc){
            StringBuilder mensagem = new StringBuilder("Não foi possível gerar p código.");
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt, rs);
        }
    }
    
}
