package david.augusto.luan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSecurityApplication {

	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("1234"));
		SpringApplication.run(DemoSecurityApplication.class, args);
	}

}
