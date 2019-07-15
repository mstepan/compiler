package com.max.compiler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;


final class Main {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Main() throws IOException {

        LOG.info("java version: {}", System.getProperty("java.version"));
    }


    public static void main(String[] args) {
        try {
            new Main();
        }
        catch (Exception ex) {
            LOG.error("Error occurred", ex);
        }
    }
}
