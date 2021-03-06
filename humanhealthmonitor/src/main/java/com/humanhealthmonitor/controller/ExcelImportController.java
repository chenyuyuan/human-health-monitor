package com.humanhealthmonitor.controller;

//import com.example.excelimport.excel.ImportData;
import com.humanhealthmonitor.pojo.Admin;
import com.humanhealthmonitor.pojo.Netmask;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.AdminService;
import com.humanhealthmonitor.service.ObjectService;
import com.humanhealthmonitor.service.UserNetmaskService;
import com.humanhealthmonitor.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ExcelImportController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserNetmaskService userNetmaskService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectService objectService;

    @GetMapping("/excelimport")
    String test(HttpServletRequest request) {
        return "importExcel";
    }


    //处理文件上传
    @ResponseBody//返回json数据
    @RequestMapping(value = "/importAdmin", method = RequestMethod.POST)
    public String uploadAdmin(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
//根据路径获取这个操作excel的实例
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());            //根据页面index 获取sheet页
            XSSFSheet sheet = wb.getSheetAt(0);
//实体类集合
            List<Admin> importDatas = new ArrayList<>();
            XSSFRow row = null;
//循环sesheet页中数据从第二行开始，第一行是标题

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//获取每一行数据
                row = sheet.getRow(i);
                if(row ==null) continue;
                Admin data = new Admin();
                String adminId = row.getCell(0).getStringCellValue();
                data.setAdminId(adminId);
                sheet.getRow(i).getCell(1).setCellType(CellType.STRING);
                data.setPwd(row.getCell(1).getStringCellValue());
                data.setAdminGroup("no_root");
                data.setLoginState(false);
                adminService.insertAdminIfAbsent(data);
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                data.setCreateDate(df.parse(df.format(HSSFDateUtil.getJavaDate(row.getCell(2).getNumericCellValue()))));
//                data.setAge(Integer.valueOf((int) row.getCell(3).getNumericCellValue()));
                importDatas.add(data);

            }
//循环展示导入的数据，实际应用中应该校验并存入数据库
            for (Admin imdata : importDatas) {
                //SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
                System.out.println("adminId:"+imdata.getAdminId()+" pwd:"+imdata.getPwd());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导入成功!";
    }
    // 处理文件上传 Netmask
    @ResponseBody//返回json数据
    @RequestMapping(value = "/importNetmask", method = RequestMethod.POST)
    public String uploadNetmask(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());            //根据页面index 获取sheet页
            XSSFSheet sheet = wb.getSheetAt(0);
            List<Netmask> importDatas = new ArrayList<>();
            XSSFRow row = null;
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if(row ==null) continue;
                Netmask data = new Netmask();
                sheet.getRow(i).getCell(0).setCellType(CellType.STRING);
                sheet.getRow(i).getCell(1).setCellType(CellType.STRING);
                int id = Integer.parseInt(row.getCell(0).getStringCellValue());
                data.setId(id);
                data.setNetmask_name(row.getCell(1).getStringCellValue());
                userNetmaskService.insertNetmaskIfAbsent(data.getId(),data.getNetmask_name());

                importDatas.add(data);

            }
            for (Netmask imdata : importDatas) {
                //SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
                System.out.println("id:"+imdata.getId()+" netmask_name:"+imdata.getNetmask_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导入成功!";
    }


    @ResponseBody//返回json数据
    @RequestMapping(value = "/importUser", method = RequestMethod.POST)
    public String uploadUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());            //根据页面index 获取sheet页
            XSSFSheet sheet = wb.getSheetAt(0);
            List<User> importDatas = new ArrayList<>();
            XSSFRow row = null;

//            long timestamp = System.currentTimeMillis() / 1000;
//            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMdd"); //设置格式
//            String currentDate = format.format(Long.parseLong(timestamp + "000"));

            Calendar calendar = Calendar.getInstance();
            String registerDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                    String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" +
                    String.valueOf(calendar.get(Calendar.DATE) + 1);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if(row ==null) continue;
                User data = new User();

                System.out.println(i);
                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(4).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.STRING);

                data.setUserId(row.getCell(0).getStringCellValue());
                data.setUserName(row.getCell(1).getStringCellValue());
                data.setPwd(row.getCell(2).getStringCellValue());
                data.setSex(row.getCell(3).getStringCellValue());
                data.setRegisterDate(java.sql.Date.valueOf(registerDate));

                data.setBirthDate(java.sql.Date.valueOf(row.getCell(4).getStringCellValue()));
                data.setUserTel(row.getCell(5).getStringCellValue());
                data.setLoginState(false);
                data.setUserGroup("个人");



                userService.insertUserIfAbsent(data);

                importDatas.add(data);

            }
            for (User imdata : importDatas) {
                //SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
                System.out.println("userid:"+imdata.getUserId()+" userName:"+imdata.getUserName()
                        +" pwd:"+imdata.getPwd()+" sex:"+imdata.getSex()
                        +" registerDate:"+imdata.getRegisterDate()+" telephone:"+imdata.getUserTel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导入成功!";
    }

    //处理文件上传
    @ResponseBody//返回json数据
    @RequestMapping(value = "/importObject", method = RequestMethod.POST)
    public String uploadObject(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());            //根据页面index 获取sheet页
            XSSFSheet sheet = wb.getSheetAt(0);
            List<Object> importDatas = new ArrayList<>();
            XSSFRow row = null;

            Calendar calendar = Calendar.getInstance();
            String registerDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                    String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" +
                    String.valueOf(calendar.get(Calendar.DATE) + 1);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if(row ==null) continue;
                Object data = new Object();

                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(4).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.STRING);
                row.getCell(6).setCellType(CellType.STRING);

                data.setObjectId(row.getCell(0).getStringCellValue());
                data.setUserId(row.getCell(1).getStringCellValue());
                data.setObjectName(row.getCell(2).getStringCellValue());
                data.setPwd(row.getCell(3).getStringCellValue());
                data.setSex(row.getCell(4).getStringCellValue());
                data.setBirthDate(java.sql.Date.valueOf(row.getCell(5).getStringCellValue()));
                data.setRegisterDate(java.sql.Date.valueOf(registerDate));
                data.setObjectTel(row.getCell(6).getStringCellValue());
                data.setLoginState(false);
                objectService.insertObjectIfAbsent(data);
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                data.setCreateDate(df.parse(df.format(HSSFDateUtil.getJavaDate(row.getCell(2).getNumericCellValue()))));
//                data.setAge(Integer.valueOf((int) row.getCell(3).getNumericCellValue()));
                importDatas.add(data);

            }
//循环展示导入的数据，实际应用中应该校验并存入数据库
            for (Object imdata : importDatas) {
                //SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
                System.out.println("objectId:"+imdata.getObjectId()+" userId:"+imdata.getUserId()+
                        " objectName:"+imdata.getObjectName()+" pwd:"+imdata.getPwd()+
                " sex:"+imdata.getSex()+" birthDate:"+imdata.getBirthDate()+" objectTel:"+imdata.getObjectTel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导入成功!";
    }
}

