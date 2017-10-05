# ServerLib
A super-tiny, super-easy, and super-quick library for building Java socket servers!
To find the javadoc, visit https://termermc.github.io/website/javadocs/serverlib/
# What can I do with this?
This library allows you to build a Java socket server in an event-based manner. ServerLib also simplifies setup, as you do not have to handle threads and connection handling yourself.
# Code Examples
**Respond to a string sent to the server**
```java
import net.termer.serverlib.*
import java.io.*
import java.net.*

public class StringResponder {
  
  // Make sure that the server exists, even if not instantiated
  private Server server = null;

  public static void main(String[] args) {
  
    // Wrap it in a try
    try {
      server = new Server(9001);
      
      server.addListener(new ServerListener() {
        // We still need to include onConnect, onDisconnect, and onIntReceive, but we won't put anything in them
        public void onConnect(Socket s) {}
        
        public void onDisconnect(Socket s) {}
        
        public void onIntReceive(Socket s, int d) {}
        
        // Here we handle what happens when the client sends a string
        public void onStringReceive(Socket s, String d) {
          // If the client sends "hello world" we send 1, if "goodbye world" we send 0
          if(d=="hello world") {
            s.getOutputStream().write(1);
          } else if(d=="goodbye world") {
            s.getOutputStream().write(0);
          }
        }
      });
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
```
