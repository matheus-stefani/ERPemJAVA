/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.Classes.ClassesAnemicas;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author tioza
 */
public class BotoesEscolha {
    
            public JButton btnInserirGet;
          
            public JButton btnEditarGet; 
            public JButton btnDeletarGet; 
            public JButton btnLimparGet; 
            
            
    public BotoesEscolha(JButton btnInserir,
            JButton btnEditar,JButton btnDeletar,JButton btnLimpar) {
            btnInserirGet = btnInserir; 
           
            btnEditarGet = btnEditar;
            btnDeletarGet = btnDeletar;
            btnLimparGet= btnLimpar;
           
    }
}
