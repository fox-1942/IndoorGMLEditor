package hu.iit.uni.miskolc.gml.editor.model.service.impl;


import hu.iit.uni.miskolc.gml.editor.model.CellSpaceException;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceManagerFactory;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceManagerServiceImpl;
import net.opengis.indoorgml.core.v_1_0.CellSpaceGeometryType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by fox on 2017.07.31.
 */
public class CellSpaceManagerServiceImplTest {

    private CellSpaceManagerService service;
    private SurfaceType surface2 = new SurfaceType();
    private SurfacePropertyType surface = new SurfacePropertyType();
    private CellSpaceType cellSpace = new CellSpaceType();
    private CellSpaceManagerServiceImpl cellSpaceManagerServiceimpl=new CellSpaceManagerServiceImpl(cellSpace);

    private CellSpaceType testCellSpace=new CellSpaceType();

    public CellSpaceManagerServiceImplTest() throws JAXBException {
    }

    @Before
    public void setUp() throws JAXBException {

        // surface ------------------------------------------
        surface.setTitle("surface 1");

        // surface2 and uploading with data -----------------
        surface2.setId("ID_OF_SURFACE2");

        List<String> list=new ArrayList<String>();
        list.add("firstAXIS");
        list.add("secondAXIS");
        list.add("thirdAXIS");
        surface2.setAxisLabels(list);

        surface2.setSrsName("SRS_NAME_OF_SURFACE2");


        // creating JAXBElement from surface2 ---------------
        QName qName=new QName("http://www.opengis.net/gml/3.2", "Surface");
        JAXBElement <SurfaceType> SurfaceTypeJAXBElement=new JAXBElement<SurfaceType>(qName, SurfaceType.class, surface2);

        // adding surface2-JAXBElement (as parameter) to surface
        surface.setAbstractSurface(SurfaceTypeJAXBElement);

        // creating geometry --------------------------------
        CellSpaceGeometryType geometry = new CellSpaceGeometryType();

        // adding surface to geometry -----------------------
        geometry.setGeometry2D(surface);

        // adding geometry to cellSpace ---------------------
        cellSpace.setCellSpaceGeometry(geometry);

        // adding cellSpace to service ----------------------
        service = new CellSpaceManagerServiceImpl(cellSpace);

        // other --------------------------------------------
        PolygonType polygon = new PolygonType();
        polygon.setId("Polygon 1");
        AbstractSurfaceType compositeSurface = new CompositeSurfaceType();
        compositeSurface.setId("Composite Surface 1");
    }

    @Test
    public void testGet2DGeometry(){
        System.out.println(service);
        System.out.println("------Attributes of 2D-geometry--------------------------");
        System.out.println(service.get2DGeometry());
        System.out.println("---------------------------------------------------------");
    }

    @Test
    public void testGetCellSpace(){
        System.out.println(service.getCellspace().toString());
    }

    @After
    public void tearDown(){
        System.out.println("Tear Down");
        System.out.println("");
    }

    @Test
    public void testMarshall() throws CellSpaceException {
        File outputFile=new File("target/generated-test-sources/testMarshallExample.xml");
        System.out.println("Marshalling Completed...");
        cellSpaceManagerServiceimpl.marshal(outputFile);
    }

    @Test
    public void testUnMarshall() throws CellSpaceException {
        File inputFile=new File("main/resources/example.xml");
        System.out.println("UN-Marshalling Completed...");
        testCellSpace = cellSpaceManagerServiceimpl.unmarshal(inputFile);
        System.out.println("GETCELLSPACEGEOMETRY" +  testCellSpace.getCellSpaceGeometry().toString());
    }

    // FACTORY METHOD TEST ************************************************************************
    @Test
    public void testCreatorCellSpaceManagerServiceImpl() throws JAXBException {
        CellSpaceManagerFactory.creatorCellSpaceManagerServiceImpl("surfaceTitle***","surface2ID***","firstAxis***","secondAxis***","SrsName***");
        System.out.println(cellSpaceManagerServiceimpl.getCellspace().toString());
    }
}