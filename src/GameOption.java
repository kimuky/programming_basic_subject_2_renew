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

    public static GameOption option (String option) {
        for (GameOption game : GameOption.values()) {
            if (game.getOption().equals(option)) {
                return game;
            }
        }
        return ERROR;
    }

}
