package com.pastaebasta.eist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The PBServerApplication starts the Spring modules, which will auto-wire components of the project.
 */
@SpringBootApplication
public class PBServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PBServerApplication.class, args);
		System.out.println("""
								
																						   88
													  ,d                                   88                                    ,d
													  88                                   88                                    88
				8b,dPPYba,   ,adPPYYba,  ,adPPYba,  MM88MMM  ,adPPYYba,      ,adPPYba,     88,dPPYba,   ,adPPYYba,  ,adPPYba,  MM88MMM  ,adPPYYba,
				88P'    "8a  ""     `Y8  I8[    ""    88     ""     `Y8     a8P_____88     88P'    "8a  ""     `Y8  I8[    ""    88     ""     `Y8
				88       d8  ,adPPPPP88   `"Y8ba,     88     ,adPPPPP88     8PP""\"""\""     88       d8  ,adPPPPP88   `"Y8ba,     88     ,adPPPPP88
				88b,   ,a8"  88,    ,88  aa    ]8I    88,    88,    ,88     "8b,   ,aa     88b,   ,a8"  88,    ,88  aa    ]8I    88,    88,    ,88
				88`YbbdP"'   `"8bbdP"Y8  `"YbbdP"'    "Y888  `"8bbdP"Y8      `"Ybbd8"'     8Y"Ybbd8"'   `"8bbdP"Y8  `"YbbdP"'    "Y888  `"8bbdP"Y8
				88
				88
				
				> Pasta e Basta - Server Application successfully started.
				
				
				""");
	}

}
