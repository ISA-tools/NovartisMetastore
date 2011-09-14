package org.isatools.novartismetastore;

import org.isatools.isacreator.configuration.Ontology;
import org.isatools.isacreator.configuration.RecommendedOntology;
import org.isatools.isacreator.ontologymanager.OntologyService;
import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
import org.isatools.isacreator.ontologymanager.utils.DownloadUtils;
import org.isatools.novartismetastore.resource.ResourceDescription;
import org.isatools.novartismetastore.xml.MetastoreXMLHandler;
import org.isatools.novartismetastore.xml.ResourceXMLHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 12/09/2011
 *         Time: 16:51
 */
public class MetastoreClient implements OntologyService {

    public static OntologySourceRefObject resourceInformation;
    public static String queryURL;

    static {
        ResourceXMLHandler xmlHandler = new ResourceXMLHandler();
        ResourceDescription resourceDescription = xmlHandler.parseXML();
        resourceInformation = new OntologySourceRefObject(resourceDescription.getResourceAbbreviation(), "", "",
                resourceDescription.getResourceName());
        queryURL = resourceDescription.getQueryURL();
    }

    public Map<String, String> getOntologyNames() {
        return new HashMap<String, String>();
    }

    public Map<String, String> getTermMetadata(String s, String s1) {
        return new HashMap<String, String>();
    }

    public Map<OntologySourceRefObject, List<OntologyTerm>> getTermsByPartialNameFromSource(String term, String source, boolean b) {

        String query = queryURL.replace("$SEARCH_TERM", term);

        System.out.println("Searching for " + query);

        DownloadUtils.downloadFile(query, DownloadUtils.DOWNLOAD_FILE_LOC + term + DownloadUtils.XML_EXT);

        MetastoreXMLHandler xmlHandler = new MetastoreXMLHandler();

        try {
            Map<OntologySourceRefObject, List<OntologyTerm>> result = xmlHandler.parseXML(DownloadUtils.DOWNLOAD_FILE_LOC + term + DownloadUtils.XML_EXT);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new HashMap<OntologySourceRefObject, List<OntologyTerm>>();
        }
    }

    public Map<OntologySourceRefObject, List<OntologyTerm>> getTermsByPartialNameFromSource(String term, List<RecommendedOntology> recommendedOntologies) {
        return new HashMap<OntologySourceRefObject, List<OntologyTerm>>();
    }

    public Map<String, String> getOntologyVersions() {
        return new HashMap<String, String>();
    }

    public Map<String, OntologyTerm> getOntologyRoots(String s) {
        return new HashMap<String, OntologyTerm>();
    }

    public Map<String, OntologyTerm> getTermParent(String s, String s1) {
        return new HashMap<String, OntologyTerm>();
    }

    public Map<String, OntologyTerm> getTermChildren(String s, String s1) {
        return new HashMap<String, OntologyTerm>();
    }

    public Map<String, OntologyTerm> getAllTermParents(String s, String s1) {
        return new HashMap<String, OntologyTerm>();
    }

    public String getOntologyURL() {
        return null;
    }

    public List<Ontology> getAllOntologies() {
        return new ArrayList<Ontology>();
    }
}
