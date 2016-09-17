package graphlab.datastructures;

import java.awt.*;

public enum NodeStatus {
    UNKNOWN(Color.RED), DISCOVERED(Color.GRAY), PROCESSED(new Color(245, 245, 245));

    public Color color;

    NodeStatus(Color color) {
        this.color = color;
    }
}
