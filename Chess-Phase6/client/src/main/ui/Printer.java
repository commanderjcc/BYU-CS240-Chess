package ui;

/**
 * A class for printing to the console with color and formatting.
 */
public class Printer {

    /**
     * Escape sequences for setting text color
     */
    public enum Color {
        BLACK(EscapeSequences.SET_TEXT_COLOR_BLACK),
        LIGHT_GREY(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY),
        DARK_GREY(EscapeSequences.SET_TEXT_COLOR_DARK_GREY),
        RED(EscapeSequences.SET_TEXT_COLOR_RED),
        GREEN(EscapeSequences.SET_TEXT_COLOR_GREEN),
        YELLOW(EscapeSequences.SET_TEXT_COLOR_YELLOW),
        BLUE(EscapeSequences.SET_TEXT_COLOR_BLUE),
        MAGENTA(EscapeSequences.SET_TEXT_COLOR_MAGENTA),
        WHITE(EscapeSequences.SET_TEXT_COLOR_WHITE),
        RESET(EscapeSequences.RESET_TEXT_COLOR);

        Color(String ES) {
            this.ES = ES;
        }

        private final String ES;

        public String getES() {
            return this.ES;
        }
    }

    /**
     * Escape sequences for setting background color
     */
    public enum BGColor {
        BLACK(EscapeSequences.SET_BG_COLOR_BLACK),
        LIGHT_GREY(EscapeSequences.SET_BG_COLOR_LIGHT_GREY),
        DARK_GREY(EscapeSequences.SET_BG_COLOR_DARK_GREY),
        RED(EscapeSequences.SET_BG_COLOR_RED),
        GREEN(EscapeSequences.SET_BG_COLOR_GREEN),
        DARK_GREEN(EscapeSequences.SET_BG_COLOR_DARK_GREEN),
        YELLOW(EscapeSequences.SET_BG_COLOR_YELLOW),
        BLUE(EscapeSequences.SET_BG_COLOR_BLUE),
        MAGENTA(EscapeSequences.SET_BG_COLOR_MAGENTA),
        WHITE(EscapeSequences.SET_BG_COLOR_WHITE),
        RESET(EscapeSequences.RESET_BG_COLOR);

        BGColor(String ES) {
            this.ES = ES;
        }

        private final String ES;

        public String getES() {
            return this.ES;
        }
    }

    /**
     * Escape sequences for setting text thickness
     */
    public enum Thickness {
        BOLD(EscapeSequences.SET_TEXT_BOLD),
        FAINT(EscapeSequences.SET_TEXT_FAINT),
        RESET(EscapeSequences.RESET_TEXT_BOLD_FAINT);

        Thickness(String ES) {
            this.ES = ES;
        }

        private final String ES;

        public String getES() {
            return this.ES;
        }
    }

    /**
     * Escape sequences for setting text italic
     */
    public enum Italic {
        ITALIC(EscapeSequences.SET_TEXT_ITALIC),
        RESET(EscapeSequences.RESET_TEXT_ITALIC);

        Italic(String ES) {
            this.ES = ES;
        }

        private final String ES;

        public String getES() {
            return this.ES;
        }
    }

    /**
     * Escape sequences for setting text underline
     */
    public enum Underline {
        UNDERLINE(EscapeSequences.SET_TEXT_UNDERLINE),
        RESET(EscapeSequences.RESET_TEXT_UNDERLINE);

        Underline(String ES) {
            this.ES = ES;
        }

        private final String ES;

        public String getES() {
            return this.ES;
        }
    }

    /**
     * Escape sequences for setting text blinking
     */
    public enum Blinking {
        BLINKING(EscapeSequences.SET_TEXT_BLINKING),
        RESET(EscapeSequences.RESET_TEXT_BLINKING);

        Blinking(String ES) {
            this.ES = ES;
        }

        private final String ES;

        public String getES() {
            return this.ES;
        }
    }

    // State variables
    private Color color;
    private BGColor background;
    private Thickness thickness;
    private Italic italic;
    private Underline underline;
    private Blinking blinking;

    private int indent;

    /**
     * Constructs a new Printer object with default settings.
     */
    public Printer() {
        this.color = Color.WHITE;
        this.background = BGColor.BLACK;
        this.thickness = Thickness.RESET;
        this.italic = Italic.RESET;
        this.underline = Underline.RESET;
        this.blinking = Blinking.RESET;
        this.indent = 0;
    }

    /**
     * Prints the specified message to the console with the current settings.
     * @param message the message to print
     */
    public void print(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.color.getES());
        sb.append(this.background.getES());
        sb.append(this.thickness.getES());
        sb.append(this.italic.getES());
        sb.append(this.underline.getES());
        sb.append(this.blinking.getES());
        sb.append(" ".repeat(Math.max(0, this.indent)));
        sb.append(message);
        System.out.print(sb);
    }

    /**
     * Prints the specified message to the console with the current settings and a newline.
     * @param message the message to print
     */
    public void println(String message) {
        this.print(message + "\n");
    }

    /**
     * Resets all settings to default.
     */
    public void reset() {
        setColor(Color.WHITE);
        setBackground(BGColor.RESET);
        setThickness(Thickness.RESET);
        setItalic(Italic.RESET);
        setUnderline(Underline.RESET);
        setBlinking(Blinking.RESET);
        setIndent(0);
        print("");
    }

    // Getters and setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BGColor getBackground() {
        return background;
    }

    public void setBackground(BGColor background) {
        this.background = background;
    }

    public Thickness getThickness() {
        return thickness;
    }

    public void setThickness(Thickness thickness) {
        this.thickness = thickness;
    }

    public Italic getItalic() {
        return italic;
    }

    public void setItalic(Italic italic) {
        this.italic = italic;
    }

    public Underline getUnderline() {
        return underline;
    }

    public void setUnderline(Underline underline) {
        this.underline = underline;
    }

    public Blinking getBlinking() {
        return blinking;
    }

    public void setBlinking(Blinking blinking) {
        this.blinking = blinking;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }
}
