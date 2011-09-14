package org.isatools.novartismetastore.xml;

import org.isatools.isacreator.io.xpath.XPathReader;
import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;

import org.isatools.novartismetastore.MetastoreClient;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 12/09/2011
 *         Time: 16:44
 */
public class MetastoreXMLHandler {

    public Map<OntologySourceRefObject, List<OntologyTerm>> parseXML(String xml) throws FileNotFoundException {
        XPathReader reader = new XPathReader(new FileInputStream(xml));

        List<OntologyTerm> terms = new ArrayList<OntologyTerm>();

        NodeList platforms = (NodeList) reader.read("/synonyms/synonym", XPathConstants.NODESET);

        if (platforms.getLength() > 0) {

            for (int sectionIndex = 0; sectionIndex <= platforms.getLength(); sectionIndex++) {
                String id = (String) reader.read("/synonyms/synonym[" + sectionIndex + "]/@id", XPathConstants.STRING);
                String token = (String) reader.read("/synonyms/synonym[" + sectionIndex + "]/token", XPathConstants.STRING);
                if (!id.equalsIgnoreCase("")) {
                    terms.add(new OntologyTerm(token, id, new OntologySourceRefObject()));
                }
            }
        }
        return Collections.singletonMap(MetastoreClient.resourceInformation, terms);
    }
}
