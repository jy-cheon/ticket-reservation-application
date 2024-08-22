package io.jeeyeon.app.ticketReserve;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageSender;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 3,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092"
        },
        ports = { 9092 })
public class KafkaApplicationTests {

    @Autowired
    private PaymentMessageSender messageSender;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private final CountDownLatch latch = new CountDownLatch(1);

    private PaymentMessage receivedMessage;

    // 카프카 메세지 발행 및 소비 테스트
    @Test
    void contextLoads() throws InterruptedException, JsonProcessingException {
        // Set up Kafka Consumer properties
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, PaymentMessage.class.getName());

        ConsumerFactory<String, PaymentMessage> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        // Create a Kafka Listener Container Factory
        ConcurrentKafkaListenerContainerFactory<String, PaymentMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setAutoStartup(true);

        // Create and start the listener container
        ConcurrentMessageListenerContainer<String, PaymentMessage> container = factory.createContainer("paymentTopic");

        container.getContainerProperties().setMessageListener(new MessageListener<String, PaymentMessage>() {
            @Override
            public void onMessage(org.apache.kafka.clients.consumer.ConsumerRecord<String, PaymentMessage> record) {
                System.out.println("Received message: " + record.value());
                receivedMessage = record.value();
                latch.countDown();
            }
        });

        container.start();

        // Send a message
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setPaymentId(10L);
        paymentEvent.setReservationId(20L);
        paymentEvent.setAmount(1000);
        paymentEvent.setUserId(1L);
        paymentEvent.setTimestamp(LocalDateTime.now());

        messageSender.send(PaymentMessage.from(paymentEvent));

        // Wait for the message to be consumed
        boolean messageConsumed = latch.await(10, TimeUnit.SECONDS);

        // Verify message was consumed and its contents
        assertTrue(messageConsumed, "Message was not consumed");
        assertNotNull(receivedMessage, "Received message is null");
        assertEquals(10L, receivedMessage.getId(), "Payment ID does not match");
        assertTrue(receivedMessage.getMessage().contains("\"paymentId\":10,\"reservationId\":20,\"userId\":1"));

        container.stop();
    }

}
