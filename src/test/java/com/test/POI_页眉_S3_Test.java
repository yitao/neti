package com.test;

/**
 * Created by yitao on 2017/3/22.
 */
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class POI_页眉_S3_Test {
    public static void main(String[] args) throws Exception {
        POI_页眉_S3_Test t=new POI_页眉_S3_Test();
        System.out.println("------------------------简单文字页脚-----------------");
        t.simpleFooter("d:/temp/sys_"+ System.currentTimeMillis() + ".docx");
        System.out.println("------------------------简单文字页眉页脚-----------------");
        XWPFDocument document = new XWPFDocument();
        t.simpleDateHeader(document);
        t.createSimpleTable(document);
        t.addNewPage(document, BreakType.PAGE);
        String str="测试测试测试测试测试文本测试测试测试测试测试文本测试\r\n测试测试测试测试文本测试测试测试测试测试文本测试";
        t.addSimpleParagraph(document, str, "宋体",11,"FF0000", true, false);
        t.addNewPage(document, BreakType.COLUMN);
        t.addSimpleParagraph(document, str,"微软雅黑",12, "00FF00", false, true);
        t.addNewPage(document, BreakType.TEXT_WRAPPING);
        t.addSimpleParagraph(document, str,"楷体",13, "0000FF", true, true);
        t.addBreakClear(document, BreakClear.ALL);
        t.addSimpleParagraph(document, str,"黑体",14,"000000", false, false);
        t.simpleNumberFooter(document);
        t.saveDocument(document, "d:/temp/sys_"+ System.currentTimeMillis() + ".docx");
    }

    /**
     * @Description: 文字页脚
     * @see: http://www.coderanch.com/t/525626/java/java/Adding-Header-Footer-Word-Document
     */
    public void simpleFooter(String savePath) throws Exception {
        XWPFDocument document = new XWPFDocument();
        CTP ctp = CTP.Factory.newInstance();
        CTR ctr = ctp.addNewR();
        CTText textt = ctr.addNewT();
        textt.setStringValue( "测试" );
        XWPFParagraph codePara = new XWPFParagraph( ctp, document );
        codePara.setAlignment(ParagraphAlignment.CENTER);
        codePara.setVerticalAlignment(TextAlignment.CENTER);
        XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
        newparagraphs[0] = codePara;
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new  XWPFHeaderFooterPolicy( document, sectPr );
        headerFooterPolicy.createFooter( STHdrFtr.DEFAULT, newparagraphs );
        headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs );
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }

    //页脚:显示页码信息
    public void simpleNumberFooter(XWPFDocument document) throws Exception {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph codePara = new XWPFParagraph(ctp, document);
        XWPFRun r1 = codePara.createRun();
        r1.setText("第");
        r1.setFontSize(11);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("宋体");
        fonts.setEastAsia("宋体");
        fonts.setHAnsi("宋体");

        r1 = codePara.createRun();
        CTFldChar fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        CTText ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("PAGE  \\* MERGEFORMAT");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("宋体");
        fonts.setEastAsia("宋体");
        fonts.setHAnsi("宋体");

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("页 总共");
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("宋体");
        fonts.setEastAsia("宋体");
        fonts.setHAnsi("宋体");

        r1 = codePara.createRun();
        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("宋体");
        fonts.setEastAsia("宋体");
        fonts.setHAnsi("宋体");

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("页");
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("宋体");
        fonts.setEastAsia("宋体");
        fonts.setHAnsi("宋体");

        codePara.setAlignment(ParagraphAlignment.CENTER);
        codePara.setVerticalAlignment(TextAlignment.CENTER);
        codePara.setBorderTop(Borders.THICK);
        XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
        newparagraphs[0] = codePara;
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
        headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
    }


    public void simpleDateHeader(XWPFDocument document) throws Exception {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph codePara = new XWPFParagraph(ctp, document);

        XWPFRun r1 = codePara.createRun();
        CTFldChar fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        CTText ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("TIME \\@ \"EEEE\"");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("年");
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        r1 = codePara.createRun();
        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("TIME \\@ \"O\"");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("月");
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        r1 = codePara.createRun();
        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("TIME \\@ \"A\"");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("日");
        r1.setFontSize(11);
        rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        codePara.setAlignment(ParagraphAlignment.CENTER);
        codePara.setVerticalAlignment(TextAlignment.CENTER);
        codePara.setBorderBottom(Borders.THICK);
        XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
        newparagraphs[0] = codePara;
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
        headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs);
    }

    public void addNewPage(XWPFDocument document,BreakType breakType){
        XWPFParagraph xp = document.createParagraph();
        xp.createRun().addBreak(breakType);
    }

    public void addBreakClear(XWPFDocument document,BreakClear breakClear){
        XWPFParagraph xp = document.createParagraph();
        xp.createRun().addBreak(breakClear);
    }

    //TODO 写的时候遇到过一次数组越界,测试几次都没法重现
    public void addSimpleParagraph(XWPFDocument document,String text,String fontName,int fontSize,String color,boolean isBold,boolean isItalic){
        XWPFParagraph xp = document.createParagraph();
        XWPFRun r1 = xp.createRun();
        r1.setText(text);
        r1.setFontSize(fontSize);
        r1.setBold(isBold);
        r1.setItalic(isItalic);
        r1.setColor(color);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(fontName);
        fonts.setEastAsia(fontName);
        fonts.setHAnsi(fontName);
        xp.setAlignment(ParagraphAlignment.CENTER);
        xp.setVerticalAlignment(TextAlignment.CENTER);
    }


    //注意: 代码采用的是先写数据再写表头
    public  void createSimpleTable(XWPFDocument doc) throws Exception {
        List<String> columnList = new ArrayList<String>();
        columnList.add("序号");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        XWPFTable table = doc.createTable(2,5);
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);

        XWPFTableRow firstRow=null;
        XWPFTableRow secondRow=null;
        XWPFTableCell firstCell=null;
        XWPFTableCell secondCell=null;

        for(int i=0;i<2;i++){
            firstRow=table.getRow(i);
            firstRow.setHeight(380);
            for(int j=0;j<5;j++){
                firstCell=firstRow.getCell(j);
                setCellText(firstCell, "测试", null, 1600);
            }
        }

        firstRow=table.insertNewTableRow(0);
        secondRow=table.insertNewTableRow(1);
        firstRow.setHeight(380);
        secondRow.setHeight(380);
        for(String str:columnList){
            if(str.indexOf("|") == -1){
                firstCell=firstRow.addNewTableCell();
                secondCell=secondRow.addNewTableCell();
                createVSpanCell(firstCell, str,"CCCCCC",1600,STMerge.RESTART);
                createVSpanCell(secondCell, "", "CCCCCC", 1600,null);
            }else{
                String[] strArr=str.split("\\|");
                firstCell=firstRow.addNewTableCell();
                createHSpanCell(firstCell, strArr[0],"CCCCCC",1600,STMerge.RESTART);
                for(int i=1;i<strArr.length-1;i++){
                    firstCell=firstRow.addNewTableCell();
                    createHSpanCell(firstCell, "","CCCCCC",1600,null);
                }
                for(int i=1;i<strArr.length;i++){
                    secondCell=secondRow.addNewTableCell();
                    setCellText(secondCell, strArr[i], "CCCCCC", 1600);
                }
            }
        }

    }

    public  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cell.setVerticalAlignment(XWPFVertAlign.CENTER);
        CTTcPr ctPr = cttc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }
    public void createHSpanCell(XWPFTableCell cell,String value, String bgcolor, int width,STMerge.Enum stMerge){
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cellPr.addNewHMerge().setVal(stMerge);
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
    }

    public void createVSpanCell(XWPFTableCell cell,String value, String bgcolor, int width,STMerge.Enum stMerge){
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cellPr.addNewVMerge().setVal(stMerge);
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
    }


    public void saveDocument(XWPFDocument document,String savePath) throws Exception{
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }


}