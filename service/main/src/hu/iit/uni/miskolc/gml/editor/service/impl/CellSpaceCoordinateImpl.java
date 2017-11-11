package hu.iit.uni.miskolc.gml.editor.service.impl;


import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;

public class CellSpaceCoordinateImpl implements CellSpaceCoordinate {

    private double coordinateX;
    private double coordinateY;
    private double coordinatez;


    public CellSpaceCoordinateImpl(double coordinateX, double coordinateY, double coordinatez) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinatez = coordinatez;

    }


    @Override
    public double getCoordinateX() {
        return coordinateX;
    }

    @Override
    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    @Override
    public double getCoordinateY() {
        return coordinateY;
    }

    @Override
    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Override
    public double getCoordinatez() {
        return coordinatez;
    }

    @Override
    public void setCoordinatez(double coordinatez) {
        this.coordinatez = coordinatez;
    }

    @Override
    public void toStringCoordinateXYZ() {
        System.out.println(coordinateX + " " + coordinateY + " " + coordinatez );
    }


}
