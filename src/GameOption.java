public enum GameOption {
    SET_DIFFICULTY("0"),
    START_GAME("1"),
    SHOW_RECORD ("2"),
    EXIT("3"),
    ERROR("4");

    private final String option;

    GameOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    // 여러가지 방법이 있겠지만 현로직상에는 문제 없지만
    //TODO 리턴에 대해서는 고민해보아야할 것
    public static GameOption option (String option) {
        for (GameOption game : GameOption.values()) {
            if (game.getOption().equals(option)) {
                return game;
            }
        }
        return ERROR;
    }
}
