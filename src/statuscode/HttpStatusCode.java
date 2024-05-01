package statuscode;

public enum HttpStatusCode {

    OK(200, "OK"),
    CREATED(201, "Created"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Invalid request body"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String description;

    HttpStatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static HttpStatusCode getByCode(int code) {
        for (HttpStatusCode statusCode : HttpStatusCode.values()) {
            if (statusCode.getCode() == code) {
                return statusCode;
            }
        }
        return null;
    }
}
