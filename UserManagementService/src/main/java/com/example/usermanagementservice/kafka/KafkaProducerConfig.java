package com.example.usermanagementservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import static com.example.usermanagementservice.common.Constants.*;

@Configuration
public class KafkaProducerConfig {


//    @Bean
//    public KafkaAdmin.NewTopics applicationKafkaTopics() {
//        return new KafkaAdmin.NewTopics(
//                new NewTopic(CREATE_WORKSPACE_TOPIC, 5, (short) 1),
//                new NewTopic(UPDATE_WORKSPACE_TOPIC, 5, (short) 1),
//                new NewTopic(DELETE_WORKSPACE_TOPIC, 5, (short) 1),
//                new NewTopic(ADD_MEMBER_TOPIC, 5, (short) 1),
//                new NewTopic(REMOVE_MEMBER_TOPIC, 5, (short) 1)
//        );
//    }
}