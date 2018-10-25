package ca.pringle.maze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Maze Maker");

        Scene scene = new Scene(new VBox(), 400, 350);
        scene.setFill(Color.OLDLACE);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");

        MenuItem newMaze = new MenuItem("New");
        newMaze.setOnAction((v) -> );

        MenuItem saveAs = new MenuItem("Save as...");
        saveAs.setDisable(true);

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((v) -> System.exit(0));


        menuFile.getItems().addAll(newMaze, saveAs, exit);

        menuBar.getMenus().addAll(menuFile);


        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

        stage.setScene(scene);
        stage.show();


//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(event -> System.out.println("Hello World!"));
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();
    }
}