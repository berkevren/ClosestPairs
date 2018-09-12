import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Point implements Comparator<Point> {

    private ArrayList<Float> coordinates;
    private int originalIndex;
    private int dimensionToSortAccordingTo;

    public Point(ArrayList<Float> coordinates, int originalIndex) {
        this.coordinates = coordinates;
        this.originalIndex = originalIndex;
        this.dimensionToSortAccordingTo = 0;
    }

    public Point() {
        this.coordinates = null;
        this.originalIndex = -1;
        this.dimensionToSortAccordingTo = -1;
    }

    public void setCoordinates(ArrayList<Float> coords) {
        this.coordinates = coords;
    }

    public void setSingleCoordinate(Float coordinate, int index) {
        coordinates.set(index, coordinate);
    }

    public ArrayList<Float> getCoordinates() {
        return coordinates;
    }

    public Float getSingleCoordinate(int index) {
        return coordinates.get(index);
    }

    public void setOriginalIndex(int or) {
        originalIndex = or;
    }

    public int getOriginalIndex() {
        return originalIndex;
    }

    public void setDimensionToSortAccordingTo(int dimensionToSortAccordingTo) {
        this.dimensionToSortAccordingTo = dimensionToSortAccordingTo;
    }

    public boolean equals(Point p) {

        if (this.getCoordinates().size() != p.getCoordinates().size()) {
            return false;
        }

        for(int i = 0; i < getCoordinates().size(); i++) {

            if(!(this.getCoordinates().get(i) == p.getCoordinates().get(i))) {
                return false;
            }
        }

        return true;

    }

    public String toString() {
        String stringToPrint = "Point " + (this.getOriginalIndex() + 1) + ": ";
        for (int i = 0; i < this.getCoordinates().size(); i++) {
            stringToPrint += this.getSingleCoordinate(i) + ", ";
        }
        return stringToPrint;
    }

    @Override
    public int compare(Point point1, Point point2) {
        return Double.compare(Double.parseDouble((point1).getSingleCoordinate(dimensionToSortAccordingTo).toString()),
                Double.parseDouble((point2).getSingleCoordinate(dimensionToSortAccordingTo).toString()));
    }
}