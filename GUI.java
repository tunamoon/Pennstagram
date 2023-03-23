package org.cis1200;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import java.util.Deque;
import java.util.LinkedList;

/**
 * The graphical user interface for the Pennstagram project.
 * 
 * Run this file to play with the results of your code in a GUI.
 */
public class GUI implements Runnable {

    // The picture that is currently displayed by the application
    private final String defaultImageURL = "images/Italy.png";
    private PixelPicture currentPic = new PixelPicture(defaultImageURL);

    private final Deque<PixelPicture> images = new LinkedList<>();

    private final JLabel imageLabel = new JLabel(currentPic.toImageIcon());

    private final JFrame frame = new JFrame("Pennstagram");

    // update the currently displayed image, saving the old version for undo.
    private void changeImage(PixelPicture pnew) {
        if (pnew == null) {
            return;
        }
        if (pnew == currentPic) {
            JOptionPane.showMessageDialog(
                    frame,
                    "This operation has no effect (yet).\n",
                    "Alert",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            if (currentPic != null) {
                images.push(currentPic);
            }
            currentPic = pnew;
            imageLabel.setIcon(currentPic.toImageIcon());
            imageLabel.repaint();
            frame.pack();
        }
    }

    /**
     * Construct the "menubar"
     * 
     * The menubar contains the buttons at the top of the frame
     * that are used for working with files and quiting the program.
     * This method creates each of the buttons found on this bar
     * and defines their actions.
     *
     */
    private JPanel makeMenuBar() {
        final JPanel menubar = new JPanel();
        final JButton load = new JButton("Load new image");
        final JButton save = new JButton("Save image");
        final JButton quit = new JButton("Quit");
        final JButton undo = new JButton("Undo");

        menubar.setLayout(new GridLayout(0, 1));
        menubar.add(new JLabel("File options"));
        menubar.add(load);
        menubar.add(save);
        menubar.add(undo);
        menubar.add(quit);

        load.addActionListener(e -> {
            String result = JOptionPane.showInputDialog(
                    frame,
                    "Type the location of an image to open.\n" +
                            "This can be a system path or URL.",
                    "Load new image",
                    JOptionPane.PLAIN_MESSAGE
            );
            try {
                if (result != null) {
                    changeImage(new PixelPicture(result));
                    images.clear();
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Cannot load file\n" + ex.getMessage(),
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        save.addActionListener(e -> {
            String result = JOptionPane.showInputDialog(
                    frame,
                    "Enter the filename to save the image as.",
                    "Save image",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (result != null && currentPic != null) {
                currentPic.save(result);
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Cannot save file",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        undo.addActionListener(e -> {
            if (!images.isEmpty()) {
                currentPic = null;
                changeImage(images.removeFirst());
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "No more steps to undo",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        quit.addActionListener(e -> System.exit(0));

        return menubar;
    }

    private JPanel makeToolBar() {
        final JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));
        final JButton rotateCW = new JButton("RotateCW");
        final JButton rotateCCW = new JButton("RotateCCW");

        final JButton border = new JButton("Border");
        final JButton transform = new JButton("Simple transform");
        final JButton recolor = new JButton("Color scale");

        final JButton vignette = new JButton("Vignette");
        final JButton blend = new JButton("alpha-Blend");
        final JButton contrast = new JButton("Contrast");
        final JButton palette = new JButton("Reduce palette");
        final JButton blur = new JButton("Blur");
        final JButton flood = new JButton("Flood");

        toolbar.add(rotateCW);
        toolbar.add(rotateCCW);
        toolbar.add(border);
        toolbar.add(transform);
        toolbar.add(recolor);
        toolbar.add(contrast);
        toolbar.add(palette);
        toolbar.add(blend);
        toolbar.add(vignette);
        toolbar.add(blur);
        toolbar.add(flood);

        border.addActionListener(arg0 -> {
            PixelPicture pn = SimpleManipulations.border(currentPic, 10, Pixel.BLACK);
            changeImage(pn);
        });

        recolor.addActionListener(arg0 -> {
            double r = 0.0;
            double g = 0.0;
            double b = 0.0;

            try {
                JTextField rField = new JTextField(5);
                JTextField gField = new JTextField(5);
                JTextField bField = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("R:"));
                myPanel.add(rField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("G:"));
                myPanel.add(gField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("B:"));
                myPanel.add(bField);

                int result = JOptionPane.showConfirmDialog(
                        null, myPanel,
                        "Please Enter R G B multipliers",
                        JOptionPane.OK_CANCEL_OPTION
                );
                if (result == JOptionPane.OK_OPTION) {
                    r = Double.parseDouble(rField.getText());
                    g = Double.parseDouble(gField.getText());
                    b = Double.parseDouble(bField.getText());
                    changeImage(SimpleManipulations.scaleColors(currentPic, r, g, b));
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Cannot parse multipliers\n" + ex.getMessage(),
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        blend.addActionListener(e -> {
            String result = JOptionPane.showInputDialog(
                    frame,
                    "Type the location of an image to blend in.\n" +
                            "This can be a system path or URL.",
                    "Load new image",
                    JOptionPane.PLAIN_MESSAGE
            );
            try {
                if (result != null) {
                    changeImage(
                            SimpleManipulations.alphaBlend(
                                    0.3,
                                    currentPic, new PixelPicture(result)
                            )
                    );
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Cannot load file: " + result + "\n" + ex.getMessage(),
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        rotateCW.addActionListener(e -> {
            try {
                changeImage(SimpleManipulations.rotateCW(currentPic));
            } catch (UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Make sure to fully implement the Pixel class!",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        rotateCCW.addActionListener(e -> changeImage(SimpleManipulations.rotateCCW(currentPic)));

        transform.addActionListener(e -> {
            String result = (String) JOptionPane.showInputDialog(
                    frame,
                    "Choose a transformer to use.",
                    "Choose a transformer",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[] {
                        "GrayScaleLuminosity",
                        "ColorInvert",
                        "GrayScaleAverage"
                    },
                    "GrayScaleLuminosity"
            );
            if (result == null) {
                return;
            }

            PixelPicture pnew = null;
            switch (result) {
                case "ColorInvert" -> pnew = SimpleManipulations.invertColors(currentPic);
                case "GrayScaleAverage" -> pnew = SimpleManipulations.grayScaleAverage(currentPic);
                case "GrayScaleLuminosity" -> pnew = SimpleManipulations
                        .grayScaleLuminosity(currentPic);
                default -> pnew = null;
            }

            if (pnew != null) {
                changeImage(pnew);
            }
        });

        vignette.addActionListener(e -> changeImage(SimpleManipulations.vignette(currentPic)));

        blur.addActionListener(e -> {
            try {
                String result = JOptionPane.showInputDialog(
                        frame,
                        "Enter a blur radius, a small integer.",
                        "Blur radius",
                        JOptionPane.PLAIN_MESSAGE
                );
                if (result != null) {
                    int radius = Integer.parseInt(result);
                    if (radius <= 0 || radius >= 20) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Blur radius must be > 0 and < 20",
                                "Alert",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    changeImage(AdvancedManipulations.blur(currentPic, radius));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid number",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        flood.addActionListener(
                e -> JOptionPane.showMessageDialog(
                        frame,
                        "Click on the image to flood it with red.",
                        "Flood instructions",
                        JOptionPane.PLAIN_MESSAGE
                )
        );

        contrast.addActionListener(e -> {
            String result = "";
            try {
                result = JOptionPane.showInputDialog(
                        frame,
                        """
                                Enter a contrast multiplier.
                                The maximum range is 0 to 255,
                                although values above 16 look similar for this image.
                                Usually contrast values are small -- between 0.5 and 1.5.""",
                        "Contrast multiplier",
                        JOptionPane.PLAIN_MESSAGE
                );
                if (result != null) {
                    double d = Double.parseDouble(result);
                    if (d >= 0.0 && d <= 255.0) {
                        changeImage(AdvancedManipulations.adjustContrast(currentPic, d));
                    } else {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid number " + result,
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        palette.addActionListener(e -> {
            try {
                String result = JOptionPane.showInputDialog(
                        frame,
                        "Enter the number of colors to which you want to reduce the palette.",
                        "Palette size",
                        JOptionPane.PLAIN_MESSAGE
                );
                if (result != null) {
                    int i = Integer.parseInt(result);
                    if (i < 1) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Expecting a number greater than 0.",
                                "Alert",
                                JOptionPane.ERROR_MESSAGE
                        );

                    } else {
                        changeImage(AdvancedManipulations.reducePalette(currentPic, i));
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid number",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        return toolbar;

    }

    private JPanel makeEffectBar() {
        final JPanel effectBar = new JPanel();

        effectBar.setLayout(new GridLayout(0, 1));

        effectBar.add(new JLabel("Effects"));

        JButton eighteenNinety = new JButton("1890s");
        effectBar.add(eighteenNinety);
        eighteenNinety.addActionListener(arg0 -> changeImage(Effects.eighteenNinety(currentPic)));
        JButton pinHole = new JButton("Pin Hole");
        effectBar.add(pinHole);
        pinHole.addActionListener(arg0 -> changeImage(Effects.pinHole(currentPic)));

        JButton zombie = new JButton("Zombie");
        effectBar.add(zombie);
        zombie.addActionListener(arg0 -> changeImage(Effects.zombie(currentPic)));

        JButton plastic = new JButton("Plastic");
        effectBar.add(plastic);
        plastic.addActionListener(e -> changeImage(Effects.plastic(currentPic)));

        JButton peaches = new JButton("Peaches");
        effectBar.add(peaches);
        peaches.addActionListener(e -> changeImage(Effects.peaches(currentPic)));

        JButton custom = new JButton("Custom");
        effectBar.add(custom);
        custom.addActionListener(e -> changeImage(Effects.custom(currentPic)));

        return effectBar;
    }

    public void run() {
        frame.setLayout(new BorderLayout());

        final JPanel menuBar = makeMenuBar();
        final JPanel toolBar = makeToolBar();
        final JPanel imageArea = new JPanel();
        final JPanel effectBar = makeEffectBar();

        final JPanel rightSide = new JPanel();
        rightSide.setLayout(new GridLayout(0, 1));
        rightSide.add(menuBar);
        rightSide.add(effectBar);

        frame.add(toolBar, BorderLayout.LINE_START);
        frame.add(imageArea, BorderLayout.CENTER);
        frame.add(rightSide, BorderLayout.LINE_END);

        // Add mouse listener to the image itself for flood-fill
        imageArea.add(imageLabel);
        imageLabel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeImage(
                        AdvancedManipulations.flood(
                                currentPic,
                                new Pixel(255, 0, 0), e.getY(), e.getX()
                        )
                );
            }
        });

        frame.pack();
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUI());
    }

}
