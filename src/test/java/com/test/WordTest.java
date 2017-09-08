package com.test;

/*
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Ignore;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.math.BigInteger;
import java.util.List;
*/

/**
 * Created by yitao on 2017/3/22.
 */
public class WordTest {
    /*

    @Test
    public void testBetterHeaderFooterExample() throws Exception {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p = doc.createParagraph();

        XWPFRun r = p.createRun();
        r.setText("Some Text");
        r.setBold(true);
        r = p.createRun();
        r.setText("Goodbye");
        // create header/footer functions insert an empty paragraph

        XWPFHeader head = doc.createHeader(HeaderFooterType.DEFAULT);
        head.createParagraph().createRun().setText("header");

        XWPFFooter foot = doc.createFooter(HeaderFooterType.DEFAULT);
        foot.createParagraph().createRun().setText("footer");


        OutputStream os = new FileOutputStream(new File("d:/temp/header2.docx"));
        doc.write(os);
        os.close();
        doc.close();
    }

    @Test
    public void testHeaderFooterTable() throws Exception {
        XWPFDocument doc = new XWPFDocument();

        // Create a header with a 1 row, 3 column table
        // changes made for issue 57366 allow a new header or footer
        // to be created empty. This is a change. You will have to add
        // either a paragraph or a table to the header or footer for
        // the document to be considered valid.
        XWPFHeader hdr = doc.createHeader(HeaderFooterType.DEFAULT);
        XWPFTable tbl = hdr.createTable(1, 3);

        // Set the padding around text in the cells to 1/10th of an inch
        int pad = (int) (.1 * 1440);
        tbl.setCellMargins(pad, pad, pad, pad);

        // Set table width to 6.5 inches in 1440ths of a point
        tbl.setWidth((int) (6.5 * 1440));
        // Can not yet set table or cell width properly, tables default to
        // autofit layout, and this requires fixed layout
        CTTbl ctTbl = tbl.getCTTbl();
        CTTblPr ctTblPr = ctTbl.addNewTblPr();
//        CTTblLayoutType layoutType = ctTblPr.addNewTblLayout();
//        layoutType.setType(STTblLayoutType.FIXED);

        // Now set up a grid for the table, cells will fit into the grid
        // Each cell width is 3120 in 1440ths of an inch, or 1/3rd of 6.5"
        BigInteger w = new BigInteger("3120");
        CTTblGrid grid = ctTbl.addNewTblGrid();
        for (int i = 0; i < 3; i++) {
            CTTblGridCol gridCol = grid.addNewGridCol();
            gridCol.setW(w);
        }

        // Add paragraphs to the cells
        XWPFTableRow row = tbl.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph p = cell.getParagraphArray(0);
        XWPFRun r = p.createRun();
        r.setText("header left cell");

        cell = row.getCell(1);
        p = cell.getParagraphArray(0);
        r = p.createRun();
        r.setText("header center cell");

        cell = row.getCell(2);
        p = cell.getParagraphArray(0);
        r = p.createRun();
        r.setText("header right cell");

        // Create a footer with a Paragraph
        XWPFFooter ftr = doc.createFooter(HeaderFooterType.DEFAULT);
        p = ftr.createParagraph();

        r = p.createRun();
        r.setText("footer text");

        OutputStream os = new FileOutputStream(new File("d:/temp/header3.docx"));
        doc.write(os);
        doc.close();
    }

    @Test
    public void testSimpleDocument() throws Exception {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setBorderBottom(Borders.DOUBLE);
        p1.setBorderTop(Borders.DOUBLE);

        p1.setBorderRight(Borders.DOUBLE);
        p1.setBorderLeft(Borders.DOUBLE);
        p1.setBorderBetween(Borders.SINGLE);

        p1.setVerticalAlignment(TextAlignment.TOP);

        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setText("The quick brown fox");
        r1.setBold(true);
        r1.setFontFamily("Courier");
        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        r1.setTextPosition(100);

        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.RIGHT);

        //BORDERS
        p2.setBorderBottom(Borders.DOUBLE);
        p2.setBorderTop(Borders.DOUBLE);
        p2.setBorderRight(Borders.DOUBLE);
        p2.setBorderLeft(Borders.DOUBLE);
        p2.setBorderBetween(Borders.SINGLE);

        XWPFRun r2 = p2.createRun();
        r2.setText("jumped over the lazy dog");
        r2.setStrikeThrough(true);
        r2.setFontSize(20);

        XWPFRun r3 = p2.createRun();
        r3.setText("and went away");
        r3.setStrikeThrough(true);
        r3.setFontSize(20);
        r3.setSubscript(VerticalAlign.SUPERSCRIPT);


        XWPFParagraph p3 = doc.createParagraph();
        p3.setWordWrapped(true);
        p3.setPageBreak(true);

        //p3.setAlignment(ParagraphAlignment.DISTRIBUTE);
        p3.setAlignment(ParagraphAlignment.BOTH);
        p3.setSpacingBetween(15, LineSpacingRule.EXACT);

        p3.setIndentationFirstLine(600);


        XWPFRun r4 = p3.createRun();
        r4.setTextPosition(20);
        r4.setText("To be, or not to be: that is the question: "
                + "Whether 'tis nobler in the mind to suffer "
                + "The slings and arrows of outrageous fortune, "
                + "Or to take arms against a sea of troubles, "
                + "And by opposing end them? To die: to sleep; ");
        r4.addBreak(BreakType.PAGE);
        r4.setText("No more; and by a sleep to say we end "
                + "The heart-ache and the thousand natural shocks "
                + "That flesh is heir to, 'tis a consummation "
                + "Devoutly to be wish'd. To die, to sleep; "
                + "To sleep: perchance to dream: ay, there's the rub; "
                + ".......");
        r4.setItalic(true);
        //This would imply that this break shall be treated as a simple line break, and break the line after that word:

        XWPFRun r5 = p3.createRun();
        r5.setTextPosition(-10);
        r5.setText("For in that sleep of death what dreams may come");
        r5.addCarriageReturn();
        r5.setText("When we have shuffled off this mortal coil,"
                + "Must give us pause: there's the respect"
                + "That makes calamity of so long life;");
        r5.addBreak();
        r5.setText("For who would bear the whips and scorns of time,"
                + "The oppressor's wrong, the proud man's contumely,");

        r5.addBreak(BreakClear.ALL);
        r5.setText("The pangs of despised love, the law's delay,"
                + "The insolence of office and the spurns" + ".......");

        FileOutputStream out = new FileOutputStream("d:/temp/header4.docx");
        doc.write(out);
        out.close();
        doc.close();
    }

    private static XWPFParagraph[] pars;

    @Test
    public void testSimpleDocumentWithHeader() {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p = doc.createParagraph();

        XWPFRun r = p.createRun();
        r.setText("Some Text");
        r.setBold(true);
        r = p.createRun();
        r.setText("Goodbye");

        CTP ctP = CTP.Factory.newInstance();
        CTText t = ctP.addNewR().addNewT();
        t.setStringValue("header");
        pars = new XWPFParagraph[1];
        p = new XWPFParagraph(ctP, doc);
        pars[0] = p;

        XWPFHeaderFooterPolicy hfPolicy = doc.createHeaderFooterPolicy();
        hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

        ctP = CTP.Factory.newInstance();
        t = ctP.addNewR().addNewT();
        t.setStringValue("My Footer");
        pars[0] = new XWPFParagraph(ctP, doc);
        hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, pars);

        try {
            OutputStream os = new FileOutputStream(new File("d:/temp/header5.docx"));
            doc.write(os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testSimpleImages() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();

        XWPFRun r = p.createRun();
        String[] args = new String[]{"d:/temp/1.jpg"};
        for (String imgFile : args) {
            int format;

            if (imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
            else if (imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
            else if (imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
            else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
            else if (imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
            else if (imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
            else if (imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
            else if (imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
            else if (imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
            else if (imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
            else if (imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
            else {
                System.err.println("Unsupported picture: " + imgFile +
                        ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
                continue;
            }

            r.setText(imgFile);
            r.addBreak();
            r.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
            r.addBreak(BreakType.PAGE);
        }

        FileOutputStream out = new FileOutputStream("d:/temp/images.docx");
        doc.write(out);
        out.close();
        doc.close();
    }

    @Test
    public void testSimpleTable() throws Exception {
        try {
            createSimpleTable();
        } catch (Exception e) {
            System.out.println("Error trying to create simple table.");
            throw (e);
        }
        try {
            createStyledTable();
        } catch (Exception e) {
            System.out.println("Error trying to create styled table.");
            throw (e);
        }
    }



    @Test
    public void testUpdateEmbeddedDoc() throws Exception {
        String[] args = new String[]{"d:/temp/embedded.docx"};
        UpdateEmbeddedDoc ued = new UpdateEmbeddedDoc(args[0]);
        ued.updateEmbeddedDoc();
        ued.checkUpdatedDoc();
    }

    class UpdateEmbeddedDoc {
        private XWPFDocument doc = null;
        private File docFile = null;
        private static final int SHEET_NUM = 0;
        private static final int ROW_NUM = 0;
        private static final int CELL_NUM = 0;
        private static final double NEW_VALUE = 100.98D;
        private static final String BINARY_EXTENSION = "xls";
        private static final String OPENXML_EXTENSION = "xlsx";
        *//**
         * Create a new instance of the UpdateEmbeddedDoc class using the following
         * parameters;
         *
         * @param filename An instance of the String class that encapsulates the name
         *                 of and path to a WordprocessingML Word document that contains an
         *                 embedded binary Excel workbook.
         * @throws java.io.FileNotFoundException Thrown if the file cannot be found
         *                                       on the underlying file system.
         * @throws java.io.IOException           Thrown if a problem occurs in the underlying
         *                                       file system.
         *//*
        public UpdateEmbeddedDoc(String filename) throws FileNotFoundException, IOException {
            this.docFile = new File(filename);
            FileInputStream fis = null;
            if (!this.docFile.exists()) {
                throw new FileNotFoundException("The Word dcoument " + filename + " does not exist.");
            }
            try {
                // Open the Word document file and instantiate the XWPFDocument
                // class.
                fis = new FileInputStream(this.docFile);
                this.doc = new XWPFDocument(fis);
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }

        *//**
         * Called to update the embedded Excel workbook. As the format and structire
         * of the workbook are known in advance, all this code attempts to do is
         * write a new value into the first cell on the first row of the first
         * worksheet. Prior to executing this method, that cell will contain the
         * value 1.
         *
         * @throws org.apache.poi.openxml4j.exceptions.OpenXML4JException Rather
         *                                                                than use the specific classes (HSSF/XSSF) to handle the embedded
         *                                                                workbook this method uses those defeined in the SS stream. As
         *                                                                a result, it might be the case that a SpreadsheetML file is
         *                                                                opened for processing, throwing this exception if that file is
         *                                                                invalid.
         * @throws java.io.IOException                                    Thrown if a problem occurs in the underlying
         *                                                                file system.
         *//*
        public void updateEmbeddedDoc() throws OpenXML4JException, IOException {
            List<PackagePart> embeddedDocs = this.doc.getAllEmbedds();
            for (PackagePart pPart : embeddedDocs) {
                String ext = pPart.getPartName().getExtension();
                if (BINARY_EXTENSION.equals(ext) || OPENXML_EXTENSION.equals(ext)) {
                    // Get an InputStream from the package part and pass that
                    // to the create method of the WorkbookFactory class. Update
                    // the resulting Workbook and then stream that out again
                    // using an OutputStream obtained from the same PackagePart.
                    InputStream is = pPart.getInputStream();
                    Workbook workbook = null;
                    OutputStream os = null;
                    try {
                        workbook = WorkbookFactory.create(is);
                        Sheet sheet = workbook.getSheetAt(SHEET_NUM);
                        Row row = sheet.getRow(ROW_NUM);
                        Cell cell = row.getCell(CELL_NUM);
                        cell.setCellValue(NEW_VALUE);
                        os = pPart.getOutputStream();
                        workbook.write(os);
                    } finally {
                        IOUtils.closeQuietly(os);
                        IOUtils.closeQuietly(workbook);
                        IOUtils.closeQuietly(is);
                    }
                }
            }

            if (!embeddedDocs.isEmpty()) {
                // Finally, write the newly modified Word document out to file.
                FileOutputStream fos = new FileOutputStream(this.docFile);
                this.doc.write(fos);
                fos.close();
            }
        }

        *//**
         * Called to test whether or not the embedded workbook was correctly
         * updated. This method simply recovers the first cell from the first row
         * of the first workbook and tests the value it contains.
         * <p/>
         * Note that execution will not continue up to the assertion as the
         * embedded workbook is now corrupted and causes an IllegalArgumentException
         * with the following message
         * <p/>
         * <em>java.lang.IllegalArgumentException: Your InputStream was neither an
         * OLE2 stream, nor an OOXML stream</em>
         * <p/>
         * to be thrown when the WorkbookFactory.createWorkbook(InputStream) method
         * is executed.
         *
         * @throws org.apache.poi.openxml4j.exceptions.OpenXML4JException Rather
         *                                                                than use the specific classes (HSSF/XSSF) to handle the embedded
         *                                                                workbook this method uses those defeined in the SS stream. As
         *                                                                a result, it might be the case that a SpreadsheetML file is
         *                                                                opened for processing, throwing this exception if that file is
         *                                                                invalid.
         * @throws java.io.IOException                                    Thrown if a problem occurs in the underlying
         *                                                                file system.
         *//*
        public void checkUpdatedDoc() throws OpenXML4JException, IOException {
            for (PackagePart pPart : this.doc.getAllEmbedds()) {
                String ext = pPart.getPartName().getExtension();
                if (BINARY_EXTENSION.equals(ext) || OPENXML_EXTENSION.equals(ext)) {
                    InputStream is = pPart.getInputStream();
                    Workbook workbook = null;
                    try {
                        workbook = WorkbookFactory.create(is);
                        Sheet sheet = workbook.getSheetAt(SHEET_NUM);
                        Row row = sheet.getRow(ROW_NUM);
                        Cell cell = row.getCell(CELL_NUM);
                        assertEquals(cell.getNumericCellValue(), NEW_VALUE, 0.0001);
                    } finally {
                        IOUtils.closeQuietly(workbook);
                        IOUtils.closeQuietly(is);
                    }
                }
            }
        }

    }

    public static void createSimpleTable() throws Exception {
        XWPFDocument doc = new XWPFDocument();

        try {
            XWPFTable table = doc.createTable(3, 3);

            table.getRow(1).getCell(1).setText("EXAMPLE OF TABLE");

            // table cells have a list of paragraphs; there is an initial
            // paragraph created when the cell is created. If you create a
            // paragraph in the document to put in the cell, it will also
            // appear in the document following the table, which is probably
            // not the desired result.
            XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);

            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setText("The quick brown fox");
            r1.setItalic(true);
            r1.setFontFamily("Courier");
            r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
            r1.setTextPosition(100);

            table.getRow(2).getCell(2).setText("only text");

            OutputStream out = new FileOutputStream("d:/temp/simpleTable.docx");
            try {
                doc.write(out);
            } finally {
                out.close();
            }
        } finally {
            doc.close();
        }
    }

    *//**
     * Create a table with some row and column styling. I "manually" add the
     * style name to the table, but don't check to see if the style actually
     * exists in the document. Since I'm creating it from scratch, it obviously
     * won't exist. When opened in MS Word, the table style becomes "Normal".
     * I manually set alternating row colors. This could be done using Themes,
     * but that's left as an exercise for the reader. The cells in the last
     * column of the table have 10pt. "Courier" font.
     * I make no claims that this is the "right" way to do it, but it worked
     * for me. Given the scarcity of XWPF examples, I thought this may prove
     * instructive and give you ideas for your own solutions.
     *
     * @throws Exception
     *//*
    public static void createStyledTable() throws Exception {
        //Create a new document from scratch
        XWPFDocument doc = new XWPFDocument();
        try {
            // -- OR --
            // open an existing empty document with styles already defined
            //XWPFDocument doc = new XWPFDocument(new FileInputStream("base_document.docx"));

            // Create a new table with 6 rows and 3 columns
            int nRows = 6;
            int nCols = 3;
            XWPFTable table = doc.createTable(nRows, nCols);

            // Set the table style. If the style is not defined, the table style
            // will become "Normal".
            CTTblPr tblPr = table.getCTTbl().getTblPr();
            CTString styleStr = tblPr.addNewTblStyle();
            styleStr.setVal("StyledTable");

            // Get a list of the rows in the table
            List<XWPFTableRow> rows = table.getRows();
            int rowCt = 0;
            int colCt = 0;
            for (XWPFTableRow row : rows) {
                // get table row properties (trPr)
                CTTrPr trPr = row.getCtRow().addNewTrPr();
                // set row height; units = twentieth of a point, 360 = 0.25"
                CTHeight ht = trPr.addNewTrHeight();
                ht.setVal(BigInteger.valueOf(360));

                // get the cells in this row
                List<XWPFTableCell> cells = row.getTableCells();
                // add content to each cell
                for (XWPFTableCell cell : cells) {
                    // get a table cell properties element (tcPr)
                    CTTcPr tcpr = cell.getCTTc().addNewTcPr();
                    // set vertical alignment to "center"
                    CTVerticalJc va = tcpr.addNewVAlign();
                    va.setVal(STVerticalJc.CENTER);

                    // create cell color element
                    CTShd ctshd = tcpr.addNewShd();
                    ctshd.setColor("auto");
                    ctshd.setVal(STShd.CLEAR);
                    if (rowCt == 0) {
                        // header row
                        ctshd.setFill("A7BFDE");
                    } else if (rowCt % 2 == 0) {
                        // even row
                        ctshd.setFill("D3DFEE");
                    } else {
                        // odd row
                        ctshd.setFill("EDF2F8");
                    }

                    // get 1st paragraph in cell's paragraph list
                    XWPFParagraph para = cell.getParagraphs().get(0);
                    // create a run to contain the content
                    XWPFRun rh = para.createRun();
                    // style cell as desired
                    if (colCt == nCols - 1) {
                        // last column is 10pt Courier
                        rh.setFontSize(10);
                        rh.setFontFamily("Courier");
                    }
                    if (rowCt == 0) {
                        // header row
                        rh.setText("header row, col " + colCt);
                        rh.setBold(true);
                        para.setAlignment(ParagraphAlignment.CENTER);
                    } else {
                        // other rows
                        rh.setText("row " + rowCt + ", col " + colCt);
                        para.setAlignment(ParagraphAlignment.LEFT);
                    }
                    colCt++;
                } // for cell
                colCt = 0;
                rowCt++;
            } // for row

            // write the file
            OutputStream out = new FileOutputStream("styledTable.docx");
            try {
                doc.write(out);
            } finally {
                out.close();
            }
        } finally {
            doc.close();
        }
    }*/
}
