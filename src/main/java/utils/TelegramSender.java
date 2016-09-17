package utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.Properties;

public class TelegramSender implements MessageSender{

    private Properties properties;

    @Override
    public String send(String subject, String text) {
        String bot_tocken = getProperties().getProperty("telegram.bot.tocken");
        String channel = getProperties().getProperty("telegram.channel");
        TelegramBot bot = TelegramBotAdapter.build(bot_tocken);

        SendResponse sendResponse = bot.execute(new SendMessage(channel, "<b>Subject</b>:\t"+subject+"\n\n<b>Message</b>:\t"+text)
                                                    .parseMode(ParseMode.HTML));
        return sendResponse.message().text();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
