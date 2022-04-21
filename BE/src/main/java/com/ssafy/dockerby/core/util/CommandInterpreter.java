package com.ssafy.dockerby.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

@Slf4j
public class CommandInterpreter {

  public static void run(String path, String projectName, int buildNumber, List<String> commands)
      throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(path).append('/').append(projectName).append('_').append(buildNumber);
    File file = new File(sb.toString());
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    commands.forEach(command -> {
      CommandLine commandLine = CommandLine.parse(command);
      DefaultExecutor executor = new DefaultExecutor();
      PumpStreamHandler handler = new PumpStreamHandler(fileOutputStream);
      executor.setStreamHandler(handler);
      executor.setExitValues(new int[] {0,1,125});  // 1 == error 하지만 network_bridge already 1 125 == already container
      try {
        int execute = executor.execute(commandLine);
        fileOutputStream.flush();
      } catch (IOException e) {
        log.error("",e);
      }
    });


    fileOutputStream.close();
  }

}
