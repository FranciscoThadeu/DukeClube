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
class GerenciadorDeConexao {
    /**
     * Obtém uma conexão com o banco de dados
     * @return a conexão com o banco de dados
     * @throws DukeClubeException 
     */
    static Connection getConexao() throws DukeClubeException{
        try {
            Class.forName(ParametrosBD.DRIVER);
            Connection con = DriverManager.getConnection(
                ParametrosBD.URL,
                ParametrosBD.URL,
                ParametrosBD.PASSWORD);
            return con;
        }
        catch(ClassNotFoundException exc){
            String mensagem = "Não foi possível localizar o drive de banco de dados";
            throw new DukeClubeException(mensagem);
        }
        catch(SQLException exc){
             String mensagem = "Não foi possível conectar com banco de dados";
            throw new DukeClubeException(mensagem);
        }
    }
    
    /**
     * Fecha a conexão com o banco de dados
     * @param con a conexão a ser fechada
     * @throws DukeClubeException 
     */
    static void closeConexao(Connection con) throws DukeClubeException{
        closeConexao(con,null,null);
    }
    
    /**
     * Fecha a conexão com o banco de dados e o objeto PreparedStatement
     * @param con a conexão a ser fechada
     * @param stmt o objeto PreparedStatement a ser fechado
     * @throws DukeClubeException 
     */
    static void closeConexao(Connection con, PreparedStatement stmt) throws DukeClubeException{
        closeConexao(con,stmt,null);
    }
    
    /**
     * Fecha a conexão com o banco de dados e o objeto PreparedStatement e ResultSet
     * @param con a conexão a ser fechada
     * @param stmt o objeto PreparedStatement a ser fechado
     * @param rs o objeto ResultSet a ser fechado
     * @throws DukeClubeException 
     */
    static void closeConexao(Connection con, PreparedStatement stmt, ResultSet rs) throws DukeClubeException{
        try{
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
            if(con != null){
                con.close();
            }
        }
        catch(SQLException exc){
            StringBuilder mensagem = new StringBuilder("Não foi possível finalizar a conexão com o banco de dados.");
            mensagem.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(mensagem.toString());
        }
    }
    
}
