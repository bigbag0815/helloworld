package com.bullshit.endpoint.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static void saveFile(InputStream uploadedInputStream, String location) 
			throws Exception {
			int read = 0;
			byte[] bytes = new byte[1024];
			OutputStream outpuStream = new FileOutputStream(new File("/var/www" + location));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
	}
}
