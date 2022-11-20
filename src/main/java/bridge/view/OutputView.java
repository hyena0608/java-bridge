package bridge.view;

import bridge.domain.BridgeMoveType;
import bridge.domain.BridgeHistory;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bridge.config.CharacterConfiguration.ENTER;
import static bridge.domain.BridgeMoveType.*;
import static bridge.config.BridgeConfiguration.*;
import static bridge.view.OutputViewMessage.*;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(BridgeHistory bridgeHistory) {
        List<BridgeMoveType> playerMoveHistory = bridgeHistory.getPlayerBridge();
        List<BridgeMoveType> bridgeAnswerHistory = bridgeHistory.getBridgeAnswer();

        String bridgeUpHistory = createBridgeHistory(playerMoveHistory, bridgeAnswerHistory, UP);
        String bridgeDownHistory = createBridgeHistory(playerMoveHistory, bridgeAnswerHistory, DOWN);

        System.out.print(MessageFormat.format("{0}{1}{2}{3}{4}",
                bridgeUpHistory, ENTER.getConfig(), bridgeDownHistory, ENTER.getConfig(), ENTER.getConfig()));
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(BridgeHistory bridgeHistory) {
        System.out.print(FINAL_GAME_RESULT.getMessage());
        printMap(bridgeHistory);
        System.out.print(MessageFormat.format(GAME_WIN_RESULT.getMessage(), bridgeHistory.getGameResult().getType()));
        System.out.print(MessageFormat.format(TOTAL_ROUND.getMessage(), bridgeHistory.getBridgeGameRound()));
    }

    private String createBridgeHistory(List<BridgeMoveType> playerMoveHistory,
                                       List<BridgeMoveType> bridgeAnswerHistory,
                                       BridgeMoveType bridgeType
    ) {
        return LEFT_SQUARE_BRACKET.getConfig() +
                IntStream.range(0, playerMoveHistory.size())
                        .mapToObj(currentLocation -> {
                            BridgeMoveType answerMoveType = bridgeAnswerHistory.get(currentLocation);
                            BridgeMoveType playerMoveType = playerMoveHistory.get(currentLocation);
                            return convertToBridgeHistoryFrom(bridgeType, answerMoveType, playerMoveType);
                        }).collect(Collectors.joining(VERTICAL_BAR.getConfig())) +
                RIGHT_SQUARE_BRACKET.getConfig();
    }

    private String convertToBridgeHistoryFrom(BridgeMoveType bridgeType,
                                              BridgeMoveType answerMoveType,
                                              BridgeMoveType playerMoveType
    ) {
        if (answerMoveType.isSame(bridgeType)) {
            if (answerMoveType.isSame(playerMoveType)) {
                return BRIDGE_CORRECT_EXPRESSION.getConfig();
            }
            return BRIDGE_EMPTY_EXPRESSION.getConfig();
        }
        if (answerMoveType.isNotSame(playerMoveType)) {
            return BRIDGE_NOT_CORRECT_EXPRESSION.getConfig();
        }
        return BRIDGE_EMPTY_EXPRESSION.getConfig();
    }
}
