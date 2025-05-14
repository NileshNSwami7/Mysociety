package com.mysociety.helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class Fileuploader {
	
	private String directorypath="D:\\Programming Room\\Spring program\\MySociety\\src\\main\\resources\\static\\files";
	
	public boolean fileuploads(MultipartFile multifiles) {
		boolean f = false;
		try{
			Files.copy(multifiles.getInputStream(),
					Paths.get(directorypath+File.separator+multifiles.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			f=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}
