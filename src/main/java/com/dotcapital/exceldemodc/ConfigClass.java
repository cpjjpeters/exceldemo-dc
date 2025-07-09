package com.dotcapital.exceldemodc;

import jakarta.annotation.PostConstruct;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.context.annotation.Configuration;

/* carlpeters created on 08/07/2025 inside the package - com.dotcapital.exceldemodc */
@Configuration
public class ConfigClass {
    @PostConstruct
    public void init() {
        ZipSecureFile.setMaxFileCount(4000);
    }
}
