package com.softsync.zerock.service;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	public String upload(MultipartFile file) {
		
		boolean result = false;
		
		
		String fileOriName = file.getOriginalFilename(); //파일 오리지날 이름을 가져옴
		String fileExtention =
				fileOriName.substring(fileOriName.lastIndexOf("."),fileOriName.length());
				//지정 문자열 추출        ( .부터 끝까지  = 확장자 가져옴)
		String uploadDir = "C:\\library\\upload\\";
		
		//universally unique idenfitier 각 개체를 자동으로 고유하게 식별 (중복 방지)
		//고유성을 충족하기 위해 사용(기존 파일 이름을 사용하면 중복으로 오류 발생가능 ㅠㅠ)
		//형식 16옥텟(128비트)의 수로 표준형식에서는 32개의 16진수로 표현
		//형식 : 8-4-4-4-12 글자
		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-","");
									// - 를 지움	
		
		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtention);
								//경로			+   파일고유이름   + 확장자
		
		if(!saveFile.exists()) //서버에 디렉터리가 없으면
			 saveFile.mkdirs();  //새 디렉터리 생성
		
		try {
			file.transferTo(saveFile);   //파일 저장
			result = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result) {
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");
			return uniqueName + fileExtention;  
		}else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");
			return null;
		}
	}
}
