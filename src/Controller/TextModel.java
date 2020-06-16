/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author shabeena
 */
public class TextModel {
    public double text_X;
    public double text_Y;
    public double text_size;
    public Font textFont;
    public String text_content;
    public TextAlignment align;
    public Text text;
    public Color color;
    
    public void setPos(double x, double y){
        text_X = x;
        text_Y = y;
    }
    public void setX(double x){
        text_X = x;
    }
    public void setY(double y){
        text_Y = y;
    }
    public void setSize(double size){
        text_size = size;
    }
    public void setFont(Font font){
        textFont = font;
    }
    public void setContent(String content){
        text_content = content;
    }
    public void setAlign(TextAlignment align){
        this.align = align;
    }
    public void setText(Text text){
        this.text = text;
    }
    public void setColor(Color color){
        this.color = color;
    }
}
