package com.opademo.web.rest;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/opa")
public class OPAResource {

    @Value("classpath:opapolicy/opademo/bundle/bundle.tar.gz")
    private Resource bundleResource;

    @Value("${upload.path}")
    private String path;

    @RequestMapping("/policy")
    public void downloadPolicyBundle(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/gzip");
        response.addHeader("Content-Disposition", "attachment;filename=bundle.tar.gz");
        try {
            IOUtils.copy(bundleResource.getInputStream(), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred downloading bundle file");
            } catch (Exception e) {
                //nothing can be done
            }
        }
    }
}
