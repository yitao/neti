package com.test;

import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.SchemaType;
import org.junit.Test;

import java.io.*;
import java.util.Date;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr.Enum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPImpl;

/**
 * Created by yitao on 2017/3/22.
 */
public class WordTest2 {
    String path = "d:/temp/header.docx";

    @Test
    public void testSimple() throws IOException {

        XWPFDocument doc = new XWPFDocument();
//        XWPFDocument doc = new XWPFDocument(new FileInputStream(path));
        writeHeader(doc);
        writeBody(doc);
        writeFooter(doc);
        OutputStream os = new FileOutputStream(new File(path));
        doc.write(os);
        os.close();
        doc.close();
    }

    public static void writeHeader(XWPFDocument doc) {
        XWPFHeaderFooterPolicy policy = doc.createHeaderFooterPolicy();
        XWPFHeader header = policy.getHeader(1);
        if(header!=null) {
            XWPFRun hrun = header.createParagraph().createRun();
            hrun.setColor("ff0000");
            hrun.setBold(true);
            hrun.setText("i am a header:" + new Date().getTime());
            String t = hrun.text();
            System.out.println(t);
        }
    }

    public static void writeBody(XWPFDocument doc) {
        XWPFParagraph body = doc.createParagraph();
        XWPFRun brun = body.createRun();
        brun.setColor("ff0000");
        brun.setBold(true);
        brun.setText("i am a body" + new Date().getTime());

        XWPFTable table = doc.createTable(4, 4);
        table.setWidth(400);
        for (XWPFTableRow row : table.getRows()) {
            row.setHeight(50);
            for (XWPFTableCell cell : row.getTableCells()) {
                cell.setParagraph(body);
            }
        }

    }

    public static void writeFooter(XWPFDocument doc) {
        XWPFHeaderFooterPolicy policy = doc.createHeaderFooterPolicy();
        XWPFHeader footer = policy.getHeader(1);
        if(footer!=null) {
            XWPFRun hrun = footer.createParagraph().createRun();
            hrun.setColor("ff0000");
            hrun.setBold(true);
            hrun.setText("i am a footer:" + new Date().getTime());
            String t = hrun.text();
            System.out.println(t);
        }
    }
}
