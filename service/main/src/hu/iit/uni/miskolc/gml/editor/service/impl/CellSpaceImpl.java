package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;

import java.util.ArrayList;

public class CellSpaceImpl implements hu.iit.uni.miskolc.gml.editor.model.CellSpace {

    private String ParentFloor;
    private String CellSpaceName;

    private ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList;
    private ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList;

    public CellSpaceImpl(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList,
                         ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList) {
        ParentFloor = parentFloor;
        CellSpaceName = cellSpaceName;
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }

    @Override
    public String getParentFloor() {
        return ParentFloor;
    }

    @Override
    public void setParentFloor(String parentFloor) {
        ParentFloor = parentFloor;
    }

    @Override
    public ArrayList<CellSpaceCoordinate> getCellSpaceCeilingCoordinatesArrayList() {
        return cellSpaceCeilingCoordinatesArrayList;
    }

    @Override
    public void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList) {
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
    }

    @Override
    public ArrayList<CellSpaceCoordinate> getCellSpaceFloorCoordinateArrayList() {
        return cellSpaceFloorCoordinateArrayList;
    }

    @Override
    public void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceCoordinate> cellSpaceCoordinateArrayList) {
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }

    public void CellSpacetoString() {
        System.out.println("\nParentFloor: " + ParentFloor + " CellSpaceName: " + CellSpaceName);
        for (int i = 0; i < cellSpaceFloorCoordinateArrayList.size(); i++) {
            System.out.println(cellSpaceFloorCoordinateArrayList.get(i).toStringCoordinateXYZ());
        }
        for (int x = 0; x < cellSpaceCeilingCoordinatesArrayList.size(); x++) {
            System.out.println(cellSpaceCeilingCoordinatesArrayList.get(x).toStringCoordinateXYZ());
        }


            System.out.println("\n---------------------------------");
        }

}