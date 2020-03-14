package com.humanhealthmonitor.controller;

//import com.example.excelimport.excel.ImportData;
import com.humanhealthmonitor.pojo.Admin;
import com.humanhealthmonitor.pojo.Netmask;
import com.humanhealthmonitor.service.AdminService;
import com.humanhealthmonitor.service.UserNetmaskService;
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
import java.util.List;

@Controller
public class ExcelImportController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserNetmaskService userNetmaskService;

    @GetMapping("/excelimport")
    String test(HttpServletRequest request) {
        return "importExcel";
    }


    //处理文件上传
    @ResponseBody//返回json数据
    @RequestMapping(value = "/importAdmin", method = RequestMethod.POST)
    public String uploadAdmin(@RequestParam("file") MultipartFile file,                            HttpServletRequest request) {
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
    //处理文件上传
    @ResponseBody//返回json数据
    @RequestMapping(value = "/importNetmask", method = RequestMethod.POST)
    public String uploadNetmask(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
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
            List<Netmask> importDatas = new ArrayList<>();
            XSSFRow row = null;
//循环sesheet页中数据从第二行开始，第一行是标题

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//获取每一行数据
                row = sheet.getRow(i);
                Netmask data = new Netmask();
                sheet.getRow(i).getCell(0).setCellType(CellType.STRING);
                sheet.getRow(i).getCell(1).setCellType(CellType.STRING);

                int id = Integer.parseInt(row.getCell(0).getStringCellValue());
                data.setId(id);


                data.setNetmask_name(row.getCell(1).getStringCellValue());




                userNetmaskService.insertNetmaskIfAbsent(data.getId(),data.getNetmask_name());
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                data.setCreateDate(df.parse(df.format(HSSFDateUtil.getJavaDate(row.getCell(2).getNumericCellValue()))));
//                data.setAge(Integer.valueOf((int) row.getCell(3).getNumericCellValue()));
                importDatas.add(data);

            }
//循环展示导入的数据，实际应用中应该校验并存入数据库
            for (Netmask imdata : importDatas) {
                //SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
                System.out.println("id:"+imdata.getId()+" netmask_name:"+imdata.getNetmask_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "导入成功!";
    }
}

