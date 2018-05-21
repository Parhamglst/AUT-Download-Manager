package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Queues extends JPanel {
    private static Queues singleton;

    private ArrayList<Queue> queues;
    private Integer numberOfQueues;
    private QueueOptions queueOptions;
    private Queues (ArrayList<Queue> queues){
        numberOfQueues = 0;

        setLayout(new BorderLayout());

        queueOptions = new QueueOptions();
        add(queueOptions,BorderLayout.NORTH);
    }

    public static final Queues getInstance(ArrayList queues){
        if(singleton == null){
            singleton = new Queues(queues);
        }
        return singleton;
    }

    public final void addQueue(String name, DefaultListModel queueList){
        queues.add(new Queue(name, queueList));
        queueOptions.getQueueList().addItem(queues.get(numberOfQueues - 1));
        numberOfQueues++;
        add(queues.get(numberOfQueues - 1), BorderLayout.CENTER);
    }

    private class QueueOptions extends JPanel{
        private JComboBox queueList;
        private JButton newQueue;

        QueueOptions() {
            queueList = new JComboBox();
            add(queueList);

            newQueue = new JButton("+");
            add(newQueue);
            newQueue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == this){
                        JFrame newQueue = new JFrame("New Queue");
                        newQueue.setLayout(new GridLayout(2,2));
                        newQueue.add(new JLabel("Queue Name: "));
                        JTextField queuesName = new JTextField();
                        newQueue.add(queuesName);

                        JButton add = new JButton("Add");
                        add(new JLabel(""));
                        add(add);
                        add.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(e.getSource() == add && queuesName.getText().replaceAll("\\s","").equals("")){
                                    queuesName.setText("Enter the queue's name here");
                                }
                                else if (e.getSource() == add){
                                    addQueue(queuesName.getText(), new DefaultListModel());
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

    private class Queue extends DownloadList{
        private String name;
        private DefaultListModel queueList;

        Queue(String name, DefaultListModel<Entity> queueList){
            super(queueList);
            this.queueList = queueList;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
