package org.isatools.novartismetastore.xml;

import org.isatools.novartismetastore.resource.ResourceDescription;
import uk.ac.ebi.utils.xml.XPathReader;

import javax.xml.xpath.XPathConstants;

public class ResourceXMLHandler {

    public static final String resourceFileLocation = "/resource-description.xml";

    public ResourceDescription parseXML() {
        XPathReader reader = new XPathReader(getClass().getResourceAsStream(resourceFileLocation));

        String name = (String) reader.read("/resource/name", XPathConstants.STRING);
        String abbreviation = (String) reader.read("/resource/abbreviation", XPathConstants.STRING);
        String queryURL = (String) reader.read("/resource/queryURL", XPathConstants.STRING);

        return new ResourceDescription(name, abbreviation, queryURL);
    }

    public static void main(String[] args) {
        ResourceXMLHandler handler = new ResourceXMLHandler();
        ResourceDescription desc = handler.parseXML();

        System.out.println(desc.getResourceName());
    }
}
