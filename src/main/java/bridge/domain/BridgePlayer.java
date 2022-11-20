package bridge.domain;

import java.util.List;

public class BridgePlayer {

    private final List<BridgeMoveType> playerBridge;

    public BridgePlayer(List<BridgeMoveType> playerBridge) {
        this.playerBridge = playerBridge;
    }

    public boolean isNotSameLocation(List<BridgeMoveType> bridgeMoveTypes) {
        int currentLocation = playerBridge.size() - 1;
        return playerBridge.get(currentLocation) != bridgeMoveTypes.get(currentLocation);
    }
    public void moveTo(BridgeMoveType moveType) {
        playerBridge.add(moveType);
    }
}
