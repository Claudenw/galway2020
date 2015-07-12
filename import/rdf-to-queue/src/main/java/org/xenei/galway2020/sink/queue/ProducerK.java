package org.xenei.galway2020.sink.queue;

import java.util.*;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerK {
   
    public void persist(String rdfGraph,String topic) {
    	
    	
    	  Properties props = new Properties();
          props.put("metadata.broker.list", "172.31.28.213:9092,172.31.28.213:9092");
          props.put("serializer.class", "kafka.serializer.StringEncoder");
          props.put("partitioner.class", "example.producer.SimplePartitioner");
          props.put("request.required.acks", "1");
   
          ProducerConfig config = new ProducerConfig(props);
   
          Producer<String, String> producer = new Producer<String, String>(config);
   
          
          KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, rdfGraph);
          producer.send(data);
          producer.close();
    }
    
}