package com.hereisalexius.l3df.entities.tools;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


/**
 *
 */
public final class HeightBrush extends Tool{

    public final Image BRUSH_IMG_WHITE = new Image(getClass().getResource("/img/brush_w.png").toExternalForm());
    public final Image BRUSH_IMG_BLACK = new Image(getClass().getResource("/img/brush_b.png").toExternalForm());

    public HeightBrush(Canvas owner) {
        super(owner);
        getPropertyMap().put("radius",new SimpleDoubleProperty(20));
        initMouseListeners();
    }

    private void initMouseListeners(){
        Canvas ownerCanvas = getOwner();

        ownerCanvas.setOnMouseDragged(e ->{
            double radius = getPropertyMap().get("radius").get();
            WritableImage img = null;
            switch (e.getButton()){
                case PRIMARY:
                    img = new WritableImage(
                            BRUSH_IMG_WHITE.getPixelReader(),
                            (int)BRUSH_IMG_WHITE.getWidth(),
                            (int)BRUSH_IMG_WHITE.getHeight());
                    break;
                case SECONDARY:
                    img = new WritableImage(
                            BRUSH_IMG_BLACK.getPixelReader(),
                            (int)BRUSH_IMG_BLACK.getWidth(),
                            (int)BRUSH_IMG_BLACK.getHeight());
                    break;
            }

            if(img!=null){
                ownerCanvas.getGraphicsContext2D().drawImage(img,e.getX()-radius,e.getY()-radius,radius*2,radius*2);
            }
        });

    }

}
