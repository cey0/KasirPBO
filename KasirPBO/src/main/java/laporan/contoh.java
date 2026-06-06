/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package laporan;
import java.sql.Timestamp;
import com.formdev.flatlaf.FlatLightLaf;
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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Container;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

/**
 *
 * @author cey0apple
 */
public class contoh extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(contoh.class.getName());
 
    private Connection conn;
    private DefaultTableModel tableModel;
    /**
     * Creates new form contoh
     */
    
    private static final Color BLUE_DARK   = new Color(0x0D6EBD);
    private static final Color BLUE_MID    = new Color(0x0379C7);
    private static final Color BLUE_LIGHT  = new Color(0x29A8E0);
    private static final Color BG_PAGE     = new Color(0xEFF3F8);
    private static final Color BG_CARD     = Color.WHITE;
    private static final Color BG_STRIPE   = new Color(0xF0F7FF);
    private static final Color BORDER_CLR  = new Color(0xC8D8E8);
    private static final Color TEXT_DARK   = new Color(0x1A2B3C);
    private static final Color TEXT_MUTED  = new Color(0x5A7A9A);
    private static final Color TEXT_WHITE  = Color.WHITE;
    private static final Color GREEN_BG    = new Color(0xE8F5E9);
    private static final Color GREEN_TEXT  = new Color(0x2E7D32);
    private static final Color CHIP_BG     = new Color(0xE3F2FD);
    private static final Color CHIP_TEXT   = new Color(0x0D6EBD);
    
     private JLabel lblStatTrx, lblStatPendapatan, lblStatRata;
     
    public contoh() {
        initComponents();
    connectDatabase();
    setupTable();
    buildModernLayout();
    styleUI();
    }
    
    private void styleUI() {
        getContentPane().setBackground(BG_PAGE);
        
        // Label "Transaksi"
        jLabel4.setForeground(BLUE_MID);
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 15));
 
        // Label Tanggal
        jLabel2.setForeground(BLUE_DARK);
        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 13));
 
        // Label footer
        jLabel3.setForeground(TEXT_DARK);
        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jLabel5.setForeground(TEXT_DARK);
        jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jLabel6.setForeground(TEXT_DARK);
        jLabel7.setForeground(TEXT_DARK);
 
        TotalTransaksi.setForeground(BLUE_MID);
        TotalTransaksi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        TotalPendapatan.setForeground(BLUE_MID);
        TotalPendapatan.setFont(new Font("Segoe UI", Font.BOLD, 14));
 
        // JDateChooser
        Tanggal.setBackground(BG_CARD);
        Tanggal.setForeground(TEXT_DARK);
        Tanggal.getDateEditor().getUiComponent().setBackground(BG_CARD);
        Tanggal.getDateEditor().getUiComponent().setForeground(TEXT_DARK);
        Tanggal.getDateEditor().getUiComponent().setFont(new Font("Segoe UI", Font.PLAIN, 12));
 
        // Tombol Cari
        styleRoundButton(jButton1, BLUE_MID, TEXT_WHITE);
 
        // Tombol Export
        styleRoundButton(jButton3, BG_CARD, TEXT_DARK);
        styleRoundButton(jButton4, BG_CARD, TEXT_DARK);
        jButton3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLUE_MID, 1, true),
                BorderFactory.createEmptyBorder(7, 16, 7, 16)));
        jButton4.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLUE_MID, 1, true),
                BorderFactory.createEmptyBorder(7, 16, 7, 16)));
 
        // Tabel
        TabelTransaksi.setBackground(BG_CARD);
        TabelTransaksi.setForeground(TEXT_DARK);
        TabelTransaksi.setGridColor(new Color(0xDCEAF5));
        TabelTransaksi.setRowHeight(34);
        TabelTransaksi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        TabelTransaksi.setSelectionBackground(new Color(0xBBDEFB));
        TabelTransaksi.setSelectionForeground(BLUE_DARK);
        TabelTransaksi.setShowHorizontalLines(true);
        TabelTransaksi.setShowVerticalLines(false);
        TabelTransaksi.setIntercellSpacing(new Dimension(0, 0));
        TabelTransaksi.setFocusable(false);
 
        // Header tabel
        JTableHeader th = TabelTransaksi.getTableHeader();
        th.setBackground(BLUE_MID);
        th.setForeground(TEXT_WHITE);
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setPreferredSize(new Dimension(0, 36));
        th.setBorder(BorderFactory.createEmptyBorder());
        ((DefaultTableCellRenderer) th.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.LEFT);
 
        // Renderer baris belang + padding
        TabelTransaksi.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setFont(new Font("Segoe UI", col == 0 ? Font.BOLD : Font.PLAIN, 12));
                if (!sel) {
                    setBackground(row % 2 == 0 ? BG_CARD : BG_STRIPE);
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        });
 
        // Chip renderer kolom Diskon (hijau) & PPN (biru)
        TabelTransaksi.getColumnModel().getColumn(3)
                .setCellRenderer(chipRenderer(GREEN_BG, GREEN_TEXT));
        TabelTransaksi.getColumnModel().getColumn(4)
                .setCellRenderer(chipRenderer(CHIP_BG, CHIP_TEXT));
 
        // ScrollPane
        jScrollPane1.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
        jScrollPane1.getViewport().setBackground(BG_CARD);
 
        // Lebar kolom
        TabelTransaksi.getColumnModel().getColumn(0).setPreferredWidth(100);
        TabelTransaksi.getColumnModel().getColumn(1).setPreferredWidth(130);
        TabelTransaksi.getColumnModel().getColumn(2).setPreferredWidth(120);
        TabelTransaksi.getColumnModel().getColumn(3).setPreferredWidth(100);
        TabelTransaksi.getColumnModel().getColumn(4).setPreferredWidth(100);
        TabelTransaksi.getColumnModel().getColumn(5).setPreferredWidth(170);
        TabelTransaksi.getColumnModel().getColumn(6).setPreferredWidth(120);
        TabelTransaksi.getColumnModel().getColumn(7).setPreferredWidth(90);
        TabelTransaksi.getColumnModel().getColumn(8).setPreferredWidth(70);
        TabelTransaksi.getColumnModel().getColumn(9).setPreferredWidth(120);
}
    
    private void buildModernLayout() {
    JPanel root = new JPanel(new BorderLayout(0, 18));
    root.setBackground(BG_PAGE);
    root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

    // HEADER
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(BLUE_MID);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));
    headerPanel.setPreferredSize(new Dimension(0, 110));

    JLabel title = new JLabel("Laporan Penjualan Harian");
    title.setForeground(Color.WHITE);
    title.setFont(new Font("Segoe UI", Font.BOLD, 28));

    JPanel titlePanel = new JPanel();
    titlePanel.setOpaque(false);
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
    titlePanel.add(title);

    headerPanel.add(titlePanel, BorderLayout.WEST);

    // CARD STATISTIK
    JPanel cardPanel = new JPanel(new GridLayout(1, 3, 14, 0));
    cardPanel.setOpaque(false);

    lblStatTrx = new JLabel("0");
    lblStatPendapatan = new JLabel("Rp 0");
    lblStatRata = new JLabel("Rp 0");

    cardPanel.add(createCard("Total Transaksi", lblStatTrx));
    cardPanel.add(createCard("Total Pendapatan", lblStatPendapatan));
    cardPanel.add(createCard("Rata-rata / Transaksi", lblStatRata));

    // FILTER
    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
    filterPanel.setOpaque(false);

    Tanggal.setPreferredSize(new Dimension(150, 35));
    Tanggal.setDateFormatString("dd MMM yyyy");

    jButton1.setPreferredSize(new Dimension(90, 35));

    filterPanel.add(jLabel2);
    filterPanel.add(Tanggal);
    filterPanel.add(jButton1);

    // TITLE TABEL
    jLabel4.setHorizontalAlignment(SwingConstants.CENTER);

    JPanel tableTitlePanel = new JPanel(new BorderLayout());
    tableTitlePanel.setOpaque(false);
    tableTitlePanel.add(jLabel4, BorderLayout.CENTER);

    // TENGAH
    JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
    centerPanel.setOpaque(false);
    centerPanel.add(filterPanel, BorderLayout.NORTH);
    centerPanel.add(tableTitlePanel, BorderLayout.CENTER);
    centerPanel.add(jScrollPane1, BorderLayout.SOUTH);

    jScrollPane1.setPreferredSize(new Dimension(0, 300));

    // FOOTER
    JPanel footerPanel = new JPanel(new BorderLayout());
    footerPanel.setOpaque(false);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(jButton3);
    buttonPanel.add(jButton4);

    footerPanel.add(buttonPanel, BorderLayout.EAST);

    // SUSUN ROOT
    JPanel topPanel = new JPanel(new BorderLayout(0, 14));
    topPanel.setOpaque(false);
    topPanel.add(headerPanel, BorderLayout.NORTH);
    topPanel.add(cardPanel, BorderLayout.SOUTH);

    root.add(topPanel, BorderLayout.NORTH);
    root.add(centerPanel, BorderLayout.CENTER);
    root.add(footerPanel, BorderLayout.SOUTH);

    setContentPane(root);
    setSize(1100, 720);
    setLocationRelativeTo(null);
}
    private JPanel createCard(String title, JLabel valueLabel) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xC8D8E8), 1),
            BorderFactory.createEmptyBorder(16, 18, 16, 18)
    ));

    valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
    valueLabel.setForeground(BLUE_MID);
    valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

    JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
    titleLabel.setForeground(TEXT_MUTED);
    titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

    card.add(valueLabel, BorderLayout.CENTER);
    card.add(titleLabel, BorderLayout.SOUTH);

    return card;
}

    private JLabel statValLabel(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("Segoe UI", Font.BOLD, 17));
        l.setForeground(BLUE_DARK);
        return l;
    }
 
    private JPanel buildStatCard(JLabel val, String labelText) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                // border tipis biru
                g2.setColor(BORDER_CLR);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));
        card.setPreferredSize(new Dimension(0, 62));
 
        val.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        JLabel lbl = new JLabel(labelText, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        card.add(val);
        card.add(Box.createVerticalStrut(3));
        card.add(lbl);
        return card;
    }
 
    // ── Chip renderer ─────────────────────────────────────────────
    private TableCellRenderer chipRenderer(Color bg, Color fg) {
        return new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
                p.setBackground(sel ? new Color(0xBBDEFB)
                        : (row % 2 == 0 ? BG_CARD : BG_STRIPE));
                String text = val == null ? "" : val.toString();
                JLabel chip = new JLabel(text) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(bg);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(),
                                getHeight(), getHeight());
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                chip.setOpaque(false);
                chip.setFont(new Font("Segoe UI", Font.BOLD, 11));
                chip.setForeground(fg);
                chip.setBorder(BorderFactory.createEmptyBorder(2, 9, 2, 9));
                p.add(chip);
                return p;
            }
        };
    }
 
    // ── Round button ──────────────────────────────────────────────
    private void styleRoundButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.putClientProperty(
    "JButton.buttonType",
    "roundRect"
);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.equals(BG_CARD)
                        ? new Color(0xE3F2FD) : bg.darker());
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
    }
    
     private void connectDatabase() {
        conn = Koneksi.getKoneksi();
    }
 
    private void setupTable() {
        String[] kolom = {
            "ID_Transaksi", "Tanggal", "Total", "Diskon",
            "PPN", "Nama Barang", "Harga Barang", "Stok Barang",
            "Jumlah", "Sub Total"
        };
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        TabelTransaksi.setModel(tableModel);
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
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        Tanggal = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TotalPendapatan1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        TotalTransaksi1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TotalTransaksi = new javax.swing.JLabel();
        TotalPendapatan = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        TotalTransaksi2 = new javax.swing.JLabel();
        TotalPendapatan2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Tanggal", "Total", "Diskon", "PPN", "Nama Barang", "Harga Barang", "Stok Barang", "Jumlah", "Sub Total"
            }
        ));
        TabelTransaksi.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(TabelTransaksi);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 123, 199));
        jLabel2.setText("Tanggal :");

        jButton1.setBackground(new java.awt.Color(0, 123, 199));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Total Transaksi      ");

        TotalTransaksi1.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(TotalTransaksi1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(TotalTransaksi1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(16, 16, 16))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TotalPendapatan.setText("0");

        jLabel5.setText("Total Pendapatan ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TotalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(387, 387, 387))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(TotalPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TotalPendapatan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TotalTransaksi)
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(16, 16, 16))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TotalPendapatan2.setText("0");

        jLabel12.setText("Rata-rata / Transaksi");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TotalPendapatan2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TotalTransaksi2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TotalTransaksi2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TotalPendapatan2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(34, 34, 34)
                .addComponent(jLabel11)
                .addGap(16, 16, 16))
        );

        jPanel6.setBackground(new java.awt.Color(0, 120, 215));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Laporan Penjualan Harian");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1082, 1082, 1082)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(806, 806, 806)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4339, 4339, 4339)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(827, 827, 827)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1045, 1045, 1045)
                .addComponent(TotalPendapatan1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(63, 63, 63)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(37, 37, 37)
                                .addComponent(jLabel6)
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(531, 531, 531)
                                .addComponent(TotalPendapatan1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel8)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addGap(190, 190, 190))
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

            Timestamp ts = rs.getTimestamp("tanggal");
            String tanggal = new SimpleDateFormat("dd MMM yyyy").format(ts);

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
            
                lblStatTrx.setText(String.valueOf(totalTransaksi));
            lblStatPendapatan.setText(formatRupiah(totalPendapatan));

            if (totalTransaksi == 0) {
                lblStatRata.setText("Rp 0");
            } else {
                lblStatRata.setText(formatRupiah(totalPendapatan / totalTransaksi));
            }
            
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
            FlatLightLaf.setup();
 
            UIManager.put("Panel.background",           new Color(0xEFF3F8));
            UIManager.put("Table.background",           Color.WHITE);
            UIManager.put("Table.foreground",           new Color(0x1A2B3C));
            UIManager.put("Table.alternateRowColor",    new Color(0xF0F7FF));
            UIManager.put("TableHeader.background",     new Color(0x0379C7));
            UIManager.put("TableHeader.foreground",     Color.WHITE);
            UIManager.put("TextField.background",       Color.WHITE);
            UIManager.put("TextField.foreground",       new Color(0x1A2B3C));
            UIManager.put("Button.arc",                 10);
            UIManager.put("Component.arc",              10);
            UIManager.put("ScrollBar.width",            8);
            UIManager.put("ScrollBar.thumbArc",         999);
            UIManager.put("ScrollBar.thumbColor",       new Color(0xB0C8E0));
            UIManager.put("ScrollPane.background",      Color.WHITE);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
 
        java.awt.EventQueue.invokeLater(() -> new contoh().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelTransaksi;
    private com.toedter.calendar.JDateChooser Tanggal;
    private javax.swing.JLabel TotalPendapatan;
    private javax.swing.JLabel TotalPendapatan1;
    private javax.swing.JLabel TotalPendapatan2;
    private javax.swing.JLabel TotalTransaksi;
    private javax.swing.JLabel TotalTransaksi1;
    private javax.swing.JLabel TotalTransaksi2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
