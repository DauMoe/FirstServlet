package Models;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private ArrayList<?> msg;
    private int code;

    public Response(ArrayList<?> msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public ArrayList<?> getMsg() {
        return msg;
    }

    public void setMsg(ArrayList<?> msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
