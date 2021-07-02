/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class ParseEmail {
	static org.slf4j.Logger log = LoggerFactory.getLogger(ParseEmail.class);

	// Search a particular content from the email body
	// Fetching email by subject

	public String searchContent(Email email, String elementLocator, String Subject, String searchContent) {
		String resMail = null;
		int newMailCount = 0;
		Message[] msgs;
		try {
			log.info("entering in verifyPlaceholders");
			Thread.sleep(1000);
			newMailCount = email.getMailCountFromInbox();
			if (newMailCount > 0) {
				log.info("New mail count: " + newMailCount);
				msgs = email.fetchMailsFromInbox(Subject);
				for (Message msg : msgs) {
					System.out.println("Subject line of new mail: " + msg.getSubject());
					resMail = ParseEmail.extractContent(msg, elementLocator, searchContent);
					// break;
				}
			} else {
				log.info("There are no new mails");
			}
			if (newMailCount == 0) {
				throw new Exception("No Emails Present");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not parse mail");
		}
		return resMail;
	}

	// To extract a content from an email

	public static String extractContent(Message msg, String xpath, String searchContent) {
		String content = null;
		try {
			content = XMLParser.retrieveTextContent(fetchMessagetextHtmlContent(msg), xpath, searchContent);
			// System.out.print(content);
		} catch (Exception e) {
			log.info("failing in comparing the mails");
			e.printStackTrace();
		}
		return content;
	}

	private static boolean ifMailContainsTrue(String x, String y) {
		x = x.trim().replaceAll("\\s+", "");
		y = y.trim().replaceAll("\\s+", "");
		return x.contains(y);
	}

	private static String fetchMessagetextHtmlContent(Part p) throws IOException, MessagingException {
		StringBuilder content = new StringBuilder();
		if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				content.append(fetchMessagetextHtmlContent(mp.getBodyPart(i)));
		} else if (p.isMimeType("text/html")) {
			content.append((String) p.getContent());
		}
		return content.toString();
	}

	// Check for placeholder in an email

	private static boolean checkforPlaceholders(Message msg, String placeholderValue) {
		boolean result = false;
		try {
			String content = fetchMessagetextHtmlContent(msg);
			result = ifMailContainsTrue(content, placeholderValue);
		} catch (Exception e) {
			log.info("failing in comparing the mails");
			e.printStackTrace();
		}
		return result;
	}

	// To check for a particular value in the email content
	public boolean verifyContent(Email email, String emailTemplate) {
		boolean resMail = false;
		int newMailCount = 0;
		Message[] msgs;
		try {
			log.info("entering in verifyPlaceholders");
			Thread.sleep(1000);
			newMailCount = email.getMailCountFromInbox();
			if (newMailCount > 1) {
				log.info("New mail count: " + newMailCount);
				msgs = email.fetchMailsFromInbox();
				for (Message msg : msgs) {
					log.info("Subject line of new mail: " + msg.getSubject());
					resMail = ParseEmail.checkforPlaceholders(msg, emailTemplate);
					if (resMail) {
						int foundMailNo = msg.getMessageNumber();
						log.info("MAIL FOUND with a placeholder! Mail No: " + foundMailNo);
						// break found;
					}
				}
			} else {
				log.info("There are no new mails");
			}
			if (newMailCount == 0) {
				throw new Exception("No Emails Present");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not parse mail");
		}
		return resMail;
	}

}
