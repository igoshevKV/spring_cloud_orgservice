package com.optimagrowth.organizationservice.controller;

import com.optimagrowth.organizationservice.model.Organization;
import com.optimagrowth.organizationservice.service.OrganizationService;
import com.optimagrowth.organizationservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {

    private final static Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService service;

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug("OrganizationController getOrganization Correlation id:{}", UserContextHolder.getContext().getCorrelationId());
        return ResponseEntity.ok(service.findById(organizationId));
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.PUT)
    public ResponseEntity<Organization> updateOrganization(@PathVariable("organizationId") String id, @RequestBody Organization organization) {
        service.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(service.create(organization));
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("id") String id, @RequestBody Organization organization) {
        service.delete(organization);
    }


}
