package org.isatools.novartismetastore;

import org.isatools.isacreator.plugins.host.service.PluginOntologyCVSearch;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 03/10/2011
 *         Time: 16:06
 */
public class Activator implements BundleActivator {


    private BundleContext context = null;


    public void start(BundleContext context) {
        this.context = context;

        Hashtable dict = new Hashtable();
        context.registerService(
                PluginOntologyCVSearch.class.getName(), new MetastoreClient(), dict);
    }


    public void stop(BundleContext context) {
    }
}