package telegramservices;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import telegramservices.enums.KeybordCommand;

import java.util.ArrayList;
import java.util.List;

public class Menucreator {

    public ReplyKeyboard getMainMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(KeybordCommand.BASE.getText()));
        keyboardRow1.add(new KeyboardButton(KeybordCommand.CALENDAR.getText()));
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(KeybordCommand.NEWS.getText()));
        keyboardRow2.add(new KeyboardButton(KeybordCommand.CHAT.getText()));
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(KeybordCommand.INFO.getText()));
        keyboardRow3.add(new KeyboardButton(KeybordCommand.SUBSCRIPTION.getText()));
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton(KeybordCommand.MY_ACCOUNT.getText()));
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
}
