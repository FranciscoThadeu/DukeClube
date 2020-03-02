/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trainning.dukeclube.visao.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import javax.swing.JOptionPane;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Socio;
import net.trainning.dukeclube.modelo.dominio.constante.CategoriaDeSocio;
import net.trainning.dukeclube.modelo.dominio.constante.Constante;
import net.trainning.dukeclube.modelo.dominio.constante.Sexo;

/**
 *
 * @author trainning
 */
public class GUIDadosSocio extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIDadosSocio
     */
    public GUIDadosSocio() {
        initComponents();
        for (CategoriaDeSocio categoria : CategoriaDeSocio.values())
        {
            cbCategoria.addItem(categoria.name());
        }
        
       
    }
     Socio socio;
     
    public Socio getSocio() throws DukeClubeException{
        int categoria = cbCategoria.getSelectedIndex();
        if(categoria == 0){
            throw new DukeClubeException("Não foi informada a categoria do sócio.");
        }else{
            socio.setCategoria(categoria - 1);
        }
        String nome = tfNome.getText();
        if(nome == null || nome.trim().equals("")){
            tfNome.requestFocus();
            throw new DukeClubeException("Não foi informado o nome do sócio.");
        }else{
            socio.setNome(nome);
        }
        
        if(rbFeminino.isSelected()){
            socio.setSexo(Sexo.Feminino.ordinal());
        }else if (rbMasculino.isSelected()){
            socio.setSexo(Sexo.Masculino.ordinal());
        }else{
            throw new DukeClubeException("Não foi informado o sexo do sócio");
        }
        
        String strDataNascimento = tfDataNascimento.getText();
        DateFormat df = DateFormat.getDateInstance();
        try{
            java.util.Date dataNascimento = df.parse(strDataNascimento);
            socio.setDataNascimento(dataNascimento);
        }catch(ParseException ex){
            tfDataNascimento.requestFocus();
            String mensagem  = "Não foi informada a data de nascimento ou ela é inválida.";
            throw new DukeClubeException(mensagem);
        }
//        catch(DukeClubeException ex){
//            tfDataNascimento.requestFocus();
//            throw new DukeClubeException(ex.getMessage());
//        }
        socio.setTelefoneFixo(tfTelefoneFixo.getText());
        socio.setTelefoneCelular(tfTelefoneCelular.getText());
        socio.setEmail(tfEmail.getText());
        
        return socio;
    }
    
    public void setSocio(Socio socio){
        this.socio = socio;
        this.exibeDadosDoSocio();
    }
    
    public void exibeDadosDoSocio(){
        limparCampos();
        if(socio.getCodigo() != Constante.NOVO){
            cbCategoria.setSelectedIndex(socio.getCategoria() + 1);
            tfNome.setText(socio.getNome());
            if(socio.getSexo() == Sexo.Masculino.ordinal()){
                rbMasculino.setSelected(true);
            }
            if(socio.getSexo() == Sexo.Feminino.ordinal()){
                rbFeminino.setSelected(true);
            }
            DateFormat df = DateFormat.getDateInstance();
            java.util.Date dataNascimento = socio.getDataNascimento();
            if(dataNascimento != null){
                tfDataNascimento .setText(df.format(dataNascimento));
            }
            tfTelefoneFixo.setText(socio.getTelefoneFixo());
            tfTelefoneCelular.setText(socio.getTelefoneCelular());
            tfEmail.setText(socio.getEmail());
        }
    }
    
    public void setPosicao(){
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation(15 + (d.width - this.getSize().width) / 5,
                         15 + (d.height - this.getSize().height) / 5);
    }
    
    public void showMensagem(String mensagem, boolean isErro){
        if(isErro){
            JOptionPane.showMessageDialog(null,
                    mensagem,
                    "Mensagem de erro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void bGravarSocioAddActionListener(ActionListener ouvinte){
        bGravarSocio.addActionListener(ouvinte);
    }
    
    public void limparCampos(){
        cbCategoria.setSelectedIndex(0);
        tfNome.setText(null);
        tfDataNascimento.setText(null);
        tfTelefoneFixo.setText(null);
        tfTelefoneCelular.setText(null);
        tfEmail.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgSexo = new javax.swing.ButtonGroup();
        pDadosSocio = new javax.swing.JPanel();
        lCategoria = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        lNome = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        lSexo = new javax.swing.JLabel();
        rbFeminino = new javax.swing.JRadioButton();
        rbMasculino = new javax.swing.JRadioButton();
        lDataNascimento = new javax.swing.JLabel();
        tfDataNascimento = new javax.swing.JFormattedTextField();
        lTelefoneFixo = new javax.swing.JLabel();
        lTelefoneCelular = new javax.swing.JLabel();
        tfTelefoneFixo = new javax.swing.JFormattedTextField();
        tfTelefoneCelular = new javax.swing.JFormattedTextField();
        lEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        bLimpar = new javax.swing.JButton();
        bGravarSocio = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("DukeClube - Dados do sócio");

        pDadosSocio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Dados do sócio")));

        lCategoria.setText("Categoria");

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));

        lNome.setText("Nome");

        lSexo.setText("Sexo");

        bgSexo.add(rbFeminino);
        rbFeminino.setText("Feminino");

        bgSexo.add(rbMasculino);
        rbMasculino.setText("Masculino");

        lDataNascimento.setText("Data de Nacimento");

        try {
            tfDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lTelefoneFixo.setText("Telefone Fixo");

        lTelefoneCelular.setText("Telefone Celular");

        try {
            tfTelefoneFixo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            tfTelefoneCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lEmail.setText("Email");

        bLimpar.setText("Limpar");
        bLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimparActionPerformed(evt);
            }
        });

        bGravarSocio.setText("Gravar");

        javax.swing.GroupLayout pDadosSocioLayout = new javax.swing.GroupLayout(pDadosSocio);
        pDadosSocio.setLayout(pDadosSocioLayout);
        pDadosSocioLayout.setHorizontalGroup(
            pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosSocioLayout.createSequentialGroup()
                .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDadosSocioLayout.createSequentialGroup()
                        .addComponent(lCategoria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lSexo)
                    .addGroup(pDadosSocioLayout.createSequentialGroup()
                        .addComponent(rbFeminino)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbMasculino)))
                .addGap(18, 18, 18)
                .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pDadosSocioLayout.createSequentialGroup()
                        .addComponent(lNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNome))
                    .addGroup(pDadosSocioLayout.createSequentialGroup()
                        .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfDataNascimento))
                        .addGap(18, 18, 18)
                        .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lTelefoneFixo)
                            .addComponent(tfTelefoneFixo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lTelefoneCelular)
                            .addComponent(tfTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDadosSocioLayout.createSequentialGroup()
                        .addComponent(bLimpar)
                        .addGap(55, 55, 55)
                        .addComponent(bGravarSocio))))
            .addComponent(lEmail)
            .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pDadosSocioLayout.setVerticalGroup(
            pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosSocioLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCategoria)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lNome)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSexo)
                    .addComponent(lDataNascimento)
                    .addComponent(lTelefoneFixo)
                    .addComponent(lTelefoneCelular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbFeminino)
                    .addComponent(rbMasculino)
                    .addComponent(tfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTelefoneFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(pDadosSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bLimpar)
                    .addComponent(bGravarSocio))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pDadosSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pDadosSocio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_bLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bGravarSocio;
    private javax.swing.JButton bLimpar;
    private javax.swing.ButtonGroup bgSexo;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JLabel lCategoria;
    private javax.swing.JLabel lDataNascimento;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lSexo;
    private javax.swing.JLabel lTelefoneCelular;
    private javax.swing.JLabel lTelefoneFixo;
    private javax.swing.JPanel pDadosSocio;
    private javax.swing.JRadioButton rbFeminino;
    private javax.swing.JRadioButton rbMasculino;
    private javax.swing.JFormattedTextField tfDataNascimento;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfNome;
    private javax.swing.JFormattedTextField tfTelefoneCelular;
    private javax.swing.JFormattedTextField tfTelefoneFixo;
    // End of variables declaration//GEN-END:variables
}