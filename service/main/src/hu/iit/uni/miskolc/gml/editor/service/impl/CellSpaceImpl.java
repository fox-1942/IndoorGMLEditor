package hu.iit.uni.miskolc.gml.editor.service.impl;

import java.util.ArrayList;

public class CellSpaceImpl {

    private String ParentFloor;
    private String CellSpaceName;

    private ArrayList<CellSpaceCeilingCoordinateImpl> cellSpaceCeilingCoordinatesArrayList;
    private ArrayList<CellSpaceFloorCoordinateImpl> cellSpaceFloorCoordinateArrayList;

    public CellSpaceImpl(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCeilingCoordinateImpl> cellSpaceCeilingCoordinatesArrayList,
                         ArrayList<CellSpaceFloorCoordinateImpl> cellSpaceFloorCoordinateArrayList) {
        ParentFloor = parentFloor;
        CellSpaceName = cellSpaceName;
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }

    public String getParentFloor() {
        return ParentFloor;
    }

    public void setParentFloor(String parentFloor) {
        ParentFloor = parentFloor;
    }

    public ArrayList<CellSpaceCeilingCoordinateImpl> getCellSpaceCeilingCoordinatesArrayList() {
        return cellSpaceCeilingCoordinatesArrayList;
    }

    public void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCeilingCoordinateImpl> cellSpaceCeilingCoordinatesArrayList) {
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
    }

    public ArrayList<CellSpaceFloorCoordinateImpl> getCellSpaceFloorCoordinateArrayList() {
        return cellSpaceFloorCoordinateArrayList;
    }

    public void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceFloorCoordinateImpl> cellSpaceFloorCoordinateArrayList) {
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }



}
