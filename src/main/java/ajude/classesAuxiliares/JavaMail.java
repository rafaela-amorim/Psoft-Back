package ajude.classesAuxiliares;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {

	public JavaMail(String destinatario, String assunto, String msg) {
		super();
	}

	public static boolean enviar(String msg, String txt, String email) {
		boolean retorno = false;
		
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class", 
	    "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	 
	    Session session = Session.getDefaultInstance(props,
	      new javax.mail.Authenticator() {
	           protected PasswordAuthentication getPasswordAuthentication() 
	           {
	                 return new PasswordAuthentication("ajude.projsw@gmail.com", 
	                 "ajudeproj");
	           }
	      });
	    
	    session.setDebug(true);
	    
	    try {
	 
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("ajude.projsw@gmail.com")); 
	 
	      Address[] toUser = InternetAddress //Destinatário
	                 .parse(email);  
	 
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject(msg); //Assunto
	      message.setText(txt);
	      /**Método para enviar a mensagem criada*/
	      Transport.send(message);
	 
	      retorno = true;
	      
	     } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	    
	    return retorno;
	}
}