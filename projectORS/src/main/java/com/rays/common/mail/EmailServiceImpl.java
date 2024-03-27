package com.rays.common.mail;

import java.io.File;
import java.util.Iterator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.rays.common.UserContext;
import com.rays.common.attachment.AttachmentDTO;
import com.rays.common.attachment.AttachmentServiceInt;
import com.rays.common.message.MessageDTO;
import com.rays.common.message.MessageServiceInt;

/**
 * Provides email services
 * 
 * @authorHarsh Patidar
 */
@Component
public class EmailServiceImpl {

	/**
	 * Send email
	 */
	@Autowired
	public JavaMailSender emailSender;

	/**
	 * Get messages from database
	 */
	@Autowired
	public MessageServiceInt messageService;

	/**
	 * Get attached filed by ids
	 */
	@Autowired
	private AttachmentServiceInt attachmentService;

	/**
	 * Sends an email
	 * 
	 * @param dto
	 * @param ctx
	 */

	/*
	 * project 4 >-Email send--> process; 
	 * 
	 *HashMap<String, String> map = new HashMap<String, String>();
	 * map.put("login", beanExist.getLogIn());
	 * map.put("password", beanExist.getPassword());
	 * map.put("firstName", beanExist.getFirstName());
	 * map.put("lastName", beanExist.getLastName());
	 * 
	 * String message = EmailBuilder.getChangePasswordMessage(map);
	 * 
	 * EmailMassage msg = new EmailMassage();
	 * 
	 * msg.setTo(beanExist.getLogIn());
	 * msg.setSubject("SUNRAYS ORS Password has been changed successfully");
	 * msg.setMessage(message); msg.setMassageType(EmailMassage.HTML_MSG);
	 * 
	 * EmailUtility.sendMail(msg);
	 */
	
	
	
	public void send(EmailDTO email, UserContext ctx) {
		if (email.getMessageCode() != null) {

			MessageDTO messageDTO = messageService.findByCode(email.getMessageCode(), ctx);

			// If message does not exist or message is active then return
			if (messageDTO == null || "Inactive".equals(messageDTO.getStatus())) {
				return;
			}
			/*
			 * project 4 EmailMessage email=new EmailMessage();
			 * msg.setsubject(params)
			 * msg.setMessage(message)
			 * 
			 **** EmailMessage ko >-relpase-> EmailDTO
			 * 
			 * project 10 se related--
			 * email.setSubject(messageDTO.getSubject(email.getMessageParams()));
			 * email.setMessage(messageDTO.getBody(email.getMessageParams()));
			 */

			email.setSubject(messageDTO.getSubject(email.getMessageParams()));
			/*
			 * es point pr message le aaya and message me params set kr ra he. project  >-4--me-->
			 * 
			 * EmailBuilder message =EmailBuilder.getforgotpassword(map);
			 * 
			 * project 10 me>----> 
			 * EmailBuilder.getforgotpassword(map)
			 * >-replace->
			 * getbody (email.getMessageParam());
			 */
			email.setBody(messageDTO.getBody(email.getMessageParams()));

			email.setIsHTML("Y".equals(messageDTO.getHtml()));

		}
//project 4---EmailUtility.sendMail(message);

		MimeMessage message = emailSender.createMimeMessage();

		try {
			System.out.println("Inside Emailserviceimpl try block");
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			if (email.getTo().size() > 0) {

				helper.setTo(email.getTo().toArray(new String[email.getTo().size()]));
			}

			if (email.getCc().size() > 0) {
				helper.setCc(email.getCc().toArray(new String[email.getCc().size()]));
			}
			if (email.getBcc().size() > 0) {
				helper.setBcc(email.getBcc().toArray(new String[email.getBcc().size()]));
			}

			helper.setSubject(email.getSubject());

			helper.setText(email.getBody(), email.getIsHTML());

			// Attach files from file system path
			Iterator<String> it = email.getAttachedFilePath().iterator();
			while (it.hasNext()) {
				FileSystemResource file = new FileSystemResource(new File(it.next()));
				helper.addAttachment(file.getFilename(), file);
			}

			// Attached files from database-->pic ki.
			Iterator<Long> itid = email.getAttachedFileId().iterator();
			while (itid.hasNext()) {
				Long id = itid.next();
				AttachmentDTO fileDto = attachmentService.findById(id, ctx);
				if (fileDto != null) {
					ByteArrayResource file = new ByteArrayResource(fileDto.getDoc());
					helper.addAttachment(fileDto.getName(), file);
				}
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		new Thread(new Runnable() {public void run() {emailSender.send(message);}  }).start();

			
				
		
		

	}
}
