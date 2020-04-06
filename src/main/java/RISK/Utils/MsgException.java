package RISK.Utils;

public abstract class MsgException extends Exception {
    protected String msg;
    protected MsgException (String msg) {
        this.msg = msg;
    }
    public String getMessage() {
        return this.msg;
    }
}
