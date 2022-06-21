package com.infybuzz.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstReport {

    /*
        Parameter - $P{}
        Field - $F{}
        Variable - $V{}
     */

    public static void main(String[] args) {
        try {
            String filePath = "C:\\REPOSITORIOS\\PERSONAL\\GITHUB\\JasperSoft-JasperStudio\\Jasper-Report\\src\\main\\resources\\FirstReport.jrxml";

            Student student1 = new Student(1L, "Raj", "Joshi", "Happy Street", "Delhi");
            Student student2 = new Student(2L, "Peter", "Smith", "Any Street", "Mumbai");

            List<Student> studentList = new ArrayList<Student>();
            studentList.add(student1);
            studentList.add(student2);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("studentName", "John");

            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

            // Dynamic changes
            JRBaseTextField jrBaseTextField = (JRBaseTextField) jasperReport.getTitle().getElementByKey("name");
            jrBaseTextField.setForecolor(Color.RED);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\REPOSITORIOS\\PERSONAL\\GITHUB\\JasperSoft-JasperStudio\\Exported-Reports\\FirstReport.pdf");

            System.out.println("Report Created...");
        } catch (Exception exception){
            System.out.println("Exception while creating report");
            exception.printStackTrace();
        }
    }
}
