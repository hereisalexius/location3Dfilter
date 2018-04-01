package com.hereisalexius.l3df.entities.tools;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class RTSCamera extends PerspectiveCamera {

    private int width = 0;
    private int height = 0;

    public RTSCamera(int width, int height){
        super(false);

        this.width = width;
        this.height = height;

        this.setTranslateX(0);
        this.setTranslateY(0);
        this.setTranslateZ(-200);

        this.getTransforms().add(new Rotate(60,Rotate.X_AXIS));


    }


    public void moveUp(){
        this.setTranslateY(this.getTranslateY()-10);
        //this.setTranslateZ(this.getTranslateZ()-5);
    }

    public void moveDown(){
        this.setTranslateY(this.getTranslateY()+10);
        //this.setTranslateZ(this.getTranslateZ()+5);
    }

    public void moveRight(){
        this.setTranslateX(this.getTranslateX()-10);
    }

    public void moveLeft(){
        this.setTranslateX(this.getTranslateX()+10);
    }

    public void zoomOut(){
        this.setTranslateZ(this.getTranslateZ()+10);
        this.setTranslateY(this.getTranslateY()-15);
    }

    public void zoomIn(){
        this.setTranslateZ(this.getTranslateZ()-10);
        this.setTranslateY(this.getTranslateY()+15);
    }








}
