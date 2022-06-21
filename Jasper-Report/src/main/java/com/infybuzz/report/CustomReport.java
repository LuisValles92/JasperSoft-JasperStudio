package com.infybuzz.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomReport {

    /*
        Parameter - Report
        Source - Table data set
        List<Subject> - Fields
     */

    public static void main(String[] args) {
        try {
            String filePath = "C:\\REPOSITORIOS\\PERSONAL\\GITHUB\\JasperSoft-JasperStudio\\Jasper-Report\\src\\main\\resources\\Student.jrxml";

            Subject subject1 = new Subject("Java", 80);
            Subject subject2 = new Subject("MySQL", 70);
            Subject subject3 = new Subject("PHP", 50);
            Subject subject4 = new Subject("MongoDB", 40);
            Subject subject5 = new Subject("C++", 60);

            List<Subject> subjectList = new ArrayList<Subject>();
            subjectList.add(subject1);
            subjectList.add(subject2);
            subjectList.add(subject3);
            subjectList.add(subject4);
            subjectList.add(subject5);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(subjectList);

            // Fields of Main Report
            JRBeanCollectionDataSource chartDataSource = new JRBeanCollectionDataSource(subjectList);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("studentName", "John");
            parameters.put("tableData", dataSource);

            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

            // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, chartDataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\REPOSITORIOS\\PERSONAL\\GITHUB\\JasperSoft-JasperStudio\\Exported-Reports\\CustomReport.pdf");

            JasperExportManager.exportReportToHtmlFile(jasperPrint, "C:\\REPOSITORIOS\\PERSONAL\\GITHUB\\JasperSoft-JasperStudio\\Exported-Reports\\CustomReport.html");

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream(new File("C:\\REPOSITORIOS\\PERSONAL\\GITHUB\\JasperSoft-JasperStudio\\Exported-Reports\\CustomReport.xlsx"))));
            exporter.exportReport();

            System.out.println("Report Created...");
        } catch (Exception exception){
            System.out.println("Exception while creating report");
            exception.printStackTrace();
        }
    }
}
