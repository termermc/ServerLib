# ServerLib
A super-tiny, super-easy, and super-quick library for building Java socket servers!
To find the javadoc, visit https://termermc.github.io/website/javadocs/serverlib/
<h2>What can I do with this?</h2>
This library allows you to build a Java socket server in an event-based manner. ServerLib also simplifies setup, as you do not have to handle threads and connection handling yourself.
<h2>Code Examples</h2>
<b>Repeat a string sent to the server</b>

<code>
import net.termer.serverlib.*
import java.io.*
import java.net.*

public class StringResponder {
  private Server server = null;

  public static void main(String[] args) {
    try {
      server = new Server(9001);
      server.addListener(new ServerListener() {
        public void onConnect(Socket s) {}
        public void onDisconnect(Socket s) {}
        public void onIntReceive(Socket s, int d) {}
        public void onStringReceive(Socket s, String d) {
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
</code>
