package com.steganography.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.steganography.model.ReponseMessage;

@Service
public class DecodeService {

	static final String DECODEDMESSAGEFILE = "C:\\Users\\AYYAPPAN\\Steganography\\result.txt";
	public static String b_msg = "";
	public static int len = 0;

	public ReponseMessage SteganoDecode(MultipartFile image, int key) throws Exception {

		ReponseMessage reponseMessage = new ReponseMessage();
		System.out.println("\n\n----------Decryption Started----------");
		System.out.println("\nkey : " + key);
		File convFile = new File(image.getOriginalFilename());
		String imageFilePath = convFile.getAbsolutePath();
		System.out.println("\nimageAbsoluteFileName : " + convFile.getAbsolutePath());

		final String STEGIMAGEFILE = imageFilePath;

		BufferedImage yImage = readImageFile(STEGIMAGEFILE);

		DecodeTheMessage(yImage);
		String msg = "";
		for (int i = 0; i < len * 8; i = i + 8) {

			String sub = b_msg.substring(i, i + 8);

			int m = Integer.parseInt(sub, 2);
			char ch = (char) m;
			msg += ch;
		}

		String arr[] = msg.split(" ", 2);
		String keyCheckString = arr[0];
		String cipherMessage = arr[1];

		int keyCheck = Integer.parseInt(keyCheckString);
		System.out.println("\nkey check : " + keyCheck);
		System.out.println("\ncipher message : " + cipherMessage);

		if (key == keyCheck) {

			String textMessage = cipherTotextMessage(cipherMessage, key);
			PrintWriter out = new PrintWriter(new FileWriter(DECODEDMESSAGEFILE, false), true);
			out.write(textMessage);
			out.close();

			System.out.println("\nDecoded Message: " + textMessage);
			System.out.println("\nSuccessfully Decoded...");
			System.out.println("\nResult in File: " + DECODEDMESSAGEFILE);

			System.out.println("\n----------Decryption Ended----------");

			reponseMessage.setResponse(true);
			reponseMessage.setMessage(textMessage);
		} else {
			System.out.println("\nKey is wrong");

			System.out.println("----------Decryption Ended----------");
			reponseMessage.setResponse(false);
		}

		return reponseMessage;

	}

	private String cipherTotextMessage(String cipherMessage, int key) {
		final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+{}[]/?.-;:<>";
		String message = "";
		char replaceVal;
		for (int ii = 0; ii < cipherMessage.length(); ii++) {
			char letter = cipherMessage.charAt(ii);
			int charPosition = ALPHABET.indexOf(cipherMessage.charAt(ii));
			// System.out.println(charPosition);
			if (charPosition >= 0) {
				int keyVal = (charPosition - key) % 86;
				if (keyVal < 0) {
					keyVal = ALPHABET.length() + keyVal;
				}
				replaceVal = ALPHABET.charAt(keyVal);
			} else {
				replaceVal = letter;
			}

			message += replaceVal;
		}
		return message;
	}

	public static BufferedImage readImageFile(String STEGIMAGEFILE) {

		BufferedImage theImage = null;
		File p = new File(STEGIMAGEFILE);
		try {
			theImage = ImageIO.read(p);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return theImage;
	}

	public static void DecodeTheMessage(BufferedImage yImage) throws Exception {

		int currentBitEntry = 0;
		String bx_msg = "";
		for (int x = 0; x < yImage.getWidth(); x++) {
			for (int y = 0; y < yImage.getHeight(); y++) {
				if (x == 0 && y < 8) {
					int currentPixel = yImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					bx_msg += x_s.charAt(x_s.length() - 1);
					len = Integer.parseInt(bx_msg, 2);

				} else if (currentBitEntry < len * 8) {
					int currentPixel = yImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					b_msg += x_s.charAt(x_s.length() - 1);

					currentBitEntry++;
				}
			}
		}
	}

}
