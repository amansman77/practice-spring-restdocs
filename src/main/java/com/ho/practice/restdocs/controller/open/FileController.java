package com.ho.practice.restdocs.controller.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

	@PostMapping(value="")
	public String saveFile(@RequestParam("myFile") MultipartFile file) { 
		System.out.println("File name: " + file.getOriginalFilename());
        
		return "success"; 
	}

	@GetMapping(value="")
	public void downloadFile(HttpServletResponse response) throws IOException {
		InputStream is = null;
		try {
			//입력 스트림 생성
			File file = new File("src/main/resources/data/movie.zip");
			is = new FileInputStream(file);
		
        	response.setContentType("application/zip");
			response.setHeader("Content-disposition", "attachment; filename="+ "sample.zip");
	    	
			ServletOutputStream outputStream = response.getOutputStream();
	    	byte[] buffer = new byte[1024];
		
			int numBytesRead;
			while ((numBytesRead = is.read(buffer)) > 0) {
				outputStream.write(buffer, 0, numBytesRead);
			}
			outputStream.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			is.close();
		}
	}
	
}
