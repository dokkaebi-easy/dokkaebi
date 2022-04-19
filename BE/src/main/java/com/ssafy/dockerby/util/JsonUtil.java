package com.ssafy.dockerby.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class JsonUtil {

  private final Path rootLocation;

  public JsonUtil(@Value("${uploadPath.path}") String uploadPath) {
    this.rootLocation = Paths.get(uploadPath);
  }
  public String saveJson(String filename, Object fileDto) throws IOException {
    //저장 경로
    StringBuilder savePath = new StringBuilder();
    //gson 객체 사용
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //파일 저장 객체

    //폴더가 없을시 생성
    if (!new File(String.valueOf(rootLocation)).exists()) {
      try {
        new File(String.valueOf(rootLocation)).mkdir();
      } catch (Exception e) {
        e.getStackTrace();
      }

    }
    System.out.println("filename = " + filename);
    //파일 저장
    savePath.append(rootLocation).append("/").append(filename);
    FileWriter fw = new FileWriter(savePath.toString());
    gson.toJson(fileDto, fw);
    fw.flush();
    fw.close();

    return savePath.toString(); // 파일 경로 리턴
  }
}
