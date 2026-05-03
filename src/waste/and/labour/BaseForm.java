package waste.and.labour;

import javax.swing.*;
import java.awt.*;

/**
 * BaseForm is the ABSTRACT PARENT CLASS for all management form windows.
 *
 * OOP CONCEPTS:
 * ─────────────────────────────────────────────────────────────────────────
 * 1. ABSTRACT CLASS
 *    ► The keyword "abstract" means: you CANNOT do "new BaseForm()".
 *    ► It is only meant to be a blueprint for child classes.
 *    ► Think of it like a template — it defines what every form MUST have.
 *
 * 2. ABSTRACT METHODS
 *    ► Methods declared with "abstract" have NO body here.
 *    ► Every child class (Waste, Wage, Labour…) MUST provide their own version.
 *    ► If a child forgets to implement an abstract method, Java gives a compile error.
 *
 * 3. CONCRETE METHODS (non-abstract)
 *    ► setupWindow() is fully implemented here — all children INHERIT it.
 *    ► Children do NOT need to rewrite window size, location, etc.
 *    ► This avoids repeating the same code in every form.
 *
 * HIERARCHY:
 *
 *     BaseForm  (abstract parent)
 *        │
 *        ├── Waste    (concrete child — overrides getFormTitle, initUI)
 *        ├── Wage     (concrete child — overrides getFormTitle, initUI)
 *        ├── Labour   (concrete child — overrides getFormTitle, initUI)
 *        ├── Raw      (concrete child — overrides getFormTitle, initUI)
 *        ├── Sales    (concrete child — overrides getFormTitle, initUI)
 *        └── Ringi    (concrete child — overrides getFormTitle, initUI)
 */
public abstract class BaseForm extends JFrame {

    // ── Shared constant: standard window size for all forms ───────────────
    protected static final int WINDOW_WIDTH  = 1400;
    protected static final int WINDOW_HEIGHT = 800;

    /**
     * Constructor — called by every child using super().
     * Sets up the window size, position, and layout ONCE here.
     * Every child form inherits this setup automatically.
     */
    public BaseForm() {
        setupWindow();
    }

    /**
     * CONCRETE METHOD — inherited by all children, no need to rewrite.
     * Sets up the standard window properties every form needs.
     */
    protected void setupWindow() {
        setTitle(getFormTitle());               // calls the overridden version in child
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ── ABSTRACT METHODS — children MUST override these ───────────────────

    /**
     * Each child returns its own window title.
     * Example: Waste returns "Waste Management"
     *          Wage  returns "Wage Management"
     *
     * @return the title string for the window title bar
     */
    public abstract String getFormTitle();

    /**
     * Each child builds its own UI components (labels, fields, buttons).
     * Called after setupWindow() to populate the form.
     */
    public abstract void initUI();

    /**
     * Each child clears its own input fields after saving.
     * Every form has different fields, so each must define its own.
     */
    public abstract void clearFields();
}

