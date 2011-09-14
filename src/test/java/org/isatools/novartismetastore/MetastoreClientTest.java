package org.isatools.novartismetastore;

import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
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
        Map<OntologySourceRefObject, List<OntologyTerm>> result = client.getTermsByPartialNameFromSource("colon", "", false);

        for (List<OntologyTerm> terms : result.values()) {
            System.out.println("There are " + terms.size() + " results");
            for (OntologyTerm term : terms) {
                System.out.println(term.getOntologyTermName() + "(" + term.getOntologySourceAccession() + ")");
            }
        }
    }

}
