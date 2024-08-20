/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package spassocasaerp.Classes;

/**
 *
 * @author tioza
 */
public enum NomeTabelas {
Cliente{
      public String toString() {
          return "Cliente";
      }
},
CategoriaProduto{
      public String toString() {
          return "Categoria Produto";
      }
},
Produto{
      public String toString() {
          return "Produto";
      }
},
Compra{
         public String toString() {
          return "Compra";
      }
}
,
ItemCompra{
         public String toString() {
          return "Item Compra";
      }
},
Fornecedor{
         public String toString() {
          return "Fornecedor";
      }
}
}
