package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {

    private static Connection koneksi;

    public static Connection getKoneksi() {

        if (koneksi == null) {

            try {

                String url =
                        "jdbc:mysql://localhost:3306/kasir_digital";

                String user = "root";
                String password = "";

                DriverManager.registerDriver(
                        new com.mysql.cj.jdbc.Driver()
                );

                koneksi =
                        DriverManager.getConnection(
                                url,
                                user,
                                password
                        );

                System.out.println(
                        "Koneksi Database Berhasil!"
                );

                buatTabelOtomatis();

            } catch (SQLException e) {

                System.out.println(
                        "Koneksi Gagal : "
                        + e.getMessage()
                );
            }
        }

        return koneksi;
    }

    private static void buatTabelOtomatis() {

        try {

            Statement stmt =
                    koneksi.createStatement();

            // =====================================
            // USERS
            // =====================================

            String tabelUsers =
                    "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) UNIQUE NOT NULL, "
                    + "password VARCHAR(255) NOT NULL, "
                    + "nama_lengkap VARCHAR(100) NOT NULL, "
                    + "role ENUM('admin','manajer','kasir') NOT NULL"
                    + ")";

            stmt.execute(tabelUsers);

            // =====================================
            // BARANG
            // =====================================

            String tabelBarang =
                    "CREATE TABLE IF NOT EXISTS barang ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nama VARCHAR(100) NOT NULL, "
                    + "harga DOUBLE NOT NULL, "
                    + "stok INT NOT NULL, "
                    + "status ENUM('aktif','nonaktif') NOT NULL DEFAULT 'aktif'"
                    + ")";

            stmt.execute(tabelBarang);

            // Migrasi: tambah kolom status jika belum ada (untuk tabel lama)
            try {
                stmt.execute(
                    "ALTER TABLE barang ADD COLUMN status ENUM('aktif','nonaktif') NOT NULL DEFAULT 'aktif'"
                );
                System.out.println("Kolom status berhasil ditambahkan ke tabel barang!");
            } catch (SQLException ignored) {
                // kolom sudah ada, abaikan error
            }

            // =====================================
            // TRANSAKSI
            // =====================================

            String tabelTransaksi =
                    "CREATE TABLE IF NOT EXISTS transaksi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "total DOUBLE NOT NULL, "
                    + "diskon DOUBLE DEFAULT 0, "
                    + "ppn DOUBLE NOT NULL"
                    + ")";

            stmt.execute(tabelTransaksi);

            // =====================================
            // DETAIL TRANSAKSI
            // =====================================

            String tabelDetailTransaksi =
                    "CREATE TABLE IF NOT EXISTS detail_transaksi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "transaksi_id INT, "
                    + "barang_id INT, "
                    + "jumlah INT NOT NULL, "
                    + "subtotal DOUBLE NOT NULL, "
                    + "FOREIGN KEY (transaksi_id) REFERENCES transaksi(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (barang_id) REFERENCES barang(id) ON DELETE CASCADE"
                    + ")";

            stmt.execute(tabelDetailTransaksi);

            // =====================================
            // DUMMY USERS
            // =====================================

            ResultSet rsUser =
                    stmt.executeQuery(
                            "SELECT COUNT(*) total FROM users"
                    );

            if (rsUser.next()
                    && rsUser.getInt("total") == 0) {

                stmt.execute("""
                    INSERT INTO users
                    (username,password,nama_lengkap,role)
                    VALUES
                    ('admin','admin123','Administrator Utama','admin'),
                    ('manajer1','manajer123','Andi Pratama','manajer'),
                    ('kasir1','kasir123','Budi Santoso','kasir'),
                    ('kasir2','kasir123','Siti Amelia','kasir')
                """);

                System.out.println(
                        "Dummy user berhasil dibuat!"
                );
            }

            // =====================================
            // DUMMY BARANG
            // =====================================

            ResultSet rsBarang =
                    stmt.executeQuery(
                            "SELECT COUNT(*) total FROM barang"
                    );

            if (rsBarang.next()
                    && rsBarang.getInt("total") == 0) {

                stmt.execute("""
                    INSERT INTO barang
                    (nama,harga,stok)
                    VALUES
                    ('Indomie Goreng',3500,100),
                    ('Indomie Soto',3500,100),
                    ('Mie Sedaap Ayam Bawang',3500,100),
                    ('Teh Botol Sosro',5000,50),
                    ('Aqua 600ml',3000,80),
                    ('Le Minerale 600ml',3500,80),
                    ('Pocari Sweat',9000,40),
                    ('Good Day Cappuccino',7000,60),
                    ('Chitato Sapi Panggang',12000,30),
                    ('Lays Rumput Laut',10000,25),
                    ('Taro Net',8500,40),
                    ('SilverQueen',15000,20),
                    ('Beng Beng',3000,100),
                    ('Roma Kelapa',9000,35),
                    ('Ultra Milk Coklat',7000,50),
                    ('Ultra Milk Stroberi',7000,50),
                    ('Yakult',12000,25),
                    ('Sari Roti Coklat',6000,30),
                    ('Sari Roti Keju',6500,30),
                    ('Oreo Vanilla',9000,40)
                """);

                System.out.println(
                        "Dummy barang berhasil dibuat!"
                );
            }

            System.out.println(
                    "Struktur tabel aman!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Gagal membuat struktur tabel : "
                    + e.getMessage()
            );
        }
    }
}