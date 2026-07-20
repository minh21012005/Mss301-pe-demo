package fu.he191060.employee.common;

public class BusinessException extends RuntimeException {
    private final int apiStatus;
    private final int httpStatus;

    public BusinessException(int apiStatus, int httpStatus) {
        this.apiStatus = apiStatus;
        this.httpStatus = httpStatus;
    }

    public int getApiStatus() { return apiStatus; }
    public int getHttpStatus() { return httpStatus; }
}
