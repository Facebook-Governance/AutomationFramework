
/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/package com.omni.pfm.utility;

import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

/*
Created by Amit Agarwal on 09/21/16. A utility to access Emails
 */

public class Email {

	Store store;

	// Login to Gmail
	public void login(final String userName, final String password) throws MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		// props.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		Session emailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		Store store = emailSession.getStore("imaps");
		store.connect("imap.gmail.com", userName, password);
		this.store = store;
	}

	// Fetches all mail from inbox
	public Message[] fetchMailsFromInbox() throws MessagingException {
		Folder inbox = this.store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		Message[] msgs = inbox.getMessages();
		return msgs;
	}

	// Fetches mail from inbox based on a subject
	public Message[] fetchMailsFromInbox(String mailSubject) throws MessagingException {
		final String MailSubject = mailSubject;
		Folder inbox = this.store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		int mailCount = this.getMailCountFromInbox();
		int start = mailCount > 100 ? (mailCount - 100) : 1;
		System.out.println("Mail count: " + mailCount);
		System.out.println("Start count: " + start);
		SearchTerm term = new SearchTerm() {

			private static final long serialVersionUID = -1775780434520075031L;

			@Override
			public boolean match(Message msg) {
				try {
					if (msg.getSubject().equals(MailSubject))
						return true;
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return false;
			}
		};

		Message[] msgs = inbox.search(term, inbox.getMessages(start, mailCount));
		// Message[] msgs = inbox.getMessages(start, mailCount);
		return msgs;
	}

	// Fetches mails for the current day only
	public Message[] fetchTodayMailsFromInbox() throws MessagingException {
		Folder inbox = this.store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		// Fetching mails received on current date
		SearchTerm st = new ReceivedDateTerm(ComparisonTerm.EQ, new Date());
		Message[] msgs = inbox.search(st);
		return msgs;
	}

	// RETURNS THE COUNT OF MAILS FROM THE INBOX
	public int getMailCountFromInbox() throws MessagingException {
		Folder inbox = this.store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		return inbox.getMessageCount();
	}

	// DELETES MAILS FROM INBOX
	public void deleteAllMailsFromInbox() throws MessagingException {
		Folder folderInbox = this.store.getFolder("Inbox");
		folderInbox.open(Folder.READ_WRITE);

		// Fetches new messages from server
		Message[] arrayMessages = folderInbox.getMessages();

		for (int i = 0; i < arrayMessages.length; i++) {
			Message message = arrayMessages[i];
			String subject = message.getSubject();
			message.setFlag(Flags.Flag.DELETED, true);
			System.out.println("Marked DELETE for message: " + subject);
		}
		boolean expunge = true;
		folderInbox.close(expunge);
	}

}
