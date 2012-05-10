package org.isatools.novartismetastore.xml;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;
import org.isatools.novartismetastore.resource.ResourceDescription;
import org.isatools.novartismetastore.resource.ResourceField;
import org.w3c.dom.NodeList;
import uk.ac.ebi.utils.xml.XPathReader;

import javax.xml.xpath.XPathConstants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ResourceXMLHandler {

    public static final String resourceFileLocation = "config/resource-description.xml";

    public List<ResourceDescription> parseXML() {
        try {
            XPathReader reader = new XPathReader(new FileInputStream(resourceFileLocation));

            NodeList resources = (NodeList) reader.read("/resources/resource", XPathConstants.NODESET);

            if (resources.getLength() > 0) {

                List<ResourceDescription> resourceDescriptions = new ArrayList<ResourceDescription>();

                for (int resourceIndex = 0; resourceIndex <= resources.getLength(); resourceIndex++) {


                    String name = (String) reader.read("resources/resource[" + resourceIndex + "]/@name", XPathConstants.STRING);
                    String abbreviation = (String) reader.read("resources/resource[" + resourceIndex + "]/@abbreviation", XPathConstants.STRING);
                    String resourceVersion = (String) reader.read("resources/resource[" + resourceIndex + "]/@version", XPathConstants.STRING);
                    String queryURL = (String) reader.read("resources/resource[" + resourceIndex + "]/@queryURL", XPathConstants.STRING);

                    if (!name.isEmpty()) {
                        ResourceDescription newDescription = new ResourceDescription(name, abbreviation, resourceVersion, queryURL);


                        NodeList fields = (NodeList) reader.read("/resources/resource[" + resourceIndex + "]/fields/field", XPathConstants.NODESET);

                        if (fields.getLength() > 0) {
                            MultiMap<String, ResourceField> resourceFields = new MultiHashMap<String, ResourceField>();

                            for (int fieldIndex = 0; fieldIndex <= fields.getLength(); fieldIndex++) {
                                String fieldName = (String) reader.read("resources/resource[" + resourceIndex + "]/fields/field[" + fieldIndex + "]/@name", XPathConstants.STRING);
                                String assayMeasurement = (String) reader.read("resources/resource[" + resourceIndex + "]/fields/field[" + fieldIndex + "]/@assay-measurement", XPathConstants.STRING);
                                String assayTechnology = (String) reader.read("resources/resource[" + resourceIndex + "]/fields/field[" + fieldIndex + "]/@assay-technology", XPathConstants.STRING);

                                if (!fieldName.isEmpty()) {
                                    resourceFields.put(fieldName, new ResourceField(fieldName,
                                            assayTechnology == null ? "" : assayTechnology,
                                            assayMeasurement == null ? "" : assayMeasurement));
                                }
                            }

                            newDescription.setResourceFields(resourceFields);
                        }

                        resourceDescriptions.add(newDescription);
                    }
                }

                return resourceDescriptions;

            }
            return new ArrayList<ResourceDescription>();

        } catch (FileNotFoundException e) {
            return new ArrayList<ResourceDescription>();
        }
    }
}
