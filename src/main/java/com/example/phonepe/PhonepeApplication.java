package com.example.phonepe;

import com.example.phonepe.models.User;
import com.example.phonepe.service.AccessManager;
import com.example.phonepe.service.DocumentManager;
import com.example.phonepe.service.UserManager;
import com.example.phonepe.service.impl.AccessManagerImpl;
import com.example.phonepe.service.impl.DocumentManagerImpl;
import com.example.phonepe.service.impl.UserManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PhonepeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhonepeApplication.class, args);

		//start application
		UserManager userManager = new UserManagerImpl();
		DocumentManager documentManager = new DocumentManagerImpl();
		AccessManager accessManager = new AccessManagerImpl();

		userManager.createUser("Amit", "AM123", 1);
		userManager.createUser("Sumit", "SUM123", 2);

		userManager.loginUser(1, "AM123");

		List<Integer> editAccessList = new ArrayList<>();
		editAccessList.add(1);
		editAccessList.add(2);
		List<Integer> viewAccessList = new ArrayList<>();
		viewAccessList.add(1);
		viewAccessList.add(2);
		documentManager.createDocument("Hello", 1, editAccessList, viewAccessList);
		documentManager.updateDocument("Hi", 2, editAccessList, viewAccessList, 1);
		documentManager.getActiveVersion(1);

	}

}
