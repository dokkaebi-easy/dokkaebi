package com.ssafy.dockerby.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ssafy.dockerby.common.Constants.ExceptionClass;
import com.ssafy.dockerby.common.exception.AroundHubException;
import com.ssafy.dockerby.dto.UserDto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileMannagerUtil {

    private final Path rootLocation;

    public FileMannagerUtil(@Value("${uploadPath.path}") String uploadPath) {
        this.rootLocation = Paths.get(uploadPath);
    }
    private boolean checkDir(String path) throws IOException, AroundHubException {
        //폴더가 없을시 생성
        if (!new File(path).exists()) {
            try {
                new File(path).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
                log.debug("파일 경로를 생성 할 수 없습니다.");
                throw new AroundHubException(ExceptionClass.FILE, HttpStatus.BAD_REQUEST,"파일 경로를 생성할수 없습니다");
            }
        }
        return true;
    }
    public boolean saveJson(String fileName, Object fileDto) throws IOException, AroundHubException {
        //저장 경로
        StringBuilder savePath = new StringBuilder();
        //gson 객체 사용
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //파일 저장 객체

        if(!checkDir(String.valueOf(rootLocation))){
            throw new RuntimeException();
        }
        //파일 저장
        savePath.append(rootLocation).append("/").append(fileName);
        FileWriter fw = new FileWriter(savePath.toString());
        gson.toJson(fileDto, fw);
        fw.flush();
        fw.close();
        return true;
    }

    public Reader loadFile(String fileName) throws FileNotFoundException {
        // FileReader 생성
        StringBuilder filePath = new StringBuilder();
        filePath.append(rootLocation).append("/").append(fileName);
        Reader reader = new FileReader(filePath.toString());
        return reader;
    }
    //문자열 저장
    public void StringFileWritter(String fileName,String str) throws IOException, AroundHubException {
        // 폴더 경로 체크
        if(!checkDir(String.valueOf(rootLocation))){
            throw new RuntimeException();
        }
        StringBuilder filePath = new StringBuilder();
        filePath.append(rootLocation).append("/").append(fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(filePath)));
            writer.write(str);
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //리스트 형식으로 파일 올경우?
    public void StringListWriter(String fileName, List<String> strs)
        throws IOException, AroundHubException {
        // 폴더 경로 체크
        if(!checkDir(String.valueOf(rootLocation))){
            throw new RuntimeException();
        }
        StringBuilder filePath = new StringBuilder();
        filePath.append(rootLocation).append("/").append(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(filePath)))) {
            for(String str : strs) {
                writer.append(str +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadFilePath (String filePath, String fileName, Class<T> type)
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath + '/' + fileName);
        if(file.exists())
            return mapper.readValue(file, type);
        throw new FileNotFoundException();
    }

    public static <T> void saveFilePath (String filePath, String fileName, T file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filePath + '/' + fileName), file);
    }
}
