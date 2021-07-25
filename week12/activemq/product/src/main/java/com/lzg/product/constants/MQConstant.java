package com.lzg.product.constants;

/**
 * @Author lzg
 * @Date 2021-07-22 16:02
 */
public interface MQConstant {
    String QUEUE_NAME = "Test-Queue";
    String TOPIC_NAME = "Test-Topic";
    String CONSUMER_CLIENT_ID = "Client-1";
    String DURABLE_SUBSCRIBER_NAME = "Durable-Subscriber";
    int MESSAGE_NUM = 5;

    String JMS_TOPIC_TEMPLATE = "jmsTopicTemplate";
    String JMS_QUEUE_TEMPLATE = "jmsQueueTemplate";

    String JMS_MESSAGE_TOPIC_TEMPLATE = "jmsMsgTopicTemplate";
    String JMS_MESSAGE_QUEUE_TEMPLATE = "jmsMsgQueueTemplate";

    String QUEUE_LISTENER_CONTAINER_FACTORY = "queueListenerFactory";
    String TOPIC_LISTENER_CONTAINER_FACTORY = "topicListenerFactory";

    interface QueueNames{
        String REQUEST_QUEUE = "Reply-Queue";
        String RESPONSE_QUEUE = "Response-Queue";
    }

}
