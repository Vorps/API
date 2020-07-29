package net.vorps.api.utils;

import java.io.*;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class File {

    private String path;
    public File(String path){
        this.path = path;
    }

	private static void copyFolder(java.io.File src, java.io.File dest) throws IOException {
		if(src.isDirectory()){
			if(!dest.exists()){
				dest.mkdir();
			}
			String files[] = src.list();
			for(String file : files){
				java.io.File srcFile = new java.io.File(src, file);
				java.io.File destFile = new java.io.File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			
			byte[] buffer = new byte[2048];
			
			int length;
			while((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}

    public static void initMap(String from, String to){
        java.io.File fromFile = new java.io.File(from);
        java.io.File toFile = new java.io.File(to);
        try{
            File.copyFolder(fromFile, toFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean delete(){
        return new java.io.File(this.path).delete();
    }


}
