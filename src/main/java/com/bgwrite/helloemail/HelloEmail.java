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
        properties.put("mail.smtp.starttls.enable", dotenv.get("MAIL_SMTP_STARTTLS.ENABLE"));
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
            message.setRecipient(Message.RecipientType.CC, new InternetAddress("bradywgerber@gmail.com"));

            String subjectTest = "Bailey, does this work!";
            String htmlTest =
                    "<body><p>I'm building a new email sending from scratch. Let me know if you get this or if any weird errors come up?</p><p>Brady</p></body>";

            String subjectNewsletter = "Theo Alexander - Sunbathing Through a Glass Screen (2021)";
            String htmlNewsletter =
                    "<body>" +
                    "<div style='margin-left:2.5rem;margin-right:2.5rem'>" +
                    // BEFORE I SEND, MAKE SURE THE FOLLOWING LINKS ARE UPDATED AND TRACKABLE
                    "<h1>Theo Alexander - Sunbathing Through a Glass Screen (2021)</h1>" +
                    "<div>Brady Gerber | Nov 29, 2021 | <a href='#'>Read Online</a></div>" +
                    "<div><a href='https://www.youtube.com/watch?v=dPBrwz_T8Fw'><img alt='A Wednesday Morning Somewhere' src='https://picsum.photos/750?random=1.webp'/></a></div>" +
                    "<div><small><i>Click the image to listen.</i></small></div>" +
                    "</div>" +
                    // AGAIN, MAKE SURE THAT THE ABOVE HAS BEEN UPDATED!

                    // THE ACTUAL REVIEW
                    "<div style='margin-left:2.5rem;margin-right:2.5rem;font-size:16px'>" +
                    "<p>In a very weird year, this remains my favorite album of 2021. With 2020 being the last year that many of the blockbuster albums recorded pre-COVID were released, 2021 felt full of shrugs. The good news is that this kind of year allows for younger and less popular artists to stand out in my queue.</p>" +
                    "<p>I don't think Theo Alexander is that obscure - I've been following him for a long time - but this felt like the best possible year for his music to find what I hoped would be a larger audience.</p>" +
                    "<p>I like it.</p>" +
                    "</div>" +

                    // CLOSING CREDITS
                    "<p style='margin-left:2.5rem;margin-right:2.5rem;font-size:16px'><i>Onward,<br>-b</i></p><br>" +
                    "<div style='margin-left:2.5rem;margin-right:2.5rem'>" +
                    "<small><i><p>My name is Brady Gerber, and I'm a music journalist (New York Magazine, Pitchfork, Rolling Stone, etc) sending out new album reviews every possible Wednesday morning by 7AM ET. " +
                    "This email was sent from a Java program that I built with the JavaMail API and Lorem Picsum. " +
                    "If you'd like to subscribe, you can sign up on my <a href='https://bit.ly/3DGbHnj'>website</a>.</p>" +
                    "<p>Want to support the newsletter? You're already helping by reading this sentence. You also can donate to my <a href='https://bit.ly/3CLgEtq'>coffee fund</a> and/or forward this email to just one new friend. Sharing really is caring." +
                    "<p>'Fill up your mind with all it can know ... Don't forget that your body will let it all go ...' - Jeff Tweedy</p>" +
                    "<p><a href='https://bit.ly/3kZ3za6'>Twitter</a> | <a href='https://bit.ly/3CKrT5n'>LinkedIn</a> | <a href='https://bit.ly/3cDGmWc'>GitHub</a></p>" +
                    "<p>If you'd like to stop receiving these emails, you can <a href='https://bit.ly/3ryls3N'>unsubscribe</a>, no problem.</p>" +
                    "<p>In accordance to the CAN-SPAM legislation that requires these emails to have physical addresses in the footer, here's the address of the USA R&D center for JetBrains, the company that builds the Intellij IDEA IDE that I'm using for Java: 2 Seaport Lane, Suite 8C, 8th Fl // Seaport EAST, Boston, MA 02210.</p>" +
                    "</i></small>" +
                    "</div>" +
                    "</body>";

            message.setSubject(subjectNewsletter); // either "subjectTest" or "subjectNewsletter"
            message.setContent(htmlNewsletter, "text/html"); // either "htmlTest" or "htmlNewsletter"

            return message;
        } catch (Exception ex) {
            Logger.getLogger(HelloEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}