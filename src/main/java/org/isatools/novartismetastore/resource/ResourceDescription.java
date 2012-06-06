package org.isatools.novartismetastore.resource;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

import java.util.HashMap;
import java.util.Map;

public class ResourceDescription {

    private String resourceName;
    private String resourceAbbreviation;
    private String resourceVersion;
    private String queryURL;

    private MultiMap<String, ResourceField> resourceFields;

    public ResourceDescription(String resourceName, String resourceAbbreviation, String resourceVersion, String queryURL) {
        this(resourceName, resourceAbbreviation, resourceVersion, queryURL, new MultiHashMap<String, ResourceField>());
    }

    public ResourceDescription(String resourceName, String resourceAbbreviation, String resourceVersion, String queryURL, MultiMap<String, ResourceField> resourceFields) {
        this.resourceName = resourceName;
        this.resourceAbbreviation = resourceAbbreviation;
        this.resourceVersion = resourceVersion;
        this.queryURL = queryURL;
        this.resourceFields = resourceFields;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceAbbreviation() {
        return resourceAbbreviation;
    }

    public String getQueryURL() {
        return queryURL;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public MultiMap<String, ResourceField> getResourceFields() {
        return resourceFields;
    }

    public void setResourceFields(MultiMap<String, ResourceField> resourceFields) {
        this.resourceFields = resourceFields;
    }
}

