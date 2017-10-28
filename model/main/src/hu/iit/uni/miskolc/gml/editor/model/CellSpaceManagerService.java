package hu.iit.uni.miskolc.gml.editor.model;

import net.opengis.gml.v_3_2_1.CompositeSolidType;
import net.opengis.gml.v_3_2_1.SolidPropertyType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceGeometryType;
import net.opengis.indoorgml.core.v_1_0.CellSpacePropertyType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import java.io.File;

public interface CellSpaceManagerService  {


    CellSpaceGeometryType getGeometry();

    CellSpacePropertyType getGeometryProperty();
    /**
     *
     * @param solidPropertyType
     */
    void set2DGeometry(SolidPropertyType solidPropertyType);

    /**
     *
     * @param compositeSolid
     */
    void set2DGeometry(CompositeSolidType compositeSolid);

    SurfacePropertyType get2DGeometry();

    void set2DGeometry(SurfacePropertyType surfacePropertyType);


    SolidPropertyType get3DGeometryProperty();


    void set3DGeometry(SolidPropertyType solidPropertyType);


    /////////////////////////////////////////////////////////////

    void marshal(File outputFile) throws CellSpaceException;

    CellSpaceType unmarshal2(File inputFile) throws CellSpaceException;

    static IndoorFeaturesType unmarshal(File inputFile) throws CellSpaceException {
        return null;
    }

    CellSpaceType getCellspace();


}