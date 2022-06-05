import com.tcs.edu.LogException;
import com.tcs.edu.MessageService;
import com.tcs.edu.decorator.OrderedDistinctedMessageService;
import com.tcs.edu.decorator.PageSeparator;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.Message;
import com.tcs.edu.repository.HashMapMessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest {

    MessageService Service = null;
    Message message1 = null;
    Message message2 = null;
    Message message3 = null;
    Message message4 = null;
    Message message5 = null;
    Message message6 = null;
    Message message7 = null;

    @BeforeEach
    public void setUp() {
        message1 = new Message("Hello world 1!", Severity.MINOR);
        message2 = new Message("Hello world 1!", Severity.MINOR);
        message3 = new Message("Hello world 1!");
        message4 = new Message("Hello world 2!");
        message5 = new Message("Hello world 3!", Severity.MINOR);
        message6 = new Message("Hello world 4!", Severity.MAJOR);


        Service = new OrderedDistinctedMessageService(
                  new HashMapMessageRepository(),
                  new TimestampMessageDecorator(),
                  new PageSeparator());


    }

    @Test
    public void returnCurrentMessage() {
        Service.process(message1
                , message2
                , message3
                , message4
                , message5
                , message6
        );
        Assertions.assertEquals(message1, Service.findByPrimaryKey(message1.getId()));


    }

    @Test
    public void returnAllMessages() {
        Service.process(message1
                , message2
                , message3
                , message4
                , message5
                , message6
        );
        for (Message current : Service.findAll()) {
            System.out.println(current);
        }
    }

    @Test
    public void returnMessagesBySeverity() {
        for (Message current : Service.findBySeverity(Severity.MINOR)) {
            System.out.println(current);
        }
    }

    @Test
    public void shouldGetException() {
        assertThrows(LogException.class,
                () -> Service.process(message1
                    , message2
                    , message3
                    , message4
                    , message5
                    , message6
                    , message7
            )
        );
    }
}