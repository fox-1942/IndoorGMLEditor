package hu.iit.uni.miskolc.gml.editor.service.impl;

import javax.xml.bind.JAXBException;

//Static Factory method

public class CellSpaceManagerFactory {
    public static CellSpaceManagerServiceImpl creatorCellSpaceManagerServiceImpl(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException
    {
        CellSpaceManagerServiceImpl cellSpaceManagerServiceImpl = new CellSpaceManagerServiceImpl(
                surfaceTitle, surface2ID, firstAxis, secondAxis, SrsName);
        return cellSpaceManagerServiceImpl;
    }
}