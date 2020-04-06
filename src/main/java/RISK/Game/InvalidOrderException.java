package RISK.Game;

import RISK.Utils.MsgException;

public class InvalidOrderException extends MsgException {
    public InvalidOrderException(String msg) {
        super(msg);
    }
}
