package SimulatorObjects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class MapDemo extends Application {

	private MapGenerator mapGen;
	private ResizableCanvas canvas;

	// Scaling of the tilemap is based on screenWidth and screenHeight
	private double screenWidth = 1425;
	private double screenHeight = 950;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane mainPane = new BorderPane();
		canvas = new ResizableCanvas(g -> draw(g), mainPane);
		canvas.setWidth(this.screenWidth);
		canvas.setHeight(this.screenHeight);
		mainPane.setCenter(canvas);
		FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
		new AnimationTimer() {
			long last = -1;
			@Override
			public void handle(long now) {
				if(last == -1)
					last = now;
				update((now - last) / 1000000000.0);
				last = now;
				draw(g2d);
			}
		}.start();
		stage.setScene(new Scene(mainPane));
		stage.setTitle("Tilemap JSON Loader");
		stage.show();
		draw(g2d);
	}

	public void init()
	{
		mapGen = new MapGenerator("testDrive.json", this.screenWidth, this.screenHeight);
	}

	public void draw(Graphics2D g)
	{
		g.setBackground(Color.black);
		g.clearRect(0,0,(int)canvas.getWidth(), (int)canvas.getHeight());

		mapGen.draw(g);
	}

	public void update(double deltaTime)
	{
//		this.screenWidth = canvas.getWidth();
//		this.screenHeight = canvas.getHeight();
	}

	public static void main(String[] args)
	{
		launch(MapDemo.class);
	}

}
