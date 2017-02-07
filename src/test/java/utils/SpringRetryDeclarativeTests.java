package utils;

import common.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringRetryDeclarativeTests {
    @Autowired
    private MessageSender telegramSender;

    @Test
    public void testRetry() throws Exception {
        String result = telegramSender.send("Hello");
        verify(telegramSender, times(3)).send("Hello");
        assertThat(result, is("Hello"));
    }

    @Configuration
    @EnableRetry
    public static class SpringConfig {

        @Bean
        public MessageSender messageSender() throws Exception {
            MessageSender telegramSender = mock(MessageSender.class);
            when(telegramSender.send("Hello"))
                    .thenThrow(new RuntimeException("Remote Exception 1"))
                    .thenThrow(new RuntimeException("Remote Exception 2"))
                    .thenReturn("Hello");
            return telegramSender;
        }
    }
}
