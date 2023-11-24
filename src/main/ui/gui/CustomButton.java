package ui.gui;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    private boolean isHovered = false;

    //MODIFIES:
    //this
    //EFFECTS:
    //Initializes the button with the specified text.
    //Adds a MouseListener to track mouse enter and exit events for hover effects.
    public CustomButton(String text) {
        super(text);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                isHovered = true;
                repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    //MODIFIES:
    //this
    //EFFECTS:
    //Draws the button with a smiley face icon on right when hovered.
    //Modifies the appearance of the button based on the isHovered state.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isHovered) {
            int smileySize = 20;
            int smileyX = getWidth() - smileySize - 5;
            int smileyY = getHeight() / 2 - smileySize / 2;

            g.setColor(Color.YELLOW);
            g.fillOval(smileyX, smileyY, smileySize, smileySize);
            g.setColor(Color.BLACK);
            g.drawOval(smileyX + 5, smileyY + 5, 3, 3); // Left eye
            g.drawOval(smileyX + smileySize - 8, smileyY + 5, 3, 3); // Right eye
            g.drawLine(smileyX + smileySize - 8, smileyY + 1,
                    smileyX + smileySize - 5, smileyY + 3); //eyebrow Right
            g.drawLine(smileyX + 2,smileyY + 2,
                    smileyX + 4, smileyY + 4); //eyebrow Left
            g.drawArc(smileyX + 10, smileyY + 8, smileySize - 10,
                    smileySize - 10, 0, -180); // Mouth (smile)
        }
    }

    //MODIFIES:
    //this
    //EFFECTS:
    //Sets the isHovered flag to false.
    //Calls repaint() to update the button appearance.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            CustomButton customButton = new CustomButton("Testing");

            frame.getContentPane().add(customButton);
            frame.setVisible(true);
        });
    }
}
