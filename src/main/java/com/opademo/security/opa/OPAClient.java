package com.opademo.security.opa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        Map<String, Object> input = createInputMap(operation);

        var node = callPdp(input);

        return parseResult(node);
    }

    public boolean authRequest(String operation, Map<String, Object> additionalInput) {
        logger.info("AuthRequest with data");
        Map<String, Object> input = createInputMap(operation);
        input.putAll(additionalInput);
        return parseResult(callPdp(input));
    }

    private Map<String, Object> createInputMap(String operation) {
        Map<String, Object> input = new HashMap<>();
        input.put("operation", operation);
        input.put("role", SecurityUtils.getAuthoritiesAsString());
        input.put("user", SecurityUtils.getCurrentUserLogin());
        return input;
    }

    private JsonNode callPdp(Map<String, Object> input) {
        JsonNode node = null;
        try {
            node = pdpClient.getJsonResponse(Map.of("input", input));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.info("Received Response--->{}", node);
        return node == null ? new ObjectMapper().createObjectNode() : node;
    }

    private boolean parseResult(JsonNode node) {
        return node.get("result") != null && node.get("result").get("allow") != null && node.get("result").get("allow").asBoolean();
    }
}
