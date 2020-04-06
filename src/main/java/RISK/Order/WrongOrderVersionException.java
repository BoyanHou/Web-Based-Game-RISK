package RISK.Order;

import RISK.Utils.MsgException;

public class WrongOrderVersionException extends MsgException {
    public WrongOrderVersionException(String msg) {
        super(msg);
    }
}
