package seabattle;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private final int size;
    private final String name;
    private List<int[]> coordinates;//keeps coordinates in List [y1, x1], [y2,x2]....

    public Ship(int size, String name) {
        this.size = size;
        this.name = name;
        this.coordinates = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public List<int[]> getCoordinates() {
        return coordinates;
    }
}
