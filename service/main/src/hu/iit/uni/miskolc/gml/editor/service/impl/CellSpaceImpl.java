package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCeilingCoordinate;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceFloorCoordinate;

import java.util.ArrayList;

public class CellSpaceImpl implements hu.iit.uni.miskolc.gml.editor.model.CellSpace {

    private String ParentFloor;
    private String CellSpaceName;

    private ArrayList<CellSpaceCeilingCoordinate> cellSpaceCeilingCoordinatesArrayList;
    private ArrayList<CellSpaceFloorCoordinate> cellSpaceFloorCoordinateArrayList;

    public CellSpaceImpl(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCeilingCoordinate> cellSpaceCeilingCoordinatesArrayList,
                         ArrayList<CellSpaceFloorCoordinate> cellSpaceFloorCoordinateArrayList) {
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
    public ArrayList<CellSpaceCeilingCoordinate> getCellSpaceCeilingCoordinatesArrayList() {
        return cellSpaceCeilingCoordinatesArrayList;
    }

    @Override
    public void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCeilingCoordinate> cellSpaceCeilingCoordinatesArrayList) {
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
    }

    @Override
    public ArrayList<CellSpaceFloorCoordinate> getCellSpaceFloorCoordinateArrayList() {
        return cellSpaceFloorCoordinateArrayList;
    }

    @Override
    public void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceFloorCoordinate> cellSpaceFloorCoordinateArrayList) {
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }



}
