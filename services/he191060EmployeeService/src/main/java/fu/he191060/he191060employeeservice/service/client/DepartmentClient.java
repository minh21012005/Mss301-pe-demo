package fu.he191060.he191060employeeservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "", url = "http://localhost:8081")
public interface DepartmentClient {


}
