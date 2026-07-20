package fu.he191060.department.dto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ApiResponseDTO {
    private int status;
    private Object data;
    private String timestamp;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(int status, Object data) {
        this.status = status;
        this.data = data;
        this.timestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
    }

    public static ApiResponseDTO success(Object data) {
        return new ApiResponseDTO(1, data);
    }

    public static ApiResponseDTO error(int status) {
        return new ApiResponseDTO(status, null);
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
