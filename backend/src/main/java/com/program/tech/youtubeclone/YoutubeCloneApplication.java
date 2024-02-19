package com.program.tech.youtubeclone;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class YoutubeCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubeCloneApplication.class, args);
	}


	@Bean
	public AmazonS3Client amazonS3Client() {
		String awsAccessKey = "AKIATUFASCO43TNXCIFJ" ;
		String awsSecretKey = "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";
		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		return new AmazonS3Client(credentials);
	}
}
