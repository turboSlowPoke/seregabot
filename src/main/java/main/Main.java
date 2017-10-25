package main;

import dbservices.DbService;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import telegramservices.WebhookSevice;

public class Main {
    public static void main(String[] args){
        DbService.getInstance();
        System.out.println("dbService started");
        ApiContextInitializer.init();
        WebhookSevice webhookSevice = new WebhookSevice();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(
                    Config.pathToCertificateStore,
                    Config.certificateStorePassword,
                    Config.EXTERNALWEBHOOKURL,
                    Config.INTERNALWEBHOOKURL,
                    Config.pathToCertificatePublicKey);
            telegramBotsApi.registerBot(webhookSevice);
            System.out.println("Telegram Bot started");

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }
}
