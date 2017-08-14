package pharmacy;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by AL on 2015-01-28.
 */
public class PharmacyJMSListener implements MessageListener {

    private List<Prescription> prescriptions = new ArrayList<Prescription>();

    private JmsTemplate jmsTemplate;
    
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            prescriptions.add((Prescription) ((MapMessage) message).getObject("prescription"));
            System.out.println("Odebrano jakas wiadomosc");
        } catch(JMSException ex) {
            throw new IllegalStateException();
        }
    }
}
