package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCeilingCoordinate;

public class CellSpaceCeilingCoordinateImpl implements CellSpaceCeilingCoordinate {

    private double CeilingX;
    private double CeilingY;
    private double CeilingZ;

    public CellSpaceCeilingCoordinateImpl(double ceilingX, double ceilingY, double ceilingZ) {
        CeilingX = ceilingX;
        CeilingY = ceilingY;
        CeilingZ = ceilingZ;
    }

    @Override
    public double getCeilingX() {
        return CeilingX;
    }

    @Override
    public void setCeilingX(double ceilingX) {
        CeilingX = ceilingX;
    }

    @Override
    public double getCeilingY() {
        return CeilingY;
    }

    @Override
    public void setCeilingY(double ceilingY) {
        CeilingY = ceilingY;
    }

    @Override
    public double getCeilingZ() {
        return CeilingZ;
    }

    @Override
    public void setCeilingZ(double ceilingZ) {
        CeilingZ = ceilingZ;
    }


}
