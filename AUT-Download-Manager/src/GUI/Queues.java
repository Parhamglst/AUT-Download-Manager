package GUI;

import Utils.Download;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The panel for the list of queues, instantiated only once
 */
public class Queues extends JPanel {
    private static Queues singleton;

    private ArrayList<Queue> queues;
    private Integer numberOfQueues;
    private OptionsBar optionsBar;
    private Queue activeQueue;

    private Queues() {
        numberOfQueues = 0;

        setLayout(new BorderLayout());

        optionsBar = new OptionsBar();
        add(optionsBar, BorderLayout.NORTH);

        queues = new ArrayList<>();
        loadQueues();
    }

    /**
     * the method for instantiating the queues
     *
     * @return
     */
    public static final Queues getInstance() {
        if (singleton == null) {
            singleton = new Queues();
        }
        return singleton;
    }

    public final void addQueue(String name, DefaultListModel queueList) {
        queues.add(new Queue(name, queueList));
        optionsBar.getQueueList().addItem(queues.get(numberOfQueues));
        saveQueues();
        loadQueues();
        activeQueue = queues.get(numberOfQueues);
        add(activeQueue, BorderLayout.CENTER);
        numberOfQueues++;
        revalidate();
        repaint();
    }

    public void setQueues(ArrayList<Queue> queues) {
        this.queues = queues;
    }

    public void loadQueues(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("./queues.jdm"))){
            setQueues((ArrayList<Queue>) in.readObject());
            for (Queue queue:
                    queues) {
                optionsBar.queueList.addItem(queue);
            }
            activeQueue = queues.get(queues.size() - 1);
            numberOfQueues = queues.size() - 1;
        } catch (IOException | ClassNotFoundException e) {
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./queues.jdm"))) {
                outputStream.writeObject(queues);
            } catch (IOException e1) {
                e.printStackTrace();
            }
        }
    }
    public void saveQueues(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./queues.jdm"))) {
            out.writeObject(queues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Queues";
    }

    private class Queue extends DownloadList {
        private String name;
        private DefaultListModel queueList;

        Queue(String name, DefaultListModel<Download> queueList) {
            super(queueList);
            this.queueList = queueList;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class OptionsBar extends JPanel {
        private JComboBox queueList;
        private JButton newQueue;

        OptionsBar() {
            setBackground(Color.white);

            queueList = new JComboBox();
//            if (queueList.getItemCount() != 0)
                add(queueList);
            queueList.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    activeQueue = (Queue)(e.getItem());
                    revalidate();
                    repaint();
                }
            });

            newQueue = new JButton("+");
            add(newQueue);
            newQueue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == newQueue) {
                        JFrame newQueue = new JFrame("New Queue");
                        newQueue.setLocationRelativeTo(null);
                        newQueue.setLayout(new GridLayout(2, 2));
                        newQueue.add(new JLabel("Queue Name: "));
                        JTextField queuesName = new JTextField();
                        newQueue.add(queuesName);

                        JButton add = new JButton("Add");
                        newQueue.add(new JLabel(""));
                        newQueue.add(add);
                        newQueue.setVisible(true);
                        add.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == add && queuesName.getText().replaceAll("\\s", "").equals("")) {
                                    queuesName.setText("Enter the queue's name here");
                                } else if (e.getSource() == add) {
                                    addQueue(queuesName.getText(), new DefaultListModel());
                                    newQueue.dispose();
                                }
                            }
                        });
                        queuesName.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    if (queuesName.getText().replaceAll("\\s", "").equals("")) {
                                        queuesName.setText("Enter the queue's name here");
                                    } else {
                                        addQueue(queuesName.getText(), new DefaultListModel());
                                        newQueue.dispose();
                                    }
                                }
                            }
                        });
                        newQueue.setVisible(true);
                    }
                }
            });
        }

        public JComboBox getQueueList() {
            return queueList;
        }
    }
}
