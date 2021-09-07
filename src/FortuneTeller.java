import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.jsoup.*;
import org.jsoup.nodes.*;


/* simple GUI Fortune Teller application.
 * extracts text from horoscope websites
 */


@SuppressWarnings("serial")
public class FortuneTeller extends JFrame{  
	private JButton btnStart;
	private JTextArea date, horoscope;
	private int sign = -1;
	private Date today;
	private JComboBox<String> hrscpSign; 
	private Image image;
	
	public FortuneTeller() {
		JFrame mainFrame = new JFrame();
		Container cp = mainFrame.getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		Color mint = new Color(145, 255, 180);
		Color purple = new Color(247, 173, 255);
		Color back = new Color(18, 14, 36);
		Color lightyellow = new Color(255, 248, 154);
		Color lightblue = new Color(45, 36, 86);
		
		//text area with the today's date
		date = new JTextArea();
		date.setText("Date: " + getDate()
		+ "\n\nWELCOME TO THE FORTUNE TELLER !" + 
				"\nPLEASE CHOOSE YOUR SIGN AND PRESS <START> TO GET YOUR DAILY HOROSCOPE");
		date.setLineWrap(true);
		date.setWrapStyleWord(true);
		date.setEditable(false);
		date.setFont(new Font("Kristen ITC", Font.BOLD, 14));
		date.setBackground(back);
		date.setForeground(lightyellow);
		date.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		date.setPreferredSize(new Dimension(183, 200));
		
		
		
		//text area with the horoscope text
		horoscope = new JTextArea();
	    horoscope.setFont(new Font("Kristen ITC", Font.PLAIN, 11));
		horoscope.setEditable(false);
		horoscope.setBackground(lightblue);
		horoscope.setForeground(mint);
		horoscope.setLineWrap(true);
		horoscope.setWrapStyleWord(true);
	    DefaultCaret caret = (DefaultCaret)horoscope.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
	    JScrollPane hrscpScrollPane = new JScrollPane(horoscope);
	    ImageIcon star = new ImageIcon("scr/images/star.png");
	    hrscpScrollPane.setBorder(BorderFactory.createLineBorder(mint, 5, true));
	    hrscpScrollPane.setPreferredSize(new Dimension(300, 155));
	    hrscpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    
	    
	    
		
		//button; press to get horoscope text
		btnStart = new JButton("START");
		btnStart.setFont(new Font("Kristen ITC", Font.PLAIN, 12));
		btnStart.setBackground(back);
		btnStart.setForeground(purple);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(sign == -1) {
					horoscope.setText("Choose your sign first !!!");
				}
				else {
					horoscope.setText(toText());

				}

			}
		});
		btnStart.setBorder(BorderFactory.createLineBorder(back, 5));
	
		
		//creating combo box for sign selection
		String[] strSigns = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio",
				"Sagittarius", "Capricorn", "Aquarius", "Pisces"};
		hrscpSign = new JComboBox<String>(strSigns);
		hrscpSign.setFont(new Font("Kristen ITC", Font.PLAIN, 12));
		hrscpSign.setBackground(back);
		hrscpSign.setForeground(lightyellow);
		hrscpSign.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					sign = hrscpSign.getSelectedIndex() + 1;
				}
			}
		});
		
	
		
		JLabel emptyLbl = new JLabel();
		emptyLbl.setPreferredSize(new Dimension(120, 90));
		JLabel emptyLbl2 = new JLabel();
		emptyLbl2.setPreferredSize(new Dimension(120, 50));
		
		ImageIcon title = new ImageIcon("src/images/fortune_teller.png");
		cp.add(date);
		cp.add(new JLabel(title));
		ImageIcon background = new ImageIcon("src/images/background_demo.png");
		cp.add(new JLabel(background));
		cp.add(emptyLbl);
		cp.add(hrscpSign);
	    cp.add(hrscpScrollPane);
		cp.add(btnStart);
		cp.add(emptyLbl2);
		cp.setBackground(lightblue);




		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setTitle("Fortune Teller");
		mainFrame.setSize(700, 700);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	
	
	
	//parsing text from website
	public String toText() {
		Document doc = null;
		try {
			 doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-today.aspx?sign=" + sign).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Element p = doc.select("strong").first();
		Node node = p.nextSibling();
		return node.toString();
	}
	
	
	//getting today's date
	public String getDate() {
		today = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM, E");
		return dateFormatter.format(today);
	}
	
	
	//starting app
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FortuneTeller();
			}
		});

	}

}
