package dat.backend.model.entities;

public class Admin3DRoof {
    private int materialId;
    private int materialVariantId;
    private int width;
    private int height;
    private int length;

    public Admin3DRoof(int materialId, int materialVariantId, int width, int height, int length) {
        this.materialId = materialId;
        this.materialVariantId = materialVariantId;
        this.width = width;
        this.height = height;
        this.length = length;
    }
}
