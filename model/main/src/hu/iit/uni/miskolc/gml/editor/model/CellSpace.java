package hu.iit.uni.miskolc.gml.editor.model;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;

import java.util.ArrayList;

public class CellSpace {

    private String ParentFloor;
    private String CellSpaceName;

    private ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList;
    private ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList;

    public CellSpace(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList,
                     ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList) {
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


    public ArrayList<CellSpaceCoordinate> getCellSpaceCeilingCoordinatesArrayList() {
        return cellSpaceCeilingCoordinatesArrayList;
    }


    public void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList) {
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
    }


    public ArrayList<CellSpaceCoordinate> getCellSpaceFloorCoordinateArrayList() {
        return cellSpaceFloorCoordinateArrayList;
    }


    public void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceCoordinate> cellSpaceCoordinateArrayList) {
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }


    public void cellSpacetoString() {
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