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
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.trainning.dukeclube.excecao.DukeClubeException;
import net.trainning.dukeclube.modelo.dominio.Dependente;
import net.trainning.dukeclube.modelo.dominio.Socio;
import net.trainning.dukeclube.modelo.dominio.constante.RelacaoDeParentesco;
import net.trainning.dukeclube.modelo.dominio.constante.Sexo;
import net.trainning.dukeclube.visao.ouvinte.OuvinteDaTabelaDependentes;

/**
 *
 * @author trainning
 */
public class GUIDependentes extends javax.swing.JInternalFrame {
    private Socio socio;
    /**
     * Creates new form GUIDependentes
     */
    public GUIDependentes() {
        initComponents();
        for(RelacaoDeParentesco parentesco: RelacaoDeParentesco.values()){
            cbParentesco.addItem(parentesco.name());
        }
        tDependentes.getSelectionModel().addListSelectionListener(new OuvinteDaTabelaDependentes());
    }

   
    
    
     public void setPosicao(){
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation(30 + (d.width - this.getSize().width) / 5,
                        30 + (d.height - this.getSize().height) / 5);
    }
     
    public Socio getSocio(){
        return this.socio;
    }
     
    public void setSocio(Socio socio){
        this.socio = socio;
        this.exibirNomeDoSocio(socio.getNome());
        this.exibirDependente(socio.getDependentes().iterator());
    }
     
    private void exibirNomeDoSocio(String nome){
        tfNomeSocio.setText(nome);
    }
     
    private void exibirDependente(Iterator dependentes) {
        DefaultTableModel model = (DefaultTableModel)tDependentes.getModel();
        this.removerLinhasDaTabela(model);
        
        while(dependentes.hasNext()){
            Dependente dependente = (Dependente)dependentes.next();
            String nome = dependente.getNome();
            String sexo = null;
            if(dependente.getSexo() == Sexo.Feminino.ordinal()){
                sexo = Sexo.Feminino.name();
            }
            if(dependente.getSexo() == Sexo.Masculino.ordinal()){
                sexo = Sexo.Masculino.name();
            }
            DateFormat df = DateFormat.getDateInstance();
            String dataNascimento = null;
            if(dependente.getDataNascimento() != null){
                dataNascimento = df.format(dependente.getDataNascimento());
            }
            
            String relacaoDeParentesco = null;
            for (RelacaoDeParentesco parentesco : RelacaoDeParentesco.values()){
                int posicao = parentesco.ordinal();
                if(++posicao == dependente.getParentesco()){
                    relacaoDeParentesco = parentesco.name();
                }
            }
            
            Object[] linha = {nome, sexo, dataNascimento, relacaoDeParentesco};
            
            model.addRow(linha);
        }
    }
    
    private void removerLinhasDaTabela(DefaultTableModel model) {
        while (model.getRowCount() > 0){
            int ultimaLinha  =model.getRowCount() -1;
            model.removeRow(ultimaLinha);
        }
    }
    
    public void bGravarSocioAddActionListener(ActionListener ouvinte){
        bGravarSocio.addActionListener(ouvinte);
    }
    
    public void exibirMensagem(String mensagem, String titulo, boolean isErro){
        int tipo;
        if(isErro){
            tipo = JOptionPane.ERROR_MESSAGE;
        }else{
            tipo = JOptionPane.INFORMATION_MESSAGE;
        }
        JOptionPane.showMessageDialog(null, mensagem, titulo, tipo);
    }
    
    public void limparCampos(){
        tfNomeDependente.setText(null);
        rbFeminino.setSelected(false);
        rbMasculino.setSelected(false);
        tfDataNascimento.setText(null);
        tfNomeDependente.setText(null);
    }
    
