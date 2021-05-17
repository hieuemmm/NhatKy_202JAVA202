package GUI;

import DAO.NhatKyServices;
import static GUI.FormNhatKy.defaultTableModel;
import NhatKy.NhatKy;
import static java.awt.Color.blue;
import static java.awt.Color.red;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sun.awt.image.PixelConverter;

/**
 *
 * @author Administrator
 */
public class TableFind extends javax.swing.JPanel {

    private static NhatKyServices NKS;

    /**
     * Creates new form InputPanel
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public TableFind() throws ClassNotFoundException, SQLException {
        initComponents();
        NKS = new NhatKyServices();
        FormNhatKy.defaultTableModel = new DefaultTableModel() {
            //không cho phép sửa dữ liệu trên table
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTableNhatKy.setModel(FormNhatKy.defaultTableModel);
        //set giá trị cột
        FormNhatKy.defaultTableModel.addColumn("Mã Nhật Ký");
        FormNhatKy.defaultTableModel.addColumn("Tên Nhật Ký");
        FormNhatKy.defaultTableModel.addColumn("Ngày Tạo");
        FormNhatKy.defaultTableModel.addColumn("Nội Dung");
        //setTableData(FormNhatKy.NhatKys);
        jTableNhatKy.setRowHeight(50);
        jTableNhatKy.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    try {
                        LayDuLieuChon();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(TableFind.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        FormNhatKy.jPanel6.setVisible(true);
                        jButtonClose.setVisible(false);
                    }
                }
            }
        });
        jTableNhatKy.setShowGrid(false);
        jTableNhatKy.setShowHorizontalLines(false);
        jTableNhatKy.setShowVerticalLines(false);
        jButtonClose.setVisible(false);
        jTableNhatKy.setVisible(false);
    }

    public static void setTableData(List<NhatKy> nhatkys) throws ClassNotFoundException, SQLException {
        FormNhatKy.defaultTableModel.setRowCount(0);
        nhatkys.forEach(x -> {
            //set giá trị hàng
            FormNhatKy.defaultTableModel.addRow(new Object[]{
                x.getMaNhatKy(),
                x.getTenNhatKy(),
                x.getNgayTao(),
                x.getNoiDung()
            });
        });
        jButtonClose.setVisible(true);
        jTableNhatKy.setVisible(true);
    }

    public static void LayDuLieuChon() throws ClassNotFoundException, SQLException {
        int row = jTableNhatKy.getSelectedRow();
        int MaNhatKy = Integer.parseInt(String.valueOf(jTableNhatKy.getValueAt(row, 0)));
        FormNhatKy.NK = NKS.getNhatKyByMaNhatKy(MaNhatKy);
        FormNhatKy.LoadNhatKy();
        FormNhatKy.jPanel6.setVisible(true);
        jButtonClose.setVisible(false);
        jTableNhatKy.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNhatKy = new javax.swing.JTable();
        jButtonClose = new javax.swing.JButton();

        jTableNhatKy.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jTableNhatKy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableNhatKy.setMaximumSize(new java.awt.Dimension(495, 64));
        jTableNhatKy.setMinimumSize(new java.awt.Dimension(495, 64));
        jTableNhatKy.setSelectionBackground(new java.awt.Color(225, 236, 244));
        jTableNhatKy.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTableNhatKy);

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        FormNhatKy.jPanel6.setVisible(true);
        jButtonClose.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButtonClose;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableNhatKy;
    // End of variables declaration//GEN-END:variables

}
