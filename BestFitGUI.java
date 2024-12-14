import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class BestFitGUI extends JFrame {
    private JTextField memoryInput, jobInput;
    private JTextArea resultArea;
    private JButton allocateButton, resetButton;

    public BestFitGUI() {
        setTitle("Best Fit Memory Allocation");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Memory Blocks (comma-separated):"));
        memoryInput = new JTextField();
        inputPanel.add(memoryInput);

        inputPanel.add(new JLabel("Job Sizes (comma-separated):"));
        jobInput = new JTextField();
        inputPanel.add(jobInput);

        allocateButton = new JButton("Allocate");
        resetButton = new JButton("Reset");
        inputPanel.add(allocateButton);
        inputPanel.add(resetButton);

        add(inputPanel, BorderLayout.NORTH);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
        add(scrollPane, BorderLayout.CENTER);

        // Action listeners
        allocateButton.addActionListener(e -> allocateMemory());
        resetButton.addActionListener(e -> resetFields());

        setVisible(true);
    }

    private void allocateMemory() {
        try {
            // Parse input
            // Parse input for memory blocks- Comma separated string is split into an integer array.
            int[] memoryBlocks = Arrays.stream(memoryInput.getText().split(","))
                                       .map(String::trim).mapToInt(Integer::parseInt).toArray();

            // Parse input for jobs- Comma separated string is split into an integer array.                           
            int[] jobs = Arrays.stream(jobInput.getText().split(","))
                               .map(String::trim).mapToInt(Integer::parseInt).toArray();

            int[] allocation = new int[jobs.length];
            Arrays.fill(allocation, -1);

            // Best Fit Algorithm 
            for (int i = 0; i < jobs.length; i++) {
                int bestIndex = -1;    // Tracks the index of the best fitting memory block.

                // Find the smallest memory block that fits the current job.
                for (int j = 0; j < memoryBlocks.length; j++) {
                    if (memoryBlocks[j] >= jobs[i]) {
                        if (bestIndex == -1 || memoryBlocks[j] < memoryBlocks[bestIndex]) {
                            bestIndex = j;
                        }
                    }
                }
                // Suitable block was found, allocate it to the job.
                if (bestIndex != -1) {
                    allocation[i] = bestIndex;
                    memoryBlocks[bestIndex] -= jobs[i];
                }
            }

            // Display results
            StringBuilder result = new StringBuilder("Job No.\tJob Size\tBlock Allocated\n");
            for (int i = 0; i < jobs.length; i++) {
                if (allocation[i] != -1) {
                    result.append((i + 1)).append("\t").append(jobs[i]).append("\t\tBlock ").append(allocation[i] + 1).append("\n");
                } else {
                    result.append((i + 1)).append("\t").append(jobs[i]).append("\t\tNot Allocated\n");
                }
            }
            resultArea.setText(result.toString());

            // Handle exceptions such as invalid input or parsing errors.
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid integers separated by commas.",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        // Clear the memory input field,job input field and result area.
        memoryInput.setText("");
        jobInput.setText("");
        resultArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BestFitGUI::new);
    }
}