    private Dependente criarDependente() throws DukeClubeException{
        Dependente novoDependente = new Dependente();
        
        String nome = tfNomeDependente.getText();
        if(nome.equals("") || nome.trim().equals("")){
            throw new DukeClubeException("Não foi informado o nome do dependente");
            
        }else{
            novoDependente.setNome(nome);
        }
        
        if(rbFeminino.isSelected()){
            novoDependente.setSexo(Sexo.Feminino.ordinal());
        }else if(rbMasculino.isSelected()){
            novoDependente.setSexo(Sexo.Masculino.ordinal());
        }else{
            throw new DukeClubeException("Não foi informado o sexo do dependente");
        }
        
        DateFormat df = DateFormat.getDateInstance();
        java.util.Date dataNascimento;
        try{
            dataNascimento = df.parse(tfDataNascimento.getText());
            novoDependente.setDataNascimento(dataNascimento);
        }catch(ParseException ex){
            throw new DukeClubeException("Não foi informado a data de nascimento do dependente ou ela é inválida.");
        }
        
        int parentesco = cbParentesco.getSelectedIndex();
        if(parentesco == 0){
            throw new DukeClubeException("Não foi informado o parentesco do dependente");
        }else{
            novoDependente.setParentesco(parentesco);
        }
        
        return novoDependente;
    }
    
    class OuvinteDaTabelaDependentes implements ListSelectionListener{
        
