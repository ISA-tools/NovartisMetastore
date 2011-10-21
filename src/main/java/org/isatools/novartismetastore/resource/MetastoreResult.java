package org.isatools.novartismetastore.resource;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 14/09/2011
 *         Time: 21:01
 */
public class MetastoreResult {

    private String id;
    private String token;
    private String species;
    private ResourceDescription parentResource;

    public MetastoreResult(String id, String token, String species, ResourceDescription parentResource) {
        this.id = id;
        this.token = token;
        this.species = species;
        this.parentResource = parentResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public ResourceDescription getParentResource() {
        return parentResource;
    }

    public void setParentResource(ResourceDescription parentResource) {
        this.parentResource = parentResource;
    }
}
