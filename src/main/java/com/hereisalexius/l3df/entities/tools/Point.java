package com.hereisalexius.l3df.entities.tools;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;


public final class Point extends Circle {
    private Pane parent;
    private Color c;
    private boolean isDashed;
    public Point(boolean isDashed,Color color, double x, double y, Pane parent) {
        super(x, y, 10);
        c = color;
        this.isDashed = isDashed;
        this.parent = parent;
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
        enableDrag();

    }

    private void enableDrag() {
        final Delta dragDelta = new Delta();
        setOnMousePressed(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    dragDelta.x = getCenterX() - mouseEvent.getX();
                    dragDelta.y = getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);
                    break;
                case SECONDARY:
                    Point p = new Point(isDashed,c,getCenterX()+40,getCenterY()+40,parent);
                    PathCurve curve = new PathCurve(isDashed,c,this,p,parent);
                    parent.getChildren().addAll(p,curve);

                    break;

            }
        });

        setOnMouseReleased(mouseEvent -> {getScene().setCursor(Cursor.HAND);
            switch (mouseEvent.getButton()) {
                case MIDDLE:
                    parent.getChildren().remove(this);
                    break;
            }
        });

        setOnMouseDragged(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setCenterX(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setCenterY(newY);
                }
                    break;
            }
        });
        setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    private class Delta { double x, y; }
}
