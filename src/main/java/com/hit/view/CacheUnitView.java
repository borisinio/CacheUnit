package main.java.com.hit.view;


import main.java.com.hit.dm.DataModel;
import main.java.com.hit.server.Response;
import main.java.com.hit.util.ObserMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Observable;

    public class CacheUnitView extends Observable implements View
    {
        private JFrame frame;
        private JFileChooser fileChooser;
		private JTable table;
		private Gson gson;
		private DefaultTableModel dtm;
		private JLabel capacityTxt;
		private JLabel availableTxt;
		private JLabel getActionTxt;
		private JLabel swapActionTxt;
		private JLabel result;
		private static int numOfData = 1;

		public CacheUnitView()
        {
            javax.swing.SwingUtilities.invokeLater (new Runnable ()
            {
                @Override
                public void run()
                {
                    start ();
                }
            });

        }
        
        @Override
        public void start()
        {
        
    		frame = new JFrame("CachUnitUI");
    		frame.setBounds(100, 100, 600, 450);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.getContentPane().setLayout(null);
    		frame.setLocationRelativeTo(null);
    		JPanel panel = new JPanel();
    		panel.setBounds(10, 11, 560, 29);
    		frame.getContentPane().add(panel);
    		panel.setLayout(new GridLayout(0, 4, 10,0));

    		fileChooser = new JFileChooser();
    		
    		JButton btnNewButton = new JButton("Load");
    		btnNewButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent arg0) {
    				
    				  int returnVal = fileChooser.showOpenDialog(frame);

                      if (returnVal == JFileChooser.APPROVE_OPTION) {
                          File file = fileChooser.getSelectedFile();
                          setChanged ();
                          notifyObservers (new ObserMessage("view", "load", file.getPath()));
    			}}
    			});
    		
    		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
    		panel.add(btnNewButton);
//    		
    		JButton btnNewButton_1 = new JButton("CacheMemory");
    		btnNewButton_1.setHorizontalAlignment(SwingConstants.RIGHT);
    		btnNewButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setChanged ();
					notifyObservers (new ObserMessage("view", "updateUI"));
				}
			});
    		panel.add(btnNewButton_1);
//    		
    		JButton clearBtn = new JButton("clear");
    		clearBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for( int i = dtm.getRowCount() - 1; i >= 0; i-- )
					{
					    dtm.removeRow(i);
					}
					numOfData = 1;
					getActionTxt.setText(0+"");
					swapActionTxt.setText(0+"");
					capacityTxt.setText(0+"");
					availableTxt.setText(0+"");

				}
			});
    		panel.add(clearBtn);
			result = new JLabel();
			panel.add(result);

    		JScrollPane scrollPane = new JScrollPane();
    		scrollPane.setBounds(10, 148, 560, 212);
    		frame.getContentPane().add(scrollPane);
    		
    		table = new JTable();
    		dtm = new DefaultTableModel(new Object[][] {
			},
			new String[] {
				"No.", "DataModelId", "Content"
			});
	    		table.setModel(dtm);
    		scrollPane.setViewportView(table);
    		frame.setVisible(true);
    		
    		JPanel panel_1 = new JPanel();
    		panel_1.setBounds(10, 51, 560, 63);
    		frame.getContentPane().add(panel_1);
    		panel_1.setLayout(null);
    		
    		JLabel capacity = new JLabel("Capacity");
    		capacity.setBounds(10, 11, 102, 14);
    		panel_1.add(capacity);
    		
    		JLabel usedCapacity = new JLabel("Used Capacity");
    		usedCapacity.setBounds(10, 36, 102, 14);
    		panel_1.add(usedCapacity);
    		
    		capacityTxt = new JLabel("Initial");
    		capacityTxt.setBounds(143, 11, 96, 14);
    		panel_1.add(capacityTxt);
    		
    		availableTxt = new JLabel("Used Capacity");
    		availableTxt.setBounds(143, 36, 96, 14);
    		panel_1.add(availableTxt);
    		
    		JLabel getAction = new JLabel("Number of get action");
    		getAction.setBounds(282, 11, 169, 14);
    		panel_1.add(getAction);
    		
    		JLabel swapAction = new JLabel("Number of swap action");
    		swapAction.setBounds(280, 36, 171, 14);
    		panel_1.add(swapAction);
    		
    		getActionTxt = new JLabel("get action");
    		getActionTxt.setBounds(473, 11, 46, 14);
    		panel_1.add(getActionTxt);
    		
    		swapActionTxt = new JLabel("swap");
    		swapActionTxt.setBounds(473, 36, 46, 14);
    		panel_1.add(swapActionTxt);
    		
        }
        
        @Override
        public <T> void updateUIData(T t)
        {

        	gson = new Gson();
        	
            ObserMessage tMsg = (ObserMessage) t;
            String labelString = null;

            //load datamodels from cacheUnitServer and show in table
            if (tMsg.getSentIdentifier().equals ("load"))
            {
                String inputString = tMsg.getMessege ();
//                labelString = "Failed";

              if(!inputString.equals("true") && !inputString.equals("removed")) {
                Type res = new TypeToken<Response<DataModel<T>[]>>(){}.getType();
                Response<DataModel<T>[]> response = gson.fromJson(inputString, res);
                DataModel<T>[] content = response.getBody();
                
                for(int i=0;i<content.length;i++,numOfData++) {
                	if(content[i]!=null) {
                	dtm.addRow(new Object[] {numOfData,content[i].getId(),content[i].getContent(),null});
                }
                }
              }else if (inputString.equals("true")){
              	result.setText("pages updated successfully");
			  }else if(inputString.equals("removed")){
              	result.setText("pages removed successfully");
			  }

            }

			if (tMsg.getSentIdentifier().equals ("stats")){

				String inputString = tMsg.getMessege ();
				String[] strings = inputString.split(" ");
				getActionTxt.setText(strings[1]);
				swapActionTxt.setText(strings[2]);
				capacityTxt.setText(strings[3]);
				availableTxt.setText(strings[4]);
			}
        }
    }
