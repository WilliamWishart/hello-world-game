import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

public class Game extends JFrame implements Runnable {

  AppLogger logging;
  private static final long serialVersionUID = 1L;
  public int mapWidth = 15;
  public int mapHeight = 15;
  private Thread thread;
  private boolean running;
  private BufferedImage image;
  public int[] pixels;
  public ArrayList<Texture> textures;
  public Camera camera;
  public Screen screen;
  public static int[][] map = { { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2 }, { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 3, 0, 3, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2 }, { 1, 0, 3, 0, 0, 0, 3, 0, 2, 2, 2, 0, 2, 2, 2 },
      { 1, 0, 3, 3, 0, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2 }, { 1, 0, 3, 3, 0, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2 }, { 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4 },
      { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 4 }, { 1, 0, 1, 0, 1, 0, 1, 4, 0, 3, 0, 0, 3, 0, 4 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };

  public Game() {
    thread = new Thread(this);
    image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    textures = new ArrayList<Texture>();
    textures.add(Texture.wood);
    textures.add(Texture.brick);
    textures.add(Texture.bluestone);
    textures.add(Texture.stone);
    camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
    screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
    addKeyListener(camera);
    setSize(640, 480);
    setResizable(false);
    setTitle("Prototype Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLocationRelativeTo(null);
    setVisible(true);
    start();
  }

  private synchronized void start() {
    running = true;
    thread.start();
  }

  public synchronized void stop() {
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    bs.show();
  }

  public void run() {
    long lastTime = System.nanoTime();
    final double ns = 1000000000.0 / 60.0;// 60 times per second
    double delta = 0;
    requestFocus();
    
    while (running) {
      long now = System.nanoTime();
      delta = delta + ((now - lastTime) / ns);
      lastTime = now;
      while (delta >= 1)// Make sure update is only happening 60 times a second
      {
        // handles all of the logic restricted time
        screen.update(camera, pixels);
        camera.update(map);
        delta--;
      }
      render();// displays to the screen unrestricted time
    }
  }

  public static void main(String[] args) {
    AppLogger obj = new AppLogger();
    obj.makeSomeLog();

    // Generating some log messages through the
    // function defined above
    LogManager lgmngr = LogManager.getLogManager();

    // lgmngr now contains a reference to the log manager.
    Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Getting the global application level logger
    // from the Java Log Manager
    log.log(Level.INFO, "This is a log message");

    // Create a log message to be displayed
    // The message has a level of Info

    Game game = new Game();
  }
}