package waste.and.labour;

/**
 * LabourRecord represents one row from the "labour" database table.
 *
 * OOP CONCEPT: ENCAPSULATION
 * ──────────────────────────
 * All fields below are declared PRIVATE.
 * This means no other class can write:
 *     record.name = "Ravi";   ← ILLEGAL — compiler error
 *
 * Instead they MUST use:
 *     record.setName("Ravi");  ← CORRECT — goes through our setter
 *
 * The setter can check the value before storing it (data protection).
 * The getter controls exactly what is returned.
 *
 * This "wrapping" of data + controlled access = ENCAPSULATION.
 */
public class LabourRecord {

    // ── PRIVATE fields — hidden from all other classes ────────────────────
    private int    id;       // Auto-assigned by the database
    private String name;     // e.g. "Ravi Kumar"
    private String skill;    // e.g. "Welding"
    private int    wageRate; // e.g. 500 (rupees per day)

    // ── CONSTRUCTOR — used to create a LabourRecord object ────────────────
    /**
     * Creates a new LabourRecord with all fields filled in.
     *
     * Example usage:
     *     LabourRecord r = new LabourRecord(0, "Ravi", "Welding", 500);
     */
    public LabourRecord(int id, String name, String skill, int wageRate) {
        this.id       = id;
        this.name     = name;
        this.skill    = skill;
        this.wageRate = wageRate;
    }

    // ── GETTERS — read the value of each field ────────────────────────────

    /** Returns the labour's ID. */
    public int getId() {
        return id;
    }

    /** Returns the labour's name. */
    public String getName() {
        return name;
    }

    /** Returns the labour's skill type. */
    public String getSkill() {
        return skill;
    }

    /** Returns the wage per day. */
    public int getWageRate() {
        return wageRate;
    }

    // ── SETTERS — write/update a field (with optional validation) ─────────

    /** Sets the labour name. Cannot be empty. */
    public void setName(String name) {
        // Setter protects the field — we validate before storing
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    /** Sets the skill type. Cannot be empty. */
    public void setSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.skill = skill.trim();
        }
    }

    /** Sets the wage rate. Must be a positive number. */
    public void setWageRate(int wageRate) {
        if (wageRate > 0) {
            this.wageRate = wageRate;
        }
    }

    /**
     * Returns a readable summary of this record.
     * Used for debugging (System.out.println(record) will call this automatically).
     */
    @Override
    public String toString() {
        return "LabourRecord{id=" + id + ", name=" + name
               + ", skill=" + skill + ", wageRate=" + wageRate + "}";
    }
}


