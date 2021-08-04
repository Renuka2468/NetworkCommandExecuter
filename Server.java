import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.Timer;

class Server
{
	public static void main(String args[])
	{
				SplashWindow splash = new SplashWindow();
				splash.setVisible(true);		//-------> Show the splash screen

				try
				{
					Thread.currentThread().join(2000);		//-------> Delay of two seconds
				}
				catch(Exception e)
				{
					JOptionPane pane = new JOptionPane();
					pane.showMessageDialog(new JFrame(),"Main Thread Interrupted","Interruption",JOptionPane.ERROR_MESSAGE);
					return;
				}//end try-catch block

				splash.dispose();						//------> Dispose the splash screen*/

				ServerWindow se=new ServerWindow();
				se.setVisible(true);		//------> Show the ServerFrame

	}//end main
}//end class "Server"





class SplashWindow extends JWindow
{
	JButton jbImg;

	public SplashWindow()
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension d = kit.getScreenSize();

		setLocation((d.width -(d.width/3))/2,(d.height-(d.height/3))/2);
		setSize(423,183);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ImageIcon img = new ImageIcon("spalsh.gif");
		jbImg = new JButton(img);
		jbImg.setBorder(new LineBorder(getBackground()));
		add(jbImg);
	}//end constructor
}

class ServerWindow extends JFrame implements ActionListener
{
	ArrayList socketList = new ArrayList();
	ArrayList chboxList = new ArrayList();
	ArrayList btnCmd = new ArrayList();

	JPanel compBox = new JPanel();

	JPanel cmdBox = new JPanel();

	public JMenuBar menuBar;

	public JToolBar toolBar;

	String strCurCmd="";

	JLabel lblSource= new JLabel("Source File:",JLabel.LEFT);
	JLabel lblDest = new JLabel("Destination File:",JLabel.LEFT);
	JTextField sfile = new JTextField();
	JTextField dfile = new JTextField();
	JTextArea ta = new JTextArea();
	JButton btnBrowse,btnOk,btnCancel;



	String btnLabel [] = {"Create File","Rename File","Delete File","Copy File","Send Message","Check File",
	"Create Folder","Delete Folder","Start Application","Shutdown"};

	int r,g=128,b=255;
	ServerWindow()
	{
		setDefaultCloseOperation(3);
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.gray);
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

		JMenu file = new JMenu("Option");
		JMenuItem ex = new JMenuItem("Exit");
		ex.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		file.add(ex);
		//file.setForeground(Color.red);
		menuBar.add(file);
		setJMenuBar(menuBar);


		ServerThread t = new ServerThread();
		t.start();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension d = kit.getScreenSize();
		setLocation(d.width/6,d.height/6);
		setSize((int)(d.width/1.5)+50,(int)(d.height/1.5));


		//setSize(800,600);
			compBox.setLayout(new GridLayout(btnLabel.length,1,5,15));
			compBox.setBackground(Color.GRAY);
		JLabel l2 = new JLabel("   Connected Computers  ");
		l2.setFont(new Font("Times",Font.BOLD,18));
		l2.setForeground(Color.white);
		compBox.add(l2);



		JScrollPane sp = new JScrollPane(compBox);
		sp.setBorder(new BevelBorder(BevelBorder.RAISED));


		add(sp,"West");
		JPanel south = new JPanel();
		south.setBackground(Color.GRAY);
		try
		{
			InetAddress address = InetAddress.getLocalHost();
			south.add(new JLabel("Server Name:  "+(address.getHostName().toUpperCase())+"    Port No: 5001"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		south.setBorder(new BevelBorder(BevelBorder.RAISED));
		add(south,"South");


		cmdBox.setLayout(new GridLayout(btnLabel.length,1,5,15));

		JScrollPane sp1 = new JScrollPane(cmdBox);
		sp1.setBorder(new BevelBorder(BevelBorder.RAISED));
		add(sp1,"East");
		sp1.setBackground(Color.DARK_GRAY);

		for(int i=0;i<btnLabel.length;i++)
		{
			final JButton te = new JButton(btnLabel[i]);
			btnCmd.add(te);
			te.setBorder(new BevelBorder(BevelBorder.RAISED));
			//te.setMinimumSize(cmdBox.getSize());
			te.addActionListener(this);

			te.setBackground(Color.GRAY);
			te.setForeground(Color.white);
			te.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					te.setForeground(Color.green);
				}
				public void mouseExited(MouseEvent e)
				{
					te.setForeground(Color.white);
				}
			});
			cmdBox.add(te);
		//	cmdBox.add(Box.createVerticalGlue());
		}


		JPanel cbox = new JPanel();
		cbox.setLayout(new GridLayout(2,1));
		cbox.setBorder(new BevelBorder(BevelBorder.RAISED));
		ta.setBorder(new BevelBorder(BevelBorder.LOWERED));
		ta.setBackground(Color.LIGHT_GRAY);

