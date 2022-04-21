package com.ssafy.dockerby.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ssafy.dockerby.common.ExceptionClass;
import com.ssafy.dockerby.common.exception.UserDefindedException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;


@Slf4j
public class FileManager {

    //Json 파일 저장
    public static <T> void saveJsonFile(String filePath, String fileName, T file)
        throws IOException, UserDefindedException {
        log.info("start save Json {} {}",filePath,fileName);
        ObjectMapper mapper = new ObjectMapper();
        //저장 경로
        String savePath = makePath(filePath, "/", fileName);
        //저장 경로 확인
        checkAndMakeDir(filePath);
        //파일 저장
        mapper.writeValue(new File(savePath), file);
        log.info("finish save Json {}",fileName);
    }

    //type으로 반환함
    public static <T> T loadJsonFile(String filePath, String fileName, Class<T> type)
        throws IOException {
        log.info("start load Json: {} {} {}", filePath, fileName, type.toString());
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(makePath(filePath, "/", fileName));
        if (file.exists()) {
            return mapper.readValue(file, type);
        }
        log.error("not found {}", fileName);
        throw new FileNotFoundException();

    }


    //String type 문자열 파일 저장
    public static void saveFile(String filePath, String fileName, String str)
        throws IOException, UserDefindedException {
        // 폴더 경로 체크
        log.info("start save File {} {}",filePath,fileName);

        checkAndMakeDir(filePath);
        String savePath = makePath(filePath, "/", fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(savePath));
            writer.write(str);
            writer.close();
            log.info("success read {} ",fileName);
        } catch (IOException e) {
            log.error("error read {} ,{}, {}, {} ",fileName,e.getCause(), e.getMessage(), e.getStackTrace());
        }
    }

    //일반 파일 String으로 반환함
    public static String loadFile(String filePath, String fileName) throws IOException {
        log.info("start load File {} {}",filePath,fileName);
        String loadPath = makePath(filePath, "/", fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(loadPath));
        return new String(bytes);
    }

    //배열 타입 문자열 저장
    public static void saveFile(String filePath, String fileName, List<String> strs)
        throws IOException, UserDefindedException {
        // 폴더 경로 체크
        log.info("start save ListFile {} {}",filePath,fileName);
        checkAndMakeDir(filePath);
        String savePath = makePath(filePath, "/", fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) {
            for (String str : strs) {
                writer.append(str + "\n");
            }
        } catch (IOException e) {
            log.error("Unable to create file path {}, {} , {}", e.getCause(), e.getMessage(), e.getStackTrace());
        }
    }

    //파일 저장 경로 생성
    private static <T> String makePath(String... strs) throws IOException {
        StringBuilder savePath = new StringBuilder();
        for (String str : strs) {
            savePath.append(str);
        }
        return savePath.toString();
    }

    private static void checkAndMakeDir(String filePath) throws IOException, UserDefindedException {
        //폴더가 없을시 생성
        log.info("start checkAndMakeDir {} {}",filePath);
        if (!new File(filePath).exists()) {
            try {
                new File(filePath).mkdirs();
            } catch (Exception e) {
                log.error("Unable to create file path:{}, {}, {}, {}",filePath, e.getCause(), e.getMessage(), e.getStackTrace());
            }
        }
        log.info("success make {} directory ",filePath);
    }

}
