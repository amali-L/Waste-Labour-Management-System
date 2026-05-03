package waste.and.labour;

/**
 * The WageRecor class represents a single record of labour wage details.
 * It stores information about the worker's name, number of days worked,
 * wage per day, and the total salary.
 *
 * OOP Concepts Used:
 * - Encapsulation: All fields are private and accessed through getters and setters.
 * - Data Integrity: totalSalary is automatically calculated and cannot be set manually.
 *
 * The total salary is always computed as:
 * totalSalary = daysWorked * wagePerDay
 *
 * This ensures that incorrect salary values cannot be stored.
 *
 * @author
 *     Your Name
 */
public class WageRecor {

    /** Name of the labour */
    private String name;

    /** Number of days worked */
    private int daysWorked;

    /** Wage per day */
    private int wagePerDay;

    /** Total salary calculated automatically */
    private int totalSalary;

    /**
     * Constructs a WageRecor object and calculates total salary.
     *
     * @param name        the name of the labour
     * @param daysWorked  number of days worked
     * @param wagePerDay  wage per day
     */
    public WageRecor(String name, int daysWorked, int wagePerDay) {
        this.name        = name;
        this.daysWorked  = daysWorked;
        this.wagePerDay  = wagePerDay;
        this.totalSalary = daysWorked * wagePerDay;
    }

    /**
     * Returns the name of the labour.
     *
     * @return labour name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of days worked.
     *
     * @return days worked
     */
    public int getDaysWorked() {
        return daysWorked;
    }

    /**
     * Returns the wage per day.
     *
     * @return wage per day
     */
    public int getWagePerDay() {
        return wagePerDay;
    }

    /**
     * Returns the total salary.
     * This value is automatically calculated and cannot be set manually.
     *
     * @return total salary
     */
    public int getTotalSalary() {
        return totalSalary;
    }

    /**
     * Sets the labour name after validation.
     *
     * @param name the new labour name
     */
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    /**
     * Sets the number of days worked and recalculates total salary.
     *
     * @param days the number of days worked
     */
    public void setDaysWorked(int days) {
        if (days > 0) {
            this.daysWorked  = days;
            this.totalSalary = daysWorked * wagePerDay;
        }
    }

    /**
     * Sets the wage per day and recalculates total salary.
     *
     * @param wage the wage per day
     */
    public void setWagePerDay(int wage) {
        if (wage > 0) {
            this.wagePerDay  = wage;
            this.totalSalary = daysWorked * wagePerDay;
        }
    }

    /**
     * Returns a string representation of the WageRecor object.
     *
     * @return formatted string with wage details
     */
    @Override
    public String toString() {
        return "WageRecord{name=" + name
                + ", days=" + daysWorked
                + ", wage=" + wagePerDay
                + ", total=" + totalSalary + "}";
    }
}