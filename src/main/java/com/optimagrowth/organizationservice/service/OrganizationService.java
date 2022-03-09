package com.optimagrowth.organizationservice.service;

import com.optimagrowth.organizationservice.events.source.SimpleSourceBean;
import com.optimagrowth.organizationservice.model.Organization;
import com.optimagrowth.organizationservice.repository.OrganizationRepository;
import com.optimagrowth.organizationservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {
    private final static Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    public SimpleSourceBean simpleSourceBean;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        return (opt.isPresent()) ? opt.get() : null;
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        simpleSourceBean.publishOrganizationChange(SimpleSourceBean.ActionEnum.CREATED,organization.getId());
        return organization;
    }

    public void update(Organization organization) {
        repository.save(organization);
        simpleSourceBean.publishOrganizationChange(SimpleSourceBean.ActionEnum.UPDATED,organization.getId());
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        simpleSourceBean.publishOrganizationChange(SimpleSourceBean.ActionEnum.DELETED,organization.getId());
    }
}
