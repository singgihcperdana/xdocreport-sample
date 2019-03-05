/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singgihcp.xdocreport.sample;

import com.singgihcp.xdocreport.sample.model.Developer;
import com.singgihcp.xdocreport.sample.model.Project;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sgh88
 */
public class Simple {

    public static void main(String[] args) {
        try {
            InputStream in = new FileInputStream("in/simple.docx");
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
            FieldsMetadata metadata = report.createFieldsMetadata();

            //Create context Java model
            IContext context = report.createContext();

            context.put("judul", "Xdocreport sample");

            //register image
            FileImageProvider logo = new FileImageProvider(new File(Simple.class.getClassLoader().getResource("images/logo.png").getFile()));
            metadata.addFieldAsImage("logo");
            context.put("logo", logo);

            //Register java model
            Project project = new Project("XDocReport");
            context.put("project", project);

            //Register developers list
            List<Developer> developers = new ArrayList<Developer>();
            developers.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
            developers.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com"));
            metadata.load("developers", Developer.class, true);
            context.put("developers", developers);

            //Generate report
            OutputStream out = new FileOutputStream(new File("out/simple_out.docx"));
            report.process(context, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
