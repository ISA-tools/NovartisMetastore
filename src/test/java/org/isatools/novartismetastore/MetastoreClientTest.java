package org.isatools.novartismetastore;

import org.isatools.novartismetastore.resource.MetastoreResult;
import org.junit.Test;

import java.util.List;

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
        List<MetastoreResult> result = client.getTermsByPartialNameFromSource("colon");

        System.out.println("There are " + result.size() + " results");
        for (MetastoreResult term : result) {
            System.out.println(term.getToken() + "(" + term.getId() + ")");
        }
    }

}
