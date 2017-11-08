package hu.iit.uni.miskolc.gml.editor.service.impl;

public class CellSpaceCeilingCoordinateImpl {

    private double CeilingX;
    private double CeilingY;
    private double CeilingZ;

    public CellSpaceCeilingCoordinateImpl(double ceilingX, double ceilingY, double ceilingZ) {
        CeilingX = ceilingX;
        CeilingY = ceilingY;
        CeilingZ = ceilingZ;
    }

    public double getCeilingX() {
        return CeilingX;
    }

    public void setCeilingX(double ceilingX) {
        CeilingX = ceilingX;
    }

    public double getCeilingY() {
        return CeilingY;
    }

    public void setCeilingY(double ceilingY) {
        CeilingY = ceilingY;
    }

    public double getCeilingZ() {
        return CeilingZ;
    }

    public void setCeilingZ(double ceilingZ) {
        CeilingZ = ceilingZ;
    }


}
