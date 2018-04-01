package com.hereisalexius.l3df.entities.tools;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public final class PathCurve extends Group {

    private Point start, end;
    private Pane p;
    final CubicCurve curve;

    public PathCurve(boolean isDashed,Paint c, Point start, Point end, Pane p) {

        this.start = start;
        this.end = end;

       curve = createStartingCurve(start.getCenterX(),start.getCenterY(),end.getCenterX(),end.getCenterY());
        curve.setStroke(c);
        if(isDashed) {
            curve.getStrokeDashArray().setAll(10.0, 5.0);
        }
        Line controlLine1 = new BoundLine(curve.controlX1Property(), curve.controlY1Property(), curve.startXProperty(), curve.startYProperty());
        Line controlLine2 = new BoundLine(curve.controlX2Property(), curve.controlY2Property(), curve.endXProperty(), curve.endYProperty());

        Anchor control1 = new Anchor(Color.GOLD, curve.controlX1Property(), curve.controlY1Property());
        Anchor control2 = new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property());

        curve.startXProperty().bind(start.centerXProperty());
        curve.startYProperty().bind(start.centerYProperty());
        curve.endXProperty().bind(end.centerXProperty());
        curve.endYProperty().bind(end.centerYProperty());




        getChildren().addAll(controlLine1, controlLine2, curve, control1, control2);

        setOnMouseReleased(mouseEvent -> {getScene().setCursor(Cursor.HAND);
            switch (mouseEvent.getButton()) {
                case MIDDLE:
                    p.getChildren().removeAll(this,controlLine1,controlLine2,control1,control2);
                    break;
            }
        });

    }

    public CubicCurve getCurve() {
        return curve;
    }

    private CubicCurve createStartingCurve(double x1, double y1, double x2, double y2) {
        CubicCurve curve = new CubicCurve();
        curve.setStartX(x1);
        curve.setStartY(y1);
        curve.setControlX1(x1+40);
        curve.setControlY1(y1);
        curve.setControlX2(x2-40);
        curve.setControlY2(y2);
        curve.setEndX(x2);
        curve.setEndY(x2);
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0));
        return curve;
    }

    private class Anchor extends Circle {
        Anchor(Color color, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 10);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        private void enableDrag() {
            final Delta dragDelta = new Delta();

            setOnMousePressed(mouseEvent -> {
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            });

            setOnMouseReleased(mouseEvent -> getScene().setCursor(Cursor.HAND));

            setOnMouseDragged(mouseEvent -> {
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setCenterX(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setCenterY(newY);
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

        private class Delta {
            double x, y;
        }
    }

    class BoundLine extends Line {
        BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStrokeWidth(2);
            setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
            setStrokeLineCap(StrokeLineCap.BUTT);
            getStrokeDashArray().setAll(10.0, 5.0);
        }
    }

}
