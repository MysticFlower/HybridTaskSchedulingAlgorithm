/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudsimsimulation;

import Controller.Params;
import View.MainView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author shabeena
 */
public class CloudSimSimulation extends Application {

    public static Stage MainStage;
    public MainView main_view;

    @Override
    public void start(Stage primaryStage) {
        Resource.initWindowSize();
        Params.initParams();

        main_view = new MainView();
        main_view.initView();

        MainStage = primaryStage;

        ScrollPane main_scroll_pane = new ScrollPane();
        main_scroll_pane.setStyle("-fx-background-color: linear-gradient( #0f60c7,  #0f60c7);");
        main_scroll_pane = new ScrollPane();
        main_scroll_pane.setFitToWidth(true);
        main_scroll_pane.setFitToHeight(true);

        main_scroll_pane.setContent(main_view.main_pane);

        Scene scene = new Scene(main_scroll_pane);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        MainStage.setTitle("MeMe");
        MainStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("View/icon.png")));
        MainStage.setScene(scene);
        MainStage.setMaximized(true);
        MainStage.setResizable(true);
        MainStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
