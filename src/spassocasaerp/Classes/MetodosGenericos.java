/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.Classes;


import spassocasaerp.Classes.ClassesAnemicas.BotoesEscolha;
import java.lang.reflect.InvocationTargetException;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import spassocasaerp.Classes.ClassesAnemicas.MudarNomeComponentes;



import spassocasaerp.JFrameMain;

/**
 *
 * @author tioza
 */
public class MetodosGenericos {
    
    public static <T extends AbstractButton>void enableBtnsIniciais(boolean isEscolha,BotoesEscolha pegarBotoesEscolha){
      
        pegarBotoesEscolha.btnInserirGet.setEnabled(isEscolha);
       
        pegarBotoesEscolha.btnDeletarGet.setEnabled(isEscolha);
        pegarBotoesEscolha.btnEditarGet.setEnabled(isEscolha);
        pegarBotoesEscolha.btnLimparGet.setEnabled(isEscolha);
        
       
    }
    
    public static void mudarTextosInicias(String entidade, MudarNomeComponentes mudarNome){
        
        mudarNome.btnInserir.setText("Inserir "+entidade);
        mudarNome.btnDeletar.setText("Deletar "+entidade);
        mudarNome.btnEditar.setText("Editar "+entidade);
        mudarNome.txtTabela.setText("A tabela "+entidade+" esta sendo listada");
       
        
    }
    
    public static <T extends javax.swing.JFrame> void abrirForm(Class<T> classe, int width, int height){
        try{
         
         T newForm = classe.getDeclaredConstructor().newInstance();
        
        newForm.setDefaultCloseOperation(JFrameMain.DISPOSE_ON_CLOSE);
        newForm.setSize(width, height);
        newForm.setLocationRelativeTo(null);
        newForm.setVisible(true);
        
        }
        catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            e.printStackTrace();
        }
    }
}
