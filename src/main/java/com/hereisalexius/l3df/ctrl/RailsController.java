package com.hereisalexius.l3df.ctrl;


import com.hereisalexius.l3df.entities.tools.PathCurve;
import com.hereisalexius.l3df.entities.tools.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

import java.net.URL;
import java.util.ResourceBundle;

public class RailsController implements Initializable{

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane canvasOwner;

    @FXML
    void setFreePoint(ActionEvent event) {
        canvasOwner.getChildren().addAll(new Point(true,Color.GREEN,256,256,canvasOwner));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CanvasLinksContainer.railwayCanvas = canvas;
        canvas.toFront();

        Canvas bg = new Canvas(512,512);
        canvas.setOnMouseMoved(e -> {
            for(int i = 0; i<canvas.getWidth();i++){
                for(int j = 0; j<canvas.getHeight();j++){
                    canvas.getGraphicsContext2D().getPixelWriter().setColor(i,j,Color.TRANSPARENT);
                }
            }
            for(Node c : canvasOwner.getChildren()){
                if(c instanceof PathCurve){
                    for(Node n:((PathCurve)c).getChildren()){
                        if(n instanceof CubicCurve){

                        }else{
                            n.setVisible(false);
                        }
                    }

                }else{
                    c.setVisible(false);
                }
            }
            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            WritableImage wi = new WritableImage(512,512);
            canvasOwner.snapshot(sp,wi);
            canvas.getGraphicsContext2D().drawImage(wi,0,0,512,512);
            for(int i = 0; i<canvas.getWidth();i++){
                for(int j = 0; j<canvas.getHeight();j++){
                    Color c = wi.getPixelReader().getColor(i,j);
                    if(!c.equals(Color.GREEN)){
                        canvas.getGraphicsContext2D().getPixelWriter().setColor(i,j,Color.TRANSPARENT);
                    }
                }
            }


            for(Node c : canvasOwner.getChildren()){

                if(c instanceof PathCurve){
                    for(Node n:((PathCurve)c).getChildren()){
                        if(n instanceof CubicCurve){

                        }else{
                            n.setVisible(true);
                        }
                    }

                }else{
                    c.setVisible(true);
                }
            }

            WritableImage wim = new WritableImage(512,512);
            CanvasLinksContainer.heightmapCanvas.snapshot(null,wim);
            bg.getGraphicsContext2D().drawImage(wim,0,0,512,512);
        });

        canvasOwner.getChildren().addAll(bg);
        bg.toBack();
    }

    public static Image posterize(Image img,int level){
        WritableImage result = new WritableImage(img.getPixelReader(),(int)img.getWidth(),(int)img.getHeight());
        for (int i = 0; i<result.getWidth(); i++){
            for(int j = 0; j<result.getHeight(); j++){
                Color pixel = result.getPixelReader().getColor(i,j);
                int r = ((int)(pixel.getRed()*255)/level)*level;
                int g = ((int)(pixel.getGreen()*255)/level)*level;
                int b = ((int)(pixel.getBlue()*255)/level)*level;
                result.getPixelWriter().setColor(i,j, Color.rgb(r,g,b));
            }
        }

        return result;
    }
}