package com.ssafy.dockerby.core.util;

import com.ssafy.dockerby.util.FileManager;
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

  public static void run(String path, String logName, int buildNumber, List<String> commands)
      throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(path).append('/').append(logName).append('_').append(buildNumber);
    FileManager.checkAndMakeDir(path);
    File file = new File(sb.toString());
    DefaultExecutor executor = new DefaultExecutor();
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      for (String command : commands) {
        CommandLine commandLine = CommandLine.parse(command);
        PumpStreamHandler handler = new PumpStreamHandler(fileOutputStream);
        fileOutputStream.write(command.getBytes());
        fileOutputStream.write('\n');
        executor.setStreamHandler(handler);
        executor.setExitValues(
            new int[]{0, 1});  // 1 == error 하지만 network_bridge already 1
        int execute = executor.execute(commandLine);
        fileOutputStream.write('\n');
        fileOutputStream.flush();
      }
    }
  }

  public static void run(String path, String logName, int buildNumber, String command)
      throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(path).append('/').append(logName).append('_').append(buildNumber);
    FileManager.checkAndMakeDir(path);
    File file = new File(sb.toString());
    DefaultExecutor executor = new DefaultExecutor();
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      CommandLine commandLine = CommandLine.parse(command);
      PumpStreamHandler handler = new PumpStreamHandler(fileOutputStream);
      fileOutputStream.write(command.getBytes());
      fileOutputStream.write('\n');
      executor.setStreamHandler(handler);
      executor.setExitValues(
          new int[]{0, 1});  // 1 == error 하지만 network_bridge already 1
      int execute = executor.execute(commandLine);
      fileOutputStream.write('\n');
      fileOutputStream.flush();
    }
  }


  public static void runDestPath(String destPath, String logPath, String logName,
      int buildNumber, List<String> commands)
      throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(logPath).append('/').append(logName).append('_').append(buildNumber);
    FileManager.checkAndMakeDir(destPath);
    FileManager.checkAndMakeDir(logPath);
    File file = new File(sb.toString());
    File destFile = new File(destPath);
    DefaultExecutor executor = new DefaultExecutor();
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      for (String command : commands) {
        CommandLine commandLine = CommandLine.parse(command);
        PumpStreamHandler handler = new PumpStreamHandler(fileOutputStream);
        fileOutputStream.write(command.getBytes());
        fileOutputStream.write('\n');
        executor.setWorkingDirectory(destFile);
        executor.setStreamHandler(handler);
        executor.setExitValues(
            new int[]{0, 1});  // 1 == error 하지만 network_bridge already 1
        int execute = executor.execute(commandLine);
        fileOutputStream.flush();
      }
    }
  }

  public static void runDestPath(String destPath, String logPath, String logName,
      int buildNumber, String command)
      throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(logPath).append('/').append(logName).append('_').append(buildNumber);
    FileManager.checkAndMakeDir(destPath);
    FileManager.checkAndMakeDir(logPath);
    File file = new File(sb.toString());
    File destFile = new File(destPath);
    DefaultExecutor executor = new DefaultExecutor();
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      CommandLine commandLine = CommandLine.parse(command);
      PumpStreamHandler handler = new PumpStreamHandler(fileOutputStream);
      fileOutputStream.write(command.getBytes());
      fileOutputStream.write('\n');
      executor.setWorkingDirectory(destFile);
      executor.setStreamHandler(handler);
      executor.setExitValues(
          new int[]{0, 1});  // 1 == error 하지만 network_bridge already 1
      int execute = executor.execute(commandLine);
      fileOutputStream.flush();
    }
  }
}
