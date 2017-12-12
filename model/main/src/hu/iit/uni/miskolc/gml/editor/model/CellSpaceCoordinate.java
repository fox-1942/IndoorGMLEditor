package hu.iit.uni.miskolc.gml.editor.model;


public class CellSpaceCoordinate {

    private double coordinateY;
    private double coordinateZ;
    private double coordinateX;

    public CellSpaceCoordinate(double coordinateX, double coordinateY, double coordinateZ) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
    }

    public double getCoordinateX() {
        return coordinateX;
    }


    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }


    public double getCoordinateY() {
        return coordinateY;
    }


    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }


    public double getCoordinateZ() {
        return coordinateZ;
    }


    public void setCoordinateZ(double coordinateZ) {
        this.coordinateZ = coordinateZ;
    }


    public String toStringCoordinateXYZ() {
        return coordinateX +" " +coordinateY + " " + coordinateZ;
    }


    public void setCoordinateXYZ(double coordinateX, double coordinateY, double coordinateZ) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
    }

}