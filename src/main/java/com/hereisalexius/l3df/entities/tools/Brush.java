package com.hereisalexius.l3df.entities.tools;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 *
 */
public final class Brush extends Tool{

    private Color drawColor;
    private Color clearColor;


    public Brush(Canvas owner, Color drawColor,Color clearColor) {
        super(owner);
        getPropertyMap().put("radius",new SimpleDoubleProperty(20));

        this.drawColor = drawColor;
        this.clearColor = clearColor;

        initMouseListeners();
    }

    private void initMouseListeners(){
        Canvas ownerCanvas = getOwner();

        ownerCanvas.setOnMouseDragged(e ->{
            double radius = getPropertyMap().get("radius").get();
            boolean colorSelected = false;
            switch (e.getButton()){
                case PRIMARY:
                    ownerCanvas.getGraphicsContext2D().setFill(drawColor);
                    colorSelected = true;
                    break;
                case SECONDARY:
                    ownerCanvas.getGraphicsContext2D().setFill(clearColor);
                    colorSelected = true;
                    break;
            }

            if(colorSelected){
                ownerCanvas.getGraphicsContext2D().fillOval(e.getX()-radius,e.getY()-radius,radius*2,radius*2);
            }
        });

        ownerCanvas.setOnMouseReleased(e -> {
            WritableImage wi = new WritableImage((int)ownerCanvas.getWidth(),(int)ownerCanvas.getHeight());
            ownerCanvas.snapshot(null,wi);
            for(int i = 0; i<ownerCanvas.getWidth();i++){
                for(int j = 0; j<ownerCanvas.getHeight();j++){
                    Color c = wi.getPixelReader().getColor(i,j);
                    if(c.getRed()==clearColor.getRed()
                            &&c.getGreen()==clearColor.getGreen()
                            &&c.getBlue()==clearColor.getBlue()){
                        ownerCanvas.getGraphicsContext2D().getPixelWriter().setColor(i,j,Color.TRANSPARENT);
                    }
                }
            }

        });


    }

    public Color getDrawColor() {
        return drawColor;
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }

    public Color getClearColor() {
        return clearColor;
    }

    public void setClearColor(Color clearColor) {
        this.clearColor = clearColor;
    }
}
