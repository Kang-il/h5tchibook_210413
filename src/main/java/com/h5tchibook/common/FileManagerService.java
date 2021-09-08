package com.h5tchibook.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	//이미지 저장경로
	public final static String FILE_UPLOAD_PATH="D:\\1\\06_Spring_Project\\project\\workSpace\\h5tchibook\\images/";
	
	public String saveFile(String userLoginId,MultipartFile file) throws IOException{
		// 파일 업로드
		// 사용자마다 폴더 별도로 만들기
		// 		파일 디렉토리 경로를 겹치지 않게 만들어준다 
		String directoryName=userLoginId+"_"+System.currentTimeMillis()+"/";
		String filePath=FILE_UPLOAD_PATH+directoryName;
		
		
		File directory=new File(filePath);
		if(directory.mkdir()==false) {//파일 업로드할 filePath 경로에 폴더 생성
			//실패시
			return null;
		}
		
		//파일 업로드 -- byte단위로 업로드
		//throws IOException
		byte[] bytes=file.getBytes();
		
		//파일 패스  ::: userId_(시간)123456789/
		//파일 이름 ::: .getOriginalFileName() 업로드하고자 했던 (input에서 올린) 파일 이름이 나온다.(확장자 포함)
		Path path=Paths.get(filePath+file.getOriginalFilename());
		Files.write(path, bytes);
		
		//imageURL 만들기 및 반환
		return "/images/"+directoryName+file.getOriginalFilename();
	}
	
	public void deleteFile(String imagePath) throws IOException {
		Path path=Paths.get(FILE_UPLOAD_PATH+imagePath.replace("/images/",""));
		//파일이 있을경우 제거
		if(Files.exists(path)) {
			//제거
			Files.delete(path);
			
			//자신의 부모(상위) 패스를 가져옴
			path=path.getParent();
			
			//디렉토리 삭제
			if(Files.exists(path)) {
				Files.delete(path);
			}
		}
		
	}
	
}
