package graphics;

public class PaletteManager {

    private String currentPalette;

    public PaletteManager() {
        currentPalette = "Balatro Classic";
    }

    public void changePalette(String palette) {
        currentPalette = palette;
    }

    public String getCurrentPalette() {
        return currentPalette;
    }
}