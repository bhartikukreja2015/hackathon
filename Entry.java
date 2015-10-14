package abc;


	import java.awt.FlowLayout;

	import java.awt.event.ActionEvent;

	import java.awt.event.ActionListener;

	import java.io.File;


	import javax.swing.JButton;

	import javax.swing.JDialog;

	import javax.swing.JFileChooser;

	import javax.swing.JFrame;


	public class Entry {

	public static void main(String[] args) {

	JFrame.setDefaultLookAndFeelDecorated(true);

	JDialog.setDefaultLookAndFeelDecorated(true);

	JFrame frame = new JFrame("JComboBox Test");

	frame.setLayout(new FlowLayout());
	frame.setSize(200, 300);

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JButton button = new JButton("Select File");

	button.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent ae) {

	JFileChooser fileChooser = new JFileChooser();

	int returnValue = fileChooser.showOpenDialog(null);

	if (returnValue == JFileChooser.APPROVE_OPTION) {

	String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
	selectedFile=selectedFile.replace("\\" , "/");
	
	ReadXlsx.getInfo(selectedFile);
	
	System.out.println(selectedFile);
	
	

	}

	}

	});

	frame.add(button);

	frame.pack();

	frame.setVisible(true);

	}

	}


