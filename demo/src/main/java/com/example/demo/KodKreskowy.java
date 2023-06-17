package com.example.demo;

import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class KodKreskowy {

    public static String Kod(String id_czesci,String nr_vin, String miejsce) {
        String path = null;
        try {

            String text = id_czesci + nr_vin + miejsce;
            path = "C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\KodyKreskowe\\"+ text +".jpg";

            Code128Writer writer = new Code128Writer();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.CODE_128, 500, 300);

            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
            return path;

        } catch(Exception e) {
            System.out.println("Error while creating barcode");
        }
        return path;
    }

}