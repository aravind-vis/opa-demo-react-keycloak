package com.opademo.security.opa;

import com.fasterxml.jackson.databind.JsonNode;
import com.opademo.security.SecurityUtils;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.LogManager;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import security.build.pdp.client.PDPClient;

@Component("opaClient")
public class OPAClient {

    private static Logger logger = LoggerFactory.getLogger(OPAClient.class);

    private final PDPClient pdpClient;

    @Autowired
    public OPAClient(PDPClient pdpClient) {
        this.pdpClient = pdpClient;
    }

    public boolean authRequest(String operation) {
        logger.info("Calling Auth Request");

        Map<String, Object> input = new HashMap<>();
        input.put("operation", operation);
        input.put("role", SecurityUtils.getAuthoritiesAsString());

        JsonNode node = null;
        try {
            node = pdpClient.getJsonResponse(Map.of("input", input));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.info("Received Response--->{}", node);
        return node.get("result") != null && node.get("result").get("allow") != null && node.get("result").get("allow").asBoolean();
    }
}
