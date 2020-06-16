/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Params;
import Controller.StringAlignUtils;
import Controller.StringAlignUtils.Alignment;
import Controller.TextModel;
import Controller.mainController;
import static Controller.mainController.view;
//import java.awt.Font;
//import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import cloudsimsimulation.Resource;

public class MainView {

    public BorderPane main_pane = new BorderPane();
    public BorderPane gray_pane = new BorderPane();
    public static BorderPane right_pane = new BorderPane();
    public BorderPane left_pane = new BorderPane();

    Button scenario_btn_1 = new Button("Scenario 1");
    Button scenario_btn_2 = new Button("Scenario 2");
    Button scenario_btn_3 = new Button("Scenario 3");
    Button setting_btn = new Button("Config");
    
    private Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
    

    public void initView() {
        initButtonGroup();
        initRightArea();
        initLeftArea();
        main_pane.setCenter(right_pane);
        main_pane.setLeft(left_pane);
        mainController.view = this;
    }

    private void initRightArea() {
        right_pane.setStyle("-fx-background-color: linear-gradient( #0f60c7,  #0f60c7);");
    }

    private void initLeftArea() {
        VBox btnBox = new VBox();
        btnBox.setSpacing(15);
        btnBox.setAlignment(Pos.TOP_CENTER);
        btnBox.getChildren().addAll(scenario_btn_1, scenario_btn_2, scenario_btn_3, setting_btn);
        GroupBox scenario_Group = new GroupBox("Scenario", btnBox, Resource.SIDE_AREA_WIDTH - 20, 30);
        //init text area
        left_pane.setStyle("-fx-background-color: linear-gradient( #0f60c7,  #0f60c7);");
        left_pane.setPrefWidth(Resource.SIDE_AREA_WIDTH);
        BorderPane.setMargin(scenario_Group, new Insets(30, 10, 10, 10));
        left_pane.setCenter(scenario_Group);
    }

    private void initButtonGroup() {
        scenario_btn_1.setId("mainbtn");
        scenario_btn_1.setPrefWidth(Resource.SIDE_AREA_WIDTH - 40);
        scenario_btn_2.setId("mainbtn");
        scenario_btn_2.setPrefWidth(Resource.SIDE_AREA_WIDTH - 40);
        scenario_btn_3.setId("mainbtn");
        scenario_btn_3.setPrefWidth(Resource.SIDE_AREA_WIDTH - 40);
        setting_btn.setId("mainbtn");
        setting_btn.setPrefWidth(Resource.SIDE_AREA_WIDTH - 40);
        
        scenario_btn_1.setOnAction((evt) -> {
            SettingView1 view_1 = new SettingView1();
            view_1.initView();
            view_1.show();
            if(SettingView1.task_numbers == 0){
                return;
            }
            ScenarioResultView1 rst_view_1 = new ScenarioResultView1();
            rst_view_1.initView();
            rst_view_1.loadContent();
            right_pane.setCenter(null);
            right_pane.setCenter(rst_view_1.scenario_pane);
        });
        scenario_btn_2.setOnAction((evt) -> {
            SettingView2 view_2 = new SettingView2();
            view_2.initView();
            view_2.show();
            if(SettingView2.datacenter_numbers == 0){
                return;
            }
            if(SettingView2.task_numbers == 0){
                return;
            }
            ScenarioResultView2 rst_view_2 = new ScenarioResultView2();
            rst_view_2.initView();
            rst_view_2.loadContent();
            right_pane.setCenter(null);
            right_pane.setCenter(rst_view_2.scenario_pane);
        });
        scenario_btn_3.setOnAction((evt) -> {
            SettingView3 view_3 = new SettingView3();
            view_3.initView();
            view_3.show();
            if(SettingView3.datacenter_numbers == 0){
                return;
            }
            if(SettingView3.task_numbers == 0){
                return;
            }
            ScenarioResultView3 rst_view_3 = new ScenarioResultView3();
            rst_view_3.initView();
            rst_view_3.loadContent();
            right_pane.setCenter(null);
            right_pane.setCenter(rst_view_3.scenario_pane);
        });
        
        setting_btn.setOnAction((evt) -> {
            ConfigView view = new ConfigView();
            view.initView();
            view.show();
        });
    }
}
