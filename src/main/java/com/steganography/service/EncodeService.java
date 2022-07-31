package com.steganography.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.steganography.model.ReponseMessage;

@Service
public class EncodeService {

	final static String STEGIMAGEFILE = "C:\\Users\\AYYAPPAN\\workspace\\Steganography\\stegimage.jpg";

	public ReponseMessage SteganoEncode(MultipartFile image, String message, int key) throws Exception {
		ReponseMessage reponseMessage = new ReponseMessage();
		System.out.println("----------Encryption Started----------");
		System.out.println("\nmessage : " + message);
		System.out.println("\nkey : " + key);
		File convFile = new File(image.getOriginalFilename());
		String imageFilePath = convFile.getAbsolutePath();
		System.out.println("\nimageAbsoluteFileName : " + convFile.getAbsolutePath());

		final String COVERIMAGEFILE = imageFilePath;

		String cipherMessage = textToCipherMessage(message, key);

		System.out.println("\ncipher message : " + cipherMessage +"\n");

		String contentOfMessageFile = key + " " + cipherMessage;

		int[] bits = bit_Msg(contentOfMessageFile);

		for (int i = 0; i < bits.length; i++)
			System.out.print(bits[i]);
		System.out.println();
		System.out.println("\nNo. of Characters: " + message.length());
		System.out.println("\nBits Length: " + bits.length);

		BufferedImage theImage = readImageFile(COVERIMAGEFILE);
		hideTheMessage(bits, theImage);
		System.out.println("\nSuccessfully Encodeded");

		System.out.println("\nResultant Stego Image: " + STEGIMAGEFILE);
		
		System.out.println("\n----------Encryption Ended----------");

		reponseMessage.setResponse(true);
		
		return reponseMessage;

	}

	private String textToCipherMessage(String message, int key) {
		final String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+{}[]/?.-;:<>";
		String cipherText = "";
		char replaceVal;
		for (int ii = 0; ii < message.length(); ii++) {
			char letter = message.charAt(ii);
			int charPosition = alpha.indexOf(message.charAt(ii));
			// System.out.println(message.charAt(ii) + " " + charPosition);
			if (charPosition >= 0) {
				int keyVal = (key + charPosition) % 86;
				replaceVal = alpha.charAt(keyVal);
			} else {
				replaceVal = letter;
			}
			// System.out.println(replaceVal);
			cipherText += replaceVal;
		}
		return cipherText;
	}

	public static int[] bit_Msg(String msg) {
		int j = 0;
		int[] b_msg = new int[msg.length() * 8];
		for (int i = 0; i < msg.length(); i++) {
			int x = msg.charAt(i);
			String x_s = Integer.toBinaryString(x);
			while (x_s.length() != 8) {
				x_s = '0' + x_s;
			}

			for (int i1 = 0; i1 < 8; i1++) {
				b_msg[j] = Integer.parseInt(String.valueOf(x_s.charAt(i1)));
				j++;
			}
			;
		}

		return b_msg;
	}

	public static BufferedImage readImageFile(String COVERIMAGEFILE) {
		BufferedImage theImage = null;
		File p = new File(COVERIMAGEFILE);
		try {
			theImage = ImageIO.read(p);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return theImage;
	}

	public static void hideTheMessage(int[] bits, BufferedImage theImage) throws Exception {
		File f = new File(STEGIMAGEFILE);
		int bit_l = bits.length / 8;
		int[] bl_msg = new int[8];
		String bl_s = Integer.toBinaryString(bit_l);
		while (bl_s.length() != 8) {
			bl_s = '0' + bl_s;
		}
		for (int i1 = 0; i1 < 8; i1++) {
			bl_msg[i1] = Integer.parseInt(String.valueOf(bl_s.charAt(i1)));
		}
		;
		int j = 0;
		int b = 0;
		int currentBitEntry = 8;

		for (int x = 0; x < theImage.getWidth(); x++) {
			for (int y = 0; y < theImage.getHeight(); y++) {
				if (x == 0 && y < 8) {
					int currentPixel = theImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					String sten_s = x_s.substring(0, x_s.length() - 1);
					sten_s = sten_s + Integer.toString(bl_msg[b]);

					int s_pixel = Integer.parseInt(sten_s, 2);
					int a = 255;
					int rgb = (a << 24) | (red << 16) | (green << 8) | s_pixel;
					theImage.setRGB(x, y, rgb);
					ImageIO.write(theImage, "png", f);
					b++;

				} else if (currentBitEntry < bits.length + 8) {

					int currentPixel = theImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					String sten_s = x_s.substring(0, x_s.length() - 1);
					sten_s = sten_s + Integer.toString(bits[j]);
					j++;
					int s_pixel = Integer.parseInt(sten_s, 2);

					int a = 255;
					int rgb = (a << 24) | (red << 16) | (green << 8) | s_pixel;
					theImage.setRGB(x, y, rgb);
					ImageIO.write(theImage, "png", f);

					currentBitEntry++;
				}
			}
		}
	}

}
