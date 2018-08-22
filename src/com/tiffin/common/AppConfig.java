package com.tiffin.common;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.google.api.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.StorageOptions.Builder;
import com.google.common.collect.Lists;

 
@Configuration
@PropertySources({
    @PropertySource("/resources/properties/jdbc.properties"),
    @PropertySource("/resources/properties/tiffinApp.properties")
})

public class AppConfig {
	
	/*static void authExplicit(String jsonPath) throws IOException {
		jsonPath="C:/Users/ayansaha33/Desktop/GCP/Project/prayojon/Credentials/Owner_Access_Of_Cloud_APIs/prayojan-a3f1b2cc4296.json";
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		}*/
	
}