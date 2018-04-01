package com.hereisalexius.l3df;



import com.hereisalexius.l3df.ctrl.CanvasLinksContainer;
import com.hereisalexius.l3df.entities.tools.RTSCamera;
import com.hereisalexius.l3df.entities.tools.Terrain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;

public class Run extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("View");

        MenuItem show = new MenuItem("3D");
        show.setOnAction(e -> {
            Stage stage = new Stage();
            final Terrain t = new Terrain();

            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);

            WritableImage wi = new WritableImage(512,512);
            CanvasLinksContainer.heightmapCanvas.snapshot(null,wi);
            t.loadHeightmap(wi);
            Canvas texCanvas = new Canvas(512,512);
            WritableImage tex = new WritableImage(512,512);
            for(int i = 0; i< 512; i++){
                for(int j = 0; j< 512; j++){
                    tex.getPixelWriter().setColor(i,j, Color.LIGHTGRAY);
                }
            }


            CanvasLinksContainer.woodCanvas.snapshot(sp,tex);
            texCanvas.getGraphicsContext2D().drawImage(tex,0,0,512,512);
            CanvasLinksContainer.riverCanvas.snapshot(sp,tex);
            texCanvas.getGraphicsContext2D().drawImage(tex,0,0,512,512);
            CanvasLinksContainer.railwayCanvas.snapshot(sp,tex);
            texCanvas.getGraphicsContext2D().drawImage(tex,0,0,512,512);
           CanvasLinksContainer.contentCanvas.snapshot(sp,tex);
            texCanvas.getGraphicsContext2D().drawImage(tex,0,0,512,512);
            texCanvas.snapshot(null,tex);


            PhongMaterial m = new PhongMaterial();
            m.setDiffuseMap(tex);

            t.setMaterial(m);

            final RTSCamera camera = new RTSCamera(800,800);
            final Group root = new Group(t);
            Scene scene = new Scene(root, 800, 800, true);
            scene.setCamera(camera);
            scene.setOnKeyPressed(ec -> {

                    switch (ec.getCode()){
                        case D: camera.moveLeft(); break;
                        case A: camera.moveRight(); break;
                        case W: camera.moveUp(); break;
                        case S: camera.moveDown(); break;
                        case UP:camera.zoomIn(); break;
                        case DOWN: camera.zoomOut(); break;
                        //case G: t.test();



                }
            });




            stage.setScene(scene);
            stage.show();
        });

        menu.getItems().addAll(show);
        menuBar.getMenus().addAll(menu);

        TabPane tp = new TabPane(
                new Tab("Карта висот",FXMLLoader.load(getClass().getResource("/fxml/hm.fxml"))),
                new Tab("Ліс",FXMLLoader.load(getClass().getResource("/fxml/wd.fxml"))),
                //new Tab("Поселення",FXMLLoader.load(getClass().getResource("/fxml/ct.fxml"))),
                new Tab("Річки",FXMLLoader.load(getClass().getResource("/fxml/rv.fxml"))),
                new Tab("Колія",FXMLLoader.load(getClass().getResource("/fxml/rw.fxml"))),
                new Tab("Текст",FXMLLoader.load(getClass().getResource("/fxml/tx.fxml")))
        );

        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        BorderPane root = new BorderPane(tp);
        root.setTop(menuBar);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
