package com.hereisalexius.l3df.ctrl;

import com.hereisalexius.l3df.entities.tools.Brush;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class WoodController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane canvasOwner;

    @FXML
    private Slider brushSizer;

    @FXML
    void clear(ActionEvent event) {
        for(int i = 0; i<canvas.getWidth();i++){
            for(int j = 0; j<canvas.getHeight();j++){
                canvas.getGraphicsContext2D().getPixelWriter().setColor(i,j,Color.TRANSPARENT);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CanvasLinksContainer.woodCanvas = canvas;
        canvas.toFront();
        Brush b = new Brush(canvas, Color.GREEN, Color.WHITE);
        b.getPropertyMap().get("radius").bind(brushSizer.valueProperty());
        canvas.setOpacity(0.6);
        Canvas bg = new Canvas(512,512);

        canvas.setOnMouseEntered(e -> {
            WritableImage wim = new WritableImage(512,512);
            CanvasLinksContainer.heightmapCanvas.snapshot(null,wim);
            bg.getGraphicsContext2D().drawImage(wim,0,0,512,512);
        });

        canvasOwner.getChildren().addAll(bg);
        bg.toBack();
    }
}