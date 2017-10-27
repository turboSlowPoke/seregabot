package telegramservices;

import dbservices.DbService;
import dbservices.ActionType;
import dbservices.entyties.*;
import main.Config;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegramservices.enums.KeybordCommand;
import telegramservices.enums.ResponseText;

import java.time.LocalDate;
import java.util.List;
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
            response = callBackContext(update.getCallbackQuery());
        }else if (update.hasMessage()
                &&!update.getMessage().isGroupMessage()
                &&update.getMessage().hasText()) {
            Message incomingMessage = update.getMessage();
            User user = DbService.getInstance().getUser(incomingMessage.getChatId());
            if (user!=null){
                response=mainContext(user,incomingMessage);
            }else {
                response = startContext(incomingMessage);
            }
        }
        return response;
    }

    private BotApiMethod callBackContext(CallbackQuery callbackQuery) {
        EditMessageText response = new EditMessageText()
                .setMessageId(callbackQuery.getMessage().getMessageId())
                .setChatId(callbackQuery.getMessage().getChatId());
        String dataFromQuery = callbackQuery.getData();
        if (dataFromQuery.startsWith("currency=")){
            Long idCurrency = Long.parseLong(dataFromQuery.substring(9));
            KryptoCurrency currency = DbService.getInstance().getCurrency(idCurrency);
            if (currency!=null){
                response.setText(currency.getShortName()+" "+currency.getName()+"\n"+currency.getDescription());
                response.setReplyMarkup(menucreator.createBackToCurrencyListButton());
            }
        }else if (dataFromQuery.equals("backToCurrencyList")){
            List<KryptoCurrency> currencyList = DbService.getInstance().getCurrencyList();
            response.setText("Список валют:");
            response.setReplyMarkup(menucreator.createCarrencyListMenu(currencyList));
        }
        else {
            response=null;
        }

        return response;
    }

    private BotApiMethod mainContext(User user, Message incomingMessage) {
        SendMessage response = new SendMessage(user.getChatId(),"Неизвестная команда!");
        KeybordCommand command = KeybordCommand.getTYPE(incomingMessage.getText());
        switch (command){
            case START:
                response.setText(ResponseText.MAINMENU.getText());
                response.setReplyMarkup(menucreator.createMainMenu());
                break;
            case BASE:
                List<KryptoCurrency> currencyList = DbService.getInstance().getCurrencyList();
                if (currencyList!=null&&currencyList.size()>0){
                    response.setReplyMarkup(menucreator.createCarrencyListMenu(currencyList));
                    response.setText("Список валют:");
                } else {
                    response.setText("Список валют пуст");
                }
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
                response.setText("Информация");
                response.setReplyMarkup(menucreator.createInfoMenu());
                break;
            case ABOUT:
                response.setText("Информация о проекте, ссылка на сайт..");
                break;
            case FAQ:
                response.setText("Список чатсых вопросов");
                break;
            case HELP:
                response.setText("Ссылка на аккаунт поддержки...");
                 break;
            case BACK:
                response.setText(ResponseText.MAINMENU.getText());
                response.setReplyMarkup(menucreator.createMainMenu());
                break;
            case SUBSCRIPTION:
                LocalDate endOfSubscription = user.getService().getEndOfSubscription();
                if (endOfSubscription!=null&&endOfSubscription.isAfter(LocalDate.now())){
                    response.setText("Ваша подписка заканчивается "+endOfSubscription +". Вы можете продлить ее, выбрав нужный вариант:");
                }else if (endOfSubscription!=null&&endOfSubscription.isBefore(LocalDate.now())){
                    response.setText("Ваша подписка закончилась "+endOfSubscription+". Выберите вариант и оплатите подписку.");
                }else {
                    response.setText("У вас еще нет пописки. Выберите вариант и оплатите подписку." +
                            "\nПосле нажатия на кнопку вы будите переадресованы на сайт платежной системы Advcash( https://advcash.com/ )");
                }
                response.setReplyMarkup(menucreator.createSubscriptionMenu());
                break;
            case PARTNERS_PROG:
                response.setText("Партнёрская программа:");
                response.setReplyMarkup(menucreator.createPartnersMenu());
                break;
            case INVITE_PARTNERS:
                response.setText("Чтобы пригласить парнтёра, скопируйте и отправьте ему ссылку:" +
                        "\nhttps://t.me/Sl0wP0ke_Bot?start="+user.getChatId());
                break;
            case MY_PARNERS:
                response.setText("Здесь будет список партнеров по уровням");
                break;
            case BONUSES:
                response.setText()
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
            response.setReplyMarkup(menucreator.createMainMenu());
        }
        //приглашенный start с параметром id пригласителя
        else if (incomingMessage.getText().startsWith("/start ")){
            DbService.getInstance().changeUser(user,ActionType.SAVECHILDRENUSER);
            response.setText(ResponseText.WELCOME.getText());
            response.setReplyMarkup(menucreator.createMainMenu());
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
