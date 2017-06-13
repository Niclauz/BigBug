package wang.xvip.bigbug.utils;


import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/27 - 16:26.
 */

public class DimenConverter {

    private static final String directPath = "F:/dimens/";
    private static final String referenceDimensFileName = "reference_dimens.xml";
    private static final String dimensFileName_m = "dimens_m.xml";
    private static final String dimensFileName_h = "dimens_h.xml";
    private static final String dimensFileName_x = "dimens_x.xml";
    private static final String dimensFileName_xx = "dimens_xx.xml";
    private static final String dimensFileName_xx_360 = "dimens_xx_360.xml";
    private static final String dimensFileName_xxx = "dimens_xxx.xml";

    public static void main(String[] args) {
        //xhdpi 320  use as base 720*1080
        generateBaseDimens(referenceDimensFileName, 1.0);
        //mhdpi 160
        generateBaseDimens(dimensFileName_m, 320 / 160.0);
        //hdpi 240
        generateBaseDimens(dimensFileName_h, 320 / 240.0);
        //xxhdpi 360
        generateBaseDimens(dimensFileName_xx_360, 320 / 360.0);
        //xxhdpi 480
        generateBaseDimens(dimensFileName_xx, 320 / 480.0);
        //xxxhdpi 640
        generateBaseDimens(dimensFileName_xxx, 320 / 640.0);
        System.out.println("success!");
    }

    public static void generateBaseDimens(String fileName, double ratio) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("utf-8");

        Element resourcesElement = document.addElement("resources");
        Element dimenElement;
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 1; i < 1000; i++) {
            dimenElement = resourcesElement.addElement("dimen");
            dimenElement.addAttribute("name", "base" + i + "dp");
            double convertedDimen = i / ratio;
            dimenElement.setText(df.format(convertedDimen) + "dp");
        }
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(new File(directPath, fileName)));
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
