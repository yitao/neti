package com.neti.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * word 文件帮助类
 * Created by yitao on 2017/3/22.
 */
public class WordHelper {

    public static XWPFDocument genXWPFDocument(){
        XWPFDocument document = new XWPFDocument();
        return document;
    }

    public static void writeXWPFDocument(XWPFDocument document,String filePath) throws IOException {
        OutputStream os = new FileOutputStream(new File(filePath));
        document.write(os);
        os.close();
        document.close();
    }



}
