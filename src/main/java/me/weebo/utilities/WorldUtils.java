package me.weebo.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WorldUtils {
	
	public static boolean copy(String src, String dest) {
		File srcFolder = new File(src);
		File destFolder = new File(dest);
		if (!srcFolder.exists()) {
			return false;
		}
		try {
			System.out.println("Starting to copy '" + src + "' to '" + dest + "'.");
			copyFolder(srcFolder, destFolder);
		} catch (IOException e) {}
		System.out.println("Copying finished.");
		return true;
	}
	  
	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String[] files = src.list();
			if (files.length > 0) {
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					copyFolder(srcFile, destFile);
				}
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
}
