package telegramservices;

import dbservices.DbService;
import dbservices.ActionType;
import dbservices.entyties.Contatct;
import dbservices.entyties.PersonalData;
import dbservices.entyties.User;
import main.Config;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import telegramservices.enums.KeybordCommand;
import telegramservices.enums.ResponseText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebhookSevice extends TelegramWebhookBot {
    private static final Logger log = Logger.getLogger(WebhookSevice.class);
    private Menucreator menucreator;


    public WebhookSevice() {
        this.menucreator = new Menucreator();
    }


    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        BotApiMethod response=null;
        if (update.hasCallbackQuery()){

        }else if (update.hasMessage()
                &&!update.getMessage().isGroupMessage()
                &&update.getMessage().hasText()) {
            Message incomingMessage = update.getMessage();
            User user = DbService.getInstance().getUser(incomingMessage.getChatId());
            if (user!=null){
                response=mainContext(user,incomingMessage);
            }else {

            }
        }
        return response;
    }

    private BotApiMethod mainContext(User user, Message incomingMessage) {
        SendMessage response = new SendMessage(user.getChatId(),"Неизвестная команда!");
        KeybordCommand command = KeybordCommand.getTYPE(incomingMessage.getText());
        switch (command){
            case START:
                response.setText("Главное меню");
                response.setReplyMarkup(menucreator.getMainMenu());
                break;
            case BASE:
                response.setText("Здесь будет список валют");
                break;
            case CALENDAR:
                response.setText("Здесь будет календарь событий");
                break;
            case NEWS:
                response.setText("Здесь будут новости");
                break;
            case CHAT:
                response.setText("Здесь будет ссылка на общий чат");
                break;
            case INFO:
                response.setText("Здесь будет меню с информацией");
                break;
            case SUBSCRIPTION:
                response.setText("Здесь будет меню с оформлением подписки");
                break;
            case PARTNERS_PROG:
                response.setText("Здесь будет меню с партнёрской программой");
                break;
            case MY_ACCOUNT:
                response.setText("Здесь будет меню настройки аккаунта");
                break;
            case FAIL:
                response.setText("Неизвестная команда!");
            default:
                response.setText("Ошибка в maincontext()");
        }
        return response;
    }

    private SendMessage startContext(Message incomingMessage){
        SendMessage response = new SendMessage(incomingMessage.getChatId(),ResponseText.SEND_START.getText());
        User user = createNewUser(incomingMessage);
        //не приглашенный пользователь start без параметров
        if (incomingMessage.getText().equals("/start")){
            DbService.getInstance().changeUser(user, ActionType.SAVEROOTUSER);
            response.setText(ResponseText.WELCOME.getText());
            response.setReplyMarkup(menucreator.getMainMenu());
        }
        //приглашенный start с параметром id пригласителя
        else if (incomingMessage.getText().startsWith("/start ")){
            DbService.getInstance().changeUser(user,ActionType.SAVECHILDRENUSER);
            response.setText(ResponseText.WELCOME.getText());
            response.setReplyMarkup(menucreator.getMainMenu());
        }
        return response;

    }

    public User createNewUser(Message incomingMessage) {
        String firstName = validName(incomingMessage.getChat().getFirstName());
        String lastName = validName(incomingMessage.getChat().getLastName());
        long chatId = incomingMessage.getChatId();
        String telegramUserName = "@"+incomingMessage.getChat().getUserName();
        Contatct contatct = new Contatct(telegramUserName);
        PersonalData personalData = new PersonalData(firstName,lastName);
        User user = new User(chatId,personalData,contatct);
        log.info("Создан новый пользователь:"
                +user
                +user.getContatct()
                +user.getPersonalData());
        return user;
    }

    public String validName(String name) {
        String validName="-";
        String p = "([\\w]|.)*";
        Pattern pattern = Pattern.compile(p,Pattern.UNICODE_CHARACTER_CLASS);
        if (name!=null) {
            try {
                Matcher matcher = pattern.matcher(name);
                if (matcher.matches()) {
                    validName = name;
                }
            } catch (Exception e) {
                log.info("Не прошло проверку имя "+name);
            }
        }
        return name;
    }

    @Override
    public String getBotUsername() {
        return Config.botname;
    }

    @Override
    public String getBotToken() {
        return Config.token;
    }

    @Override
    public String getBotPath() {
        return Config.botPath;
    }
}
