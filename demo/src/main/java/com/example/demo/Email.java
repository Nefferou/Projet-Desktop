package com.example.demo;



import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Email extends PersistentObject{

    private String body ;
    private String sender ;
    private String recipient ;
    private String subject ;
    private Date date ;

    /**************************************************************
     * Constructeur complet, pour l'envoi ou la réception
     * @param sender
     * @param recipient
     * @param subject
     * @param body
     * @param date
     ***************************************************************/
    public Email(String sender, String recipient, String subject, String body, Date date)
    {
        this.body = body ;
        this.date = date ;
        this.recipient = recipient;
        this.sender = sender ;
        this.subject = subject ;
    }

    /**************************************************
     * Constructeur pour la revception d'email
     * @param sender
     * @param subject
     * @param body
     * @param date
     ****************************************************/
    public Email(String sender, String subject, String body, Date date)
    {
        this (sender, null, subject, body, date) ;
    }

    /**************************************************************
     * Construction d'un email à envoyer
     * @param recipient
     * @param subject
     * @param body
     *******************************************************************/
    public Email(String recipient, String subject, String body)
    {
        this (null, recipient, subject, body, new Date()) ;
    }

    /********************************************************
     * Autre constructeur qui se base sur un objet Message
     * @param message
     ********************************************************/
    public Email (Message message)
    {

        try {
            this.recipient = ((Address)message.getRecipients(Message.RecipientType.TO)[0]).toString() ;
            this.sender = ((Address)message.getFrom()[0]).toString() ;
            this.date = message.getSentDate() ;
            this.subject = message.getSubject();
            this.body = ReadEmails.getTextFromMessage(message) ;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Email(){};

    /* PERSIST AND FETCH */

    public void persist() {
        try {
            Connection conn = dbConnection();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(this.date);

            String query = "SELECT * FROM email WHERE sender=? " +
                    "AND recipient=? " +
                    "AND body=? " +
                    "AND subject=? " +
                    "AND date=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.sender);
            pstmt.setString(2, this.recipient);
            pstmt.setString(3, this.body);
            pstmt.setString(4, this.subject);
            pstmt.setDate(5, new java.sql.Date(this.date.getTime()));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                System.out.println("Objet déjà existant \n\tTraitement suivant");
            }
            else {
                String query2 = "INSERT INTO email(sender, recipient, subject, body, date) VALUES (?,?,?,?,?)";
                PreparedStatement pst = dbConnection().prepareStatement(query2);

                pst.setString(1, this.sender);
                pst.setString(2, this.recipient);
                pst.setString(3, this.subject);
                pst.setString(4, this.body);
                pst.setDate(5, new java.sql.Date(this.date.getTime()));

                System.out.println("Objet non existant \n\tCréation de l'objet");
                pst.executeUpdate();
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Email> fetchAll() {
        List<Email> all = new ArrayList<Email>();
        try {
            Email e = new Email();
            Connection conn = e.dbConnection();

            Statement st = conn.createStatement();
            String query = "SELECT * FROM email";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                Email p = new Email(rs.getString("sender"),
                        rs.getString("recipient"),
                        rs.getString("subject"),
                        rs.getString("body"),
                        rs.getDate("date"));
                all.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    /* GETTERS et SETTERS */

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return subject ;
    }
}