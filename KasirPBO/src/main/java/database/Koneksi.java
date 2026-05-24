/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author cey0apple
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/kasir_digital";
                String user = "root";
                String password = ""; // Kosongkan kalau pakai bawaan XAMPP
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi Database Berhasil!");
                
                // Jalankan migrasi tabel otomatis
                buatTabelOtomatis();
                
            } catch (SQLException e) {
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }

    private static void buatTabelOtomatis() {
        try {
            Statement stmt = koneksi.createStatement();
            // 0. Bikin Tabel Users
            String tabelUsers = "CREATE TABLE IF NOT EXISTS users ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "username VARCHAR(50) UNIQUE NOT NULL, "
            + "password VARCHAR(255) NOT NULL, "
            + "nama_lengkap VARCHAR(100) NOT NULL, "
            + "role ENUM('admin', 'kasir') NOT NULL)";
            stmt.execute(tabelUsers);

            // Opsional: Cek apakah tabel users kosong. Kalau kosong, bikin 1 admin default.
            String cekUser = "SELECT COUNT(*) AS total FROM users";
            java.sql.ResultSet rs = stmt.executeQuery(cekUser);
            if (rs.next() && rs.getInt("total") == 0) {
            String insertAdmin = "INSERT INTO users (username, password, nama_lengkap, role) "
            + "VALUES ('admin', 'admin123', 'Administrator Utama', 'admin')";
            stmt.execute(insertAdmin);
                }
            // 1. Bikin Tabel Barang
            String tabelBarang = "CREATE TABLE IF NOT EXISTS barang ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nama VARCHAR(100) NOT NULL, "
                    + "harga DOUBLE NOT NULL, "
                    + "stok INT NOT NULL)";
            stmt.execute(tabelBarang);
            
            // 2. Bikin Tabel Transaksi
            String tabelTransaksi = "CREATE TABLE IF NOT EXISTS transaksi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "total DOUBLE NOT NULL, "
                    + "diskon DOUBLE DEFAULT 0, "
                    + "ppn DOUBLE NOT NULL)";
            stmt.execute(tabelTransaksi);
            
            // 3. Bikin Tabel Detail Transaksi (Harus setelah tabel barang & transaksi ada)
            String tabelDetailTransaksi = "CREATE TABLE IF NOT EXISTS detail_transaksi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "transaksi_id INT, "
                    + "barang_id INT, "
                    + "jumlah INT NOT NULL, "
                    + "subtotal DOUBLE NOT NULL, "
                    + "FOREIGN KEY (transaksi_id) REFERENCES transaksi(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (barang_id) REFERENCES barang(id) ON DELETE CASCADE)";
            stmt.execute(tabelDetailTransaksi);
            
            System.out.println("Struktur tabel aman (sudah dicek/dibuat otomatis)!");
        } catch (SQLException e) {
            System.out.println("Gagal membuat struktur tabel: " + e.getMessage());
        }
    }
}
