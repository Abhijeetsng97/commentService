package com.abhijeet.commentsService;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class CommentsServiceApplication {

	@Bean
	public Connection hbaseConnection() throws IOException {
		Configuration configuration = new Configuration();
//		configuration.set("hbase.master", "localhost:16010");
		configuration.set("hbase.zookeeper.quorum", "localhost");
		return ConnectionFactory.createConnection(configuration);
	}

	public static void main(String[] args) {
		SpringApplication.run(CommentsServiceApplication.class, args);
	}

}
