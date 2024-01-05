package org.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 需要引入的依赖
 *
 * <dependency>
 *             <groupId>com.itextpdf</groupId>
 *             <artifactId>itext-asian</artifactId>
 *             <version>5.2.0</version>
 *         </dependency>
 *
 *         <dependency>
 *             <groupId>com.itextpdf</groupId>
 *             <artifactId>itextpdf</artifactId>
 *             <version>5.5.13</version>
 *         </dependency>
 *
 */
public class Text2PdfUtil {

    /**
     * txt文本文件  转pdf文件
     * @param text   F:/data/te616.txt
     * @param pdf  F:/data/aet618.pdf
     * @throws DocumentException
     * @throws IOException
     */
    public static void text2pdf(String text,String pdf) throws DocumentException, IOException {
        Document doc = new Document();
        OutputStream os = new FileOutputStream(new File(pdf));
        PdfWriter.getInstance(doc, os);
        doc.open();
        //指定 使用内置的中文字体
        BaseFont baseFont =
                BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        Font font = new Font(baseFont,12,Font.NORMAL);
        //指定输出编码为UTF-8
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(new File(text)), Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        String str = "";
        while((str = br.readLine()) != null){
            doc.add(new Paragraph(str,font));
        }
        isr.close();
        br.close();
        doc.close();
    }


    /**
     * 读取pdf文件的内容
     * @param filename  F:/data/aet618.pdf
     * @return  String
     */
    public static String readPDF(String filename){
        StringBuilder result = new StringBuilder();
        try {
            PdfReader reader = new PdfReader(filename);
            int countPage = reader.getNumberOfPages();
            for(int i=1;i<=countPage;i++){
                result.append(PdfTextExtractor.getTextFromPage(reader, i));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}


