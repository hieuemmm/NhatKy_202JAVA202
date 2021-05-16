/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DAO.NguoiDungServices;
import DAO.NhatKyServices;
import DAO.ThuMucServices;
import NhatKy.NguoiDung;
import NhatKy.NhatKy;
import NhatKy.ThuMuc;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import sun.applet.Main;

/**
 *
 * @author Administrator
 */
public class FormNhatKy extends javax.swing.JFrame {

    private final ThuMucServices thumucServices;
    private NhatKyServices nhatkyServices;
    private final NguoiDung ND;
    private static NhatKy NK;
    private static ThuMuc TM;

    /**
     * Creates new form FormNhatKy
     *
     * @param nguoidung
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public FormNhatKy(NguoiDung nguoidung) throws ClassNotFoundException, SQLException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        //this.setBackground(Color.WHITE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        initComponents();
        ZoomjTextArea();
        centerFrame();
        ND = nguoidung;
        TM = new ThuMuc();
        NK = new NhatKy();
        thumucServices = new ThuMucServices();
        nhatkyServices = new NhatKyServices();
        LoadJtreeThuMuc();
        jTextAreaNoiDung.setLineWrap(true);
        jLabelTrangThaiFIle.setVisible(false);
        jTextFieldNhapTenFolder.setEditable(false);
        //Sự kiện Tạo Nhật Ký
        SuKienTaoMoiNhatKy();
        //Sự kiện Lưu Lại
        SuKienLuuNhatKy();
        //Sự kiện Xóa Nhật Ký
        SukienXoaThuMucHoacNhatKy();
        //Sự kiện New Folder
        Action NewFolder;
        NewFolder = new AbstractAction("New Folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabelTrangThaiFIle.setText("NewFolder");
                jTextFieldNhapTenFolder.setEditable(true);
                jTextFieldNhapTenFolder.setText("");
                jTextFieldNhapTenFolder.requestFocus();//or inWindow
                System.out.println("new folder");
            }
        };
        String keyNewFolder = "New Folder";
        jButtonNewFolder.setAction(NewFolder);
        //NewFolder.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        jButtonNewFolder.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.SHIFT_DOWN_MASK), keyNewFolder);
        jButtonNewFolder.getActionMap().put(keyNewFolder, NewFolder);
        //Rename Folder
        Action RenameFolder;
        RenameFolder = new AbstractAction("Rename Folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("rename");
                if (jLabelTrangThaiFIle.getText().equals("EditForder")) {
                    jTextFieldNhapTenFolder.setEditable(true);
                    jTextFieldNhapTenFolder.requestFocus();//or inWindow
                }
            }
        };
        String keyRenameFolder = "Rename Folder";

        jButtonReNameFolder.setAction(RenameFolder);
        jButtonReNameFolder.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), keyRenameFolder);

        jButtonReNameFolder.getActionMap().put(keyRenameFolder, RenameFolder);

    }

    private void LoadJtreeThuMuc() throws ClassNotFoundException, SQLException {
        List<ThuMuc> thumucs = thumucServices.getAllThuMuc(ND);
        DefaultMutableTreeNode RootNode = new DefaultMutableTreeNode("Nhật ký của " + ND.getTaiKhoan());
        for (ThuMuc thumuc : thumucs) {
            DefaultMutableTreeNode ChildNode = new DefaultMutableTreeNode(thumuc.getTenThuMuc());
            //add tên nhật ký vào cây
            List<NhatKy> nhatkys = nhatkyServices.getAllNhatKyByMaThuMuc(thumuc);
            for (NhatKy nhatky : nhatkys) {
                DefaultMutableTreeNode ChildNodeNhatKy = new DefaultMutableTreeNode(nhatky.getTenNhatKy());
                ChildNode.add(ChildNodeNhatKy);
            }
            RootNode.add(ChildNode);
        }

        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(RootNode);
        jTreeThuMuc.setModel(defaultTreeModel);
    }

    private void centerFrame() {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        setLocation(dx, dy);
    }

    private void SuKienTaoMoiNhatKy() {
        Action NewFileNhatKy;
        NewFileNhatKy = new AbstractAction("Tạo Nhật Ký") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabelTrangThaiFIle.setText("NewFile");
                jTextFieldNhapTenNhatKy.setText("");
                jTextAreaNoiDung.setText("");
                jLabelNgayTao.setText("Ngày tạo: " + LocalDate.now().toString() + " Lúc " + LocalTime.now().toString().substring(0, 5));
                jLabelSuaLanCuoi.setText("Sửa lần cuối: Chưa sửa lần nào");
                jTextFieldNhapTenNhatKy.requestFocus();//or inWindow
            }
        };
        String key = "Tạo Nhật Ký";
        jButtonNewFile.setAction(NewFileNhatKy);
        //NewFileNhatKy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        jButtonNewFile.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), key);
        jButtonNewFile.getActionMap().put(key, NewFileNhatKy);
    }

    private void SuKienLuuNhatKy() {
        Action SaveFile;
        SaveFile = new AbstractAction("Lưu lại") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jLabelTrangThaiFIle.getText().equals("NewFile")) {
                    System.out.println("Tạo mới file...");
                    if (TM.getMaThuMuc() != -1) {//đã chọn thư mục
                        NK.setMaThuMuc(TM.getMaThuMuc());
                        if (!jTextFieldNhapTenNhatKy.getText().equals("")) {
                            NK.setTenNhatKy(jTextFieldNhapTenNhatKy.getText());
                            NK.setNgayTao(jLabelNgayTao.getText().substring(10, jLabelNgayTao.getText().length()));
                            NK.setNgayChinhSuaCuoiCung(jLabelSuaLanCuoi.getText().substring(14, jLabelSuaLanCuoi.getText().length()));
                            NK.setNoiDung(jTextAreaNoiDung.getText());
                            try {
                                if (nhatkyServices.KiemTraNhatKyTonTai(NK, ND)) {
                                    JOptionPane.showMessageDialog(FormNhatKy.this, "Tên nhật ký đã tồn tại", "Tạo Mới nhật ký", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    //đã lưu thành công return true;
                                    if (nhatkyServices.ThemNhatKyMoi(NK)) {
                                        jLabelTrangThaiFIle.setText("");
                                        LoadJtreeThuMuc();
                                        JOptionPane.showMessageDialog(FormNhatKy.this, "Lưu thành công.", "Tạo Mới nhật ký", JOptionPane.DEFAULT_OPTION);
                                    } else {
                                        JOptionPane.showMessageDialog(FormNhatKy.this, "Lưu thất bại, thử lại.", "Tạo Mới nhật ký", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } catch (ClassNotFoundException | SQLException ex) {
                                Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Chưa nhập tên nhật ký", "Tạo Mới nhật ký", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(FormNhatKy.this, "Chưa chọn thư mục lưu", "Tạo Mới nhật ký", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (jLabelTrangThaiFIle.getText().equals("EditFile")) {
                    System.out.println("Sửa file...");
                    NK.setTenNhatKy(jTextFieldNhapTenNhatKy.getText());
                    NK.setNoiDung(jTextAreaNoiDung.getText());
                    NK.setNgayChinhSuaCuoiCung(LocalDate.now().toString() + " Lúc " + LocalTime.now().toString().substring(0, 5));
                    try {
                        if (nhatkyServices.SuaNhatKy(NK)) {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Sửa Thành Công", "Sửa nhật ký", JOptionPane.DEFAULT_OPTION);
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (jLabelTrangThaiFIle.getText().equals("NewFolder")) {
                    if (!jTextFieldNhapTenFolder.getText().equals("")) {
                        TM.setTaiKhoan(ND.getTaiKhoan());
                        TM.setTenThuMuc(jTextFieldNhapTenFolder.getText());
                        try {
                            if (!thumucServices.KiemTraThuMucTonTai(TM)) {
                                //ADD THANH CONG
                                thumucServices.ThemThuMuc(TM);
                                LoadJtreeThuMuc();
                            } else {
                                JOptionPane.showMessageDialog(FormNhatKy.this, "Thư mục đã tồn tại", "New Folder", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(FormNhatKy.this, "Chưa nhập tên thư mục", "New Folder", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (jLabelTrangThaiFIle.getText().equals("EditForder")) {
                    if (!jTextFieldNhapTenFolder.getText().equals("")) {
                        TM.setTenThuMuc(jTextFieldNhapTenFolder.getText());
                        try {
                            if (!thumucServices.KiemTraThuMucTonTai(TM)) {
                                //ADD THANH CONG
                                thumucServices.DoiTenThuMuc(TM);
                                LoadJtreeThuMuc();
                                jTextFieldNhapTenFolder.setEditable(false);
                                jTextAreaNoiDung.requestFocus();
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(FormNhatKy.this, "Tên thư mục không được rỗng", "ReName Folder", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };
        String keysave = "Lưu lại";
        jButtonLuuLai.setAction(SaveFile);
        jButtonLuuLai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), keysave);
        jButtonLuuLai.getActionMap().put(keysave, SaveFile);
    }

    private void SukienXoaThuMucHoacNhatKy() {
        Action DeleteFileNhatKy;
        DeleteFileNhatKy = new AbstractAction("Xóa Nhật Ký") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int TraLoi = JOptionPane.showConfirmDialog(null, "Bạn thực sự muốn xóa?", "Xóa Nhật Ký", JOptionPane.YES_NO_OPTION);
                if (TraLoi == JOptionPane.OK_OPTION) {
                    try {
                        if (nhatkyServices.XoaMotNhatKy(NK)) {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Xóa thành công.", "Xóa nhật ký", JOptionPane.DEFAULT_OPTION);
                            jLabelTrangThaiFIle.setText("");
                            jLabelTrangThaiFIle.setText("NewFile");
                            jTextFieldNhapTenNhatKy.setText("");
                            jTextAreaNoiDung.setText("");
                            jLabelNgayTao.setText("Ngày tạo: " + LocalDate.now().toString() + " Lúc " + LocalTime.now().toString().substring(0, 5));
                            jLabelSuaLanCuoi.setText("Sửa lần cuối: Chưa sửa lần nào");
                            jTextFieldNhapTenNhatKy.requestFocus();//or inWindow
                            LoadJtreeThuMuc();
                        } else {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Xóa thất bại, thử lại.", "Tạo Mới nhật ký", JOptionPane.DEFAULT_OPTION);
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        String keyDel = "Xóa Nhật Ký";
        jButtonXoa.setAction(DeleteFileNhatKy);
        jButtonXoa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_DOWN_MASK), keyDel);
        jButtonXoa.getActionMap().put(keyDel, DeleteFileNhatKy);
    }

    private void ZoomjTextArea() {
        jTextAreaNoiDung.addMouseWheelListener(mouseWheelEvent -> {
            if (mouseWheelEvent.isControlDown()) {
                jTextAreaNoiDung.setFont(new Font(
                        jTextAreaNoiDung.getFont().getFontName(),
                        jTextAreaNoiDung.getFont().getStyle(),
                        mouseWheelEvent.getUnitsToScroll() > 0
                        ? jTextAreaNoiDung.getFont().getSize() - 2
                        : jTextAreaNoiDung.getFont().getSize() + 2));
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTrangThaiFIle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonNewFile = new javax.swing.JButton();
        jTextFieldTìmKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeThuMuc = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jTextFieldNhapTenFolder = new javax.swing.JTextField();
        jButtonNewFolder = new javax.swing.JButton();
        jButtonLuuLai = new javax.swing.JButton();
        jButtonReNameFolder = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabelThongTinFile3 = new javax.swing.JLabel();
        jLabelThongTinFile = new javax.swing.JLabel();
        jLabelNgayTao = new javax.swing.JLabel();
        jLabelSuaLanCuoi = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldNhapTenNhatKy = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaNoiDung = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTrangThaiFIle.setEnabled(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(100, 100));

        jButtonNewFile.setText("New Diary");
        jButtonNewFile.setToolTipText("Ctrl + N");
        jButtonNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewFileActionPerformed(evt);
            }
        });

        jTextFieldTìmKiem.setMinimumSize(new java.awt.Dimension(34, 20));
        jTextFieldTìmKiem.setPreferredSize(new java.awt.Dimension(34, 20));

        jButtonTimKiem.setText("Find");
        jButtonTimKiem.setToolTipText("Ctrl+ F");
        jButtonTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimKiemActionPerformed(evt);
            }
        });

        jButtonXoa.setText("Delete");
        jButtonXoa.setToolTipText("Ctrl + Del");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldTìmKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNewFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTìmKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Nhật Ký của tôi");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Gia Đình");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("dạ thầy ơi,hôm trước thầy bảo là ngày 15/5 mà thầy, thầy đổi hạn lại qua ngày mai được ko ạ");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Bạn Bè");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Công Việc");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("pizza");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ravioli");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("bananas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTreeThuMuc.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeThuMuc.setScrollsOnExpand(false);
        jTreeThuMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeThuMucMouseClicked(evt);
            }
        });
        jTreeThuMuc.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeThuMucValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeThuMuc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        );

        jButtonNewFolder.setText("New Folder");
        jButtonNewFolder.setToolTipText("Shift + N");
        jButtonNewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewFolderActionPerformed(evt);
            }
        });

        jButtonLuuLai.setText("Save");
        jButtonLuuLai.setToolTipText("Ctrl + S");

        jButtonReNameFolder.setText("Rename Folder");
        jButtonReNameFolder.setToolTipText("F2");
        jButtonReNameFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReNameFolderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButtonLuuLai, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonReNameFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNewFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTextFieldNhapTenFolder)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jTextFieldNhapTenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLuuLai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReNameFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelThongTinFile3.setText("Thông tin nhật ký:");

        jLabelThongTinFile.setText("Cách đây 10 ngày");

        jLabelNgayTao.setText("12/05/2020 17:31 PM");

        jLabelSuaLanCuoi.setText("Sửa lần cuối: 12/05/2020 17:31 PM");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelThongTinFile)
                    .addComponent(jLabelNgayTao)
                    .addComponent(jLabelSuaLanCuoi)
                    .addComponent(jLabelThongTinFile3))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabelThongTinFile3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelThongTinFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelNgayTao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSuaLanCuoi)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jLabel1.setText("Name Diary :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jTextFieldNhapTenNhatKy, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNhapTenNhatKy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)))
        );

        jTextAreaNoiDung.setColumns(20);
        jTextAreaNoiDung.setFont(new java.awt.Font("Consolas", 0, 17)); // NOI18N
        jTextAreaNoiDung.setRows(5);
        jTextAreaNoiDung.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextAreaNoiDung.setRequestFocusEnabled(false);
        jTextAreaNoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextAreaNoiDungMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextAreaNoiDung);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2)
                .addGap(0, 0, 0))
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/newfile.png"))); // NOI18N
        jMenuItem1.setText("New File");
        jMenuItem1.setActionCommand("Save");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Deletefile.png"))); // NOI18N
        jMenuItem2.setText("Delete File");
        jMenu1.add(jMenuItem2);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/DangXuat.png"))); // NOI18N
        jMenuItem5.setText("Đăng Xuât");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Folder");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/NewFolder.png"))); // NOI18N
        jMenuItem3.setText("New folder");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Account");

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/DoiMatKhau.png"))); // NOI18N
        jMenuItem6.setText("Đổi Mật Khẩu");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTrangThaiFIle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabelTrangThaiFIle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewFolderActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonTimKiemActionPerformed

    private void jButtonNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewFileActionPerformed

    private void jTreeThuMucValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeThuMucValueChanged

    }//GEN-LAST:event_jTreeThuMucValueChanged

    private void jTextAreaNoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextAreaNoiDungMouseClicked
        jTextAreaNoiDung.requestFocus();//or inWindow
    }//GEN-LAST:event_jTextAreaNoiDungMouseClicked

    private void jTreeThuMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeThuMucMouseClicked
        int MaNhatKy = -1;
        int MaThuMuc = -1;
        NhatKy NKCurrent = new NhatKy();
        ThuMuc TMCurrent = new ThuMuc();
        //jTextFieldNhapTenFolder1.setText(evt.getPath().toString());
        DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) jTreeThuMuc.getLastSelectedPathComponent();
        try {
            List<NhatKy> nhatkys = nhatkyServices.getAllNhatKyByTaiKhoan(ND);
            if (nhatkys.size() > 0) {
                for (NhatKy nhatky : nhatkys) {
                    if (nhatky.getTenNhatKy().equals(selectNode.getUserObject().toString())) {
                        MaNhatKy = nhatky.getMaNhatKy();
                        break;
                    }
                }
            }
            if (MaNhatKy != -1) { //là nhật ký
                jLabelTrangThaiFIle.setText("EditFile");
                NKCurrent = nhatkyServices.getNhatKyByMaNhatKy(MaNhatKy);
                NK.setMaNhatKy(NKCurrent.getMaNhatKy());
                NK.setMaThuMuc(NKCurrent.getMaThuMuc());
                NK.setTenNhatKy(NKCurrent.getTenNhatKy());
                NK.setNgayTao(NKCurrent.getNgayTao());
                NK.setNgayChinhSuaCuoiCung(NKCurrent.getNgayChinhSuaCuoiCung());
                NK.setNoiDung(NKCurrent.getNoiDung());
                //load nhật ký ra màn hình
                jTextFieldNhapTenNhatKy.setText(NK.getTenNhatKy());
                jTextAreaNoiDung.setText(NK.getNoiDung());
                jLabelNgayTao.setText("Ngày tạo: " + NK.getNgayTao());
                jLabelSuaLanCuoi.setText("Sửa lần cuối: " + NK.getNgayChinhSuaCuoiCung());
            } else //là thư mục
            {
                jTextFieldNhapTenNhatKy.setText("");
                jTextAreaNoiDung.setText("");
                jLabelNgayTao.setText("");
                jLabelSuaLanCuoi.setText("");
                jLabelTrangThaiFIle.setText("EditForder");

                List<ThuMuc> thumucs = thumucServices.getAllThuMuc(ND);
                if (thumucs.size() > 0) {
                    for (ThuMuc thumuc : thumucs) {
                        if (thumuc.getTenThuMuc().equals(selectNode.getUserObject().toString())) {
                            MaThuMuc = thumuc.getMaThuMuc();
                            break;
                        }
                    }
                }
                if (MaThuMuc != -1) { //là nhật ký
                    jTextFieldNhapTenFolder.setText(selectNode.getUserObject().toString());

                    TMCurrent = thumucServices.getThuMucByID(MaThuMuc);
                    TM.setMaThuMuc(TMCurrent.getMaThuMuc());
                    TM.setTaiKhoan(TMCurrent.getTaiKhoan());
                    TM.setTenThuMuc(TMCurrent.getTenThuMuc());
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            jTextFieldTìmKiem.setText(jLabelTrangThaiFIle.getText());
        }
    }//GEN-LAST:event_jTreeThuMucMouseClicked

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jButtonReNameFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReNameFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonReNameFolderActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FormNhatKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormNhatKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormNhatKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormNhatKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormNhatKy().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLuuLai;
    private javax.swing.JButton jButtonNewFile;
    private javax.swing.JButton jButtonNewFolder;
    private javax.swing.JButton jButtonReNameFolder;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelNgayTao;
    private javax.swing.JLabel jLabelSuaLanCuoi;
    private javax.swing.JLabel jLabelThongTinFile;
    private javax.swing.JLabel jLabelThongTinFile3;
    private javax.swing.JLabel jLabelTrangThaiFIle;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaNoiDung;
    private javax.swing.JTextField jTextFieldNhapTenFolder;
    private javax.swing.JTextField jTextFieldNhapTenNhatKy;
    private javax.swing.JTextField jTextFieldTìmKiem;
    private javax.swing.JTree jTreeThuMuc;
    // End of variables declaration//GEN-END:variables
}
