package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@ComponentScan("com.example.demo")
@EntityScan(basePackages = {"com.example.demo.Model", "com.example.Repository"})
@EnableJpaRepositories("com.example.demo.Repository")
public class Ratatuile23Application {

	public static void main(String[] args) {
		SpringApplication.run(Ratatuile23Application.class, args);
		try {
			avviaServizioNotificaFirebase();
		} catch (IOException e) {
			System.out.println("Errore nell'avvio del servizio di notifica Firebase");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void avviaServizioNotificaFirebase() throws IOException {
		FileInputStream serviceAccount = new FileInputStream("demo/ratatouille23-grp21-firebase-adminsdk-134t0-7755617655.json");
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		FirebaseApp.initializeApp(options);
	}

}
