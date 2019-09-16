package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.AdminMapper;
import com.humanhealthmonitor.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    //按照账号检索管理员
    public Admin queryAdminByAdminId(String adminId) {
        return adminMapper.queryAdminByAdminId(adminId);
    }

    //查询所有管理员信息
    List<Admin> queryAdminByAdminId() {
        return adminMapper.queryAllAdmin();
    }

    //管理员登录
    public Admin adminLogin(String adminId, String pwd) {
        Admin sqlAdmin = adminMapper.queryAdminByAdminId(adminId);
        if (sqlAdmin == null) {
//            System.out.println("不存在此管理员账号");
            return null;
        } else if (sqlAdmin.getAdminId().equals(adminId) && sqlAdmin.getPwd().equals(pwd)) {
//            System.out.println("账号密码正确");
            //把管理员状态设置成在线并更新数据库
            sqlAdmin.setLoginState(true);
            adminMapper.updateAdminLoginState(sqlAdmin);
            return sqlAdmin;
        } else {
//            System.out.println("管理员密码错误");
            return null;
        }
    }

    //更新管理员登录状态,登录或离线时使用
    public int updateAdminLoginState(Admin admin) {
        return adminMapper.updateAdminLoginState(admin);
    }

    //更新管理员密码
    public int updateAdminPassword(Admin admin) {
        return adminMapper.updateAdminPassword(admin);
    }

}
