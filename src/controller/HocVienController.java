package controller;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.sql.Date;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.HocVien;
import service.HocVienService;
import service.HocVienServiceImpl;

/**
 *
 * @author Admin
 */
public class HocVienController {

    private final JButton btnSubmit;
    private final JTextField jtfMaHocVien;
    private final JTextField jtfHoTen;
    private final JDateChooser jdcNgaySinh;
    private final JRadioButton jrdNam;
    private final JRadioButton jrdNu;
    private final JTextField jtfSoDienThoai;
    private final JTextArea jtaDiaChi;
    private final JCheckBox jchkTinhTrang;
    private final JLabel jlbMsg;

    private HocVien hocVien = null;
    private HocVienService hocVienService = null;

    public HocVienController(JButton btnSubmit, JTextField jtfMaHocVien, JTextField jtfHoTen, JDateChooser jdcNgaySinh, JRadioButton jrdNam, JRadioButton jrdNu, JTextField jtfSoDienThoai, JTextArea jtaDiaChi, JCheckBox jchkTinhTrang, JLabel jlbMsg) {
        this.btnSubmit = btnSubmit;
        this.jtfMaHocVien = jtfMaHocVien;
        this.jtfHoTen = jtfHoTen;
        this.jdcNgaySinh = jdcNgaySinh;
        this.jrdNam = jrdNam;
        this.jrdNu = jrdNu;
        this.jtfSoDienThoai = jtfSoDienThoai;
        this.jtaDiaChi = jtaDiaChi;
        this.jchkTinhTrang = jchkTinhTrang;
        this.jlbMsg = jlbMsg;

        this.hocVienService = new HocVienServiceImpl();
    }

    public void setView(HocVien hocVien) {
        this.hocVien = hocVien;
        jtfMaHocVien.setText("#" + hocVien.getMa_hoc_vien());
        jtfHoTen.setText(hocVien.getHo_ten());
        jdcNgaySinh.setDate(hocVien.getNgay_sinh());
        if (hocVien.isGioi_tinh()) {
            jrdNam.setSelected(true);
//            jrdNu.setSelected(false);
        } else {
//            jrdNam.setSelected(false);
            jrdNu.setSelected(true);
        }
        jtfSoDienThoai.setText(hocVien.getSo_dien_thoai());
        jtaDiaChi.setText(hocVien.getDia_chi());
        jchkTinhTrang.setSelected(hocVien.isTinh_trang());

        setEvent();
    }

    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNotNull()) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                    } else {
                        hocVien.setHo_ten(jtfHoTen.getText().trim());
                        hocVien.setNgay_sinh(covertDateToDateSql(jdcNgaySinh.getDate()));
                        hocVien.setSo_dien_thoai(jtfSoDienThoai.getText());
                        hocVien.setDia_chi(jtaDiaChi.getText());
                        hocVien.setGioi_tinh(jrdNam.isSelected());
                        hocVien.setTinh_trang(jchkTinhTrang.isSelected());
                        if (showDialog()) {
                            int lastId = hocVienService.createOrUpdate(hocVien);
                            if (lastId != 0) {
                                hocVien.setMa_hoc_vien(lastId);
                                jtfMaHocVien.setText("#" + hocVien.getMa_hoc_vien());
                                jlbMsg.setText("Xử lý cập nhật dữ liệu thành công!");
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        }
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(100, 221, 23));
            }

        });
    }

    private boolean checkNotNull() {
        return jtfHoTen.getText() != null && !jtfHoTen.getText().equalsIgnoreCase("");
    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn cập nhật dữ liệu hay không?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    public java.sql.Date covertDateToDateSql(Date d) {
        return new java.sql.Date(d.getTime());
    }
}
