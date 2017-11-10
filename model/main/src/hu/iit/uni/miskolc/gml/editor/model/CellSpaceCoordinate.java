package hu.iit.uni.miskolc.gml.editor.model;

public interface CellSpaceCoordinate {
     double getCoordinateX();

     void setCoordinateX(double coordinateX);

     double getCoordinateY();
     void setCoordinateY(double coordinateY);

     double getCoordinatez();

     void setCoordinatez(double coordinatez);

     void toStringCoordinateXYZ();
}
