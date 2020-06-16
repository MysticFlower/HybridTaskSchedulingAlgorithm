/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Font;

/**
 *
 * @author shabeena
 */
public class Params {

    public static double image_width;
    public static double image_height;
    public static String out_path;
    
    public static double logo_X;
    public static double logo_Y;
    public static double logo_ratio = 1;
    public static double logo_width = 100;
    public static double logo_height = 100;
    
//    public static double text_X;
//    public static double text_Y;
////    public static double text_size;
////    public static Font textFont;
//    public static String text_content;
    public static List<TextModel> text_list = new ArrayList<TextModel>();
    
    
    public static boolean img_load_flag = false;
    public static boolean logo_load_flag = false;
    public static boolean draw_text_flag = false;

    public static void initParams() {
        try {
            File config_setting = new File("Config/setup.dat");
            if (!config_setting.exists()) {
                image_width = 640;
                image_height = 640;
                out_path = "C:\\Result\\";
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(config_setting));
            String img_width_line = reader.readLine();
            image_width = Double.parseDouble(img_width_line);
            String img_height_line = reader.readLine();
            image_height = Double.parseDouble(img_height_line);
            String outpath_line = reader.readLine();
            out_path = outpath_line;
            reader.close();
            if(image_width == 0) image_width = 640;
            if(image_height == 0) image_height = 640;
            if(out_path == null || out_path.isEmpty()) out_path = "C:\\Result\\";
        } catch (Exception e) {
            image_width = 640;
            image_height = 640;
            out_path = "C:\\Result\\";
        }
    }
}
