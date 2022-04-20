package com.ssafy.dockerby.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.dockerby.common.Constants.ExceptionClass;
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
        log.info("saveJson");
        ObjectMapper mapper = new ObjectMapper();
        //저장 경로
        String savePath = makePath(filePath, "/", fileName);
        //저장 경로 확인
        if (checkDir(filePath)) {
            log.info("Save Path Success");
        }
        //파일 저장
        mapper.writeValue(new File(String.valueOf(savePath)), file);
    }

    //type으로 반환함
    public static <T> T loadJsonFile(String filePath, String fileName, Class<T> type)
        throws IOException {
        log.info("loadJson");
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(makePath(filePath, "/", fileName));
        if (file.exists()) {
            return mapper.readValue(file, type);
        }
        throw new FileNotFoundException();
    }


    //String type 문자열 파일 저장
    public static void saveFile(String filePath, String fileName, String str)
        throws IOException, UserDefindedException {
        // 폴더 경로 체크
        if (checkDir(filePath)) {
            log.info("Save Path Success");
        }
        String savePath = makePath(filePath, "/", fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(savePath)));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //일반 파일 String으로 반환함
    public static String loadFile(String filePath, String fileName) throws IOException {
        String loadPath = makePath(filePath, "/", fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(loadPath));
        return new String(bytes);
    }

    //배열 타입 문자열 저장
    public static void saveFile(String filePath, String fileName, List<String> strs)
        throws IOException, UserDefindedException {
        // 폴더 경로 체크
        if (checkDir(filePath)) {
            log.info("Save Path Success");
        }
        String savePath = makePath(filePath, "/", fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(savePath)))) {
            for (String str : strs) {
                writer.append(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //파일 저장 경로 생성
    public static <T> String makePath(String... strs) throws IOException {
        StringBuilder savePath = new StringBuilder();
        for (String str : strs) {
            savePath.append(str);
        }
        return String.valueOf(savePath);
    }

    public static boolean checkDir(String path) throws IOException, UserDefindedException {
        //폴더가 없을시 생성
        if (!new File(path).exists()) {
            try {
                new File(path).mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
                log.error("Unable to create file path");
                throw new UserDefindedException(ExceptionClass.FILE, HttpStatus.BAD_REQUEST,
                    "파일 경로를 생성할수 없습니다");
            }
        }
        return true;
    }

}
