package fu.he191060.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class He191060EmployeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(He191060EmployeeServiceApplication.class, args);
    }
}
