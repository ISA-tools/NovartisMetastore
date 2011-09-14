package org.isatools.novartismetastore;

import org.isatools.novartismetastore.resource.MetastoreResult;
import org.isatools.novartismetastore.resource.ResourceDescription;
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
public class MetastoreClient {

    public static ResourceDescription resourceInformation;
    public static String queryURL;

    static {
        ResourceXMLHandler xmlHandler = new ResourceXMLHandler();
        resourceInformation = xmlHandler.parseXML();
        queryURL = resourceInformation.getQueryURL();
    }


    public List<MetastoreResult> getTermsByPartialNameFromSource(String term) {

        String query = queryURL.replace("$SEARCH_TERM", term);

        System.out.println("Searching for " + query);

        DownloadUtils.downloadFile(query, DownloadUtils.DOWNLOAD_FILE_LOC + term + DownloadUtils.XML_EXT);

        MetastoreXMLHandler xmlHandler = new MetastoreXMLHandler();

        try {
            List<MetastoreResult> result = xmlHandler.parseXML(DownloadUtils.DOWNLOAD_FILE_LOC + term + DownloadUtils.XML_EXT);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<MetastoreResult>();
        }
    }

}
