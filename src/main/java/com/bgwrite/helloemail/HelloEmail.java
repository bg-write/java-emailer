package com.bgwrite.helloemail;

import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloEmail {
    public static void  sendMail(String recipient) throws Exception {
        Dotenv dotenv = Dotenv.load();

        System.out.println("Preparing to send email to <" +  recipient + ">");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", dotenv.get("MAIL_SMTP_AUTH"));
        properties.put("mail.smtp.ssl.enable", dotenv.get("MAIL_SMTP_STARTTLS.ENABLE"));
        properties.put("mail.smtp.host", dotenv.get("MAIL_SMTP_HOST"));
        properties.put("mail.smtp.port", dotenv.get("MAIL_SMTP_PORT"));

        String myAccountEmail = dotenv.get("ACC_E");
        String password = dotenv.get("ACC_P");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recipient);

        Transport.send(message);
        System.out.println("Message sent successfully to <" +  recipient + ">");
        System.out.println("-------------------------------------------------");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            String subjectTest = "Write Your Subject Line Here";
            String htmlTest =
            "<html>" +
                    "<head>" +
                        "<style>" +
                            ".style {" +
                                "font-size: 26px;" +
                                "font-family: Georgia, serif;" +
                                "color: red;" +
                            "}" +
                            "@media screen and (min-width: 500px) {" +
                                ".style {" +
                                    "font-size: 21px;" +
                                    "font-family: Georgia, serif;" +
                                "}" +
                            "}" +
                        "</style>" +
                    "</head>" +
                    "<body class='style'>" +
                        "<div>" +
                            "<img alt='A Wednesday Morning Somewhere' src='https://picsum.photos/440?random=1.webp'/ style='border: 1px solid black'>" +
                        "</div>" +
                        "<p>Hello, World.</p>" +
                        "<div>" +
                            "<small><i><p>CAN-SPAM legislation requires newsletter emails to have physical addresses in the footer, so make sure to include an address here.</p></i></small>" +
                        "</div>" +
                    "</body>" +
            "</html>";

            message.setSubject(subjectTest);
            message.setContent(htmlTest, "text/html");

            return message;
        } catch (Exception ex) {
            Logger.getLogger(HelloEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}