package com.hereisalexius.l3df.entities.tools;

import com.sun.javafx.collections.ObservableFloatArrayImpl;
import com.sun.javafx.collections.ObservableIntegerArrayImpl;
import javafx.collections.ObservableFloatArray;
import javafx.collections.ObservableIntegerArray;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;


public class Terrain extends MeshView {

    public static final int DEFAULT_WIDTH_N_HEIGHT = 1024;
    public static final int DEFAULT_SIZE = 512;

    private TriangleMesh terrainMesh;

    private int[][] zIDs;

    public Terrain() {
        initZIDs();
        terrainMesh = new TriangleMesh();
        terrainMesh.getPoints().addAll(initPoints());
        terrainMesh.getTexCoords().addAll(initTexCoordinates());
        terrainMesh.getFaces().addAll(initFaces());
        this.setCullFace(CullFace.NONE);
        this.setMesh(terrainMesh);
    }

    private ObservableFloatArray initPoints(){
        ObservableFloatArray points = new ObservableFloatArrayImpl();
        float spacing = DEFAULT_WIDTH_N_HEIGHT/ DEFAULT_SIZE;
        for(int i = 0; i<= DEFAULT_SIZE; i++){
            for(int j = 0; j<= DEFAULT_SIZE; j++){
                float x = i*spacing;
                float y = j*spacing;
                float z = 0;
                points.addAll(x,y,z);
            }
        }
        return points;
    }

    private void initZIDs(){
        zIDs = new int[ DEFAULT_SIZE+1][ DEFAULT_SIZE+1];
        int id = 2;
        for(int i = 0; i<= DEFAULT_SIZE; i++){
            for(int j = 0; j<= DEFAULT_SIZE; j++){
                zIDs[i][j] = id;
                id+=3;
            }
        }
    }

    private ObservableFloatArray initTexCoordinates(){
        ObservableFloatArray texCoordinates = new ObservableFloatArrayImpl();
        for(int i = 0; i<= DEFAULT_SIZE; i++){
            for(int j = 0; j<= DEFAULT_SIZE; j++){
                float tx = (float)(i)/(float)( DEFAULT_SIZE);
                float ty = (float)(j)/(float)( DEFAULT_SIZE);
                texCoordinates.addAll(tx,ty);
            }
        }
        return texCoordinates;
    }

    private ObservableIntegerArray initFaces(){
        ObservableIntegerArray faces = new ObservableIntegerArrayImpl();

        int cornerID = 0;

        for(int i = 0; i< DEFAULT_SIZE; i++){
            for(int j = 0; j<= DEFAULT_SIZE; j++){
            if(j>0) {
                faces.addAll(getRectFaces(cornerID));
            }

                cornerID++;
            }
        }

        return faces;


    }

    private ObservableIntegerArray getRectFaces(int cornerID){
        ObservableIntegerArray faces = new ObservableIntegerArrayImpl();
        int corner_1 = cornerID;
        int corner_0 = corner_1 - 1;
        int corner_2 = corner_0 + DEFAULT_SIZE +1;
        int corner_3 = corner_2 + 1;
        faces.addAll(corner_1,corner_1,corner_2,corner_2,corner_0,corner_0);
        faces.addAll(corner_1,corner_1,corner_3,corner_3,corner_2,corner_2);
        return faces;
    }

    public void loadHeightmap(Image img){
        WritableImage heightmap = new WritableImage( DEFAULT_SIZE+1, DEFAULT_SIZE+1);
        Canvas canvas = new Canvas( DEFAULT_SIZE+1, DEFAULT_SIZE+1);
        canvas.getGraphicsContext2D().drawImage(img,0,0, DEFAULT_SIZE+1, DEFAULT_SIZE+1);
        canvas.snapshot(null,heightmap);

        for(int i = 0; i<= DEFAULT_SIZE; i++){
            for(int j = 0; j<= DEFAULT_SIZE; j++){
                setZAt(i,j,(float)heightmap.getPixelReader().getColor(i,j).getRed()*-255);
            }
        }
    }

    public WritableImage getHeightmap(){
        WritableImage heightmap = new WritableImage( DEFAULT_SIZE+1, DEFAULT_SIZE+1);
        for(int i = 0; i<= DEFAULT_SIZE; i++){
            for(int j = 0; j<= DEFAULT_SIZE; j++){
                int colorValue = (int)(-1*(getZAt(i,j)));
                heightmap.getPixelWriter().setColor(i,j,Color.rgb(colorValue,colorValue,colorValue));
                setZAt(i,j,(float)heightmap.getPixelReader().getColor(i,j).getRed()*-255);
            }
        }
    return heightmap;
    }
    
    public float getZAt(int x, int y){
        return terrainMesh.getPoints().get(zIDs[x][y]);
    }

    public void setZAt(int x, int y, float value){
        terrainMesh.getPoints().set(zIDs[x][y],value);
    }

    public Point3D getNearestPoint(double x, double y){
        float spacing = DEFAULT_WIDTH_N_HEIGHT/ DEFAULT_SIZE;
        double newX = ((int)x/(int)spacing)*(int)spacing;
        double newY = ((int)x/(int)spacing)*(int)spacing;
        double newZ = getZAt((int)x/(int)spacing,(int)y/(int)spacing);
        return  new Point3D(newX,newY,newZ);
    }

}
