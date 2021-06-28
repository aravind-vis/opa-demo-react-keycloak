package com.opademo.security.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opademo.IntegrationTest;
import com.opademo.OpademoreactkeycloakApp;
import com.opademo.config.TestSecurityConfiguration;
import com.opademo.security.AuthoritiesConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

@IntegrationTest
class CustomClaimConverterIT {

    private static final String USERNAME = "admin";
    private static final String NAME = "John";
    private static final String FAMILY_NAME = "Doe";

    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private CustomClaimConverter customClaimConverter;

    @BeforeEach
    public void initTest() {
        customClaimConverter = new CustomClaimConverter(clientRegistrationRepository.findByRegistrationId("oidc"), restTemplate);
    }

    private void mockHttpGetUserInfo(ObjectNode userInfo) {
        when(
            restTemplate.exchange(
                eq("https://api.jhipster.org/user"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<Class<ObjectNode>>any()
            )
        )
            .thenReturn(ResponseEntity.ok(userInfo));
    }

    @Test
    void testConvert() {
        // GIVEN
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "123");
        // AND
        ObjectNode user = mapper.createObjectNode();
        user.put("preferred_username", USERNAME);
        user.put("given_name", NAME);
        user.put("family_name", FAMILY_NAME);
        user.putArray("groups").add(AuthoritiesConstants.ADMIN).add(AuthoritiesConstants.USER);
        mockHttpGetUserInfo(user);

        // WHEN
        Map<String, Object> convertedClaims = customClaimConverter.convert(claims);

        // THEN
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", USERNAME)
            .containsEntry("given_name", NAME)
            .containsEntry("family_name", FAMILY_NAME)
            .containsEntry("groups", Arrays.asList(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER));
    }

    @Test
    void testConvert_withoutGroups() {
        // GIVEN
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "123");
        // AND
        ObjectNode user = mapper.createObjectNode();
        user.put("preferred_username", USERNAME);
        user.put("given_name", NAME);
        user.put("family_name", FAMILY_NAME);
        mockHttpGetUserInfo(user);

        // WHEN
        assertThatCode(() -> customClaimConverter.convert(claims)).doesNotThrowAnyException();
    }
}
