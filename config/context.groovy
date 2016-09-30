package common

import org.springframework.core.io.ClassPathResource

//def properties = new Properties()
//properties.load(new ClassPathResource('common.properties').inputStream);
//properties.load(new ClassPathResource('private.properties').inputStream);

beans {
//    telegramSender(utils.TelegramSender, properties: properties)

    jsoupPageParser(utils.JsoupPageParser)
//    executor(common.Executor,
//            messageSender: emailSender,
//            pageParser: jsoupPageParser)
//    launcher(common.Launcher, executor: executor)
}