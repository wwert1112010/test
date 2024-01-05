package org.example.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFTableExtractor {
    public static void main(String[] args) {
        try {
            String path = "C:\\File\\项目资料\\pdf\\RIS\\49126841-ST108803-Annex 14.pdf";

            // 加载PDF文件
            PDDocument document = PDDocument.load(new File(path));

            // 创建PDFTextStripper对象
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            // 提取PDF文本
            String pdfText = pdfTextStripper.getText(document);

            // 打印提取的文本
            System.out.println(pdfText);

            // 关闭PDF文档
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

