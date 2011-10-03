package org.isatools.novartismetastore;

import org.isatools.isacreator.ontologymanager.OntologySourceRefObject;
import org.isatools.isacreator.ontologymanager.common.OntologyTerm;
import org.isatools.isacreator.plugins.host.service.PluginOntologyCVSearch;
import org.isatools.isacreator.plugins.registries.OntologySearchPluginRegistry;
import org.isatools.novartismetastore.resource.MetastoreResult;
import org.isatools.novartismetastore.resource.ResourceDescription;
import org.isatools.novartismetastore.utils.Convert;
import org.isatools.novartismetastore.xml.MetastoreXMLHandler;
import org.isatools.novartismetastore.xml.ResourceXMLHandler;
import uk.ac.ebi.utils.io.DownloadUtils;

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
public class MetastoreClient implements PluginOntologyCVSearch {

    public static ResourceDescription resourceInformation;
    public static String queryURL;

    static {
        ResourceXMLHandler xmlHandler = new ResourceXMLHandler();
        resourceInformation = xmlHandler.parseXML();
        queryURL = resourceInformation.getQueryURL();
    }


    public Map<OntologySourceRefObject, List<OntologyTerm>> searchRepository(String term) {

        String query = queryURL.replace("$SEARCH_TERM", term);

        System.out.println("Searching for " + query);

        DownloadUtils.downloadFile(query, DownloadUtils.DOWNLOAD_FILE_LOC + term + DownloadUtils.XML_EXT);

        MetastoreXMLHandler xmlHandler = new MetastoreXMLHandler();

        try {
            List<MetastoreResult> result = xmlHandler.parseXML(DownloadUtils.DOWNLOAD_FILE_LOC + term + DownloadUtils.XML_EXT);
            return Convert.convertMetastoreResult(result);

        } catch (FileNotFoundException e) {
            System.out.println("No file found. Assuming connection is down...");
            e.printStackTrace();
            return new HashMap<OntologySourceRefObject, List<OntologyTerm>>();
        }
    }

    public void registerSearch() {
        OntologySearchPluginRegistry.registerPlugin(this);
    }

    public void deregisterSearch() {
        OntologySearchPluginRegistry.deregisterPlugin(this);
    }

}
