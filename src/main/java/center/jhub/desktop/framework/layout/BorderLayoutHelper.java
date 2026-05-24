package center.jhub.desktop.framework.layout;

public class BorderLayoutHelper {
    public static final int EMPTY_COL_ONE = 0;
    public static final int CONTENT_COL_ONE = 1;
    public static final int EMPTY_COL_TWO = 2;
    public static final int CONTENT_COL_TWO = 3;


    public BorderLayoutHelper() {
    }

    public String restraintForContent(int contentCol, int row) {
        return switch(contentCol) {
            case 0 -> CONTENT_COL_ONE  + "," + row;
            case 1 -> CONTENT_COL_TWO  + "," + row;
            default -> throw new RuntimeException("Content col: " + contentCol + " does not exit");
        };
    }
}
