package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceFloorCoordinate;

public class CellSpaceFloorCoordinateImpl implements CellSpaceFloorCoordinate {

    private double FloorX;
    private double FloorY;
    private double FloorZ;


    public CellSpaceFloorCoordinateImpl(double floorX, double floorY, double floorZ) {
        FloorX = floorX;
        FloorY = floorY;
        FloorZ = floorZ;

    }


    @Override
    public double getFloorX() {
        return FloorX;
    }

    @Override
    public void setFloorX(double floorX) {
        FloorX = floorX;
    }

    @Override
    public double getFloorY() {
        return FloorY;
    }

    @Override
    public void setFloorY(double floorY) {
        FloorY = floorY;
    }

    @Override
    public double getFloorZ() {
        return FloorZ;
    }

    @Override
    public void setFloorZ(double floorZ) {
        FloorZ = floorZ;
    }



}
