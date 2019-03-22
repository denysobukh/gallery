package com.gallery;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	private class GalleryApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

		@Override
		public void onApplicationEvent(ApplicationStartedEvent event) {
			LoggerFactory.getLogger(Application.class).debug("ApplicationStartedEvent: " + event);
		}
	}
}
