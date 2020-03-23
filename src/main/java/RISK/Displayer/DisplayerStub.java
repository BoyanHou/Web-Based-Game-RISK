package RISK.Displayer;

import RISK.Game.GameDisplayable;

public class DisplayerStub implements Displayer {
    @Override
    public void display(GameDisplayable game) {
        System.out.println("STUB_PLAYER" + game.getPlayerID() + ":[game]");
    }

    @Override
    public void display(GameDisplayable game, String text) {
        System.out.println("STUB_PLAYER" + game.getPlayerID() +":[game]");
        System.out.println("STUB_PLAYER" + game.getPlayerID() + ":" + text);
    }

}
