package org.example.pdf;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public class PdfTest {

    public static void main(String[] args) throws DocumentException, IOException {
        String path = "C:\\File\\项目资料\\pdf\\RIS\\49126841-ST108803-Annex 14.pdf";
        String s = Text2PdfUtil.readPDF(path);
        System.out.println(s);


    }
}
