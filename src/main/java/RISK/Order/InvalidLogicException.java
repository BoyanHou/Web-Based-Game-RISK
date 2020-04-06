package RISK.Order;

import RISK.Utils.MsgException;

public class InvalidLogicException extends MsgException {
    public InvalidLogicException (String msg) {
        super(msg);
    }
}
