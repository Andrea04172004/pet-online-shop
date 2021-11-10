package com.sazonov.mainonlineshop.cucumberintegration;

import com.sazonov.mainonlineshop.cucumberintegration.configuration.reader.Config;
import com.sazonov.mainonlineshop.cucumberintegration.configuration.reader.properties.PropertiesLoader;
import com.sazonov.mainonlineshop.cucumberintegration.configuration.reader.properties.Property;
import com.sazonov.mainonlineshop.cucumberintegration.configuration.reader.properties.PropertyFile;

@PropertyFile("config.properties")
public class TestsConfig extends Config {
    private static TestsConfig config;

    public static TestsConfig getConfig() {
        if (config == null) {
            config = new TestsConfig();
        }
        return config;
    }

    public TestsConfig() {
        PropertiesLoader.populate(this);
    }

    @Property("base.url")
    private String baseUrl;

    /**
     * @return Test Object URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

}
