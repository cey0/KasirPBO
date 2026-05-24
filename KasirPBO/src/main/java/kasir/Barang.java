/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kasir;

/**
 *
 * @author nadra
 */
public class Barang {
    private int id;
    private String nama;
    private double harga;
    private int stok;
    
    public Barang(int id, String nama, double harga, int stok){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }
    
 @Override
 public String toString(){
     return nama;
 }
 
 public int getId() {
     return id;
 }
 
 public String getNama(){
     return nama;
}
 public double getHarga(){
     return harga;
 }
 public int getstok(){
     return stok;
 }
}
