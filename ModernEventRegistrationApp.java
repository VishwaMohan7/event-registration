import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ModernEventRegistrationApp extends JFrame {

    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(59, 130, 246);     // Blue-500
    private static final Color PRIMARY_HOVER = new Color(37, 99, 235);      // Blue-600
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);      // Green-500
    private static final Color SUCCESS_HOVER = new Color(22, 163, 74);      // Green-600
    private static final Color DANGER_COLOR = new Color(239, 68, 68);       // Red-500
    private static final Color DANGER_HOVER = new Color(220, 38, 38);       // Red-600
    private static final Color WARNING_COLOR = new Color(245, 158, 11);     // Amber-500
    private static final Color WARNING_HOVER = new Color(217, 119, 6);      // Amber-600
    private static final Color NEUTRAL_COLOR = new Color(107, 114, 128);    // Gray-500
    private static final Color NEUTRAL_HOVER = new Color(75, 85, 99);       // Gray-600
    private static final Color BACKGROUND = new Color(249, 250, 251);       // Gray-50
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);        // Gray-900
    private static final Color TEXT_SECONDARY = new Color(75, 85, 99);      // Gray-600
    private static final Color BORDER_COLOR = new Color(229, 231, 235);     // Gray-200

    private final Map<String, EventData> events = new HashMap<>();
    private final Map<String, java.util.List<Registration>> registrations = new HashMap<>();
    private JTable participantsTable;
    private JComboBox<String> eventCombo;
    private CardLayout cardLayout;
    private JPanel mainContainer;

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ModernEventRegistrationApp app = new ModernEventRegistrationApp();
            app.setVisible(true);
        });
    }

    public ModernEventRegistrationApp() {
        initializeUI();
        setupFrame();
        createCards();
        showMainMenu();
    }

    private void setupFrame() {
        setTitle("Event Registration System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BACKGROUND);
        
        // Add some sample data for demonstration
        events.put("Tech Conference 2024", new EventData("Tech Conference 2024", "Computer Science Club", "2024-07-15", "Conference", 50));
        events.put("Music Festival", new EventData("Music Festival", "Music Society", "2024-08-20", "Festival", 100));
        events.put("Coding Workshop", new EventData("Coding Workshop", "Programming Club", "2024-07-10", "Workshop", 25));
        
        // Initialize registration lists for each event
        for (String eventName : events.keySet()) {
            registrations.put(eventName, new java.util.ArrayList<>());
        }
    }

    private void initializeUI() {
        // Set up modern UI properties
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }

    private void createCards() {
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.setBackground(BACKGROUND);
        
        mainContainer.add(createMainMenuPanel(), "MAIN");
        mainContainer.add(createEventPanel(), "CREATE");
        mainContainer.add(createRegistrationPanel(), "REGISTER");
        mainContainer.add(createEventsListPanel(), "EVENTS");
        mainContainer.add(createRegistrationsPanel(), "REGISTRATIONS");
        
        setContentPane(mainContainer);
    }

    private void showMainMenu() {
        cardLayout.show(mainContainer, "MAIN");
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        // Header section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Event Registration System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Manage events and registrations with ease");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitleLabel);

        // Stats panel
        JPanel statsPanel = createStatsPanel();

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(BACKGROUND);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createEventBtn = createModernButton("🎯 Create New Event", "Organize and set up new events", SUCCESS_COLOR, SUCCESS_HOVER);
        JButton registerBtn = createModernButton("📝 Register for Event", "Join exciting events and activities", PRIMARY_COLOR, PRIMARY_HOVER);
        JButton viewEventsBtn = createModernButton("📊 View All Events", "Browse and manage existing events", WARNING_COLOR, WARNING_HOVER);
        JButton viewRegistrationsBtn = createModernButton("👥 View Registrations", "See who's registered for events", NEUTRAL_COLOR, NEUTRAL_HOVER);

        createEventBtn.addActionListener(e -> cardLayout.show(mainContainer, "CREATE"));
        registerBtn.addActionListener(e -> cardLayout.show(mainContainer, "REGISTER"));
        viewEventsBtn.addActionListener(e -> cardLayout.show(mainContainer, "EVENTS"));
        viewRegistrationsBtn.addActionListener(e -> cardLayout.show(mainContainer, "REGISTRATIONS"));

        buttonsPanel.add(createEventBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));
        buttonsPanel.add(registerBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));
        buttonsPanel.add(viewEventsBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));
        buttonsPanel.add(viewRegistrationsBtn);

        panel.add(headerPanel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(statsPanel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(buttonsPanel);

        return panel;
    }

    private JPanel createRegistrationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Header
        JPanel headerPanel = createHeaderPanel("All Registrations", "View all student registrations across events");

        // Controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.setBackground(BACKGROUND);

        JLabel filterLabel = new JLabel("Filter by Event:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterLabel.setForeground(TEXT_PRIMARY);

        JComboBox<String> eventFilterCombo = new JComboBox<>();
        eventFilterCombo.addItem("All Events");
        for (String eventName : events.keySet()) {
            eventFilterCombo.addItem(eventName);
        }
        eventFilterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        controlsPanel.add(filterLabel);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(eventFilterCombo);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {"Student Name", "Roll Number", "Event Name", "Registration Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Populate table with all registrations
        for (Map.Entry<String, java.util.List<Registration>> entry : registrations.entrySet()) {
            for (Registration reg : entry.getValue()) {
                tableModel.addRow(new Object[]{
                    reg.getStudentName(),
                    reg.getRollNumber(),
                    reg.getEventName(),
                    reg.getRegistrationDate()
                });
            }
        }

        JTable table = new JTable(tableModel);
        styleTable(table);

        // Add filter functionality
        eventFilterCombo.addActionListener(e -> {
            String selectedFilter = (String) eventFilterCombo.getSelectedItem();
            tableModel.setRowCount(0);
            
            for (Map.Entry<String, java.util.List<Registration>> entry : registrations.entrySet()) {
                if ("All Events".equals(selectedFilter) || selectedFilter.equals(entry.getKey())) {
                    for (Registration reg : entry.getValue()) {
                        tableModel.addRow(new Object[]{
                            reg.getStudentName(),
                            reg.getRollNumber(),
                            reg.getEventName(),
                            reg.getRegistrationDate()
                        });
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_BG);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonsPanel.setBackground(CARD_BG);

        JButton backButton = createSecondaryButton("Back to Menu");
        JButton refreshButton = createPrimaryButton("Refresh");

        backButton.addActionListener(e -> showMainMenu());
        refreshButton.addActionListener(e -> {
            // Refresh table data
            String selectedFilter = (String) eventFilterCombo.getSelectedItem();
            tableModel.setRowCount(0);
            
            for (Map.Entry<String, java.util.List<Registration>> entry : registrations.entrySet()) {
                if ("All Events".equals(selectedFilter) || selectedFilter.equals(entry.getKey())) {
                    for (Registration reg : entry.getValue()) {
                        tableModel.addRow(new Object[]{
                            reg.getStudentName(),
                            reg.getRollNumber(),
                            reg.getEventName(),
                            reg.getRegistrationDate()
                        });
                    }
                }
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(refreshButton);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        tablePanel.add(buttonsPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(20), BorderLayout.CENTER);
        panel.add(controlsPanel, BorderLayout.CENTER);
        panel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(BACKGROUND);
        statsPanel.setMaximumSize(new Dimension(600, 100));

        int totalEvents = events.size();
        int totalSlots = events.values().stream().mapToInt(EventData::getAvailableSlots).sum();
        int totalRegistered = events.values().stream().mapToInt(e -> e.getInitialSlots() - e.getAvailableSlots()).sum();

        statsPanel.add(createStatCard("Total Events", String.valueOf(totalEvents), PRIMARY_COLOR));
        statsPanel.add(createStatCard("Available Slots", String.valueOf(totalSlots), SUCCESS_COLOR));
        statsPanel.add(createStatCard("Registrations", String.valueOf(totalRegistered), WARNING_COLOR));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createEventPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Header
        JPanel headerPanel = createHeaderPanel("Create New Event", "Set up a new event for registration");

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JTextField eventNameField = createModernTextField("Enter event name");
        JTextField clubField = createModernTextField("Enter club name");
        JTextField dateField = createModernTextField("Enter date (YYYY-MM-DD)");
        JTextField typeField = createModernTextField("Enter event type");
        JTextField slotsField = createModernTextField("Enter available slots");

        formPanel.add(createFieldPanel("Event Name", eventNameField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Club Name", clubField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Event Date", dateField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Event Type", typeField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Available Slots", slotsField));

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonsPanel.setBackground(CARD_BG);

        JButton backButton = createSecondaryButton("Cancel");
        JButton createButton = createPrimaryButton("Create Event");

        backButton.addActionListener(e -> showMainMenu());
        createButton.addActionListener(e -> {
            try {
                String name = eventNameField.getText().trim();
                String club = clubField.getText().trim();
                String date = dateField.getText().trim();
                String type = typeField.getText().trim();
                int slots = Integer.parseInt(slotsField.getText().trim());

                if (name.isEmpty() || club.isEmpty() || date.isEmpty() || type.isEmpty()) {
                    showErrorDialog("Please fill in all fields.");
                    return;
                }

                events.put(name, new EventData(name, club, date, type, slots));
                registrations.put(name, new java.util.ArrayList<>()); // Initialize registration list
                showSuccessDialog("Event created successfully!");
                
                // Clear fields
                eventNameField.setText("");
                clubField.setText("");
                dateField.setText("");
                typeField.setText("");
                slotsField.setText("");
                
                showMainMenu();
            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter a valid number for slots.");
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(createButton);

        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(buttonsPanel);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(30), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Header
        JPanel headerPanel = createHeaderPanel("Register for Event", "Join an exciting event today");

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        eventCombo = createModernComboBox();
        JTextField rollField = createModernTextField("Enter your roll number");
        JTextField nameField = createModernTextField("Enter your full name");

        // Update combo box with current events
        eventCombo.removeAllItems();
        for (String eventName : events.keySet()) {
            eventCombo.addItem(eventName);
        }

        formPanel.add(createFieldPanel("Select Event", eventCombo));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Student Roll No", rollField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Full Name", nameField));

        // Event details panel
        JPanel detailsPanel = createEventDetailsPanel();
        
        // Update details when event selection changes
        eventCombo.addActionListener(e -> updateEventDetails(detailsPanel));
        if (eventCombo.getItemCount() > 0) {
            updateEventDetails(detailsPanel);
        }

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonsPanel.setBackground(CARD_BG);

        JButton backButton = createSecondaryButton("Cancel");
        JButton registerButton = createPrimaryButton("Register Now");

        backButton.addActionListener(e -> showMainMenu());
        registerButton.addActionListener(e -> {
            String selectedEvent = (String) eventCombo.getSelectedItem();
            String rollNo = rollField.getText().trim();
            String name = nameField.getText().trim();

            if (selectedEvent == null || rollNo.isEmpty() || name.isEmpty()) {
                showErrorDialog("Please fill in all fields.");
                return;
            }

            EventData event = events.get(selectedEvent);
            if (event.getAvailableSlots() > 0) {
                event.reduceSlot();
                // Add registration record
                Registration registration = new Registration(rollNo, name, selectedEvent, java.time.LocalDateTime.now().toString());
                registrations.get(selectedEvent).add(registration);
                showSuccessDialog("Registration successful for " + selectedEvent + "!");
                rollField.setText("");
                nameField.setText("");
                updateEventDetails(detailsPanel);
                showMainMenu();
            } else {
                showErrorDialog("Sorry, this event is full!");
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(registerButton);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(detailsPanel);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(buttonsPanel);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(30), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEventsListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Header
        JPanel headerPanel = createHeaderPanel("All Events", "Manage and view all registered events");

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {"Event Name", "Club", "Date", "Type", "Available Slots", "Registered"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (EventData event : events.values()) {
            tableModel.addRow(new Object[]{
                event.getEventName(),
                event.getClubName(),
                event.getEventDate(),
                event.getEventType(),
                event.getAvailableSlots(),
                event.getInitialSlots() - event.getAvailableSlots()
            });
        }

        JTable table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_BG);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonsPanel.setBackground(CARD_BG);

        JButton backButton = createSecondaryButton("Back to Menu");
        JButton refreshButton = createPrimaryButton("Refresh");

        backButton.addActionListener(e -> showMainMenu());
        refreshButton.addActionListener(e -> {
            // Refresh table data
            tableModel.setRowCount(0);
            for (EventData event : events.values()) {
                tableModel.addRow(new Object[]{
                    event.getEventName(),
                    event.getClubName(),
                    event.getEventDate(),
                    event.getEventType(),
                    event.getAvailableSlots(),
                    event.getInitialSlots() - event.getAvailableSlots()
                });
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(refreshButton);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        tablePanel.add(buttonsPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(30), BorderLayout.CENTER);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHeaderPanel(String title, String subtitle) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);

        return panel;
    }

    private JPanel createEventDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(248, 250, 252)); // Light blue background
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(219, 234, 254), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        return panel;
    }

    private void updateEventDetails(JPanel detailsPanel) {
        detailsPanel.removeAll();
        
        String selectedEvent = (String) eventCombo.getSelectedItem();
        if (selectedEvent != null && events.containsKey(selectedEvent)) {
            EventData event = events.get(selectedEvent);
            
            JLabel titleLabel = new JLabel("Event Details");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            titleLabel.setForeground(TEXT_PRIMARY);
            
            JLabel clubLabel = new JLabel("Club: " + event.getClubName());
            JLabel dateLabel = new JLabel("Date: " + event.getEventDate());
            JLabel typeLabel = new JLabel("Type: " + event.getEventType());
            JLabel slotsLabel = new JLabel("Available Slots: " + event.getAvailableSlots());
            
            Font detailFont = new Font("Segoe UI", Font.PLAIN, 12);
            clubLabel.setFont(detailFont);
            dateLabel.setFont(detailFont);
            typeLabel.setFont(detailFont);
            slotsLabel.setFont(detailFont);
            
            Color detailColor = TEXT_SECONDARY;
            clubLabel.setForeground(detailColor);
            dateLabel.setForeground(detailColor);
            typeLabel.setForeground(detailColor);
            slotsLabel.setForeground(detailColor);
            
            detailsPanel.add(titleLabel);
            detailsPanel.add(Box.createVerticalStrut(8));
            detailsPanel.add(clubLabel);
            detailsPanel.add(dateLabel);
            detailsPanel.add(typeLabel);
            detailsPanel.add(slotsLabel);
        }
        
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private JButton createModernButton(String text, String description, Color bgColor, Color hoverColor) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(CARD_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(400, 80));

        JLabel titleLabel = new JLabel(text);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(bgColor);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_BG);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);

        button.add(textPanel, BorderLayout.CENTER);

        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(249, 250, 251));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColor, 2),
                    BorderFactory.createEmptyBorder(19, 24, 19, 24)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(20, 25, 20, 25)
                ));
            }
        });

        return button;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_SECONDARY);
        button.setBackground(CARD_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(11, 23, 11, 23)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(249, 250, 251));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(NEUTRAL_COLOR, 1),
                    BorderFactory.createEmptyBorder(11, 23, 11, 23)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(11, 23, 11, 23)
                ));
            }
        });

        return button;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(CARD_BG);
        field.setForeground(TEXT_PRIMARY);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // Placeholder functionality
        field.setText(placeholder);
        field.setForeground(TEXT_SECONDARY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_SECONDARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(12, 16, 12, 16)
                ));
            }
        });

        return field;
    }

    private JComboBox<String> createModernComboBox() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(CARD_BG);
        combo.setForeground(TEXT_PRIMARY);
        combo.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return combo;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_PRIMARY);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setReorderingAllowed(false);

        // Custom cell renderer for better styling
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(CARD_BG);
                    } else {
                        c.setBackground(new Color(249, 250, 251));
                    }
                }
                
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Enhanced EventData class with initial slots tracking
    private static class EventData {
        private final String eventName;
        private final String clubName;
        private final String eventDate;
        private final String eventType;
        private final int initialSlots;
        private int availableSlots;

        public EventData(String eventName, String clubName, String eventDate, String eventType, int availableSlots) {
            this.eventName = eventName;
            this.clubName = clubName;
            this.eventDate = eventDate;
            this.eventType = eventType;
            this.initialSlots = availableSlots;
            this.availableSlots = availableSlots;
        }

        public String getEventName() { return eventName; }
        public String getClubName() { return clubName; }
        public String getEventDate() { return eventDate; }
        public String getEventType() { return eventType; }
        public int getAvailableSlots() { return availableSlots; }
        public int getInitialSlots() { return initialSlots; }
        public void reduceSlot() { if (availableSlots > 0) availableSlots--; }
    }

    // Registration class to track individual registrations
    private static class Registration {
        private final String rollNumber;
        private final String studentName;
        private final String eventName;
        private final String registrationDate;

        public Registration(String rollNumber, String studentName, String eventName, String registrationDate) {
            this.rollNumber = rollNumber;
            this.studentName = studentName;
            this.eventName = eventName;
            this.registrationDate = registrationDate;
        }

        public String getRollNumber() { return rollNumber; }
        public String getStudentName() { return studentName; }
        public String getEventName() { return eventName; }
        public String getRegistrationDate() { return registrationDate; }
    }
}
