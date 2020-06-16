/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static View.SettingView3.wait_limit;
import cloudsim.Algorithm2;
import cloudsimsimulation.CloudSimSimulation;
import cloudsimsimulation.Resource;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author shabeena
 */
public class SettingView2 {
    BorderPane gray_pane = new BorderPane();
    BorderPane main_pane = new BorderPane();

    Stage setting_stage;

    public Label job_label = new Label("Job Size: ");
    public TextField job_field = new TextField();
    
    public Label datacenter_label = new Label("Data Centers: ");
    public TextField datacenter_field = new TextField();
    
    public Label task_label = new Label("Task Number: ");
    public TextField task_field = new TextField();
    
   //public Label wait_label = new Label("Wait Limit: ");
    //public TextField wait_field = new TextField();
    
    private Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

    Button start_btn = new Button("Start");
    
    public static int task_numbers = 0;
    public static int datacenter_numbers = 0;
    //public static double wait_limit = 20000;
    
    public void initView() {
        job_label.setId("roundLabel");
        job_field.setId("round");
        datacenter_label.setId("roundLabel");
        datacenter_field.setId("round");
        task_label.setId("roundLabel");
        task_field.setId("round");
        //wait_label.setId("roundLabel");
        //wait_field.setId("round");
        
        job_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    job_field.setText(oldValue);
                }
            }
        });
        
        task_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    task_field.setText(oldValue);
                }
            }
        });
        datacenter_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    datacenter_field.setText(oldValue);
                }
            }
        });
       // wait_field.textProperty().addListener((observableValue, oldValue, newValue) -> {
           // if (newValue != null) {
                //if (!validEditingState.matcher(newValue).matches()) {
                   // wait_field.setText(oldValue);
               // }
            //}
        //});
        job_field.setText("" + Algorithm2.total_size);
        task_field.setText("" + task_numbers);
        datacenter_field.setText("" + datacenter_numbers);
        //wait_field.setText("" + wait_limit);
        initButtonGroup();

        //init grid pane
        GridPane main_pane1 = new GridPane();
        main_pane1.setId("main_area");
        main_pane1.setPadding(new Insets(15, 0, 15, 0));
        main_pane1.setHgap(15);
        main_pane1.setVgap(7);
        main_pane1.setAlignment(Pos.CENTER);

        main_pane1.add(job_label, 0, 0, 1, 1);
        main_pane1.add(job_field, 1, 0, 1, 1);
        
        main_pane1.add(datacenter_label, 0, 1, 1, 1);
        main_pane1.add(datacenter_field, 1, 1, 1, 1);
        main_pane1.add(task_label, 0, 2, 1, 1);
        main_pane1.add(task_field, 1, 2, 1, 1);
        //main_pane1.add(wait_label, 0, 3, 1, 1);
        //main_pane1.add(wait_field, 1, 3, 1, 1);

        GroupBox mainBox = new GroupBox("Scenario 2", main_pane1, Resource.WINDOW_WIDTH - 60, 15);
        gray_pane.setId("main_area");

        BorderPane.setMargin(mainBox, new Insets(30, 20, 20, 20));
        BorderPane.setAlignment(main_pane1, Pos.TOP_CENTER);
        gray_pane.setCenter(mainBox);

        HBox btnBox = new HBox();
        btnBox.setSpacing(15);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(start_btn);
        GroupBox button_Group = new GroupBox("Select Required Option", btnBox, Resource.WINDOW_WIDTH - 60, 15);

        BorderPane.setAlignment(button_Group, Pos.CENTER);
        BorderPane.setMargin(button_Group, new Insets(10, 20, 20, 20));
        gray_pane.setBottom(button_Group);

        main_pane.setId("black_area");
        BorderPane.setMargin(gray_pane, new Insets(15, 20, 20, 20));
        main_pane.setCenter(gray_pane);

        setting_stage = new Stage();
        Scene scene = new Scene(main_pane);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        setting_stage.setTitle("Senario 2");
        setting_stage.setScene(scene);
        setting_stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("View/icon.png")));
        setting_stage.setResizable(false);

        setting_stage.initOwner(CloudSimSimulation.MainStage);
        setting_stage.initModality(Modality.WINDOW_MODAL);

    }

    public void show() {
        setting_stage.showAndWait();
    }

    private void initButtonGroup() {
        start_btn.setId("smallbtn");
        start_btn.setPrefWidth(170);
      
        start_btn.setOnAction((evt) -> {
            String job_size_str = job_field.getText();
            String task_num_str = task_field.getText();
            String datacenter_num_str = datacenter_field.getText();
            //String wait_num_str = wait_field.getText();
            if(job_size_str == null || job_size_str.isEmpty()){
                Algorithm2.total_size = 0;
            }else{
                Algorithm2.total_size = Long.valueOf(job_size_str);
            }
            if(datacenter_num_str == null || datacenter_num_str.isEmpty()){
                datacenter_numbers = 0;
            }else{
                datacenter_numbers = Integer.valueOf(datacenter_num_str);
            }
            if(task_num_str == null || task_num_str.isEmpty()){
                task_numbers = 0;
            }else{
                task_numbers = Integer.valueOf(task_num_str);
            }
            //if(wait_num_str == null || wait_num_str.isEmpty()){
                //wait_limit = 0;
           // }else{
               // wait_limit = Double.valueOf(wait_num_str);
           // }
            setting_stage.close();
        });
    }
}
