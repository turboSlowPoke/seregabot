package telegramservices.enums;

public enum KeybordCommand {
    //mainmenu
    START("/start"),
    BASE("База знаний"),
    CALENDAR("Календарь событий"),
    INFO("Информация"),
    SUBSCRIPTION("Подписка"),
    CHAT("Чат"),
    NEWS("Новости"),
    PARTNERS_PROG("Партнёрская программа"),
    MY_ACCOUNT("Мой аккаунт"),
    //info menu
    ABOUT("О проекте"),
    FAQ("FAQ"),
    HELP("Помощь"),
    //subscription menu
    THREE_MONTH("3 месяца - 100р"),
    SIX_MONTTH("6 месяцев - 180р"),
    TWELVE_MONTH("12 месяцев - 320р"),
    BACK("<= В главное меню"),
    URL_PAYMENTS("https://advcash.com/"),
    URL_OPTION_THREEMONTH("threemonth"),
    URL_OPTION_SIXMONTH("sixmonth"),
    URL_OPTION_TWELVEMONTH("twelvemonth"),
    //partners menu
    INVITE_PARTNERS("Пригласить партнера"),
    MY_PARNERS("Мои партнёры"),
    BONUSES("Мои бонусы"),

    FAIL("Неизвестная команда")
    ;

    KeybordCommand(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public static KeybordCommand getTYPE(String s){
        KeybordCommand type = FAIL;
        for (KeybordCommand tempTYPE : KeybordCommand.values()){
            if (s.equals(tempTYPE.getText()))
                type = tempTYPE;
        }
        return type;
    }


}
