package com.pch.search.utilities;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.sun.mail.util.MailSSLSocketFactory;


public class EmailUtils {

	Store store=null;
	private Folder inbox;

	/**
	 * Returns "success" if logged into mail server mailbox is opened, successfully.
	 * @param hostName
	 * @param userName
	 * @param password
	 * @return
	 */
	public String connectToMailServer(String hostName,String userName,String password){
		Properties props = new Properties();
		MailSSLSocketFactory socketFactory;
		try {
			socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustAllHosts(true);
			props.put("mail.pop3s.ssl.socketFactory", socketFactory);
			Session session = Session.getDefaultInstance(props);			
			this.store = session.getStore("pop3");
			CustomLogger.log("Connecting to Mailserver : "+ hostName+" with UserName : "+userName+" Password : "+password);
			store.connect(hostName, userName, password);
			CustomLogger.log("Connected succesfully to mail server.");
			return "success";
		} catch (GeneralSecurityException e) {			
			CustomLogger.logException(e);
			return e.getMessage();
		} catch (NoSuchProviderException e) {			
			CustomLogger.logException(e);
			return e.getMessage();
		} catch (MessagingException e) {
			CustomLogger.logException(e);
			return e.getMessage();
		}
	}


	private Message[] getRecentMessages(int count) throws MessagingException{
		inbox=store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		System.out.println("Getting last "+count+" emails.");
		if (count>0){
			int length=inbox.getMessages().length;
			Message[] msgs = inbox.getMessages(length-count+1,length);
			return msgs;
		}else{
			return null;
		}
	}

	public void closeEmailConnection() throws MessagingException {

		store.close();
	}

	private void closeInbox() throws MessagingException{
		inbox.close(true);
	}


	private boolean isMessageRead(Message msg) throws MessagingException{
		Flags flgs=msg.getFlags();
		return flgs.contains(Flag.SEEN);
	}

	private Date getMessageSentDate(Message msg) throws MessagingException{
		Date msgSentDate=null;
		try {
			//DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");		
			DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");		
			String sentDate = Arrays.asList(msg.getHeader("Date")).get(0);
			CustomLogger.log(sentDate);
		//vamsi k Dec-10-2015
			sentDate=Common.subString(sentDate, "(\\d{2}\\s[a-z A-Z]{3}\\s[0-9]{4}\\s\\d{2}.\\d{2}.\\d{2}\\s[-+]\\d{4})");
			//sentDate=Common.subString(sentDate, "[a-z A-Z]{3} \\d{2} [a-z A-Z]{3} \\d{4} \\d{2}:\\d{2}:\\d{2} [- +]\\d{4}");
			msgSentDate = df.parse(sentDate);
			System.out.println(msgSentDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			CustomLogger.logException(e);			
		}
		return msgSentDate;
	}
	
	
	private boolean isvalidMessageTimeLine(Message msg, Date passwordInitiatedDate) throws MessagingException{
		Date d1 = getMessageSentDate(msg); 
		CustomLogger.log("Comparing email sent date "+d1+" (in millis "+d1.getTime()+") with password initiated date "+passwordInitiatedDate+" (in millis "+passwordInitiatedDate.getTime()+")");
		
		/*if(d1.equals(passwordInitiatedDate)){
			System.out.println("Test");
		}*/
		
		if(d1.getTime()-passwordInitiatedDate.getTime()>=0){
			return true;
		}else{
			return false;
		}
		
		/*if(d1.compareTo(passwordInitiatedDate)>=0){
			return true;
		}else{
			return false;
		}*/
	}

	/*public void deleteOldForgotPasswordEmails_should be removed(){
		String sender ="Publishers Clearing House <PublishersClearingHouse@pchcustomerservice.pch.com>";
		String subject="Forgot Your Password?";

		String msgSender,msgSubject;
		boolean isRead;

		CustomLogger.log("Marking previously present reset Password email as read Emails.");
		try {
			Message[] msgs=getRecentMessages(10);				
			for(Message msg:msgs){
				msgSender=msg.getFrom()[0].toString();
				msgSubject=msg.getSubject();
				isRead=isMessageRead(msg);				
				if(!isRead &&
						msgSubject.equals(subject)&&
						msgSender.equals(sender))
				{
					CustomLogger.log("Unread reset password email found marking read.");					
					msg.setFlag(Flag.SEEN, true);

					CustomLogger.log("Marked Read");
				}
			}
			closeInbox();
			//store.close();

		} catch (MessagingException e) {			
			CustomLogger.logException(e);				
		} 
	}*/

	/**
	 * 
	 * @param date time at which sending and of password was triggered.
	 * @return the password link from email
	 */
	public String getPasswordResetLink(Date d){
		//d = Common.getDateInTimeZone(d, "America/New_York");		
		String sender ="Publishers Clearing House <PublishersClearingHouse@e.pchcustomerservice.pch.com>";
		String subject="Forgot Your Password?";
//		String contentType ="text/html";
		String contentType ="multipart/alternative";
		String resetLink=null;
		String msgSender,msgSubject,msgContentType;
		boolean isRead,validMessageTimeLine;
		int counter=1;
		while(counter<=5){
			CustomLogger.log("Checking emails.");
			try {
				Message[] msgs=getRecentMessages(5);				
				for(Message msg:msgs){
					msgSender=msg.getFrom()[0].toString();
					msgSubject=msg.getSubject();
					msgContentType=msg.getContentType();
					isRead=isMessageRead(msg);
					
					CustomLogger.log("*****************************-------Reading email-------*****************************");
					
					CustomLogger.log(String.format("IsRead :%s \n Subject : %s\n Sender : %s\n ContentType :%s\n Sent date : %s", isRead,msgSubject,msgSender,msgContentType,getMessageSentDate(msg)));
					/*
					 * Message is a valid Forgot password if 
					 * - It's not read already.
					 * - Sender is Publishers Clearing house
					 * - Subject is 'Forgot Your password'
					 * - Message contains HTML type content (as there is a continue button)
					 * - Message is sent after the password reset was requested.
					 */
					validMessageTimeLine= isvalidMessageTimeLine(msg,d);
					if(!isRead &&
							msgSubject.equals(subject)&&
							msgSender.equals(sender)&&
							msgContentType.contains(contentType)&&
							validMessageTimeLine)
					{
						String htmlContent = msg.getContent().toString();						
						Document doc=Jsoup.parse(htmlContent);
						for(Element e:doc.getElementsByTag("img")){						
							if(e.attr("alt").equals("Create Password")){
								resetLink=(e.parent().attr("href"));
								CustomLogger.log("Password reset link - "+resetLink);
								closeInbox();
								//store.close();
								return resetLink;
							}
						}
					}
				}		
				counter++;
				CustomLogger.log("Reset password email not found in inbox, will re-check after 5 seconds.");
				Common.sleepFor(7000);
			} catch (MessagingException e) {			
				CustomLogger.logException(e);
				return null;
			} catch (IOException e) {			
				CustomLogger.logException(e);
				return null;
			}
		}
		CustomLogger.log("After 5 retries Reset password email not found in inbox , returning reset link as null.");
		return null;
	}
}
