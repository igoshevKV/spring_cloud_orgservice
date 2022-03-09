package com.optimagrowth.organizationservice.events.source;

import com.optimagrowth.organizationservice.events.model.OrganizationChangeModel;
import com.optimagrowth.organizationservice.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class SimpleSourceBean {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishOrganizationChange(ActionEnum action, String organizationId) {
        logger.debug("sending Kafka message: {} for organization: {}", action, organizationId);
        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContext.getCorrelationId());
        source.output().send(MessageBuilder.withPayload(change).build());
    }

    public enum ActionEnum {
        GET,
        CREATED,
        UPDATED,
        DELETED
    }
}
