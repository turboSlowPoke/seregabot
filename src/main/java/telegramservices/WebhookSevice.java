package telegramservices;

import dbservices.DbService;
import dbservices.entyties.Contatct;
import dbservices.entyties.PersonalData;
import dbservices.entyties.User;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import telegramservices.enums.ResponseText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebhookSevice extends TelegramWebhookBot {
    private static final Logger log = Logger.getLogger(WebhookSevice.class);
    private Menucreator menucreator;


    public WebhookSevice(Menucreator menucreator, DbService dbService) {
        this.menucreator = menucreator;
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

            }else {

            }
        }
        return response;
    }

    private SendMessage startContext(Message incomingMessage){
        SendMessage response = new SendMessage(incomingMessage.getChatId(),ResponseText.SEND_START.getText());
        User user = createNewUser(incomingMessage);
        //не приглашенный пользователь start без параметров
        if (incomingMessage.getText().equals("/start")){
            //DbService.getInstance().addParentUser(user);
            response.setText(ResponseText.WELCOME.getText());
            response.setReplyMarkup(menucreator.getMainMenu());
        }
        //приглашенный start с параметром id пригласителя
        else if (incomingMessage.getText().startsWith("/start ")){
            //DbService.getInstance().addChildrenUser(user);
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
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
