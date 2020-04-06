package RISK.Order;

import RISK.Utils.MsgException;

public class InvalidOptionException extends MsgException {
    public InvalidOptionException(String msg) {
        super(msg);
    }
}
