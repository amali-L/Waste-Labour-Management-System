package waste.and.labour;

/**
 * WasteRecord represents one row from the "waste" database table.
 *
 * OOP CONCEPT: ENCAPSULATION
 * ► wasteAmount is private and auto-calculated (totalMaterial - usedMaterial).
 * ► No direct field access from outside — only through methods.
 */
public class WasteRecord {

    private String materialName;
    private int    totalMaterial;
    private int    usedMaterial;
    private int    wasteAmount;   // auto-calculated

    /**
     * Constructor auto-calculates wasteAmount.
     * If usedMaterial > totalMaterial, wasteAmount is set to 0 (safe default).
     */
    public WasteRecord(String materialName, int totalMaterial, int usedMaterial) {
        this.materialName  = materialName;
        this.totalMaterial = totalMaterial;
        this.usedMaterial  = usedMaterial;
        // Protected calculation — never negative
        this.wasteAmount   = (usedMaterial <= totalMaterial)
                             ? totalMaterial - usedMaterial
                             : 0;
    }

    // ── GETTERS ───────────────────────────────────────────────────────────
    public String getMaterialName()  { return materialName; }
    public int    getTotalMaterial() { return totalMaterial; }
    public int    getUsedMaterial()  { return usedMaterial; }
    public int    getWasteAmount()   { return wasteAmount; }  // read-only, no setter

    @Override
    public String toString() {
        return "WasteRecord{material=" + materialName + ", total=" + totalMaterial
               + ", used=" + usedMaterial + ", waste=" + wasteAmount + "}";
    }
}
