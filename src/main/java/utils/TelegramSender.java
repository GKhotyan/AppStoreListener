package utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TelegramSender implements MessageSender{

    @Autowired
    private Environment env;

    @Override
    public String send(String message) {
        String bot_tocken = env.getProperty("bot.tocken");
        String channel = env.getProperty("channel");
        TelegramBot bot = TelegramBotAdapter.build(bot_tocken);

        SendResponse sendResponse = bot.execute(new SendMessage(channel, message)
                                                    .parseMode(ParseMode.HTML));
        return sendResponse.message().text();
    }

}
