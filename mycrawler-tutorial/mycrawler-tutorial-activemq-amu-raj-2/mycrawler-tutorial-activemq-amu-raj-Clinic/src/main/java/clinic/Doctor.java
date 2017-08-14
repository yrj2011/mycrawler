package clinic;
/**
 * Created by AL on 2015-01-28.
 */

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.apache.activemq.Message;
import org.springframework.web.client.RestTemplate;

import javax.jms.*;
import javax.print.attribute.standard.Destination;
import java.util.Scanner;

public class Doctor {

    private RestTemplate restTemplate = new RestTemplate();
    private JmsTemplate jmsTemplate;

    private Destination pharmacyList;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setPharmacyList(Destination pharmacyList) {
        this.pharmacyList = pharmacyList;
    }

    public void sendPrescription(final Prescription pres){
        jmsTemplate.send(String.valueOf(pharmacyList), new MessageCreator() {
            @Override
            public MapMessage createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setObject("prescription", pres);
                //message.setJMSReplyTo(pharmacyList);

                return message;
            }


        });
    }

    public static void main(String[] args){

        Prescription pres = new Prescription("91013000000");
        pres.addMedicine("apap", (float) 1.0);

        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");

        Doctor doc = new Doctor();

        //doc.findDiscount("Nassenne");

        String finish = "";
        Scanner read = new Scanner(System.in);
        while(true){
            System.out.println("Exit? ");
            finish = read.next();
            if (finish.equals("yes")) break;
            doc.appoinment();
        }

        //doc.sendPrescription(pres);


    }

    public void findDiscount(String nameMed){
        try{
            Medicine taken = restTemplate.getForObject("http://localhost:8080/medicines/" + nameMed, Medicine.class);
            int i = 1;
            for (Discount disc: taken.getDiscounts()){
                System.out.println("[" + i+ "] " + disc.getDescribe() + " " + disc.getValue() + "%");
                i++;
            }
            System.out.println("Choose i: ");
                Scanner read = new Scanner(System.in);
                int k = read.nextInt();
            //Prescription presc = new Prescription(pesel);
            //presc.addMedicine(nameMed, (float) taken.getPercentOfDiscount(k));

            System.out.print("Twoj wybor: " + nameMed + " " + taken.getDiscounts().get(k-1));

        }catch(Exception ex){
            System.out.println("Upss.. cos nie wyszlo dokotrku! Nie ma takiego leku/znizki");
        }
    }
    public void appoinment(){
        Scanner read = new Scanner(System.in);
        System.out.println("\nPESEL: ");
        String pesel = read.next();

        String med = "";
        System.out.println("\nMedicine: ");
        med = read.next();
        while (!med.equals("next")) {
            findDiscount(med);
            System.out.println("\nMedicine: ");
            med = read.next();
        }

    }
}