        public void valueChaged(ListSelectionEvent e){
            if(e.getValueIsAdjusting()){
                int linha = tDependentes.getSelectedRow();
                
                tfNomeDependente.setText((String) tDependentes.getValueAt(linha, 0));
                String sexo = (String)tDependentes.getValueAt(linha, 1);
                if(sexo.equals(Sexo.Feminino.name())){
                    rbFeminino.setSelected(true);
                }
                if(sexo.equals(Sexo.Masculino.name())){
                    rbMasculino.setSelected(true);
                }
                
                 tfDataNascimento.setText((String) tDependentes.getValueAt(linha, 2));
                 String relacaoDeParentesco = (String) tDependentes.getValueAt(linha, 3);
                 for (RelacaoDeParentesco parentesco : RelacaoDeParentesco.values()){
                     if(relacaoDeParentesco.equals(parentesco.name())){
                         cbParentesco.setSelectedIndex(parentesco.ordinal() +1);
                     }
                 }
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
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
        INomeSocio = new javax.swing.JLabel();
        tfNomeSocio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tDependentes = new javax.swing.JTable();
        pDadosDependente = new javax.swing.JPanel();
        tfNomeDependente = new javax.swing.JTextField();
        lNomeDependente = new javax.swing.JLabel();
        lSexo = new javax.swing.JLabel();
        rbFeminino = new javax.swing.JRadioButton();
        rbMasculino = new javax.swing.JRadioButton();
        lDataNascimento = new javax.swing.JLabel();
        tfDataNascimento = new javax.swing.JFormattedTextField();
        lParentesco = new javax.swing.JLabel();
        cbParentesco = new javax.swing.JComboBox<>();
        bIncluirDependente = new javax.swing.JButton();
        bAlterarDependente = new javax.swing.JButton();
        bExcluirDependente = new javax.swing.JButton();
        bGravarSocio = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("DukeClube - Dependentes");

        INomeSocio.setText("Sócio");

        tfNomeSocio.setEditable(false);

        tDependentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Sexo", "Data de Nascimento", "Parentesco"
            }
        ));
        tDependentes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tDependentes);

        pDadosDependente.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Dependente"));

        lNomeDependente.setText("Nome");

        lSexo.setText("Sexo");

        bgSexo.add(rbFeminino);
        rbFeminino.setText("Feminino");

        bgSexo.add(rbMasculino);
        rbMasculino.setText("Masculino");

        lDataNascimento.setText("Data de Nascimento");

        try {
            tfDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lParentesco.setText("Parentesco");

        cbParentesco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));

        javax.swing.GroupLayout pDadosDependenteLayout = new javax.swing.GroupLayout(pDadosDependente);
        pDadosDependente.setLayout(pDadosDependenteLayout);
        pDadosDependenteLayout.setHorizontalGroup(
            pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosDependenteLayout.createSequentialGroup()
                .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDadosDependenteLayout.createSequentialGroup()
                        .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lSexo)
                            .addGroup(pDadosDependenteLayout.createSequentialGroup()
                                .addComponent(rbFeminino)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbMasculino)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lDataNascimento)
                            .addComponent(tfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lParentesco)
                            .addComponent(cbParentesco, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pDadosDependenteLayout.createSequentialGroup()
                        .addComponent(lNomeDependente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomeDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pDadosDependenteLayout.setVerticalGroup(
            pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosDependenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNomeDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lNomeDependente))
                .addGap(18, 18, 18)
                .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSexo)
                    .addComponent(lDataNascimento)
                    .addComponent(lParentesco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbFeminino)
                    .addComponent(rbMasculino)
                    .addComponent(tfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbParentesco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bIncluirDependente.setText("Incluir");
        bIncluirDependente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIncluirDependenteActionPerformed(evt);
            }
        });

        bAlterarDependente.setText("Alterar");
        bAlterarDependente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAlterarDependenteActionPerformed(evt);
            }
        });

        bExcluirDependente.setText("Excluir");
        bExcluirDependente.setMaximumSize(new java.awt.Dimension(100, 30));
        bExcluirDependente.setMinimumSize(new java.awt.Dimension(100, 30));
        bExcluirDependente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirDependenteActionPerformed(evt);
            }
        });

        bGravarSocio.setText("Gravar Sócio");
        bGravarSocio.setMaximumSize(new java.awt.Dimension(100, 30));
        bGravarSocio.setMinimumSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(INomeSocio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNomeSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 21, Short.MAX_VALUE)
                        .addComponent(pDadosDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bIncluirDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bAlterarDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bExcluirDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bGravarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(INomeSocio)
                    .addComponent(tfNomeSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pDadosDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bIncluirDependente)
                    .addComponent(bAlterarDependente)
                    .addComponent(bExcluirDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bGravarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bIncluirDependenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIncluirDependenteActionPerformed
        try{
            Dependente novoDependente = this.criarDependente();
            socio.adicionarDependente(novoDependente);
            this.exibirDependente(socio.getDependentes().iterator());
            this.limparCampos();
        }catch(DukeClubeException exc){
            StringBuilder mensagem = new StringBuilder("Não foi possível adicional o dependente.");
            mensagem.append("\nMotivo: " + exc.getMessage());
            this.exibirMensagem(mensagem.toString(),"DukeClube - Dependentes", true);
        }
    }//GEN-LAST:event_bIncluirDependenteActionPerformed

    private void bAlterarDependenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAlterarDependenteActionPerformed
        int linhaSelecionada = tDependentes.getSelectedRow();
        if(linhaSelecionada < 0) {
            this.exibirMensagem("Não foi selecionado nenhum dependente", "DukeClube - Dependentes", true);
        }else{
            socio.removerDependente(linhaSelecionada);
            try{
                Dependente novoDependente = this.criarDependente();
                socio.adicionarDependente(novoDependente);
                this.exibirDependente(socio.getDependentes().iterator());
                this.limparCampos();
            }catch(DukeClubeException exc){
                StringBuilder mensagem = new StringBuilder("Não foi possível alterar os dados do dependente.");
            mensagem.append("\nMotivo: " + exc.getMessage());
            this.exibirMensagem(mensagem.toString(),"DukeClube - Dependentes", true);
            }
        }
    }//GEN-LAST:event_bAlterarDependenteActionPerformed

    private void bExcluirDependenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirDependenteActionPerformed
         int linhaSelecionada = tDependentes.getSelectedRow();
        if(linhaSelecionada < 0) {
            this.exibirMensagem("Não foi selecionado nenhum dependente", "DukeClube - Dependentes", true);
        }else{
            socio.removerDependente(linhaSelecionada);
            this.exibirDependente(socio.getDependentes().iterator());
            this.limparCampos();
        }
        
    }//GEN-LAST:event_bExcluirDependenteActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel INomeSocio;
    private javax.swing.JButton bAlterarDependente;
    private javax.swing.JButton bExcluirDependente;
    private javax.swing.JButton bGravarSocio;
    private javax.swing.JButton bIncluirDependente;
    private javax.swing.ButtonGroup bgSexo;
    private javax.swing.JComboBox<String> cbParentesco;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lDataNascimento;
    private javax.swing.JLabel lNomeDependente;
    private javax.swing.JLabel lParentesco;
    private javax.swing.JLabel lSexo;
    private javax.swing.JPanel pDadosDependente;
    private javax.swing.JRadioButton rbFeminino;
    private javax.swing.JRadioButton rbMasculino;
    private javax.swing.JTable tDependentes;
    private javax.swing.JFormattedTextField tfDataNascimento;
    private javax.swing.JTextField tfNomeDependente;
    private javax.swing.JTextField tfNomeSocio;
    // End of variables declaration//GEN-END:variables

   

   
}
