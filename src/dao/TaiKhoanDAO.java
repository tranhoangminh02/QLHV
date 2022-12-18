package dao;

import model.TaiKhoan;

/**
 *
 * @author Admin
 */
public interface TaiKhoanDAO {
    public TaiKhoan login(String tenDangNhap, String matKhau);
}
