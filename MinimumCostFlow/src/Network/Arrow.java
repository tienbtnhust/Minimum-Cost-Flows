package Network;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Arrow extends Path{
    private static final double defaultArrowHeadSize = 7;
    private double startX, startY, endX, endY;
    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize){
        super();
        
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        
        strokeProperty().bind(fillProperty());
        setFill(Color.BLACK);
        
        //Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));
        
        //ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        
        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
    }
    
    public static double getDefaultarrowheadsize() {
		return defaultArrowHeadSize;
	}

	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	public double getEndX() {
		return endX;
	}

	public double getEndY() {
		return endY;
	}

	public Arrow(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }
	public static Arrow getArrow(Line line,double moveX,double moveY) {
		//super();
		double length = Math.pow(line.getStartX()-line.getEndX(),2)+Math.pow(line.getStartY()-line.getEndY(),2);
		length  = Math.sqrt(length);
		double eX = line.getEndX()+moveX + (line.getStartX()-line.getEndX())/length *12;
		double eY = line.getEndY()+moveY + (line.getStartY()-line.getEndY())/length *12;
		double sX = line.getStartX()+moveX - (line.getStartX()-line.getEndX())/length *12;
		double sY =line.getStartY()+moveY - (line.getStartY()-line.getEndY())/length *12;
		return new Arrow(sX,sY,eX,eY);
	}
}
