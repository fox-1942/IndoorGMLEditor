//
//
//package hu.iit.uni.miskolc.gml.editor.service.impl;
//
//import hu.iit.uni.miskolc.gml.editor.model.CellSpaceException;
//import hu.iit.uni.miskolc.gml.editor.model.CellSpaceManagerService;
//import net.opengis.gml.v_3_2_1.*;
//import net.opengis.indoorgml.core.v_1_0.*;
//import net.opengis.indoorgml.core.v_1_0.CellSpacePropertyType;
//import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
//
//import javax.xml.bind.*;
//import javax.xml.namespace.QName;
//import javax.xml.transform.Source;
//import javax.xml.transform.stream.StreamSource;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;
//
//import org.w3c.dom.Document;
//import sun.java2d.loops.SurfaceType;
//
////import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
//
//import static sun.management.Agent.error;
//
///**
// * Created by fox on 2017.07.26..
// */
//
//
//public class CellSpaceManagerServiceImpl implements CellSpaceManagerService {
//
//    private CellSpaceType cellSpace=new CellSpaceType() ;
//
//    private CellSpaceManagerService service;
//
//    private JAXBContext context = JAXBContext.newInstance(CellSpaceType.class);
//
//    // Constructor
//    public CellSpaceManagerServiceImpl(CellSpaceType cellSpace) throws JAXBException {
//        this.cellSpace = cellSpace;
//    }
//
//
//protected CellSpaceManagerServiceImpl(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException {
//    SurfacePropertyType surface = new SurfacePropertyType();
//
//    SurfaceType surface2 = new SurfaceType();
//
//    // surface ------------------------------------------
//    surface.setTitle(surfaceTitle);
//
//    // surface2 and uploading with data -----------------
//    surface2.setId(surface2ID);
//
//    List<String> list = new ArrayList<String>();
//    list.add(firstAxis);
//    list.add(secondAxis);
//    surface2.setAxisLabels(list);
//
//    surface2.setSrsName(SrsName);
//
//    // creating JAXBElement from surface2 ---------------
//    QName qName = new QName("http://www.opengis.net/gml/3.2", "Surface");
//    JAXBElement<SurfaceType> SurfaceTypeJAXBElement = new JAXBElement<SurfaceType>(qName, SurfaceType.class, surface2);
//
//    // adding surface2-JAXBElement (as parameter) to surface
//    surface.setAbstractSurface(SurfaceTypeJAXBElement);
//
//    // creating geometry --------------------------------
//    CellSpaceGeometryType geometry = new CellSpaceGeometryType();
//
//    // adding surface to geometry -----------------------
//    geometry.setGeometry2D(surface);
//
//    // adding geometry to cellSpace ---------------------
//
//    cellSpace.setCellSpaceGeometry(geometry);
//
//    // adding cellSpace to service ----------------------
//    service = new CellSpaceManagerServiceImpl(cellSpace);
//    }
//
//
//
//
//   public JAXBElement unmarshal(File inputFile) throws JAXBException {
//    // Unmarshalling implementation ------------------------------------------------------
//
//            Unmarshaller unmarshaller=context.createUnmarshaller();
//            File file2 = new File("main/resources/example.xml");
//            //unmarshaller.unmarshal(file2);
//
//            System.out.println("Unmarshalling: Completed.");
//            JAXBElement element = (JAXBElement) unmarshaller.unmarshal(file2);
//
//        return element;
//    }
//
//
//    ////////////////////////////////////////////////////////////////////
//
//    // Marshalling implementation -------------------------------------------------------
//    public void marshal(File outputFile) throws CellSpaceException {
//        Marshaller marshaller = null;
//
//        try {
//            marshaller = context.createMarshaller();
//        } catch (JAXBException e) {
//            throw new CellSpaceException(e.getMessage());
//        }
//
//
//        try {
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        } catch (PropertyException e) {
//            throw new CellSpaceException(e.getMessage());
//            // throw new Exception(e.getMessage());
//        }
//
//        try {
//            try {
//                marshaller.marshal(new JAXBElement<CellSpaceType>(new QName("http://www.opengis.net/gml/3.2", "cellspace"), CellSpaceType.class, cellSpace), new FileOutputStream(outputFile));
//            } catch (JAXBException e) {
//                throw new CellSpaceException(e.getMessage());
//            }
//        } catch (FileNotFoundException e) {
//            throw new CellSpaceException(e.getMessage());
//        }
//    }
//
//
//    @Override
//    public CellSpacePropertyType getGeometryProperty() {
//        return null;
//    }
//
//    @Override
//    public void set2DGeometry(SolidPropertyType solidPropertyType) {
//
//    }
//
//    @Override
//    public void set2DGeometry(CompositeSolidType compositeSolid) {
//
//    }
//
//    @Override
//    public SurfacePropertyType get2DGeometry() {
//        return null;
//    }
//
//    @Override
//    public void set2DGeometry(SurfacePropertyType surfacePropertyType) {
//
//    }
//
//    @Override
//    public SolidPropertyType get3DGeometryProperty() {
//        return null;
//    }
//
//    @Override
//    public void set3DGeometry(SolidPropertyType solidPropertyType) {
//
//    }
//
//    public CellSpaceType unmarshal2(File inputFile) throws CellSpaceException {
//        try {
//            Unmarshaller unmarshaller=context.createUnmarshaller();
//            JAXBElement<CellSpaceType> cellSpace2 = (JAXBElement) unmarshaller.unmarshal(inputFile);
//            return cellSpace2.getValue();
//        } catch (JAXBException e) {
//            throw new CellSpaceException(e.getMessage());
//        }
//    }
//
//    public CellSpaceType getCellspace() {
//        return this.cellSpace;
//        }
//    }