/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Params;
import Controller.mainController;
import cloudsim.ConfigParams;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import cloudsimsimulation.CloudSimSimulation;
import cloudsimsimulation.Resource;
import java.util.regex.Pattern;
import javafx.geometry.Orientation;

public class ConfigView {

    BorderPane gray_pane = new BorderPane();
    BorderPane main_pane = new BorderPane();

    Stage setting_stage;
    public Label vm_size_label = new Label("VM Size: ");
    public Label vm_ram_label = new Label("VM RAM: ");
    public Label vm_mip_label = new Label("VM MIP: ");
    public Label vm_bw_label = new Label("VM BandWidth: ");
    public Label vm_pes_label = new Label("VM CPU: ");
    public Label vm_name_label = new Label("VM Name: ");
    
    public TextField vm_size = new TextField();
    public TextField vm_ram = new TextField();
    public TextField vm_mip = new TextField();
    public TextField vm_bw = new TextField();
    public TextField vm_pes = new TextField();
    public TextField vm_name = new TextField();
    
    public Label arch_label = new Label("Architecture: ");
    public Label os_label = new Label("Host OS: ");
    public Label name_label = new Label("Host Name: ");
    public Label timezone_label = new Label("Host Timezone: ");
    public Label proceesing_cost_label = new Label("Processing Cost: ");
    public Label memory_cost_label = new Label("memory Cost: ");
    public Label storage_cost_label = new Label("Storage Cost: ");
    public Label bandwidth_cost_label = new Label("BandwWidth Cost: ");
    
    public TextField arch = new TextField();
    public TextField os = new TextField();
    public TextField name = new TextField();
    public TextField timezone = new TextField();
    public TextField proceesing_cost = new TextField();
    public TextField memory_cost = new TextField();
    public TextField storage_cost = new TextField();
    public TextField bandwidth_cost = new TextField();
    
   
    Button save_btn = new Button("Save");
    Button cancel_btn = new Button("Cancel");
    
    private Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

