package fu.he191060.he191060departmentservice.dto;

public class ApiResponseDTO {
    private int status;
    private Object data;
    private String timestamp;

    public ApiResponseDTO(int status, Object data, String timestamp) {
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
