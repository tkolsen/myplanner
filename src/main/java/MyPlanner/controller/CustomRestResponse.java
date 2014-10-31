package MyPlanner.controller;

/**
 * Created by Tom on 31.10.2014.
 */
public class CustomRestResponse {
    public String action;
    public String success;
    public String message;
    public String data;

    public CustomRestResponse(String action, String success, String message, String data) {
        this.action = action;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CustomRestResponse{" +
                "action='" + action + '\'' +
                ", success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
