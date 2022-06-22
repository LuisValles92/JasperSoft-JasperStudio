package com.example.demo;

import com.example.pojo.Student;
import com.example.pojo.Subject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @GetMapping("/report")
    public ResponseEntity<byte[]> getReport() {

        try {
            String filePath = ResourceUtils.getFile("classpath:Student.jrxml").getAbsolutePath();

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
            parameters.put("subReport", getSubReport());
            parameters.put("subDataSource", getSubDataSource());
            parameters.put("subParameters", getSubParameters());

            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

            // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, chartDataSource);

            byte[] byteArray = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "CustomReport.pdf");

            System.out.println("Report Created...");
            return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);
        } catch (Exception exception){
            System.out.println("Exception while creating report");
            exception.printStackTrace();
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public static JasperReport getSubReport() {

        JasperReport jasperReport;
        try {
            String filePath = ResourceUtils.getFile("classpath:FirstReport.jrxml").getAbsolutePath();
            jasperReport = JasperCompileManager.compileReport(filePath);
            return jasperReport;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JRBeanCollectionDataSource getSubDataSource() {
        Student student1 = new Student(1L, "Raj", "Joshi", "Happy Street", "Delhi");
        Student student2 = new Student(2L, "Peter", "Smith", "Any Street", "Mumbai");

        List<Student> studentList = new ArrayList<Student>();
        studentList.add(student1);
        studentList.add(student2);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);
        return dataSource;
    }

    public static Map<String, Object> getSubParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("studentName", "Raj");
        return parameters;
    }

}
