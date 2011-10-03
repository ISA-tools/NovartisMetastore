package org.isatools.novartismetastore;

import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
import org.isatools.novartismetastore.resource.MetastoreResult;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 12/09/2011
 *         Time: 16:55
 */
public class MetastoreClientTest {

    @Test
    public void getTermsByPartialNameFromSource() {
        MetastoreClient client = new MetastoreClient();
        Map<OntologySourceRefObject, List<OntologyTerm>> result = client.searchRepository("colon");

        System.out.println("There are " + result.size() + " results");
        for (OntologySourceRefObject source : result.keySet()) {
            System.out.println("For " + source.getSourceName());

            for(OntologyTerm term : result.get(source)) {
                System.out.println("\t" + term.getUniqueId());
            }
        }
    }

}
