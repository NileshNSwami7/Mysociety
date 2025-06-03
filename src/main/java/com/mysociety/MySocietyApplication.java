package com.mysociety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySocietyApplication {
//implements CommandLineRunner {

//	@Autowired
//	private Userservice userservice;
//	
	public static void main(String[] args) {
		SpringApplication.run(MySocietyApplication.class, args);
	}
	
//	public void run(String... args) throws Exception {
//		System.out.println("Starting code");
//		
//		User user = new User();
//		user.setFirstname("Nilesh");
//		user.setLastname("Swami");
//		user.setUsername("nil#123");
//		user.setEmail("nilnswami110@gmail.com");
//		user.setMobileno("9028233596");
//		user.setProfile("default.png");
//		
//		Role role = new Role();
//		role.setRoleid(21L);
//		role.setRolename("Admin");
//		
//		Set<Userrole> userroleset = new HashSet<>();
//		Userrole userroles = new Userrole();
//		userroles.setRoles(role);
//		userroles.setUser(user);
//		
//		userroleset.add(userroles);
//		
//		User firstuser = this.userservice.createUser(user, userroleset);
//		System.out.println(firstuser.getUsername());
//	}
}
