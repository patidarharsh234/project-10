package com.rays.common.projectattachment;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

/**
 * Contains attached file information and data
 * 
 * @authorHarsh Patidar
 */

@Entity
@Table(name = "ST_PROJECTATTACHMENT")

public class ProjectAttachmentDTO extends ProjectAttachmentBaseDTO {

	public ProjectAttachmentDTO() {
		super();
	}

	public ProjectAttachmentDTO(MultipartFile file) {
		name = file.getOriginalFilename();
		type = file.getContentType();
		try {
			doc = file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Contains file data
	 */
	
	@Lob
	@Column(name = "DOC")
	private byte[] doc;

	public byte[] getDoc() {
		return doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

}