import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

class JTextFieldHintUI extends JTextField {

    private String hint;
    private boolean isEmpty = true;
    private Font gainFont = new Font("Tahoma", Font.BOLD, 12);
    private Font lostFont = new Font("Tahoma", Font.ITALIC, 12);

    JTextFieldHintUI(final String hint) {
        this.hint = hint;
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);

        // JTextField focus listener
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new LineBorder(Color.BLACK));
                if (isEmpty()) {
                    setText("");
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().length() != 0)
                    isEmpty = false;
                else
                    isEmpty = true;

                if (isEmpty()) {
                    // TextField is empty, display the hint string in gray.
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                    isEmpty = true;
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });

    }

    // Init the TextField
    void init() {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        isEmpty = true;
    }

    boolean isEmpty() {
        return isEmpty;
    }
}