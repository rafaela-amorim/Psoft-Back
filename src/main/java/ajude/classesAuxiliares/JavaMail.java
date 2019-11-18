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
	public static void main(String[] args) {
		JavaMail.enviar();
	}
	
	private String destinatario;
	private String assunto;
	private String msg;
	
	public JavaMail(String destinatario, String assunto, String msg) {
		super();
		this.destinatario = destinatario;
		this.assunto = assunto;
		this.msg = msg;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static boolean enviar() {
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
	                 return new PasswordAuthentication("ajude.projsoft@gmail.com", 
	                 "ajudeproj");
	           }
	      });
	    
	    session.setDebug(true);
	    
	    try {
	 
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("ajude.projsoft@gmail.com")); 
	 
	      Address[] toUser = InternetAddress //Destinatário(s)
	                 .parse("ajude.projsoft@gmail.com");  
	 
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject("Enviando email com JavaMail");//Assunto
	      message.setText("teste javamail");
	      /**Método para enviar a mensagem criada*/
	      Transport.send(message);
	 
	      retorno = true;
	      
	     } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	    
	    return retorno;
	 
	}
	
}