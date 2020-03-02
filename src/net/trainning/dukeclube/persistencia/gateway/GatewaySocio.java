/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.persistencia.gateway;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Dependente;
import net.trainning.dukeclube.modelo.dominio.Socio;
import net.trainning.dukeclube.modelo.dominio.constante.Constante;
import net.trainning.dukeclube.persistencia.igateway.IGatewaySocio;

/**
 *
 * @author trainning
 */
public class GatewaySocio  implements IGatewaySocio {
    private static final String SQL_INCLUIR =
            "INSERT INTO socio " +
            "(categoria, nome,sexo,datanascimento, telefonefixo, telefonecelular, email) " +
            "values (?,?,?,?,?,?,?)";

    private static final String SQL_ALTERAR =
            "UPDATE socio SET " +
            "categoria = ?, nome = ?, sexo = ?, datanascimento = ?, telefonefixo = ?, telefonecelular = ?, email = ? " +
            "WHERE codigosocio = ? ";
    
    private static final String SQL_EXCLUIR = 
            "DELETE FROM socio " +
            "WHERE codigosocio = ? ";
    
    private static final String SQL_PESQUISARTODOS =
            "SELECT * FROM socio ";
    
    private static final String SQL_PESQUISARPORNOME = 
            "SELECT * FROM socio " +
            "WHERE nome LIKE ?";
    
    
     private static final String SQL_INCLUIRDEPENDENTE =
            "INSERT INTO dependente " +
            "(codigodependente, nome,sexo,datanascimento, parentesco, codigosocio) " +
            "values (?,?,?,?,?,?)";
    
    private static final String SQL_EXCLUIRDEPENDENTE = 
            "DELETE FROM dependente " +
            "WHERE codigosocio = ? ";
    
    private static final String SQL_PESQUISARDEPENDENTE =
            "SELECT * FROM dependente " +
            "WHERE codigosocio = ? ";
    
    
    
    
    private void incluirSocio(Socio socio) throws DukeClubeException{
        if(socio == null){
            String mensagem = "Não foi informado o socio a cadastrar.";
            throw new DukeClubeException(mensagem);
        }
        
        Connection con = null;
        PreparedStatement  stmt = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_INCLUIR);
          //  GeradorDeChave geradorDeChave = new GeradorDeChave("Socio");
          //  long codigosocio = geradorDeChave.getProximoCodigo();
         //   stmt.setLong(1,codigosocio);
            stmt.setInt(1,socio.getCategoria());
            stmt.setString(2,socio.getNome());
            stmt.setInt(3,socio.getSexo());
            java.util.Date dataNascimento = socio.getDataNascimento();
            stmt.setString(4, (String)dataNascimento.toString());
            stmt.setString(5, socio.getTelefoneFixo());
            stmt.setString(6, socio.getTelefoneCelular());
            stmt.setString(7, socio.getEmail());
            
