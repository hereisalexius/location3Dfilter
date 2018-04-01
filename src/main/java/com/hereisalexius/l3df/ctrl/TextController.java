package com.hereisalexius.l3df.ctrl;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TextController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane canvasOwner;

    @FXML
    private TextField textView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CanvasLinksContainer.contentCanvas = canvas;
        canvas.toFront();


        Canvas bg = new Canvas(512,512);

        canvas.setOnMouseMoved(e->{
            for(int i = 0; i<canvas.getWidth();i++){
                for(int j = 0; j<canvas.getHeight();j++){
                    canvas.getGraphicsContext2D().getPixelWriter().setColor(i,j,Color.TRANSPARENT);
                }
            }
            for(Node c : canvasOwner.getChildren()){
                if(c instanceof Text){

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
                    if(!c.equals(Color.HOTPINK)){
                        canvas.getGraphicsContext2D().getPixelWriter().setColor(i,j,Color.TRANSPARENT);
                    }
                }
            }


            for(Node c : canvasOwner.getChildren()){
                if(c instanceof Text){


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
        canvas.setOnMouseClicked(e -> {
            if(!textView.getText().isEmpty()){
                Text txt = new Text(textView.getText());
                txt.setStyle("-fx-font-size: 25px");
                txt.setFill(Color.HOTPINK);

                canvasOwner.getChildren().addAll(txt);
                txt.setX(e.getX()-txt.getWrappingWidth());
                txt.setY(e.getY());
                txt.setOnMouseDragged(et -> {
                    switch (et.getButton()) {
                        case PRIMARY:
                            txt.setX(et.getX()-txt.getWrappingWidth());
                            txt.setY(et.getY());
                            break;
                        case SECONDARY:
                            canvasOwner.getChildren().removeAll(txt);
                            break;
                    }
                });

                txt.setOnScroll(et -> {
                    txt.setRotate(txt.getRotate()+et.getDeltaY());
                });
            }
            textView.setText("");
        });

    }
}
