package telegramservices.enums;

public enum ResponseText {
    WELCOME("Добро пожаловать! \n Выберите пункт меню!"),
    SEND_START("Нажмите старт"),
    FAIL("Я не знаю что на это ответить")
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