            stmt.executeUpdate();
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível incluir o sócio");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt);
        }
        
    }
    
    private void alterarSocio(Socio socio) throws DukeClubeException{
        if(socio == null){
            String mensagem = "Não foi informado o socio a alterar.";
            throw new DukeClubeException(mensagem);
        }
        
        Connection con = null;
        PreparedStatement  stmt = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_ALTERAR);
            
            stmt.setInt(1,socio.getCategoria());
            stmt.setString(2,socio.getNome());
            stmt.setInt(3,socio.getSexo());
            java.util.Date dataNascimento = socio.getDataNascimento();
            stmt.setDate(4, (Date) new java.util.Date(dataNascimento.getTime()));
            stmt.setString(5, socio.getTelefoneFixo());
            stmt.setString(6, socio.getTelefoneCelular());
            stmt.setString(7, socio.getEmail());

            stmt.setLong(8,socio.getCodigo());
            
            stmt.executeUpdate();
            atualizarDependente(socio);
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível atualizar ps dados do sócio");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt);
        }
        
    }
    
    
     private Socio criarSocio(ResultSet rs) throws DukeClubeException{
        Socio socio = new Socio();
         
        try{
           socio.setCodigo(rs.getLong("codigosocio"));
           socio.setCategoria(rs.getByte("Categoria"));
           socio.setNome(rs.getString("nome"));
           socio.setSexo(rs.getByte("sexo"));
           socio.setDataNascimento(rs.getDate("datanascimento"));
           socio.setTelefoneFixo(rs.getString("telefonefixo"));
           socio.setTelefoneCelular(rs.getString("telefonecelular"));
           socio.setEmail(rs.getString("email"));
           this.carregarDependentes(socio);
           
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível obter dados do sócio");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        return socio;
        
    }
    
    @Override
    public void gravarSocio(Socio socio) throws DukeClubeException {
        if(socio.getCodigo() == Constante.NOVO){
            incluirSocio(socio);
        }
        else{
            alterarSocio(socio);
        }
    }

    @Override
    public void excluirSocio(Socio socio) throws DukeClubeException {
          if(socio == null){
            String mensagem = "Não foi informado o socio a excluir.";
            throw new DukeClubeException(mensagem);
        }
        
        Connection con = null;
        PreparedStatement  stmt = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_EXCLUIR);
            stmt.setLong(1,socio.getCodigo());
            
            stmt.executeUpdate();
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível excluir o sócio");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt);
        }
    }

    @Override
    public List pesquisar() throws DukeClubeException {
    
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_PESQUISARTODOS);
            
            rs = stmt.executeQuery();
            List socios = new ArrayList();
            while(rs.next()){
                Socio socio = criarSocio(rs);
                socios.add(socio);
            }
            
            return socios;
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível realizar a consulta");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt, rs);
        }
    }

    @Override
    public List pesquisar(String nome) throws DukeClubeException {
         if(nome == null || nome.trim().equals("")){
            return pesquisar();
        }
        
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_PESQUISARPORNOME);
            
            stmt.setString(1,"%" + nome + "%");
            rs = stmt.executeQuery();
            List socios = new ArrayList();
            while(rs.next()){
                Socio socio = criarSocio(rs);
                socios.add(socio);
            }
            
            return socios;
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível realizar a consulta");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt, rs);
        }
    }
    
    
    
     public void atualizarDependente(Socio socio) throws DukeClubeException{
        if(socio == null){
            String mensagem = "Não foi informado o socio a alterar.";
            throw new DukeClubeException(mensagem);
        }
        
        this.excluirDependente(socio);
        this.incluirDependente(socio);
        
    }
     
       
     
    private void excluirDependente(Socio socio) throws DukeClubeException {
          if(socio == null){
            String mensagem = "Não foi informado o socio a alterar.";
            throw new DukeClubeException(mensagem);
        }
        
        Connection con = null;
        PreparedStatement  stmt = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_EXCLUIRDEPENDENTE);
            stmt.setLong(1,socio.getCodigo());
            
            stmt.executeUpdate();
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível atualiazar dependentes");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt);
        }
    }
    
    private void incluirDependente(Socio socio) throws DukeClubeException{
        if(socio == null){
            String mensagem = "Não foi informado o socio a alterar.";
            throw new DukeClubeException(mensagem);
        }
        
        Connection con = null;
        PreparedStatement  stmt = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_INCLUIRDEPENDENTE);
            long codigo = 1;
            Iterator dependentes = socio.getDependentes().iterator();
            while(dependentes.hasNext()){
                Dependente dependente = (Dependente)dependentes.next();
                stmt.setLong(1,codigo);
                stmt.setString(2,dependente.getNome());
                stmt.setInt(3,dependente.getSexo());
                java.util.Date dataNascimento = dependente.getDataNascimento();
                stmt.setDate(4, (Date) new java.util.Date(dataNascimento.getTime()));
                stmt.setInt(5,dependente.getParentesco());
                stmt.setLong(6,socio.getCodigo());
                stmt.executeUpdate();
                codigo ++;
            }
            
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível atualizar os dependentes");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt);
        }
        
    }
    
    private void carregarDependentes(Socio socio) throws DukeClubeException {
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try{
            con = GerenciadorDeConexao.getConexao();
            stmt = con.prepareStatement(SQL_PESQUISARDEPENDENTE);
            
            stmt.setLong(1,socio.getCodigo());
            rs = stmt.executeQuery();
            List socios = new ArrayList();
            while(rs.next()){
                Dependente dependente = criarDependente(rs);
                boolean add = socios.add(dependente);
            }
            
        }
        catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível realizar a consulta");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        finally{
            GerenciadorDeConexao.closeConexao(con, stmt, rs);
        }
    }
    
    private Dependente criarDependente(ResultSet rs)throws DukeClubeException {
        Dependente dependente = new Dependente();
        try{
            dependente.setNome(rs.getString(2));
            dependente.setSexo(rs.getInt(3));
            dependente.setDataNascimento(rs.getDate(4));
            dependente.setParentesco(rs.getInt(5));
        }catch(SQLException exc){
            StringBuilder msg = new StringBuilder("Não foi possível obter dados do dependente.");
            msg.append("\nMotivo: " + exc.getMessage());
            throw new DukeClubeException(msg.toString());
        }
        
        return dependente;
    }
     
}
