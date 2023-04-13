import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public class Game {
    private static Mine mine;
    private static Pickaxe axe;
    private static Minecart minecart;
    private static final Color CAVE_BACKGROUND = new Color(9, 1, 64);
    private static CanvasWindow canvas;

        // for testing:
        public static void main(String[] args) {
            mine = new Mine();
            axe = new Pickaxe();
            minecart = new Minecart();
            CanvasWindow canvas = new CanvasWindow("Game", 800, 600);
            canvas.setBackground(CAVE_BACKGROUND);
    
            Mine.addCave(300);
            Mine.generateRocks();
            canvas.add(Mine.getGraphicsGroup());
            canvas.draw();

            canvas.add(Minecart.drawMinecart());

            GraphicsObject axeShape = Pickaxe.drawAxe();
            canvas.add(axeShape, 100, 100); // arbitrary starting point
            canvas.draw();

            canvas.onMouseMove(event -> {
                axeShape.setCenter(event.getPosition());
            });

            canvas.onClick(event -> {
                if (axe.testRockHit(canvas) != null){
                    rockDissolve(canvas, axe.testRockHit(canvas));
                }
            });
    

        }

        private static void rockDissolve(CanvasWindow canvas, GraphicsObject rock) {
            Point rockPosition = rock.getPosition();
            GraphicsObject twoThirds = Rock.twoThirdsRock(rockPosition.getX(), rockPosition.getY());
            GraphicsObject oneThird = Rock.oneThirdRock(rockPosition.getX(), rockPosition.getY());

            // hit #1
            axe.getAxe().rotateBy(90);
            System.out.println(rock);
            // canvas.remove(rock);
            canvas.add(twoThirds);
            canvas.draw();

            canvas.pause(150);
            axe.getAxe().rotateBy(-90);
            canvas.draw();
            canvas.pause(150);

            // hit #2
            axe.getAxe().rotateBy(90);
            canvas.remove(twoThirds);
            canvas.add(oneThird);
            canvas.draw();

            canvas.pause(150);
            axe.getAxe().rotateBy(-90);
            canvas.draw();
            canvas.pause(150);

            // hit #3
            axe.getAxe().rotateBy(90);
            canvas.remove(Rock.oneThirdRock(rockPosition.getX(), rockPosition.getY()));
            canvas.draw();

            canvas.pause(150);
            axe.getAxe().rotateBy(-90);
            canvas.draw();
            canvas.pause(150);

        }
    
}
