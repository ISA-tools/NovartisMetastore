package org.isatools.novartismetastore.utils;

import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
import org.isatools.novartismetastore.MetastoreClient;
import org.isatools.novartismetastore.resource.MetastoreResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 03/10/2011
 *         Time: 17:04
 */
public class Convert {

    public static Map<OntologySourceRefObject, List<OntologyTerm>> convertMetastoreResult(List<MetastoreResult> metastoreResults) {
        Map<OntologySourceRefObject, List<OntologyTerm>> convertedResult = new HashMap<OntologySourceRefObject, List<OntologyTerm>>();

        OntologySourceRefObject source = new OntologySourceRefObject(
                MetastoreClient.resourceInformation.getResourceAbbreviation(), "", "", MetastoreClient.resourceInformation.getResourceName());

        convertedResult.put(source, new ArrayList<OntologyTerm>());

        for(MetastoreResult result : metastoreResults) {
            convertedResult.get(source).add(new OntologyTerm(result.getToken(), result.getId(), source));
        }

        return convertedResult;
    }
}
