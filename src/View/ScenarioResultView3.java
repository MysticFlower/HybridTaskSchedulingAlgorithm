/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Scenario2;
import Model.Scenario3;
import cloudsim.Algorithm2;
import cloudsim.Algorithm3;
import cloudsimsimulation.Resource;
import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.cloudbus.cloudsim.Cloudlet;

/**
 *
 * @author shabeena
 */
public class ScenarioResultView3 {

    public BorderPane scenario_pane;
    public BorderPane scenario_group_pane;

    private Label result_label = new Label("Max Estimated Time: ");
    private Label result_view = new Label();

    private Label result_label_2 = new Label("Min Estimated Time: ");
    private Label result_view_2 = new Label();

    private Label result_label_3 = new Label("Failed Task: ");
    private Label result_view_3 = new Label();

    public TableView<Scenario3> scenario_view;

    public ObservableList<Scenario3> scenario_list;

    public void initView() {
        result_label.setId("roundLabel");
        result_view.setId("roundLabel");

        result_label_2.setId("roundLabel");
        result_view_2.setId("roundLabel");

        result_label_3.setId("roundLabel");
        result_view_3.setId("roundLabel");

        HBox nameBox = new HBox();
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setSpacing(15);
        nameBox.getChildren().addAll(result_label, result_view, result_label_2, result_view_2, result_label_3, result_view_3);

        HBox contentBox = new HBox();
        contentBox.setSpacing(20);
        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox.setMargin(nameBox, new Insets(0, 0, 0, 20));
        contentBox.getChildren().addAll(nameBox, region1);

        //initialize customer table
        initResultTable();

        VBox customerBox = new VBox();
        customerBox.setSpacing(10);
        Separator sep = new Separator();
        customerBox.getChildren().addAll(contentBox, sep, scenario_view);

        GroupBox customer_group = new GroupBox("Scenario 3", customerBox, Resource.RIGHT_AREA_WIDTH, 30);

        scenario_group_pane = new BorderPane();
        scenario_group_pane.setId("main_area");
        BorderPane.setMargin(customer_group, new Insets(20, 10, 10, 10));
        scenario_group_pane.setCenter(customer_group);

        scenario_pane = new BorderPane();
        scenario_pane.setId("black_area");
        BorderPane.setMargin(scenario_group_pane, new Insets(20, 20, 20, 20));
        scenario_pane.setCenter(scenario_group_pane);
    }

    public void initResultTable() {
        //initilalize customer table
        scenario_view = new TableView<Scenario3>();
        scenario_view.setId("header_table");
        scenario_view.setPlaceholder(new Label("Sorry! There is no result."));
        scenario_view.setPrefHeight(Resource.WINDOW_HEIGHT);

        scenario_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Scenario3, String> task_id = new TableColumn<>("Task Id");
        TableColumn<Scenario3, String> datacenter_id = new TableColumn<>("Datacenter ID");
        TableColumn<Scenario3, String> vm_id = new TableColumn<>("Vm Id");
        TableColumn<Scenario3, String> start_time = new TableColumn<>("Start Time");
        TableColumn<Scenario3, String> finish_time = new TableColumn<>("Finish Time");
        TableColumn<Scenario3, String> estimated_time = new TableColumn<>("Estimated Time");
        TableColumn<Scenario3, String> wait_time = new TableColumn<>("Wait");
        TableColumn<Scenario3, String> status = new TableColumn<>("Status");

        task_id.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("TaskId"));
        datacenter_id.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("DataCenterId"));
        vm_id.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("VmID"));
        start_time.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("StartTime"));
        finish_time.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("EndTime"));
        estimated_time.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("Estimate"));
        wait_time.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("Wait"));
        status.setCellValueFactory(new PropertyValueFactory<Scenario3, String>("Status"));

        task_id.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.1));
        datacenter_id.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.13));
        vm_id.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.1));
        start_time.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.14));
        finish_time.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.15));
        estimated_time.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.14));
        wait_time.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.14));
        status.prefWidthProperty().bind(scenario_view.widthProperty().multiply(0.1));

        task_id.setResizable(false);
        datacenter_id.setResizable(false);
        vm_id.setResizable(false);
        start_time.setResizable(false);
        finish_time.setResizable(false);
        estimated_time.setResizable(false);
        wait_time.setResizable(false);
        status.setResizable(false);

        scenario_view.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(ListChangeListener.Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    scenario_view.getColumns().setAll(task_id, datacenter_id, vm_id, start_time, finish_time, estimated_time, wait_time, status);
                    this.suspended = false;
                }
            }
        });

        scenario_view.getColumns().addAll(task_id, datacenter_id, vm_id, start_time, finish_time, estimated_time, wait_time, status);
        scenario_list = FXCollections.observableArrayList();
        scenario_view.setItems(scenario_list);
    }

    public void loadContent() {
        scenario_list.clear();
        Algorithm3.datacenter_num = SettingView3.datacenter_numbers;
        Algorithm3.jobs_num = SettingView3.task_numbers;
        Algorithm3.wait_limit = SettingView3.wait_limit;
        Algorithm3.StartAlgo3();
        Algorithm3.calcMaxEstimatedTime();
        Algorithm3.calcMinEstimatedTime();

        int size = Algorithm3.resultList.size();
        Cloudlet cloudlet;

        DecimalFormat dft = new DecimalFormat("###.##");
        int fail_number = 0;
        for (int i = 0; i < size; i++) {
            cloudlet = Algorithm3.resultList.get(i);
            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                double waited_time = cloudlet.getWaitingTime();
                if (waited_time <= Algorithm3.wait_limit) {
                    scenario_list.add(new Scenario3("" + cloudlet.getCloudletId(), "" + cloudlet.getResourceId(), "" + cloudlet.getVmId(),
                            dft.format(cloudlet.getExecStartTime()), dft.format(cloudlet.getFinishTime()), dft.format(cloudlet.getActualCPUTime()), dft.format(waited_time), "Success"));
                } else {
                    scenario_list.add(new Scenario3("" + cloudlet.getCloudletId(), "" + cloudlet.getResourceId(), "" + cloudlet.getVmId(),
                            dft.format(cloudlet.getExecStartTime()), dft.format(cloudlet.getFinishTime()), dft.format(cloudlet.getActualCPUTime()), dft.format(waited_time), "Fail"));
                    fail_number++;
                }

            }
        }
        result_view.setText("" + dft.format(Algorithm3.max_time));
        result_view_2.setText("" + dft.format(Algorithm3.min_time));
        result_view_3.setText("" + fail_number);
        scenario_view.setItems(scenario_list);
    }
}
