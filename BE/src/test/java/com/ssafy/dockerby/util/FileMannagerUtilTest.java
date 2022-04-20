package com.ssafy.dockerby.util;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssafy.dockerby.common.exception.AroundHubException;
import com.ssafy.dockerby.dto.UserDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FileMannagerUtilTest {


    @Autowired
    FileMannagerUtil fileMannagerUtil;
    @Test
    public void 파일읽어오기() throws ExecutionException, InterruptedException, FileNotFoundException {
        // given
        String fileName = "userinfo";
        // when
        Gson gson = new Gson();
        UserDto userDto = gson.fromJson(fileMannagerUtil.loadFile(fileName),UserDto.class);
        // then
        Assertions.assertThat(userDto.getPrincipal()).isEqualTo("strbbing");
    }

    @Test
    public void 파일저장하기1()
        throws ExecutionException, InterruptedException, IOException, AroundHubException {
        // given
        String fileName = "testFile";
        String str = "title\ngood\nmylife";
        // when
        fileMannagerUtil.StringFileWritter(fileName,str);
        // then

    }

}