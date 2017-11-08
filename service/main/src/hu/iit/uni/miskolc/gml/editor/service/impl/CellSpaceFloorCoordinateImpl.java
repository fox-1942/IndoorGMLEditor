package hu.iit.uni.miskolc.gml.editor.service.impl;

public class CellSpaceFloorCoordinateImpl {

    private double FloorX;
    private double FloorY;
    private double FloorZ;

    public CellSpaceFloorCoordinateImpl(double floorX, double floorY, double floorZ) {
        FloorX = floorX;
        FloorY = floorY;
        FloorZ = floorZ;
    }

    public double getFloorX() {
        return FloorX;
    }

    public void setFloorX(double floorX) {
        FloorX = floorX;
    }

    public double getFloorY() {
        return FloorY;
    }

    public void setFloorY(double floorY) {
        FloorY = floorY;
    }

    public double getFloorZ() {
        return FloorZ;
    }

    public void setFloorZ(double floorZ) {
        FloorZ = floorZ;
    }
}
