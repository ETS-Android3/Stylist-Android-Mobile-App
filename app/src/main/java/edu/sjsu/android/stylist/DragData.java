package edu.sjsu.android.stylist;

public class DragData {
    public final Clothing item;
    public final int width;
    public final int height;

    public DragData(Clothing item, int width, int height) {
        this.item = item;
        this.width = width;
        this.height = height;
    }
}
