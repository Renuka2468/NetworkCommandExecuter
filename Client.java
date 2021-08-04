import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;

class Client
{
	BufferedReader in;
	PrintWriter out;
	Socket socket;
	String localhost,serverip;

	int port ;
	Client(String s, int p)
	{
		serverip=s;
		try
		{
			socket =new Socket(s,p);
			InetAddress address = InetAddress.getLocalHost();
			localhost=address.getHostName();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		port =p;
		try
		{
			in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			ReadThread thread = new ReadThread();
			thread.start();
			out = new PrintWriter (socket.getOutputStream(),true);
			//out.println("Msg From clinet Connected");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	protected void finalize()
	{
		out.println("disconnected");
	}
	class ReadThread extends Thread
	{
		String source,dest,path,filename;
		public void run()
		{
			while(true)
			{
				try
				{
					System.out.println("Client");
					source = in.readLine();
					if(source.equals("Create File"))
					{
						System.out.println(source);
						path= in.readLine();
						System.out.println(path);
						File f = new File(path);
						try
						{
							f.createNewFile();
							out.println(localhost +": File created Successfully");
						}
						catch(Exception e)
						{
							System.out.println(e);

							out.println(localhost+": "+e.getMessage());
							System.exit(0);
						}
					}
					else
					if(source.equals("Delete File"))
					{
						System.out.println(source);
						path= in.readLine();
						System.out.println(path);
						File f = new File(path);
						if(f.delete())
						out.println(localhost+":"+path+" File deleted suceessfully");
						else
						out.println(localhost+": File is not deleted due to wrong path or file not exists");
					}
					else
					if(source.equals("Check File"))
					{
						System.out.println(source);
						path= in.readLine();
						System.out.println(path);
						File f = new File(path);
						if(f.exists())
						out.println(localhost+":"+path+" File Exists");
						else
						out.println(localhost+": File is not exists");
					}
					else
					if(source.equals("Create Folder"))
					{
						System.out.println(source);
						path= in.readLine();
						System.out.println(path);
						File f = new File(path);
						if(f.mkdirs())
							out.println(localhost+":"+path+" Folder Created Sucessfully");
						else
							out.println(localhost+": Folder is not created");
					}
					else
					if(source.equals("Delete Folder"))
					{
						System.out.println(source);
						path= in.readLine();
						System.out.println(path);
						File f = new File(path);
						if(f.delete())
							out.println(localhost+":"+path+" Folder Deleted Sucessfully");
						else
							out.println(localhost+": Folder is not empty or not exists");
					}
					else
					if(source.equals("Copy File"))
					{
						File dfile = new File(in.readLine());

						FileOutputStream out=null;
						DataInputStream din=null;
						Socket s=null;
						try
						{

							out = new FileOutputStream(dfile);
							if(out==null)
							System.out.println("asdf");

							s = new Socket(serverip,6123);
						    din = new DataInputStream(s.getInputStream());
							byte ch;
							while((ch=din.readByte())!=-1)
							out.write(ch);
							out.close();
							din.close();
							s.close();

						}
						catch(Exception e)
						{
							System.out.println("Client:"+e);
							s.close();
							out.close();
							din.close();
						}
					}
					else
					if(source.equals("Send Message"))
					{
						String str = in.readLine();
						JOptionPane.showMessageDialog(null,str,"Message from Server",3);
					}
					else
					if(source.equals("Shutdown"))
					{
						try
						{
							path= in.readLine();
							Runtime rt = Runtime.getRuntime();
							rt.exec("shutdown -f -s");
							out.println(localhost +": Shutdown Successfully");
						}
						catch(Exception e)
						{
								System.out.println(e);
								out.println(localhost+": Exception occurs, Application Can not started");
						}
					}
					else
					if(source.equals("Rename File"))
					{
						File sfile=new File(in.readLine());
						File dfile= new File(in.readLine());
						if(sfile.renameTo(dfile))
						out.println(localhost +": Rename Successfully");
						else
						out.println(localhost+": Rename fail");
					}
					else
					if(source.equals("Start Application"))
					{
						try
						{
							path= in.readLine();
							Runtime rt = Runtime.getRuntime();
							rt.exec(path);
							out.println(localhost +": Application Started Successfully");
						}
						catch(Exception e)
						{
							System.out.println(e);
							out.println(localhost+": Exception occurs, Application Can not started");
						}
					}

				}
				catch(Exception e)
				{
					System.out.println(e);
					System.exit(0);
				}
			}
		}
	}






	public static void main(String args[])
	{
		SettingFrame f = new SettingFrame();
		f.setVisible(true);

	}
}

class SettingFrame extends JFrame
{
	String ipadd;
	String port;
	JTextField tfip;
	JTextField tfport;
	SettingFrame()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w=screenSize.width/2;
		int h=screenSize.height/5;
		setSize(w,h);
		setLocation((screenSize.width-w)/2,(screenSize.height-h)/2);
		JPanel p = new JPanel();
		p.setBorder(new BevelBorder(BevelBorder.LOWERED));
		p.setLayout(null);
		p.setBackground(Color.gray);
		JPanel x = new JPanel();
		x.setLayout(null);
		x.setBorder(new BevelBorder(BevelBorder.LOWERED));
		x.setBackground(Color.LIGHT_GRAY);
		x.setBounds(20,20,w-40,h-40);


		JLabel lblip = new JLabel("Server IP Address:");
		lblip.setForeground(Color.black);
		lblip.setFont(new Font("Verdana",1,14));
		x.add(lblip);
		lblip.setBounds(100,20,150,20);

		tfip = new JTextField();
		tfip.setFont(new Font("Verdana",1,12));
		tfip.setBounds(275,20,200,20);
		x.add(tfip);

		JLabel lblport = new JLabel("Port No :");
		lblport.setForeground(Color.black);
		lblport.setFont(new Font("Verdana",1,14));
		x.add(lblport);
		lblport.setBounds(100,50,150,20);

		tfport = new JTextField();
		tfport.setFont(new Font("Verdana",1,12));
		tfport.setBounds(275,50,200,20);
		x.add(tfport);

		final JButton jbOK = new JButton("OK");

		jbOK.setBackground(Color.gray);
		jbOK.setForeground(Color.white);
		jbOK.setBorder(new BevelBorder(0));
		jbOK.setFont(new Font("Verdana",1,12));
		x.add(jbOK);
		jbOK.setBounds(130,80,150,25);
		jbOK.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent me)
					{
						jbOK.setForeground(Color.green);
						jbOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
					public void mouseExited(MouseEvent me)
					{
						jbOK.setForeground(Color.white);
					}
				});
		jbOK.addActionListener (new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ipadd = tfip.getText();
				port = tfport.getText();
				Client client = new Client(ipadd,Integer.parseInt(port));
				setVisible(false);
			}
		});

		final JButton	jbCancel = new JButton("CANCEL");
		jbCancel.setBackground(Color.gray);
		jbCancel.setForeground(Color.white);
		jbCancel.setBorder(new BevelBorder(0));
		jbCancel.setFont(new Font("Verdana",1,12));
		x.add(jbCancel);
		jbCancel.setBounds(300,80,150,25);
		jbCancel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me)
			{
				jbCancel.setForeground(Color.red);
				jbCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent me)
			{
				jbCancel.setForeground(Color.white);
			}
		});

		jbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				//System.exit(0);
				dispose();
			}
		});

		p.add(x);

		add(p);
		setUndecorated(true);
	}
}
