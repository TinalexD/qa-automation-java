import com.tcs.edu.LogException;
import com.tcs.edu.MessageService;
import com.tcs.edu.decorator.OrderedDistinctedMessageService;
import com.tcs.edu.decorator.PageSeparator;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.Message;
import com.tcs.edu.repository.HashMapMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationTest {

    MessageService Service = null;
    Message message1 = null;
    Message message2 = null;
    Message message3 = null;
    Message message4 = null;
    Message message5 = null;
    Message message6 = null;

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
        assertThat(message1).isEqualTo(Service.findByPrimaryKey(message1.getId()));


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
        Collection<Message> allMessages = new ArrayList<>();
        allMessages.add(message1);
        allMessages.add(message2);
        allMessages.add(message3);
        allMessages.add(message4);
        allMessages.add(message5);
        allMessages.add(message6);
        Collection<Message> addedMessages = Service.findBySeverity(Severity.MINOR);

        assertAll(
                () -> assertThat(allMessages.size()).isEqualTo(6),
                () -> addedMessages.stream().forEach(message -> assertThat(addedMessages).contains(message))
        );

    }

    @Test
    public void returnMessagesBySeverity() {
        Service.process(message1
                , message2
                , message3
                , message4
                , message5
                , message6
        );
        Collection<Message> filteredMessages = Service.findBySeverity(Severity.MINOR);
        Collection<Message> minorMessages = new ArrayList<>();
        minorMessages.add(message1);
        minorMessages.add(message2);
        minorMessages.add(message3);
        minorMessages.add(message4);
        minorMessages.add(message5);
        assertAll(
                () -> assertThat(filteredMessages.size()).isEqualTo(5),
                () -> filteredMessages.stream().forEach(message -> assertThat(minorMessages).contains(message))
        );
    }
    @Nested
    class Negative{
        Message message7 = null;

        @Test
        public void shouldGetException() {
            assertThatExceptionOfType(LogException.class).isThrownBy(
                    () -> Service.process(message1
                            , message2
                            , message3
                            , message4
                            , message5
                            , message6
                            , message7
                    )).withMessage("notValidArgMessage");
        }
    }
}