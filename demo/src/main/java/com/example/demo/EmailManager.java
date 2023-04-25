package com.example.demo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailManager {
    public static void sendEmail(Session session, String destinataire, String sujet, String message) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setSubject(sujet, "UTF-8");

            msg.setText(message, "UTF-8");

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire, false));
            Transport.send(msg);

            System.out.println("sendEmail => OK !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Email> getEmails() {
        List<Email> listEmail = new ArrayList<>();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "993");
        properties.put("mail.smtp.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);
        try {
            Store store = emailSession.getStore("imaps");
            store.connect("imap.gmail.com", "fertijava@gmail.com", "egfrunrjlrklfeos");
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            System.out.println("Vous avez " + messages.length + " mail(s)");

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                Email mail = new Email(((InternetAddress) message.getFrom()[0]).getAddress(), message.getAllRecipients()[0].toString(), message.getSubject(), ReadEmails.getTextFromMessage(message), message.getSentDate());
                listEmail.add(mail);
                /*System.out.println("Sujet : " + message.getSubject());
                System.out.println("De : " + ((InternetAddress) message.getFrom()[0]).getAddress());
                System.out.println("Date : " + message.getSentDate());
                System.out.println("Text : " + ReadEmails.getTextFromMessage(message));
                System.out.println("---------------------------------------\n");*/
            }
            emailFolder.close();
            store.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listEmail;
    }
}
