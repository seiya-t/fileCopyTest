package com.azuki.fileCopyTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCopyTest {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		final String SRC_PATH = "C:\\temp\\testSrc.m2ts";

		long start = 0;
		long stop = 0;

		try {
			System.out.println("***** streamCopy *****");
			start = System.currentTimeMillis();
			streamCopy(SRC_PATH, "C:\\temp\\streamCopy\\testSrc.m2ts");
			stop = System.currentTimeMillis();
			System.out.println("実行時間： " + (stop - start) + " ms");

			System.out.println("***** filesCopy *****");
			start = System.currentTimeMillis();
			filesCopy(SRC_PATH, "C:\\temp\\filesCopy\\testSrc.m2ts");
			stop = System.currentTimeMillis();
			System.out.println("実行時間： " + (stop - start) + " ms");

			System.out.println("***** channelCopy *****");
			start = System.currentTimeMillis();
			channelCopy(SRC_PATH, "C:\\temp\\channelCopy\\testSrc.m2ts");
			stop = System.currentTimeMillis();
			System.out.println("実行時間： " + (stop - start) + " ms");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public static boolean streamCopy(String srcFileName, String desFileName) throws IOException {
		FileInputStream fis  = new FileInputStream(srcFileName);
		FileOutputStream fos = new FileOutputStream(desFileName);
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (fis != null) fis.close();
			if (fos != null) fos.close();
		}
		return true;
	}

	public static boolean channelCopy(String srcFileName, String desFileName) throws IOException {
		FileChannel inChannel = new FileInputStream(srcFileName).getChannel();
		FileChannel outChannel = new FileOutputStream(desFileName).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(),outChannel);
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
		return true;
	}

	public static boolean filesCopy(String srcFileName, String desFileName) throws IOException {
		Path src = Paths.get(srcFileName);
		Path dest = Paths.get(desFileName);

		Files.copy(src, dest);

		return true;
	}
}