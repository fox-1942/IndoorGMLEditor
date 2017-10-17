package hu.iit.uni.miskolc.gml.editor.model.service.impl;

import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.SurfaceType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceGeometryType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

//Static Factory method

public class CellSpaceManagerFactory {
    public static CellSpaceManagerServiceImpl creatorCellSpaceManagerServiceImpl(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException
    {
        CellSpaceManagerServiceImpl cellSpaceManagerService = new CellSpaceManagerServiceImpl(
                surfaceTitle, surface2ID, firstAxis, secondAxis, SrsName);
        return cellSpaceManagerService;
    }
}