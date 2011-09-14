package org.isatools.novartismetastore.xml;


import org.isatools.novartismetastore.MetastoreClient;
import org.isatools.novartismetastore.resource.MetastoreResult;
import org.w3c.dom.NodeList;
import uk.ac.ebi.utils.xml.XPathReader;

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

    public List<MetastoreResult> parseXML(String xml) throws FileNotFoundException {
        XPathReader reader = new XPathReader(new FileInputStream(xml));

        List<MetastoreResult> terms = new ArrayList<MetastoreResult>();

        NodeList platforms = (NodeList) reader.read("/synonyms/synonym", XPathConstants.NODESET);

        if (platforms.getLength() > 0) {

            for (int sectionIndex = 0; sectionIndex <= platforms.getLength(); sectionIndex++) {
                String id = (String) reader.read("/synonyms/synonym[" + sectionIndex + "]/@id", XPathConstants.STRING);
                String token = (String) reader.read("/synonyms/synonym[" + sectionIndex + "]/token", XPathConstants.STRING);
                if (!id.equalsIgnoreCase("")) {
                    terms.add(new MetastoreResult(id, token, MetastoreClient.resourceInformation));
                }
            }

            return terms;
        }
        return new ArrayList<MetastoreResult>();
    }

}
