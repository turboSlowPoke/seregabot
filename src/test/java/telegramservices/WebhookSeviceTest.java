package telegramservices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Message;

import static org.junit.Assert.*;

public class WebhookSeviceTest {

    WebhookSevice webhookSevice;
    @Before
    public void setUp() throws Exception {
        webhookSevice=new WebhookSevice(null,null);
    }

    @After
    public void tearDown() throws Exception {
        webhookSevice=null;
    }

    @Test
    public void validName() throws Exception {
        assertEquals("MyName123-+/?;",webhookSevice.validName("MyName123-+/?;"));
    }

}