		JPanel center = new JPanel();
		center.setLayout(null);
		btnBrowse = new JButton("Browse");
		btnBrowse.setBorder(new BevelBorder(BevelBorder.RAISED));
		btnBrowse.setBackground(Color.LIGHT_GRAY);
		 lblSource.setBounds(20,40,150,25);
		 //lblSource.setBorder(new BevelBorder(BevelBorder.RAISED));
		 center.add(lblSource);
		 lblDest.setBounds(20,90,150,25);
		 //lblDest.setBorder(new BevelBorder(BevelBorder.RAISED));
		 center.add(lblDest);
		 sfile.setBounds(170,40,250,25);
		 sfile.setBorder(new LineBorder(Color.LIGHT_GRAY,3));
		 center.add(sfile);
		 dfile.setBounds(170,90,250,25);
		 dfile.setBorder(new LineBorder(Color.LIGHT_GRAY,3));
		 center.add(dfile);
		 btnBrowse.setBounds(425,40,70,25);
		 center.add(btnBrowse);

		center.setBackground(Color.GRAY);
		center.setBorder(new BevelBorder(BevelBorder.RAISED));
		btnOk = new JButton("Ok",new ImageIcon("Check_P.png"));
		btnOk.setBorder(new BevelBorder(BevelBorder.RAISED));
		btnOk.setBackground(Color.LIGHT_GRAY);
		btnOk.setBounds(100,140,100,25);
		btnOk.addActionListener(this);

		center.add(btnOk);



		btnCancel= new JButton("Cancel", new ImageIcon("Exit.png"));
		btnCancel.setBorder(new BevelBorder(BevelBorder.RAISED));
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(250,140,100,25);
		center.add(btnCancel);
		btnCancel.addActionListener(this);

		setPanelButton(false);

