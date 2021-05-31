/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.LineNumberComponent;
import Core.LineNumberModelImpl;
import DAO.NguoiDungServices;
import DAO.NhatKyServices;
import DAO.ThuMucServices;
import NhatKy.NguoiDung;
import NhatKy.NhatKy;
import NhatKy.ThuMuc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import sun.applet.Main;

/**
 *
 * @author Võ Văn Hiếu && Nguyễn Trần Anh Tuấn
 */
public class FormNhatKy extends javax.swing.JFrame {

    public static List<NhatKy> NhatKys;
    public static DefaultTableModel defaultTableModel;
    public final ThuMucServices thumucServices;
    public final NhatKyServices nhatkyServices;
    public static NguoiDung ND;
    public static NhatKy NK;
    private static ThuMuc TM;
    private int pos = 0;
    private final UndoManager undoManager;//undo textarea
    private static String StringFind = "";
    private final LineNumberModelImpl lineNumberModel = new LineNumberModelImpl();
    private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);

    /**
     * Creates new form FormNhatKy
     *
     * @param nguoidung
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public FormNhatKy(NguoiDung nguoidung) throws ClassNotFoundException, SQLException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());chuối
        initComponents();
        CreateTableFind();
        ZoomjTextArea();
        jTextAreaDiary.setWrapStyleWord(true);
        jTextAreaDiary.setLineWrap(true);
        centerFrame();
        ND = nguoidung;
        TM = new ThuMuc();
        NK = new NhatKy();
        TM.setMaThuMuc(1);
        thumucServices = new ThuMucServices();
        nhatkyServices = new NhatKyServices();
        undoManager = new UndoManager();
        this.setResizable(false);
        LoadJtreeThuMuc();
        SetCusor();
        jTextAreaDiary.setLineWrap(true);
        jLabelTrangThaiChucNang.setVisible(false);

        //Lắng nghe sự kiện
        SuKienTaoMoiNhatKy();
        SuKienLuuNhatKy();
        SukienXoaThuMucHoacNhatKy();
        SuKienNewFolder();
        SuKienRenameFolder();
        SuKienTimKiem();
        SuKienFindText();
        SuKienUndoRedo();
        LineNumber();
        //Set Giá trị mặc định 
        GiaTriMacDinh();
    }

    private void SuKienTaoMoiNhatKy() {
        Action NewFileNhatKy;
        NewFileNhatKy = new AbstractAction("New Diary") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabelTrangThaiChucNang.setText("NewFile");
                GiaTriKhiChonNewFile();
                jLabelNgayTao.setText("Ngày tạo: " + LocalDate.now().toString() + " Lúc " + LocalTime.now().toString().substring(0, 5));
                jLabelSuaLanCuoi.setText("Sửa lần cuối: Chưa sửa lần nào");
                jLabelThongTinFile.setText("Cách đây 00 ngày.");
            }
        };
        String key = "New Diary";
        jButtonNewFile.setAction(NewFileNhatKy);
        //NewFileNhatKy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        jButtonNewFile.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), key);
        jButtonNewFile.getActionMap().put(key, NewFileNhatKy);
    }

    private void SuKienLuuNhatKy() {
        Action SaveFile;
        SaveFile = new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (jLabelTrangThaiChucNang.getText()) {
                    case "NewFile":
                        NK.setMaThuMuc(TM.getMaThuMuc());
                        if (jTextFieldNhapTenNhatKy.getText().equals("")) {
                            NK.setTenNhatKy(jLabelNgayTao.getText());
                        } else {
                            NK.setTenNhatKy(jTextFieldNhapTenNhatKy.getText());
                        }
                        NK.setNgayTao(jLabelNgayTao.getText().substring(10, jLabelNgayTao.getText().length()));
                        NK.setNgayChinhSuaCuoiCung(jLabelSuaLanCuoi.getText().substring(14, jLabelSuaLanCuoi.getText().length()));
                        NK.setNoiDung(jTextAreaDiary.getText());
                        try {
                            if (nhatkyServices.KiemTraNhatKyTonTai(NK, ND)) {
                                JOptionPane.showMessageDialog(FormNhatKy.this, "Tên nhật ký đã tồn tại", "Tạo Mới nhật ký", JOptionPane.WARNING_MESSAGE);
                            } else {
                                //đã lưu thành công return true;
                                if (nhatkyServices.ThemNhatKyMoi(NK)) {
                                    jLabelTrangThaiChucNang.setText("EditFile");
                                    //selet nhật ký mới insert ra
                                    NK = nhatkyServices.getNhatKyByTenNhatKy(NK.getTenNhatKy());
                                    GiaTriKhiChonNhatKyTrenJTree();
                                    LoadJtreeThuMuc();
                                } else {
                                    JOptionPane.showMessageDialog(FormNhatKy.this, "Lưu thất bại, thử lại.", "Tạo Mới nhật ký", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FormNhatKy.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "EditFile":
                        if (jTextFieldNhapTenNhatKy.getText().equals("")) {

                            NK.setTenNhatKy(jLabelNgayTao.getText());
                        } else {
                            NK.setTenNhatKy(jTextFieldNhapTenNhatKy.getText());
                        }
                        NK.setNoiDung(jTextAreaDiary.getText());
                        NK.setNgayChinhSuaCuoiCung(LocalDate.now().toString() + " Lúc " + LocalTime.now().toString().substring(0, 5));
                        try {
                            if (nhatkyServices.SuaNhatKy(NK)) {
                                LoadJtreeThuMuc();
                                NK = nhatkyServices.getNhatKyByMaNhatKy(NK.getMaNhatKy());
                                jLabelSuaLanCuoi.setText("Sửa lần cuối: " + NK.getNgayChinhSuaCuoiCung());
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "NewFolder":
                        if (!jTextFieldNhapTenFolder.getText().equals("")) {
                            TM.setTaiKhoan(ND.getTaiKhoan());
                            TM.setTenThuMuc(jTextFieldNhapTenFolder.getText());
                            try {
                                if (!thumucServices.KiemTraThuMucTonTai(TM)) {
                                    //Thêm thư mục thnahf công
                                    thumucServices.ThemThuMuc(TM);
                                    LoadJtreeThuMuc();
                                    GiaTriMacDinh();
                                } else {
                                    JOptionPane.showMessageDialog(FormNhatKy.this, "Thư mục đã tồn tại", "New Folder", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (ClassNotFoundException | SQLException ex) {
                                Logger.getLogger(FormNhatKy.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Chưa nhập tên thư mục", "New Folder", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case "RenameFolder":
                        if (!jTextFieldNhapTenFolder.getText().equals("")) {
                            TM.setTenThuMuc(jTextFieldNhapTenFolder.getText());
                            try {
                                if (!thumucServices.KiemTraThuMucTonTai(TM)) {
                                    //ADD THANH CONG
                                    thumucServices.DoiTenThuMuc(TM);
                                    GiaTriMacDinh();
                                    LoadJtreeThuMuc();
                                }
                            } catch (ClassNotFoundException | SQLException ex) {
                                Logger.getLogger(FormNhatKy.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Tên thư mục không được rỗng", "ReName Folder", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        String keysave = "Save";
        jButtonLuuLai.setAction(SaveFile);
        jButtonLuuLai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), keysave);
        jButtonLuuLai.getActionMap().put(keysave, SaveFile);
    }

    private void SukienXoaThuMucHoacNhatKy() {
        Action DeleteFileNhatKy;
        DeleteFileNhatKy = new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jLabelTrangThaiChucNang.getText().equals("EditFile")) {
                    int TraLoi = JOptionPane.showConfirmDialog(null, "Bạn thực sự muốn xóa?", "Xóa Nhật Ký", JOptionPane.YES_NO_OPTION);
                    if (TraLoi == JOptionPane.OK_OPTION) {
                        try {
                            if (nhatkyServices.XoaMotNhatKy(NK)) {
                                GiaTriMacDinh();
                                DeleteNodeJtreeThuMuc();
                            } else {
                                JOptionPane.showMessageDialog(FormNhatKy.this, "Xóa thất bại, thử lại.", "Tạo Mới nhật ký", JOptionPane.DEFAULT_OPTION);
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(FormNhatKy.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (jLabelTrangThaiChucNang.getText().equals("EditFolder")) {
                    try {
                        //Nếu có nhật ký trong thư mục
                        if (nhatkyServices.getAllNhatKyByMaThuMuc(TM).size() > 0) {
                            int TraLoi = JOptionPane.showConfirmDialog(null, "Tất cả nhật ký trong thư mục này sẽ bị xóa.", "Xóa Thư Mục", JOptionPane.YES_NO_OPTION);
                            if (TraLoi == JOptionPane.OK_OPTION) {
                                try {
                                    if (nhatkyServices.XoaNhieuNhatKy(TM) || thumucServices.XoaThuMuc(TM)) {
                                        JOptionPane.showMessageDialog(FormNhatKy.this, "Xóa thành công.", "Xóa Thư Mục", JOptionPane.DEFAULT_OPTION);
                                        GiaTriMacDinh();
                                        DeleteNodeJtreeThuMuc();
                                    } else {
                                        JOptionPane.showMessageDialog(FormNhatKy.this, "Xóa thất bại, thử lại.", "Tạo Mới nhật ký", JOptionPane.DEFAULT_OPTION);
                                    }
                                } catch (ClassNotFoundException | SQLException ex) {
                                    Logger.getLogger(FormNhatKy.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            thumucServices.XoaThuMuc(TM);
                            GiaTriMacDinh();
                            DeleteNodeJtreeThuMuc();
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };
        String keyDel = "Delete";
        jButtonXoa.setAction(DeleteFileNhatKy);
        jButtonXoa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, true), keyDel);
        jButtonXoa.getActionMap().put(keyDel, DeleteFileNhatKy);
    }

    private void SuKienNewFolder() {
        Action NewFolder;
        NewFolder = new AbstractAction("New Folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabelTrangThaiChucNang.setText("NewFolder");
                GiaTriKhiChonNewFolder();
            }
        };
        String keyNewFolder = "New Folder";
        jButtonNewFolder.setAction(NewFolder);
        //NewFolder.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        jButtonNewFolder.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK), keyNewFolder);
        jButtonNewFolder.getActionMap().put(keyNewFolder, NewFolder);
    }

    private void SuKienRenameFolder() {
        Action RenameFolder;
        RenameFolder = new AbstractAction("Rename Folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("rename");
                if (jLabelTrangThaiChucNang.getText().equals("EditFolder")) {
                    GiaTriKhiChonRenameFolder();
                }
            }
        };
        String keyRenameFolder = "Rename Folder";
        jButtonReNameFolder.setAction(RenameFolder);
        jButtonReNameFolder.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true), keyRenameFolder);
        jButtonReNameFolder.getActionMap().put(keyRenameFolder, RenameFolder);
    }

    private void SuKienTimKiem() {
        Action FindNhatKy;
        FindNhatKy = new AbstractAction("Find") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTextFieldTimKiem.getText().equals("")) {
                    jTextFieldTimKiem.requestFocus();
                } else {
                    try {
                        //Danh Sách kết quả tìm thấy
//                        jTextFieldTimKiem.setText(String.valueOf(NhatKys.size()));
                        List<NhatKy> NhatKys = nhatkyServices.TimKiemNangCao(jTextFieldTimKiem.getText(), ND);
                        if (NhatKys.size() > 0) {
                            TableFind.setTableData(NhatKys);
                            jPanel6.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(FormNhatKy.this, "Không tìm thấy kết quả nào");
                        }

                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(FormNhatKy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        String keyFind = "Find";
        jButtonTimKiem.setAction(FindNhatKy);
        jButtonTimKiem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), keyFind);
        jButtonTimKiem.getActionMap().put(keyFind, FindNhatKy);
    }

    private void SuKienFindText() {
        findButton.addActionListener((ActionEvent e) -> {
            FindText();
        });
    }

    private void SuKienUndoRedo() {
        Document doc = jTextAreaDiary.getDocument();
        doc.addUndoableEditListener((UndoableEditEvent e) -> {
            //System.out.println("Add edit");
            undoManager.addEdit(e.getEdit());
        });

        InputMap im = jTextAreaDiary.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = jTextAreaDiary.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "FindText");
        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("FindText", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (StringFind.equals("")) {
                        jTextFindtext.requestFocus();
                    } else {
                        jTextFindtext.setText(StringFind);
                        FindText();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
    }

    private void LineNumber() {
        jScrollPane.setRowHeaderView(lineNumberComponent);
        lineNumberComponent.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        jTextAreaDiary.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }
        });
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

    private void DeleteNodeJtreeThuMuc() throws ClassNotFoundException, SQLException {
        DefaultMutableTreeNode Selectnode = (DefaultMutableTreeNode) jTreeThuMuc.getSelectionPath().getLastPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) jTreeThuMuc.getModel();
        if (Selectnode != jTreeThuMuc.getModel().getRoot()) {
            model.removeNodeFromParent(Selectnode);
        }
    }

    public static void LoadNhatKy() {
        jTextFieldNhapTenNhatKy.setText(NK.getTenNhatKy());
        jTextAreaDiary.setText(NK.getNoiDung());
        jLabelThongTinFile.setText(TinhKhoanCachHaiNgay(NK.getNgayTao().substring(0, 10), LocalDate.now().toString()));
        jLabelNgayTao.setText("Ngày tạo: " + NK.getNgayTao());
        jLabelSuaLanCuoi.setText("Sửa lần cuối: " + NK.getNgayChinhSuaCuoiCung());
    }

    private static String TinhKhoanCachHaiNgay(String D1, String D2) {
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Date date1 = Date.valueOf(D1);
        Date date2 = Date.valueOf(D2);
        c1.setTime(date1);
        c2.setTime(date2);
        long KhoangCach = (Math.abs((c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000)));
        String CachDay = "";
        if ((KhoangCach / 30) / 12 >= 1) {
            if (KhoangCach % (30 * 12.0) >= 0.5) {
                CachDay = "Cách đây hơn " + Math.round((KhoangCach / 30) / 12) + " năm.";
            } else {
                CachDay = "Cách đây " + Math.round((KhoangCach / 30) / 12) + " năm.";
            }
        } else if ((KhoangCach / 30) >= 1) {
            if (KhoangCach % 30 >= 15) {
                CachDay = "Cách đây hơn " + Math.round((KhoangCach / 30)) + " tháng.";
            } else {
                CachDay = "Cách đây " + Math.round((KhoangCach / 30)) + " tháng.";
            }
        } else {
            CachDay = "Cách đây " + Math.round(KhoangCach) + " ngày.";
        }
        return CachDay;
    }

    private void GiaTriMacDinh() {
        jLabelTrangThaiChucNang.setText("NewFile");
        jButtonTimKiem.setEnabled(true);
        jButtonXoa.setEnabled(false);
        jButtonNewFile.setEnabled(false);
        jButtonLuuLai.setEnabled(true);
        jButtonReNameFolder.setEnabled(false);
        jButtonNewFolder.setEnabled(true);

        jTextFieldTimKiem.setEditable(true);
        jTextFieldNhapTenNhatKy.setEditable(true);
        jTextFieldNhapTenFolder.setEditable(false);
        jTextAreaDiary.setEditable(true);
        jTextAreaDiary.setBackground(new Color(255, 255, 255));

        jTextFieldTimKiem.setText("");
        jTextFieldNhapTenNhatKy.setText("");
        jTextFieldNhapTenFolder.setText("");
        jTextAreaDiary.setText("");
        jTextAreaDiary.requestFocus();
        jLabelNgayTao.setText("Ngày tạo: " + LocalDate.now().toString() + " Lúc " + LocalTime.now().toString().substring(0, 5));
        jLabelSuaLanCuoi.setText("Sửa lần cuối: Chưa sửa lần nào");
        jLabelThongTinFile.setText("Cách đây 00 ngày.");
    }

    private void GiaTriKhiChonThuMucTrenJTree() {
        jButtonTimKiem.setEnabled(true);
        jButtonXoa.setEnabled(true);
        jButtonNewFile.setEnabled(true);
        jButtonLuuLai.setEnabled(false);
        jButtonReNameFolder.setEnabled(true);
        jButtonNewFolder.setEnabled(true);

        jTextFieldTimKiem.setEditable(true);
        jTextFieldNhapTenNhatKy.setEditable(false);
        jTextFieldNhapTenFolder.setEditable(false);
        jTextAreaDiary.setEditable(false);
        jTextAreaDiary.setBackground(new Color(240, 240, 240));

        jTextFieldTimKiem.setText("");
        jTextFieldNhapTenNhatKy.setText("");
        jTextFieldNhapTenFolder.setText("");
        jTextAreaDiary.setText("");
    }

    public static void GiaTriKhiChonNhatKyTrenJTree() {
        jButtonTimKiem.setEnabled(true);
        jButtonXoa.setEnabled(true);
        jButtonNewFile.setEnabled(true);
        jButtonLuuLai.setEnabled(true);
        jButtonReNameFolder.setEnabled(false);
        jButtonNewFolder.setEnabled(true);

        jTextFieldTimKiem.setEditable(true);
        jTextFieldNhapTenNhatKy.setEditable(true);
        jTextFieldNhapTenFolder.setEditable(false);
        jTextAreaDiary.setEditable(true);
        jTextAreaDiary.setBackground(new Color(255, 255, 255));
    }

    private void GiaTriKhiChonNewFolder() {
        jLabelTrangThaiChucNang.setText("NewFolder");
        jButtonTimKiem.setEnabled(false);
        jButtonNewFile.setEnabled(false);
        jButtonNewFolder.setEnabled(false);
        jButtonLuuLai.setEnabled(true);
        jButtonReNameFolder.setEnabled(false);
        jButtonXoa.setEnabled(false);

        jTextFieldTimKiem.setEditable(false);
        jTextFieldNhapTenNhatKy.setEditable(false);
        jTextFieldNhapTenFolder.setEditable(true);
        jTextAreaDiary.setEditable(false);
        jTextAreaDiary.setBackground(new Color(240, 240, 240));

        jTextFieldTimKiem.setText("");
        jTextFieldNhapTenNhatKy.setText("");
        jTextFieldNhapTenFolder.setText("");
        jTextAreaDiary.setText("");

        jTextFieldNhapTenFolder.requestFocus();
    }

    private void GiaTriKhiChonRenameFolder() {
        jLabelTrangThaiChucNang.setText("RenameFolder");
        jButtonTimKiem.setEnabled(false);
        jButtonNewFile.setEnabled(false);
        jButtonNewFolder.setEnabled(false);
        jButtonLuuLai.setEnabled(true);
        jButtonReNameFolder.setEnabled(false);
        jButtonXoa.setEnabled(false);

        jTextFieldTimKiem.setEditable(false);
        jTextFieldNhapTenNhatKy.setEditable(false);
        jTextFieldNhapTenFolder.setEditable(true);
        jTextAreaDiary.setEditable(false);
        jTextAreaDiary.setBackground(new Color(240, 240, 240));

        jTextFieldTimKiem.setText("");
        jTextFieldNhapTenNhatKy.setText("");
        jTextFieldNhapTenFolder.setText(TM.getTenThuMuc());
        jTextAreaDiary.setText("");

        jTextFieldNhapTenFolder.requestFocus();
    }

    private void GiaTriKhiChonNewFile() {
        jButtonTimKiem.setEnabled(false);
        jButtonXoa.setEnabled(false);
        jButtonNewFile.setEnabled(false);
        jButtonLuuLai.setEnabled(true);
        jButtonReNameFolder.setEnabled(false);
        jButtonNewFolder.setEnabled(false);

        jTextFieldTimKiem.setEditable(false);
        jTextFieldNhapTenNhatKy.setEditable(true);
        jTextFieldNhapTenFolder.setEditable(false);
        jTextAreaDiary.setEditable(true);
        jTextAreaDiary.setBackground(new Color(255, 255, 255));

        jTextFieldTimKiem.setText("");
        jTextFieldNhapTenNhatKy.setText("");
        jTextFieldNhapTenFolder.setText("");
        jTextAreaDiary.setText("");
        jTextAreaDiary.requestFocus();
    }

    private void centerFrame() {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        setLocation(dx, dy);
    }

    private void ZoomjTextArea() {
        jScrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                if (evt.isControlDown()) {
                    jTextAreaDiary.setFont(new java.awt.Font(jTextAreaDiary.getFont().getFontName(), jTextAreaDiary.getFont().getStyle(),
                            evt.getUnitsToScroll() > 0 ? jTextAreaDiary.getFont().getSize() - 1
                            : jTextAreaDiary.getFont().getSize() + 1));
                }
            }
        });
    }

    private void FindText() {
        if (!jTextFindtext.getText().equals("")) {
            // Lấy văn bản cần tìm ... chuyển nó thành chữ thường để so sánh dễ dàng hơn
            String find = jTextFindtext.getText().toLowerCase();
            // Focus vào JtextArea, nếu không phần tô sáng sẽ không hiển thị
            jTextAreaDiary.requestFocusInWindow();
            // Từ khóa hợp lệ
            if (find != null && find.length() > 0) {
                Document document = jTextAreaDiary.getDocument();
                int findLength = find.length();
                try {
                    boolean found = false;
                    // Nếu từ khóa dài hơn text của Arera
                    if (pos + findLength > document.getLength()) {
                        pos = 0;
                    }
                    while (pos + findLength <= document.getLength()) {
                        // Lấy văn bản tìm kiếm ra
                        String match = document.getText(pos, findLength).toLowerCase();
                        // Khớp với từ khóa không
                        if (match.equals(find)) {
                            found = true;
                            break;
                        }
                        pos++;
                    }
                    // Nếu tìm thấy
                    if (found) {
                        // Lấy hình chữ nhật nơi văn bản sẽ hiển thị ...
                        Rectangle viewRect = jTextAreaDiary.modelToView(pos);
                        // Cuộn để hiển thị hình chữ nhật
                        jTextAreaDiary.scrollRectToVisible(viewRect);
                        // Đánh dấu văn bản
                        jTextAreaDiary.setCaretPosition(pos + findLength);
                        jTextAreaDiary.moveCaretPosition(pos);
                        // Di chuyển vị trí tìm kiếm ra ngoài kết quả phù hợp hiện tại
                        pos += findLength;
                    }

                } catch (BadLocationException exp) {
                }
            }
        } else {
            jTextFindtext.requestFocus();
        }
    }

    private void SetCusor() {
        jButtonLuuLai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonNewFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonNewFolder.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonReNameFolder.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void CreateTableFind() throws ClassNotFoundException, SQLException {
        TableFind TableFind = new TableFind();
        TableFind.setBounds(jPanel6.getX(), jPanel6.getY(), jPanel6.getWidth() - 50, jPanel6.getHeight());
        javax.swing.JPanel jPanel7 = new javax.swing.JPanel();
        jPanel7.setBounds(jPanel6.getX(), jPanel6.getY(), jPanel6.getWidth(), jPanel6.getHeight() + 100);
        TableFind.setPreferredSize(new Dimension(jPanel6.getWidth(), jPanel6.getHeight()));
        jPanel7.add(TableFind);
        jPanel7.updateUI();
        jPanel1.add(jPanel7);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTrangThaiChucNang = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextFieldNhapTenFolder = new javax.swing.JTextField();
        jButtonNewFolder = new javax.swing.JButton();
        jButtonLuuLai = new javax.swing.JButton();
        jButtonReNameFolder = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeThuMuc = new javax.swing.JTree();
        jButtonNewFile = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jTextFieldTimKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldNhapTenNhatKy = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFindtext = new javax.swing.JTextField();
        findButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        jTextAreaDiary = new javax.swing.JTextArea();
        jPaneltH = new javax.swing.JPanel();
        jLabelThongTinFile3 = new javax.swing.JLabel();
        jLabelThongTinFile = new javax.swing.JLabel();
        jLabelNgayTao = new javax.swing.JLabel();
        jLabelSuaLanCuoi = new javax.swing.JLabel();
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
        setTitle("Diary Edittor");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabelTrangThaiChucNang.setEnabled(false);

        jPanel1.setBackground(new java.awt.Color(241, 243, 229));

        jPanel3.setBackground(new java.awt.Color(241, 243, 229));

        jTextFieldNhapTenFolder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jButtonNewFolder.setText("New Folder");
        jButtonNewFolder.setToolTipText("Shift + N");

        jButtonLuuLai.setText("Save");
        jButtonLuuLai.setToolTipText("Ctrl + S");

        jButtonReNameFolder.setText("Rename Folder");
        jButtonReNameFolder.setToolTipText("F2");

        jTreeThuMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        jScrollPane1.setViewportView(jTreeThuMuc);

        jButtonNewFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonNewFile.setText("New Diary");
        jButtonNewFile.setToolTipText("Ctrl + N");

        jButtonXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonXoa.setText("Delete");
        jButtonXoa.setToolTipText("Ctrl + Del");

        jTextFieldTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldTimKiem.setMinimumSize(new java.awt.Dimension(34, 20));
        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(34, 20));

        jButtonTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonTimKiem.setText("Find");
        jButtonTimKiem.setToolTipText("Ctrl+ F");

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                .addComponent(jTextFieldTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                .addComponent(jButtonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNewFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNhapTenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLuuLai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReNameFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(241, 243, 229));

        jTextFieldNhapTenNhatKy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setText("Diary Name:");

        jTextFindtext.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        findButton.setText("Next");
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNhapTenNhatKy, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFindtext, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNhapTenNhatKy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jTextFindtext)
                    .addComponent(findButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTextAreaDiary.setColumns(20);
        jTextAreaDiary.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTextAreaDiary.setRows(5);
        jTextAreaDiary.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextAreaDiary.setRequestFocusEnabled(false);
        jTextAreaDiary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextAreaDiaryMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(jTextAreaDiary);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabelThongTinFile3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelThongTinFile3.setText("Thông tin nhật ký:");

        jLabelThongTinFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelThongTinFile.setText("Cách đây 10 ngày");

        jLabelNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNgayTao.setText("Ngày Tạo: 12/05/2020 17:31 PM ");

        jLabelSuaLanCuoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSuaLanCuoi.setText("Sửa lần cuối: Chưa sửa lần nào.");

        javax.swing.GroupLayout jPaneltHLayout = new javax.swing.GroupLayout(jPaneltH);
        jPaneltH.setLayout(jPaneltHLayout);
        jPaneltHLayout.setHorizontalGroup(
            jPaneltHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneltHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPaneltHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelThongTinFile)
                    .addComponent(jLabelNgayTao)
                    .addComponent(jLabelSuaLanCuoi)
                    .addComponent(jLabelThongTinFile3))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPaneltHLayout.setVerticalGroup(
            jPaneltHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneltHLayout.createSequentialGroup()
                .addComponent(jLabelThongTinFile3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelThongTinFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelNgayTao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSuaLanCuoi)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPaneltH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPaneltH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(241, 243, 229));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/newfile.png"))); // NOI18N
        jMenuItem1.setText("New File");
        jMenuItem1.setActionCommand("Save");
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
                .addGap(968, 968, 968)
                .addComponent(jLabelTrangThaiChucNang)
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabelTrangThaiChucNang)))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextAreaDiaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextAreaDiaryMouseClicked
        jTextAreaDiary.requestFocus();
        if (jTextAreaDiary.getSelectedText() != null) { // See if they selected something 
            StringFind = jTextAreaDiary.getSelectedText();
        } else {
            StringFind = "";
        }
    }//GEN-LAST:event_jTextAreaDiaryMouseClicked

    private void jTreeThuMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeThuMucMouseClicked
        int MaNhatKy = -1;
        int MaThuMuc = -1;
        NhatKy NKCurrent = new NhatKy();
        ThuMuc TMCurrent = new ThuMuc();
        TreeSelectionModel smd = jTreeThuMuc.getSelectionModel();
        if (jLabelTrangThaiChucNang.getText().equals("NewFile") && !jTextAreaDiary.getText().trim().equals("")) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn lưu file này không?", "Save Diary", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                jButtonNewFile.doClick();
            }
        }
        if (smd.getSelectionCount() > 0) {
            DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) jTreeThuMuc.getSelectionPath().getLastPathComponent();
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
                    GiaTriKhiChonNhatKyTrenJTree();
                    jLabelTrangThaiChucNang.setText("EditFile");
                    NKCurrent = nhatkyServices.getNhatKyByMaNhatKy(MaNhatKy);
                    NK.setMaNhatKy(NKCurrent.getMaNhatKy());
                    NK.setMaThuMuc(TM.getMaThuMuc());
                    NK.setMaThuMuc(NKCurrent.getMaThuMuc());
                    NK.setTenNhatKy(NKCurrent.getTenNhatKy());
                    NK.setNgayTao(NKCurrent.getNgayTao());
                    NK.setNgayChinhSuaCuoiCung(NKCurrent.getNgayChinhSuaCuoiCung());
                    NK.setNoiDung(NKCurrent.getNoiDung());
                    //load nhật ký ra màn hình
                    LoadNhatKy();
                } else //là thư mục
                {
                    jLabelTrangThaiChucNang.setText("EditFolder");
                    GiaTriKhiChonThuMucTrenJTree();
                    List<ThuMuc> thumucs = thumucServices.getAllThuMuc(ND);
                    if (thumucs.size() > 0) {
                        for (ThuMuc thumuc : thumucs) {
                            if (thumuc.getTenThuMuc().equals(selectNode.getUserObject().toString())) {
                                MaThuMuc = thumuc.getMaThuMuc();
                                break;
                            }
                        }
                    }
                    if (MaThuMuc != -1) { //Tìm thấy thư mục
                        jTextFieldNhapTenFolder.setText(selectNode.getUserObject().toString());
                        TMCurrent = thumucServices.getThuMucByID(MaThuMuc);
                        TM.setMaThuMuc(TMCurrent.getMaThuMuc());
                        TM.setTaiKhoan(TMCurrent.getTaiKhoan());
                        TM.setTenThuMuc(TMCurrent.getTenThuMuc());
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FormNhatKy.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }
    }//GEN-LAST:event_jTreeThuMucMouseClicked

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new DangNhap(ND).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new DoiMatKhau().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_findButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton findButton;
    public static javax.swing.JButton jButtonLuuLai;
    public static javax.swing.JButton jButtonNewFile;
    public static javax.swing.JButton jButtonNewFolder;
    public static javax.swing.JButton jButtonReNameFolder;
    public static javax.swing.JButton jButtonTimKiem;
    public static javax.swing.JButton jButtonXoa;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabelNgayTao;
    public static javax.swing.JLabel jLabelSuaLanCuoi;
    public static javax.swing.JLabel jLabelThongTinFile;
    private javax.swing.JLabel jLabelThongTinFile3;
    private javax.swing.JLabel jLabelTrangThaiChucNang;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPaneltH;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextAreaDiary;
    public static javax.swing.JTextField jTextFieldNhapTenFolder;
    public static javax.swing.JTextField jTextFieldNhapTenNhatKy;
    public static javax.swing.JTextField jTextFieldTimKiem;
    private javax.swing.JTextField jTextFindtext;
    public static javax.swing.JTree jTreeThuMuc;
    // End of variables declaration//GEN-END:variables

}
