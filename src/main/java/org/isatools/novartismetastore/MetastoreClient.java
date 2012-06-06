package org.isatools.novartismetastore;

import org.isatools.isacreator.configuration.RecommendedOntology;
import org.isatools.isacreator.gui.ApplicationManager;
import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
import org.isatools.isacreator.plugins.host.service.PluginOntologyCVSearch;
import org.isatools.isacreator.plugins.registries.OntologySearchPluginRegistry;
import org.isatools.novartismetastore.resource.MetastoreResult;
import org.isatools.novartismetastore.resource.ResourceDescription;
import org.isatools.novartismetastore.resource.ResourceField;
import org.isatools.novartismetastore.utils.Convert;
import org.isatools.novartismetastore.xml.MetastoreXMLHandler;
import org.isatools.novartismetastore.xml.ResourceXMLHandler;
import uk.ac.ebi.utils.io.DownloadUtils;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 12/09/2011
 *         Time: 16:51
 */
public class MetastoreClient implements PluginOntologyCVSearch {

    public static List<ResourceDescription> resourceInformation;

    static {
        ResourceXMLHandler xmlHandler = new ResourceXMLHandler();
        resourceInformation = xmlHandler.parseXML();
    }

    public Map<OntologySourceRefObject, List<OntologyTerm>> searchRepository(String term) {
        return searchRepository(term, new HashMap<String, RecommendedOntology>(), true);

    }

    /**
     * Searches on the premise that we are only looking for recommended ontologies, which should only be called if
     * the field being populated is specifed in the resource description or if the recommended ontologies flag has been set for this field.
     *
     * @param term                  - term to search for
     * @param recommendedOntologies - recommended ontologies.
     * @return Map<OntologySourceRefObject, List<OntologyTerm>> of the source to the terms found for that source.
     */
    public Map<OntologySourceRefObject, List<OntologyTerm>> searchRepository(String term, Map<String, RecommendedOntology> recommendedOntologies, boolean searchAll) {
        Map<OntologySourceRefObject, List<OntologyTerm>> results = new HashMap<OntologySourceRefObject, List<OntologyTerm>>();
        for (ResourceDescription resourceDescription : resourceInformation) {
            String fieldDetails = ApplicationManager.getCurrentlySelectedFieldName();
            // only do the search if the field matches one expected by the system
            if (searchAll) {
                results.putAll(performQuery(term, resourceDescription));
            } else {
                if (checkIfResourceHasField(resourceDescription, fieldDetails) || checkIfResourceIsRecommended(resourceDescription, recommendedOntologies)) {
                    System.out.println("Querying on " + resourceDescription.getResourceName() + "for " + term + " on " + fieldDetails);
                    results.putAll(performQuery(term, resourceDescription));
                }
            }
        }
        return results;
    }

    /**
     * We can check against current assay and the field
     *
     * @param resourceDescription - resource to check
     * @param fieldDetails        - field to look for
     * @return true or false. True if the resource should be searched on for this field.
     */
    public boolean checkIfResourceHasField(ResourceDescription resourceDescription, String fieldDetails) {
        if (fieldDetails == null) {
            return false;
        } else {
            String fieldName = fieldDetails.substring(fieldDetails.lastIndexOf(":>") + 2).trim();

            if (resourceDescription.getResourceFields().containsKey(fieldName)) {
                String assayMeasurement, assayTechnology = "";
                if (fieldDetails.contains("using")) {
                    String[] fields = fieldDetails.split("using");
                    assayMeasurement = fields[0].trim();
                    assayTechnology = fields[1].substring(0, fields[1].lastIndexOf(":>")).trim();
                } else {
                    assayMeasurement = fieldDetails.substring(0, fieldDetails.lastIndexOf(":>"));
                }

                for (ResourceField resourceField : resourceDescription.getResourceFields().get(fieldName)) {
                    if (resourceField.getAssayMeasurement().isEmpty()) {
                        return true;
                    } else if (assayMeasurement.equalsIgnoreCase(resourceField.getAssayMeasurement())
                            && assayTechnology.equalsIgnoreCase(resourceField.getAssayTechnology())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private boolean checkIfResourceIsRecommended(ResourceDescription resourceDescription, Map<String, RecommendedOntology> recommendedOntologies) {
        for (String ontology : recommendedOntologies.keySet()) {
            if (recommendedOntologies.get(ontology).getOntology().getOntologyAbbreviation().equals(resourceDescription.getResourceAbbreviation())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPreferredResourceForCurrentField(Map<String, RecommendedOntology> recommendedOntologies) {
        for (ResourceDescription resourceDescription : resourceInformation) {
            String fieldDetails = ApplicationManager.getCurrentlySelectedFieldName();
            if (checkIfResourceHasField(resourceDescription, fieldDetails) || checkIfResourceIsRecommended(resourceDescription, recommendedOntologies)) {
                return true;
            }
        }
        return false;
    }

    private Map<OntologySourceRefObject, List<OntologyTerm>> performQuery(String term, ResourceDescription resourceDescription) {
        String query = resourceDescription.getQueryURL().replace("$SEARCH_TERM", term);

        System.out.println("Searching for " + query);

        String downloadLocation = DownloadUtils.DOWNLOAD_FILE_LOC + term + "-" + resourceDescription.getResourceAbbreviation() + DownloadUtils.XML_EXT;

        DownloadUtils.downloadFile(query, downloadLocation);

        MetastoreXMLHandler xmlHandler = new MetastoreXMLHandler();

        try {
            List<MetastoreResult> result = xmlHandler.parseXML(downloadLocation, resourceDescription);
            return Convert.convertMetastoreResult(result, resourceDescription);

        } catch (FileNotFoundException e) {
            System.out.println("No file found. Assuming connection is down...");
            e.printStackTrace();
            return new HashMap<OntologySourceRefObject, List<OntologyTerm>>();
        }
    }

    public Set<String> getAvailableResourceAbbreviations() {
        Set<String> resources = new HashSet<String>();
        for(ResourceDescription resource : resourceInformation) {
            resources.add(resource.getResourceAbbreviation());
        }
        return resources;
    }

    public void registerSearch() {
        OntologySearchPluginRegistry.registerPlugin(this);
    }

    public void deregisterSearch() {
        OntologySearchPluginRegistry.deregisterPlugin(this);
    }


}