		btnBrowse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser dlg = new JFileChooser(new File("."));
				dlg.showDialog(ServerWindow.this,"Select File");
				File f = dlg.getSelectedFile();
				sfile.setText(f.getPath());
			}
		});


		cbox.add(center);

		ta.setFont(new Font("Times",Font.BOLD,14));
		ta.setEditable(false);
		cbox.add(new JScrollPane(ta));

		add(cbox);
			//code to add toolbar
		    // Create a set of actions to use in both the menu and toolbar
		    DemoAction b1 = new DemoAction("Create File", new ImageIcon(
		        "icon4.jpg"), "Create a file", 'N');
		    DemoAction b2 = new DemoAction("Rename File", new ImageIcon(
		        "rename.gif"), "Rename a File", 'R');
		    DemoAction b3 = new DemoAction("Delete File",
		        new ImageIcon("delete.png"), "Delete File", 'D');
		    DemoAction b4 = new DemoAction("Copy File", new ImageIcon(
		        "bnew.png"), "File Copy", 'C');
		        DemoAction b5 = new DemoAction("Send Message", new ImageIcon(
		        "bnew.png"), "Send Message to selected computer", 'M');
		        DemoAction b6 = new DemoAction("Check File", new ImageIcon(
		        "clipbord.gif"), "Check Whether File is exists or not", 'S');
		        DemoAction b7 = new DemoAction("Create Folder", new ImageIcon(
		        "folder.png"), "Create Folder", 'F');
		        DemoAction b8 = new DemoAction("Delete Folder", new ImageIcon(
		        "trash.gif"), "Delete Folder", 'X');
				DemoAction b9 = new DemoAction("Start Application", new ImageIcon(
		        "application.jpg"), "Start A Particular Application", 'A');
				DemoAction b10 = new DemoAction("Shutdown", new ImageIcon(
		        "shutdown.gif"), "Shutdown Selected Computer", 'O');
				DemoAction b11 = new DemoAction("Exit", new ImageIcon(
		        "stopicon.jpg"), "Exit", 'E');
	b1.setEnabled(false);
    toolBar = new JToolBar("Formatting");
    toolBar.setBorderPainted(true);
    toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));
    toolBar.add(b1);
    toolBar.add(b2);
    toolBar.add(b3);
    toolBar.add(b4);
	toolBar.add(b5);
    toolBar.add(b6);
    toolBar.addSeparator();
    toolBar.add(b7);
    toolBar.add(b8);
    toolBar.add(b9);
    toolBar.add(b10);
    toolBar.addSeparator();
    toolBar.add(b11);
    toolBar.addSeparator();
    JLabel label = new JLabel(new ImageIcon("title.png"),JLabel.CENTER);
    toolBar.add(label);
    toolBar.addSeparator();
    toolBar.setBackground(Color.LIGHT_GRAY);
    add(toolBar, BorderLayout.NORTH);

    JButton b=new JButton("Client");
    toolBar.add(b);
    b.addActionListener(new ActionListener()
    {
		public void actionPerformed(ActionEvent e)
		{
			SettingFrame f = new SettingFrame();
			f.setVisible(true);
		}
	});

	setButton(false);

	addWindowListener(new WindowAdapter()
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	});

	}

	void setPanelButton(boolean f)
	{
		lblSource.setVisible(f);
				lblDest.setVisible(f);
				dfile.setVisible(f);
				sfile.setVisible(f);
				btnBrowse.setVisible(f);
				btnOk.setVisible(f);
				btnCancel.setVisible(f);
	}
	public void actionPerformed(ActionEvent e)
	{
		String s = e.getActionCommand();
		System.out.println(s);
		process(s);
	}

	public void process(String s)
	{

		if(s.equals("Create File"))
		{

			strCurCmd=s;
			lblSource.setText("File Name With Path:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Delete File"))
		{
			strCurCmd=s;
			lblSource.setText("Enter File Name with Path:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Check File"))
		{
			strCurCmd=s;
			lblSource.setText("File/Folder Name with Path:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Create Folder"))
		{
			strCurCmd=s;
			lblSource.setText("Enter Folder Name with Path:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Delete Folder"))
		{
			strCurCmd=s;
			lblSource.setText("Enter Folder Name with Path:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Rename File"))
		{
			strCurCmd = s;
			lblSource.setText("Old File Name:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setText("New File Name:");
			lblDest.setVisible(true);
			btnBrowse.setVisible(true);
			dfile.setVisible(true);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Copy File"))
		{
			strCurCmd = s;
			lblSource.setText("Select File From Server:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setText("Provide Destination Path:");
			lblDest.setVisible(true);
			btnBrowse.setVisible(true);
			dfile.setVisible(true);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			btnBrowse.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Send Message"))
		{
			strCurCmd=s;
			lblSource.setText("Type Message:");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Start Application"))
		{
			strCurCmd=s;
			lblSource.setText("Application Name :");
			lblSource.setVisible(true);
			sfile.setVisible(true);
			lblDest.setVisible(false);
			btnBrowse.setVisible(false);
			dfile.setVisible(false);
			setButton(false);
			btnOk.setVisible(true);
			btnCancel.setVisible(true);
			sfile.requestFocus(true);
		}
		else
		if(s.equals("Shutdown"))
		{
			int x = JOptionPane.showConfirmDialog(null," Are you sure to shutdown remote computer","Confirmation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			if(x==JOptionPane.OK_OPTION)
			executeCommand("Shutdown","");
		 }
		else
		if(s.equals("Exit"))
		{
			int x = JOptionPane.showConfirmDialog(null, "Are you sure to close Network Command Executer","Network Command Executer", JOptionPane.YES_NO_OPTION, 2);
			if(x==JOptionPane.YES_OPTION)

			System.exit(0);
		}
		else
		if(s.equals("Cancel"))
		{
			setButton(true);
			lblSource.setVisible(false);
			lblDest.setVisible(false);
			sfile.setVisible(false);
			dfile.setVisible(false);
			btnBrowse.setVisible(false);
			btnOk.setVisible(false);
			btnCancel.setVisible(false);
		}
		else
		if(s.equals("Ok"))
		{
			System.out.println(strCurCmd);

			if(strCurCmd.equals("Create File"))
				executeCommand("Create File",sfile.getText());
			else
			if(strCurCmd.equals("Delete File"))
				executeCommand("Delete File", sfile.getText());
			else
			if(strCurCmd.equals("Check File"))
				executeCommand("Check File",sfile.getText());
			else
			if(strCurCmd.equals("Rename File"))
			{
				File st=new File(sfile.getText());
				String str=st.getAbsolutePath();
				int l=str.lastIndexOf("\\");
				executeCommand("Rename File", sfile.getText(),str.substring(0,l)+"\\"+dfile.getText());
			}
			else
			if(strCurCmd.equals("Copy File"))
			{
				File st=new File(sfile.getText());
				if(!st.exists())
				JOptionPane.showMessageDialog(null,"File Does not exists","Error",3);
				else
				{
					try
					{
						String str=st.getAbsolutePath();
						int l=str.lastIndexOf("\\");

						ServerSocket server = new ServerSocket(6123);
						executeCommand("Copy File", dfile.getText()+"\\"+str.substring(l,str.length()));
						Socket incoming = server.accept();
						DataOutputStream dout = new DataOutputStream(incoming.getOutputStream());
						FileInputStream fin = new FileInputStream(st);
						int ch;
						while((ch=fin.read())!=-1)
						dout.writeByte((byte)ch);
						dout.close();
						incoming.close();
						server.close();
					}
					catch(Exception e)
					{
						System.out.println("Server:"+e);
					}
				}
			}
			else
			if(strCurCmd.equals("Send Message"))
				executeCommand("Send Message", sfile.getText());
			if(strCurCmd.equals("Create Folder"))
				executeCommand("Create Folder", sfile.getText());
			else
			if(strCurCmd.equals("Delete Folder"))
				executeCommand("Delete Folder", sfile.getText());
			else
			if(strCurCmd.equals("Start Application"))
				executeCommand("Start Application",sfile.getText());
			else
			if(strCurCmd.equals("Shutdown"))
				executeCommand("Shutdown"," ");

			setButton(true);
			setPanelButton(false);
			sfile.setText("");
			dfile.setText("");
		}
	}
	public void setButton(boolean f)
	{
		for(int i=0;i<btnCmd.size();i++)
		{
			JButton t = (JButton)btnCmd.get(i);
			t.setEnabled(f);

		}
		for(int i=0;i<12;i++)
		{
			Component c = toolBar.getComponentAtIndex(i);
			c.setEnabled(f);
		}

	}
	public void executeCommand(String cmd,String path)
	{

		for(int i=0;i<chboxList.size();i++)
		{
		System.out.println("inside execute");
		JCheckBox ch=(JCheckBox)chboxList.get(i);
			if(ch.isSelected())
			{
				try
				{
				Socket client = (Socket)socketList.get(i);
				PrintWriter out = new PrintWriter(client.getOutputStream(),true);
				out.println(cmd);
				out.println(path);
				//BufferedReader read = new BufferedReader(new InputStreamReader (client.getInputStream()));
				//ReadThread thread = new ReadThread(read);
				//thread.start();
				}
				catch(Exception e)
				{
					System.out.println(e);

				}
			}
		}
	}
	public void executeCommand(String cmd,String sourcef, String destf)
		{

			for(int i=0;i<chboxList.size();i++)
			{
			System.out.println("inside execute");
			JCheckBox ch=(JCheckBox)chboxList.get(i);
				if(ch.isSelected())
				{
					try
					{
					Socket client = (Socket)socketList.get(i);
					PrintWriter out = new PrintWriter(client.getOutputStream(),true);
					out.println(cmd);
					out.println(sourcef);
					out.println(destf);
					//BufferedReader read = new BufferedReader(new InputStreamReader (client.getInputStream()));
					//ReadThread thread = new ReadThread(read);
					//thread.start();
					}
					catch(Exception e)
					{
						System.out.println(e);

					}
				}
			}
		}
	class ReadThread extends Thread
	{
		Socket client;
		BufferedReader in;
		ReadThread(Socket s)
		{

			client=s;
		    try
		    {
		    	in = new BufferedReader(new InputStreamReader (client.getInputStream()));
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

		}
		public void run()
		{

			while(true)
			{
				try
				{
					ta.append(in.readLine()+"\n\r");
				}
				catch(Exception e)
				{
					int i= socketList.indexOf(client);
					socketList.remove(client);
					System.out.println(socketList.size());
					JCheckBox ch =(JCheckBox) chboxList.get(i);
					ta.append(ch.getText()+" Disconnected\n\r");
					chboxList.remove(i);
					compBox.remove(i+1);
					compBox.validate();
					compBox.repaint();
					if(socketList.size()==0)
					setButton(false);
					break;
				}
			}

		}
	}



	  class DemoAction extends AbstractAction
	  {
		  public DemoAction(String text, Icon icon, String description, char accelerator)
		  {
			  super(text, icon);
			  putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator,
			  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			  putValue(SHORT_DESCRIPTION, description);
		}
	    public void actionPerformed(ActionEvent ect)
	    {
			String s=(String)getValue(NAME);
			process(s);
		}
	}

	class ServerThread extends Thread
	{
		ServerSocket ss;
		ServerThread()
		{
			try
			{
				ss = new ServerSocket(5001);
			}catch(Exception e) { System.out.println(e);}

		}
		public void run()
		{
			try
			{
				while(true)
				{
					Socket client =ss.accept();
					setButton(true);
					InetAddress address = client.getInetAddress();


					final JCheckBox check = new JCheckBox((address.getHostName()).toUpperCase(),true);
					check.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
								int i;
								for(i=0;i<chboxList.size();i++)
								{
									JCheckBox ch=(JCheckBox)chboxList.get(i);
									if(ch.isSelected())
									{
										setButton(true);
										break;
									}
								}
								if(i==chboxList.size())
								setButton(false);
						}
					});

					check.setBackground(Color.GRAY);
					check.setForeground(Color.white);
					check.setBorder(new BevelBorder(BevelBorder.RAISED));
					chboxList.add(check);
					compBox.add(check);
					socketList.add(client);

					ReadThread thread = new ReadThread(client);
					thread.start();

					validate();

				    System.out.println("Connected");
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}

}

