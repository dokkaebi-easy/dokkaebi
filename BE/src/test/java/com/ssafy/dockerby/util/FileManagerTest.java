package com.ssafy.dockerby.util;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
class FileManagerTest {

    @Test
    public void str파일저장하고_읽어오기()
        throws ExecutionException, InterruptedException, IOException, UserDefindedException {
        // given
        String filePath = "./fileData";
        String fileName = "testFile";
        String str = "title\ngood\nmylife";
        // when
        // 파일 저장
        FileManager.saveFile(filePath, fileName, str);
        // 파일 읽어오기
        String result = FileManager.loadFile(filePath, fileName);
        // then
        Assertions.assertThat(result.equals(str));
    }

    @Test
    public void List파일저장하고_읽어오기()
        throws ExecutionException, InterruptedException, IOException, UserDefindedException {
        // given
        String filePath = "./fileData";
        String fileName = "testlistFile";
        ArrayList<String> str = new ArrayList<>();
        str.add("aa");
        str.add("bb");
        str.add("cc");
        str.add("dd");
        str.add("ee");
        // when
        //파일 저장
        FileManager.saveFile(filePath, fileName, str);
        //파일 읽어오기
        String result = FileManager.loadFile(filePath, fileName);
        // then
        Assertions.assertThat(result.equals(str));
    }


//    @Test
//    public void Json파일저장및읽어오기()
//        throws ExecutionException, InterruptedException, IOException, UserDefindedException {
//        // given
//        String filePath = "./fileData";
//        String fileName = "userinfo";
//        UserDto userDto = UserDto.builder()
//            .id(0L)
//            .name("김민현")
//            .principal("kimminhyeon")
//            .credential("12345")
//            .build();
//        // when
//        //파일 저장
//        FileManager.saveJsonFile(filePath, fileName, userDto);
//        //파일 읽어오기
//        UserDto resultDto = FileManager.loadJsonFile(filePath, fileName, UserDto.class);
//        // then
//        Assertions.assertThat(userDto.equals(resultDto));
//    }


}