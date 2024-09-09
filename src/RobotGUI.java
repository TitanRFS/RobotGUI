import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RobotGUI extends JFrame implements InvariantCheck {
    private static final int ROOM_SIZE = 6;
    private int currentX;
    private int currentY;
    private JButton[][] gridButtons;
    private JLabel positionLabel;
    private JLabel errorLabel;
    private boolean status = true;

    public RobotGUI() {
        super("RobotMonitor");
        setSize(800, 840);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentX = 0;
        currentY = 0;

        JPanel gridPanel = new JPanel(new GridLayout(ROOM_SIZE, ROOM_SIZE));
        gridButtons = new JButton[ROOM_SIZE][ROOM_SIZE];

        for (int i = 0; i < ROOM_SIZE; i++) {
            for (int j = 0; j < ROOM_SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 80));
                gridButtons[i][j] = button;
                gridPanel.add(button);
            }
        }
        updateGrid();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton moveUpButton = new JButton("Move Up");
        JButton moveDownButton = new JButton("Move Down");
        JButton moveLeftButton = new JButton("Move Left");
        JButton moveRightButton = new JButton("Move Right");
        JButton exitButton = new JButton("Exit Room");
        JButton toggleStatusButton = new JButton("Toggle Status");

        moveUpButton.addActionListener(e -> {
            try {
                if (status) { // Check if status is ON (true)
                    moveUp();
                    clearError();
                } else {
                    showError("Robot status is OFF. Cannot move.");
                }
            } catch (RuntimeException ex) {
                showError("VDMException : Precondition broken");
            }
        });

        moveDownButton.addActionListener(e -> {
            try {
                if (status) { // Check if status is ON (true)
                    moveDown();
                    clearError();
                } else {
                    showError("Robot status is OFF. Cannot move.");
                }
            } catch (RuntimeException ex) {
                showError("VDMException : Precondition broken");
            }
        });

        moveLeftButton.addActionListener(e -> {
            try {
                if (status) { // Check if status is ON (true)
                    moveLeft();
                    clearError();
                } else {
                    showError("Robot status is OFF. Cannot move.");
                }
            } catch (RuntimeException ex) {
                showError("VDMException : Precondition broken");
            }
        });

        moveRightButton.addActionListener(e -> {
            try {
                if (status) { // Check if status is ON (true)
                    moveRight();
                    clearError();
                } else {
                    showError("Robot status is OFF. Cannot move.");
                }
            } catch (RuntimeException ex) {
                showError("VDMException : Precondition broken");
            }
        });

        exitButton.addActionListener(e -> {
            exitRoom();
            clearError();
        });

        toggleStatusButton.addActionListener(e -> {
            if (currentX != 0) { // Only allow status toggle if not on the first row
                status = !status; // Toggle the status
                updateRobotAppearance(); // Update robot's appearance based on status
            } else {
                showError("Cannot toggle status while robot is on the first row.");
            }
        });

        controlPanel.add(moveUpButton);
        controlPanel.add(moveLeftButton);
        controlPanel.add(moveRightButton);
        controlPanel.add(moveDownButton);
        controlPanel.add(exitButton);
        controlPanel.add(toggleStatusButton);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));

        positionLabel = new JLabel("Current Position: (0, 0)"); // Initialize positionLabel
        positionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusPanel.add(positionLabel);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        statusPanel.add(errorLabel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(statusPanel, BorderLayout.NORTH);

        setVisible(true);

        // Perform initial invariant check
        VDM.invTest(this);
    }

    private void updateGrid() {
        for (int i = 0; i < ROOM_SIZE; i++) {
            for (int j = 0; j < ROOM_SIZE; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }

        gridButtons[currentX][currentY].setText("R");
        updateRobotAppearance();

        if (isAtDoor()) {
            showCongratulationsMessage();
        }

        updatePositionLabel(); // Ensure position label is updated
    }

    private void updatePositionLabel() {
        if (positionLabel != null) {
            positionLabel.setText("Current Position: (" + currentX + ", " + currentY + ")");
        }
    }

    private void updateRobotAppearance() {
        if (status) {
            gridButtons[currentX][currentY].setBackground(Color.GREEN);
        } else {
            gridButtons[currentX][currentY].setBackground(Color.RED);
        }
    }

    private void moveUp() {
        VDM.preTest(currentX > 0);
        currentX--;
        updateGrid();
        VDM.invTest(this);
    }

    private void moveDown() {
        VDM.preTest(currentX < ROOM_SIZE - 1);
        currentX++;
        updateGrid();
        VDM.invTest(this);
    }

    private void moveLeft() {
        VDM.preTest(currentY > 0);
        currentY--;
        updateGrid();
        VDM.invTest(this);
    }

    private void moveRight() {
        VDM.preTest(currentY < ROOM_SIZE - 1);
        currentY++;
        updateGrid();
        VDM.invTest(this);
    }

    private void exitRoom() {
        VDM.preTest(isAtDoor());
        currentX = 0;
        currentY = 0;
        updateGrid();
        VDM.invTest(this);
    }

    public boolean isAtDoor() {
        return currentX == ROOM_SIZE - 1 && currentY == ROOM_SIZE - 1;
    }

    private void showCongratulationsMessage() {
        JOptionPane.showMessageDialog(this, "Congratulations! You have reached the exit.", "Exit Reached", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void clearError() {
        errorLabel.setText("");
    }

    public boolean isStatusOn() {
        return status;
    }
    public boolean isStatusOff()
    {
    	return !status;
    }
    @Override
    public boolean inv() {
        return currentX >= 0 && currentX < ROOM_SIZE && currentY >= 0 && currentY < ROOM_SIZE;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RobotGUI();
        });
    }
}
