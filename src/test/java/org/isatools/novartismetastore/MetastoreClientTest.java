package org.isatools.novartismetastore;

import org.isatools.isacreator.configuration.Ontology;
import org.isatools.isacreator.configuration.RecommendedOntology;
import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
import org.isatools.novartismetastore.resource.ResourceDescription;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

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
        Map<OntologySourceRefObject, List<OntologyTerm>> result = client.searchRepository("colon cancer");

        System.out.println("There are " + result.size() + " results");
        for (OntologySourceRefObject source : result.keySet()) {
            System.out.println("For " + source.getSourceName());

            for (OntologyTerm term : result.get(source)) {
                System.out.println("\t" + term.getUniqueId() + " term name is: " + term.getOntologyTermName());
            }
        }

        assertTrue("Not the correct number of source references. Should be 2.", result.size() == 2);
    }

    @Test
    public void getTermsByPartialNameFromSourceWithRecommended() {
        MetastoreClient client = new MetastoreClient();
        Map<OntologySourceRefObject, List<OntologyTerm>> result = client.searchRepository("colon cancer", Collections.singletonMap("NVMIS2", new RecommendedOntology(new Ontology("12312", "1.0", "NVMIS2", "Novartis Metastore"))), false);

        System.out.println("There are " + result.size() + " results");
        for (OntologySourceRefObject source : result.keySet()) {
            System.out.println("For " + source.getSourceName());

            for (OntologyTerm term : result.get(source)) {
                System.out.println("\t" + term.getUniqueId() + " term name is: " + term.getOntologyTermName());
            }
        }

        assertTrue("Not the correct number of source references. Should be 1.", result.size() == 1);
    }

    @Test
    public void checkIfResourceIsAvailableForField() {
        MetastoreClient client = new MetastoreClient();

        String field = "metabolite profiling using NMR spectroscopy:>Label";

        for(ResourceDescription resourceDescription : MetastoreClient.resourceInformation) {
            boolean allowed = client.checkIfResourceHasField(resourceDescription, field);
            System.out.println(resourceDescription.getResourceAbbreviation() + (allowed ? " can " : " can not ") + " be used to search on " + field);
        }
    }

}
