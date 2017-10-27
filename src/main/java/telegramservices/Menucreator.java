package telegramservices;

import dbservices.entyties.KryptoCurrency;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import telegramservices.enums.KeybordCommand;

import java.util.ArrayList;
import java.util.List;

public class Menucreator {

    public ReplyKeyboard createMainMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(KeybordCommand.BASE.getText()));
        keyboardRow1.add(new KeyboardButton(KeybordCommand.CALENDAR.getText()));
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(KeybordCommand.NEWS.getText()));
        keyboardRow2.add(new KeyboardButton(KeybordCommand.CHAT.getText()));
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton(KeybordCommand.INFO.getText()));
        keyboardRow3.add(new KeyboardButton(KeybordCommand.SUBSCRIPTION.getText()));
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardRow4.add(new KeyboardButton(KeybordCommand.MY_ACCOUNT.getText()));
        keyboardRow4.add(new KeyboardButton(KeybordCommand.PARTNERS_PROG.getText()));
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        keyboardMarkup.setKeyboard(keyboardRows);
        // mainMenuMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup createCarrencyListMenu(List<KryptoCurrency> currencyList){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (KryptoCurrency currency : currencyList){
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton()
                    .setText(currency.getShortName()+" "+currency.getName())
                    .setCallbackData("currency="+currency.getId()));
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup createBackToCurrencyListButton() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton()
                .setText("<=назад к списку")
                .setCallbackData("backToCurrencyList"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public ReplyKeyboardMarkup createInfoMenu(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(KeybordCommand.ABOUT.getText()));
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(KeybordCommand.FAQ.getText()));
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton(KeybordCommand.HELP.getText()));
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardRow4.add(new KeyboardButton(KeybordCommand.BACK.getText()));
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        keyboardMarkup.setKeyboard(keyboardRows);
        // mainMenuMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup createSubscriptionMenu(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        rowInline1.add(new InlineKeyboardButton().setText(KeybordCommand.THREE_MONTH.getText()).setUrl(KeybordCommand.URL_PAYMENTS.getText()));
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline2.add(new InlineKeyboardButton().setText(KeybordCommand.SIX_MONTTH.getText()).setUrl(KeybordCommand.URL_PAYMENTS.getText()));
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        rowInline3.add(new InlineKeyboardButton().setText(KeybordCommand.TWELVE_MONTH.getText()).setUrl(KeybordCommand.URL_PAYMENTS.getText()));
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public ReplyKeyboardMarkup createPartnersMenu(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(KeybordCommand.INVITE_PARTNERS.getText()));
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(KeybordCommand.MY_PARNERS.getText()));
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton(KeybordCommand.BONUSES.getText()));
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardRow4.add(new KeyboardButton(KeybordCommand.BACK.getText()));
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }
}
