package telegramservices.enums;

public enum KeybordCommand {
    //mainmenu
    BASE("База знаний"),
    CALENDAR("Календарь событий"),
    INFO("Информация"),
    SUBSCRIPTION("Подписка"),
    CHAT("Чат"),
    NEWS("Новости"),
    PARTNERS_PROG("Партнёрская программа"),
    MY_ACCOUNT("Мой аккаунт"),

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
