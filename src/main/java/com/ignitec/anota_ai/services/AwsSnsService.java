package com.ignitec.anota_ai.services;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.ignitec.anota_ai.dtos.MessageDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {

    AmazonSNS amazonSNS;
    Topic catalogTopic;

    public AwsSnsService(AmazonSNS amazonSNS, @Qualifier("catalogEventsTopic") Topic catalogTopic) {
        this.amazonSNS = amazonSNS;
        this.catalogTopic = catalogTopic;
    }

    public void publishe(MessageDto messageDto) {
        this.amazonSNS.publish(catalogTopic.getTopicArn(), messageDto.message());
    }
}
