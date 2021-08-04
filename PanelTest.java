import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class PanelTest extends JFrame
{
	public static void main(String args[])
	{
		PanelTest f = new PanelTest();
		f.setVisible(true);
		f.setDefaultCloseOperation(3);
	}
	PanelTest()
	{
		setSize(600,500);
		final JPanel p = new JPanel();
		final JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		JButton remove = new JButton("REmove");

		p.add(ok);
		p.add(cancel);
		add(p);
		add(remove,"South");

		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				p.remove(ok);
				p.repaint();
			}
		});
	}
}