    public void initView() {
        vm_size_label.setId("roundLabel");
        vm_ram_label.setId("roundLabel");
        vm_mip_label.setId("roundLabel");
        vm_bw_label.setId("roundLabel");
        vm_pes_label.setId("roundLabel");
        vm_name_label.setId("roundLabel");
        
        vm_size.setId("round");
        vm_ram.setId("round");
        vm_mip.setId("round");
        vm_bw.setId("round");
        vm_pes.setId("round");
        vm_name.setId("round");
        
        arch_label.setId("roundLabel");
        os_label.setId("roundLabel");
        name_label.setId("roundLabel");
        timezone_label.setId("roundLabel");
        proceesing_cost_label.setId("roundLabel");
        memory_cost_label.setId("roundLabel");
        storage_cost_label.setId("roundLabel");
        bandwidth_cost_label.setId("roundLabel");
        
        arch.setId("round");
        os.setId("round");
        name.setId("round");
        timezone.setId("round");
        proceesing_cost.setId("round");
        memory_cost.setId("round");
        storage_cost.setId("round");
        bandwidth_cost.setId("round");
        
        timezone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (!validEditingState.matcher(newValue).matches()) {
                    timezone.setText(oldValue);
                }
            }
        });
        proceesing_cost.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (!validEditingState.matcher(newValue).matches()) {
                    proceesing_cost.setText(oldValue);
                }
            }
        });
        memory_cost.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (!validEditingState.matcher(newValue).matches()) {
                    memory_cost.setText(oldValue);
                }
            }
        });
        storage_cost.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (!validEditingState.matcher(newValue).matches()) {
                    storage_cost.setText(oldValue);
                }
            }
        });
        bandwidth_cost.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (!validEditingState.matcher(newValue).matches()) {
                    bandwidth_cost.setText(oldValue);
                }
            }
        });
       
        vm_size.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    vm_size.setText(oldValue);
                }
            }
        });
        vm_ram.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    vm_ram.setText(oldValue);
                }
            }
        });
        vm_mip.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    vm_mip.setText(oldValue);
                }
            }
        });
        vm_bw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    vm_bw.setText(oldValue);
                }
            }
        });
        vm_pes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.matches("\\d*")) {
                } else {
                    vm_pes.setText(oldValue);
                }
            }
        });
        initButtonGroup();

        Separator sep1 = new Separator();
        sep1.setOrientation(Orientation.VERTICAL);
        //init grid pane
        GridPane main_pane1 = new GridPane();
        main_pane1.setId("main_area");
        main_pane1.setPadding(new Insets(15, 0, 15, 0));
        main_pane1.setHgap(15);
        main_pane1.setVgap(7);
        main_pane1.setAlignment(Pos.CENTER);

        main_pane1.add(arch_label, 0, 0, 1, 1);
        main_pane1.add(arch, 1, 0, 1, 1);
        main_pane1.add(os_label, 0, 1, 1, 1);
        main_pane1.add(os, 1, 1, 1, 1);
        main_pane1.add(name_label, 0, 2, 1, 1);
        main_pane1.add(name, 1, 2, 1, 1);
        main_pane1.add(timezone_label, 0, 3, 1, 1);
        main_pane1.add(timezone, 1, 3, 1, 1);
        main_pane1.add(proceesing_cost_label, 0, 4, 1, 1);
        main_pane1.add(proceesing_cost, 1, 4, 1, 1);
        main_pane1.add(memory_cost_label, 0, 5, 1, 1);
        main_pane1.add(memory_cost, 1, 5, 1, 1);
        main_pane1.add(storage_cost_label, 0, 6, 1, 1);
        main_pane1.add(storage_cost, 1, 6, 1, 1);
        main_pane1.add(bandwidth_cost_label, 0, 7, 1, 1);
        main_pane1.add(bandwidth_cost, 1, 7, 1, 1);
        
        main_pane1.add(sep1, 2, 0, 1, 8);
        
        main_pane1.add(vm_size_label, 3, 0, 1, 1);
        main_pane1.add(vm_size, 4, 0, 1, 1);
        main_pane1.add(vm_ram_label, 3, 1, 1, 1);
        main_pane1.add(vm_ram, 4, 1, 1, 1);
        main_pane1.add(vm_mip_label, 3, 2, 1, 1);
        main_pane1.add(vm_mip, 4, 2, 1, 1);
        main_pane1.add(vm_bw_label, 3, 3, 1, 1);
        main_pane1.add(vm_bw, 4, 3, 1, 1);
        main_pane1.add(vm_pes_label, 3, 4, 1, 1);
        main_pane1.add(vm_pes, 4, 4, 1, 1);
        main_pane1.add(vm_name_label, 3, 5, 1, 1);
        main_pane1.add(vm_name, 4, 5, 1, 1);

        GroupBox mainBox = new GroupBox("Config", main_pane1, Resource.WINDOW_WIDTH - 60, 15);
        gray_pane.setId("main_area");

        BorderPane.setMargin(mainBox, new Insets(30, 20, 20, 20));
        BorderPane.setAlignment(main_pane1, Pos.TOP_CENTER);
        gray_pane.setCenter(mainBox);

        HBox btnBox = new HBox();
        btnBox.setSpacing(15);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(save_btn, cancel_btn);
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
        setting_stage.setTitle("Setting");
        setting_stage.setScene(scene);
        setting_stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("View/icon.png")));
        setting_stage.setResizable(false);

        setting_stage.initOwner(CloudSimSimulation.MainStage);
        setting_stage.initModality(Modality.WINDOW_MODAL);

        initParams();
    }

    public void show() {
        setting_stage.showAndWait();
    }

    private void initButtonGroup() {
        save_btn.setId("smallbtn");
        save_btn.setPrefWidth(150);
        cancel_btn.setId("smallbtn");
        cancel_btn.setPrefWidth(150);

        save_btn.setOnAction((evt) -> {
            saveConfig();
        });
        save_btn.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                saveConfig();
            }
        });
        cancel_btn.setOnAction((evt) -> {
            setting_stage.close();
        });
        cancel_btn.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                setting_stage.close();
            }
        });
    }

    private void saveConfig() {
        if(!vm_size.getText().isEmpty()) ConfigParams.vm_size = Long.parseLong(vm_size.getText());
        if(!vm_ram.getText().isEmpty()) ConfigParams.vm_ram = Integer.parseInt(vm_ram.getText());
        if(!vm_mip.getText().isEmpty()) ConfigParams.vm_mips = Integer.parseInt(vm_mip.getText());
        if(!vm_bw.getText().isEmpty()) ConfigParams.vm_bw = Long.parseLong(vm_bw.getText());
        if(!vm_pes.getText().isEmpty()) ConfigParams.vm_pesNumber = Integer.parseInt(vm_pes.getText());
        if(!vm_name.getText().isEmpty()) ConfigParams.vm_name = vm_name.getText();
        
        if(!arch.getText().isEmpty()) ConfigParams.arch = arch.getText();
        if(!os.getText().isEmpty()) ConfigParams.os = os.getText();
        if(!name.getText().isEmpty()) ConfigParams.host_name = name.getText();
        if(!timezone.getText().isEmpty()) ConfigParams.time_zone = Double.valueOf(timezone.getText());
        if(!proceesing_cost.getText().isEmpty()) ConfigParams.cost = Double.valueOf(proceesing_cost.getText());
        if(!memory_cost.getText().isEmpty()) ConfigParams.costPerMem = Double.valueOf(memory_cost.getText());
        if(!storage_cost.getText().isEmpty()) ConfigParams.costPerStorage = Double.valueOf(storage_cost.getText());
        if(!bandwidth_cost.getText().isEmpty()) ConfigParams.costPerBw = Double.valueOf(bandwidth_cost.getText());
        
        
        setting_stage.close();
    }

    private void initParams() {
        vm_size.setText("" + ConfigParams.vm_size);
        vm_ram.setText("" + ConfigParams.vm_ram);
        vm_mip.setText("" + ConfigParams.vm_mips);
        vm_bw.setText("" + ConfigParams.vm_bw);
        vm_pes.setText("" + ConfigParams.vm_pesNumber);
        vm_name.setText("" + ConfigParams.vm_name);
        
        arch.setText("" + ConfigParams.arch);
        os.setText("" + ConfigParams.os);
        name.setText("" + ConfigParams.host_name);
        timezone.setText("" + ConfigParams.time_zone);
        proceesing_cost.setText("" + ConfigParams.cost);
        memory_cost.setText("" + ConfigParams.costPerMem);
        storage_cost.setText("" + ConfigParams.costPerStorage);
        bandwidth_cost.setText("" + ConfigParams.costPerBw);
    }
}
