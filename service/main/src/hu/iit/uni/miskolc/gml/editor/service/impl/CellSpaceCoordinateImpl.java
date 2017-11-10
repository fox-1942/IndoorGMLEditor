package hu.iit.uni.miskolc.gml.editor.service.impl;


import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;

public class CellSpaceCoordinateImpl implements CellSpaceCoordinate {

    private double coordinateY;
    private double coordinateZ;
    private double coordinateX;

    public CellSpaceCoordinateImpl(double coordinateX, double coordinateY, double coordinateZ) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
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
    public double getCoordinateZ() {
        return coordinateZ;
    }

    @Override
    public void setCoordinateZ(double coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    @Override
    public void toStringCoordinateXYZ() {
    }







}

