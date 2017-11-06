package hu.iit.uni.miskolc.gml.editor.service.impl;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Implementation of {@link NamespacePrefixMapper} that maps the schema
 * namespaces more to readable names. Used by the jaxb marshaller. Requires
 * setting the property "com.sun.xml.bind.namespacePrefixMapper" to an instance
 * of this class.
 * <p>
 * Requires dependency on JAXB implementation jars
 * </p>
 */

public class IndoorGMLNameSpaceMapperImpl extends NamespacePrefixMapper {
    private static final String DEFAULT_PREFIX = "";
    private static final String DEFAULT_URI = "http://www.opengis.net/indoorgml/1.0/core";

    private static final String GML_PREFIX = "gml";
    private static final String GML_URI = "http://www.opengis.net/gml/3.2";

    private static final String XLINK_PREFIX = "xlink";
    private static final String XLINK_URI = "http://www.w3.org/1999/xlink";


    /** @link getPrefferedPrefix
     *  Returns a namespace prefix for the given namespaceUri
     */
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if(DEFAULT_URI.equals(namespaceUri)) {
            return DEFAULT_PREFIX;
        } else if(GML_URI.equals(namespaceUri)) {
            return GML_PREFIX;
        } else if(XLINK_URI.equals(namespaceUri)) {
            return XLINK_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { DEFAULT_URI, GML_URI, XLINK_URI };
    }
}

