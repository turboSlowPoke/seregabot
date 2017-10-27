package telegramservices.enums;

public enum ResponseText {
    WELCOME("Добро пожаловать!"),
    SEND_START("Отправьте /start"),
    FAIL("Я не знаю что на это ответить"),
    MAINMENU("Главное меню")
    ;

    ResponseText(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public static ResponseText getTYPE(String s){
        ResponseText type = FAIL;
        for (ResponseText tempTYPE : ResponseText.values()){
            if (s.equals(tempTYPE.getText()))
                type = tempTYPE;
        }
        return type;
    }
}
