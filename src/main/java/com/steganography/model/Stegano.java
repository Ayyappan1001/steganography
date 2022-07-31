package com.steganography.model;

import java.io.File;

public class Stegano {

	private File image;
	private String message;
	private File messageFile;
	private String key;

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getMessageFile() {
		return messageFile;
	}

	public void setMessageFile(File messageFile) {
		this.messageFile = messageFile;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
