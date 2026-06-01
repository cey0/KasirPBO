/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package laporan;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Date;
import database.Koneksi;

/**
 *
 * @author cey0apple
 */
public class contoh extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(contoh.class.getName());
    private Connection conn;
    private DefaultTableModel tableModel;
    /**
     * Creates new form contoh
     */
    public contoh() {
        initComponents();
        connectDatabase();
        setupTable();
        styleUI();
    }
    
    private void styleUI() {
    // Background abu-abu muda seperti punya teman
    getContentPane().setBackground(new Color(0xF0F4F8));

    // Judul biru bold
    jLabel1.setOpaque(true);
    jLabel1.setBackground(new Color(0x0379C7));
    jLabel1.setForeground(Color.WHITE);
    jLabel1.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

    // Label warna biru gelap
    jLabel2.setForeground(new Color(0x2A6D99));
    jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 13));
    jLabel3.setForeground(new Color(0x2A6D99));
    jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 13));
    jLabel4.setForeground(new Color(0x2A6D99));
    jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 16));
    jLabel5.setForeground(new Color(0x2A6D99));
    jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 13));
    jLabel6.setForeground(new Color(0x2A6D99));
    jLabel7.setForeground(new Color(0x2A6D99));
    TotalTransaksi.setForeground(new Color(0x0379C7));
    TotalTransaksi.setFont(new Font("Segoe UI", Font.BOLD, 13));
    TotalPendapatan.setForeground(new Color(0x0379C7));
    TotalPendapatan.setFont(new Font("Segoe UI", Font.BOLD, 13));

    // Tombol Cari
    jButton1.setBackground(new Color(0x0379C7));
    jButton1.setForeground(Color.WHITE);
    jButton1.setFont(new Font("Segoe UI", Font.BOLD, 13));
    jButton1.setFocusPainted(false);
    jButton1.setBorderPainted(false);
    jButton1.setOpaque(true);
    jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Tombol Export TXT
    jButton3.setBackground(new Color(0x0379C7));
    jButton3.setForeground(Color.WHITE);
    jButton3.setFont(new Font("Segoe UI", Font.BOLD, 13));
    jButton3.setFocusPainted(false);
    jButton3.setBorderPainted(false);
    jButton3.setOpaque(true);
    jButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Tombol Export PDF
    jButton4.setBackground(new Color(0x0379C7));
    jButton4.setForeground(Color.WHITE);
    jButton4.setFont(new Font("Segoe UI", Font.BOLD, 13));
    jButton4.setFocusPainted(false);
    jButton4.setBorderPainted(false);
    jButton4.setOpaque(true);
    jButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Tabel
    TabelTransaksi.setBackground(Color.WHITE);
    TabelTransaksi.setGridColor(new Color(0xB9F0F9));
    TabelTransaksi.setRowHeight(28);
    TabelTransaksi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    TabelTransaksi.setSelectionBackground(new Color(0xB9F0F9));
    TabelTransaksi.setSelectionForeground(new Color(0x0379C7));

    // Header tabel biru solid seperti punya teman
    TabelTransaksi.getTableHeader().setBackground(new Color(0x0379C7));
    TabelTransaksi.getTableHeader().setForeground(Color.WHITE);
    TabelTransaksi.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    TabelTransaksi.getTableHeader().setPreferredSize(new Dimension(0, 32));

    // Border scroll panel
    jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(0x78AEC6), 1));
    jScrollPane1.getViewport().setBackground(Color.WHITE);
    
    // Atur lebar kolom
    TabelTransaksi.getColumnModel().getColumn(0).setPreferredWidth(100); // ID_Transaksi
    TabelTransaksi.getColumnModel().getColumn(1).setPreferredWidth(140); // Tanggal
    TabelTransaksi.getColumnModel().getColumn(2).setPreferredWidth(120); // Total
    TabelTransaksi.getColumnModel().getColumn(3).setPreferredWidth(100); // Diskon
    TabelTransaksi.getColumnModel().getColumn(4).setPreferredWidth(100); // PPN
    TabelTransaksi.getColumnModel().getColumn(5).setPreferredWidth(180); // Nama_Barang
    TabelTransaksi.getColumnModel().getColumn(6).setPreferredWidth(120); // Harga_Barang
    TabelTransaksi.getColumnModel().getColumn(7).setPreferredWidth(90);  // Stok_Barang
    TabelTransaksi.getColumnModel().getColumn(8).setPreferredWidth(70);  // Jumlah
    TabelTransaksi.getColumnModel().getColumn(9).setPreferredWidth(120); // Sub_Total
}
    
    private void connectDatabase() {
        conn = Koneksi.getKoneksi();
    }

    private void setupTable() {
        String[] kolom = {
            "ID_Transaksi", "Tanggal", "Total", "Diskon",
            "PPN", "Nama_Barang", "Harga_Barang", "Stok_Barang",
            "Jumlah", "Sub_Total"
        };
        
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
         jScrollPane1.setViewportView(new JTable(tableModel));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TabelTransaksi = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Tanggal = new com.toedter.calendar.JDateChooser();
        TotalTransaksi = new javax.swing.JLabel();
        TotalPendapatan = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_Transaksi", "Tanggal", "Total", "Diskon", "PPN", "Nama_Barang", "Harga_Barang", "Stok_Barang", "Jumlah", "Sub_Total"
            }
        ));
        jScrollPane1.setViewportView(TabelTransaksi);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Laporan Penjualan Harian");

        jLabel2.setText("Tanggal :");

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Total Transaksi      ");

        jButton3.setText("Export TXT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Export PDF");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Transaksi");

        jLabel5.setText("Total Pendapatan ");

        TotalTransaksi.setText("0");

        TotalPendapatan.setText("0");

        jLabel6.setText(":");

        jLabel7.setText(":");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 123, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TotalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TotalPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
            .addGroup(layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TotalTransaksi)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(TotalPendapatan)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(64, 64, 64))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cariLaporan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cariLaporan() {
        Date selectedDate = Tanggal.getDate();
 
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this,
                "Pilih tanggal terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalCari = sdf.format(selectedDate);
 
        tableModel.setRowCount(0);
 
        String query = "SELECT " +
            "t.id, " +
            "t.tanggal, " +
            "t.total, " +
            "t.diskon, " +
            "t.ppn, " +
            "b.nama, " +
            "b.harga, " +
            "b.stok, " +
            "dt.jumlah, " +
            "dt.subtotal " +
            "FROM transaksi t " +
            "JOIN detail_transaksi dt ON t.id = dt.transaksi_id " +
            "JOIN barang b ON dt.barang_id = b.id " +
            "WHERE DATE(t.tanggal) = ? " +
            "ORDER BY t.id";
        
         try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, tanggalCari);
            ResultSet rs = ps.executeQuery();
 
            int totalTransaksi = 0;
            double totalPendapatan = 0;
            String lastIdTransaksi = "";
 
            while (rs.next()) {
                String idTransaksi = rs.getString("id");
                String tanggal     = rs.getString("tanggal");
                double total       = rs.getDouble("total");
                double diskon      = rs.getDouble("diskon");
                double ppn         = rs.getDouble("ppn");
                String namaBarang  = rs.getString("nama");
                double hargaBarang = rs.getDouble("harga");
                int stokBarang     = rs.getInt("stok");
                int jumlah         = rs.getInt("jumlah");
                double subTotal    = rs.getDouble("subtotal");
                
                tableModel.addRow(new Object[]{
                    idTransaksi, tanggal,
                    formatRupiah(total), formatRupiah(diskon),
                    formatRupiah(ppn), namaBarang,
                    formatRupiah(hargaBarang), stokBarang,
                    jumlah, formatRupiah(subTotal)
                });
                
                if (!idTransaksi.equals(lastIdTransaksi)) {
                    totalTransaksi++;
                    totalPendapatan += total;
                    lastIdTransaksi = idTransaksi;
                }
            }
            
            TotalTransaksi.setText(String.valueOf(totalTransaksi));
            TotalPendapatan.setText(formatRupiah(totalPendapatan));
            
            if (totalTransaksi == 0) {
                JOptionPane.showMessageDialog(this,
                    "Tidak ada transaksi pada tanggal tersebut.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error query: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private String formatRupiah(double angka) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(
            new java.util.Locale("id", "ID"));
        return nf.format(angka);
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        exportPDF();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void exportPDF() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diexport!");
            return;
        }
 
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("laporan_penjualan.pdf"));
        int result = fc.showSaveDialog(this);
 
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                com.itextpdf.text.Document doc =
                    new com.itextpdf.text.Document(
                        com.itextpdf.text.PageSize.A4.rotate());
 
                com.itextpdf.text.pdf.PdfWriter.getInstance(
                    doc, new FileOutputStream(fc.getSelectedFile()));
                doc.open();
 
                com.itextpdf.text.Font titleFont =
                    new com.itextpdf.text.Font(
                        com.itextpdf.text.Font.FontFamily.HELVETICA,
                        16, com.itextpdf.text.Font.BOLD);
                doc.add(new com.itextpdf.text.Paragraph("LAPORAN PENJUALAN HARIAN", titleFont));
                doc.add(new com.itextpdf.text.Paragraph(" "));
 
                com.itextpdf.text.pdf.PdfPTable table =
                    new com.itextpdf.text.pdf.PdfPTable(10);
                table.setWidthPercentage(100);
 
                String[] headers = {
                    "ID_Transaksi", "Tanggal", "Total", "Diskon", "PPN",
                    "Nama_Barang", "Harga_Barang", "Stok_Barang", "Jumlah", "Sub_Total"
                };
                for (String h : headers) {
                    com.itextpdf.text.pdf.PdfPCell cell =
                        new com.itextpdf.text.pdf.PdfPCell(
                            new com.itextpdf.text.Phrase(h));
                    cell.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                }
 
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        table.addCell(tableModel.getValueAt(i, j).toString());
                    }
                }
 
                doc.add(table);
                doc.add(new com.itextpdf.text.Paragraph(" "));
                doc.add(new com.itextpdf.text.Paragraph("Total Transaksi  : " + TotalTransaksi.getText()));
                doc.add(new com.itextpdf.text.Paragraph("Total Pendapatan : " + TotalPendapatan.getText()));
                doc.close();
 
                JOptionPane.showMessageDialog(this, "Export PDF berhasil!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal export PDF: " + e.getMessage());
            }
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        exportTXT();
    }//GEN-LAST:event_jButton3ActionPerformed

     private void exportTXT() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diexport!");
            return;
        }
 
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("laporan_penjualan.txt"));
        int result = fc.showSaveDialog(this);
 
        if (result == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile()))) {
                pw.println("====================================");
                pw.println("     LAPORAN PENJUALAN HARIAN");
                pw.println("====================================");
                pw.printf("%-15s %-20s %-15s %-15s %-15s %-15s %-15s %-12s %-8s %-15s%n",
                    "ID_Transaksi", "Tanggal", "Total", "Diskon", "PPN",
                    "Nama_Barang", "Harga_Barang", "Stok_Barang", "Jumlah", "Sub_Total");
                pw.println("------------------------------------");
 
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    pw.printf("%-15s %-20s %-15s %-15s %-15s %-15s %-15s %-12s %-8s %-15s%n",
                        tableModel.getValueAt(i, 0), tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2), tableModel.getValueAt(i, 3),
                        tableModel.getValueAt(i, 4), tableModel.getValueAt(i, 5),
                        tableModel.getValueAt(i, 6), tableModel.getValueAt(i, 7),
                        tableModel.getValueAt(i, 8), tableModel.getValueAt(i, 9));
                }
 
                pw.println("====================================");
                pw.println("Total Transaksi  : " + TotalTransaksi.getText());
                pw.println("Total Pendapatan : " + TotalPendapatan.getText());
 
                JOptionPane.showMessageDialog(this, "Export TXT berhasil!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Gagal export: " + e.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new contoh().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelTransaksi;
    private com.toedter.calendar.JDateChooser Tanggal;
    private javax.swing.JLabel TotalPendapatan;
    private javax.swing.JLabel TotalTransaksi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
