package com.hereisalexius.l3df.ctrl;

import com.hereisalexius.l3df.entities.tools.HeightBrush;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HeightmapController implements Initializable{

    @FXML
    private Canvas canvas;

    @FXML
    private Slider brushSizer;

    @FXML
    void clear(ActionEvent event) {
       clearCanvas();
    }

    @FXML
    void importImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File rf = fileChooser.showOpenDialog(null);
        if(rf!=null){
            canvas.getGraphicsContext2D().drawImage(new Image("file://"+rf.getAbsolutePath()),0,0,canvas.getWidth(),canvas.getHeight());
        }
    }

    @FXML
    void exportImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File rf = fileChooser.showSaveDialog(null);
        if(rf!=null){
            WritableImage img = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
            canvas.snapshot(null,img);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", new File(rf.getAbsolutePath()+".png"));
            } catch (Exception s) {
            }
        }
    }

    private void clearCanvas(){
        canvas.getGraphicsContext2D().fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CanvasLinksContainer.heightmapCanvas = canvas;
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        clearCanvas();

        HeightBrush hb = new HeightBrush(canvas);
        hb.getPropertyMap().get("radius").bind(brushSizer.valueProperty());
    }
}
