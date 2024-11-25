package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import reproductor.Reproductor;
import reproductor.ReproductorImpl;
import sonido.Sonido;

public class GUI {
	
	private static Reproductor reproductor;
	
	private static JSlider slider;
	private JButton button_1;
	private static JList<String> list;
	private JFrame frmReproductor;
	private static JLabel label_playing;
	private static JLabel label_samplerate;
	private static JLabel label_bitssample;
	private static JLabel label_mode;
	private static JLabel label_format;
	private JToggleButton toggleButton;
	private JToggleButton tglbtnNewToggleButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmReproductor.setVisible(true);
					window.frmReproductor.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		this.reproductor = new ReproductorImpl(slider);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReproductor = new JFrame();
		frmReproductor.setTitle("Reproductor");
		frmReproductor.setBounds(100, 100, 559, 354);
		frmReproductor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReproductor.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(145, 218, 200, 75);
		frmReproductor.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reproductor.anterior();
			}
		});
		button.setBounds(36, 45, 30, 30);
		panel.add(button);
		button.setIcon(new ImageIcon(GUI.class.getResource("/resources/previous.png")));
		
		button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(reproductor.getSonidos().size()>0){
					if(reproductor.isPlaying()){
//						System.out.println("PAUSANDO");
						reproductor.pause();
						button_1.setIcon(new ImageIcon(GUI.class.getResource("/resources/play.png")));
						
					}else if(reproductor.isPaused()){
//						System.out.println("Reanudando");
						reproductor.restart();
						button_1.setIcon(new ImageIcon(GUI.class.getResource("/resources/pause.png")));
					}else{
//						System.out.println("INICIANDO");
						reproductor.start();
						button_1.setIcon(new ImageIcon(GUI.class.getResource("/resources/pause.png")));
						actualizaInfo();
					}
				}else{
					JOptionPane.showMessageDialog(frmReproductor, "La lista de reproducción esta vacía");
				}
			}
		});
		button_1.setBounds(76, 25, 40, 40);
		panel.add(button_1);
		button_1.setIcon(new ImageIcon(GUI.class.getResource("/resources/play.png")));
		
		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproductor.siguiente();
			}
		});
		button_2.setBounds(126, 45, 30, 30);
		panel.add(button_2);
		button_2.setIcon(new ImageIcon(GUI.class.getResource("/resources/next.png")));
		
		slider = new JSlider();
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Long posicion = (long) slider.getValue();
				if(reproductor.isPlaying()){
					reproductor.setPosicion(posicion);
					reproductor.restart();
				}else if(reproductor.isPaused() || reproductor.isStopped()){
					reproductor.getReproduccion().setPosicion(posicion);
				}
			}
		});
		
		slider.setBounds(0, 0, 200, 26);
		panel.add(slider);
		slider.setValue(0);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(reproductor.isPlaying()){
					reproductor.stop();
					reproductor.setIndice(0);
					button_1.setIcon(new ImageIcon(GUI.class.getResource("/resources/play.png")));
				}
				reproductor.creaListaReproduccion();
				Vector<String> datos = new Vector<String>();
				for(Sonido s:reproductor.getSonidos()){
					datos.add(s.toString());
				}
//				list = new JList (datos);
				list.setListData(datos);
				System.out.println(reproductor.getSonidos());
			}
		});
		btnNewButton.setIcon(new ImageIcon(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/newFolder.gif")));
		btnNewButton.setSelectedIcon(new ImageIcon(GUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		
		btnNewButton.setBounds(315, 34, 30, 30);
		frmReproductor.getContentPane().add(btnNewButton);
		
		JLabel lblListaDeReproduccin = new JLabel("Lista de Reproducci\u00F3n");
		lblListaDeReproduccin.setBounds(355, 11, 152, 14);
		frmReproductor.getContentPane().add(lblListaDeReproduccin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(393, 235, 60, 23);
		frmReproductor.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		tglbtnNewToggleButton = new JToggleButton("");
		tglbtnNewToggleButton.setIcon(new ImageIcon(GUI.class.getResource("/resources/media-shuffle-1.png")));
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnNewToggleButton.isSelected()){
					reproductor.setRandom(true);
					tglbtnNewToggleButton.setIcon(new ImageIcon(GUI.class.getResource("/resources/media-shuffle.png")));
				}else{
					reproductor.setRandom(false);
					tglbtnNewToggleButton.setIcon(new ImageIcon(GUI.class.getResource("/resources/media-shuffle-1.png")));
				}
			}
		});
		tglbtnNewToggleButton.setBounds(37, 0, 23, 23);
		panel_1.add(tglbtnNewToggleButton);
		
		toggleButton = new JToggleButton("");
		toggleButton.setIcon(new ImageIcon(GUI.class.getResource("/resources/media-loop.png")));
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(toggleButton.isSelected()){
					reproductor.setLoop(true);
					toggleButton.setIcon(new ImageIcon(GUI.class.getResource("/resources/media-loop.png")));
				}else{
					reproductor.setLoop(false);
					toggleButton.setIcon(new ImageIcon(GUI.class.getResource("/resources/media-loop-1.png")));
				}
			}
		});
		toggleButton.setSelected(true);
		toggleButton.setBounds(0, 0, 23, 23);
		panel_1.add(toggleButton);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 33, 295, 114);
		frmReproductor.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("Reproduciendo:");
		label.setBounds(10, 0, 93, 14);
		panel_2.add(label);
		
		label_playing = new JLabel("-");
		label_playing.setBounds(113, 0, 172, 14);
		panel_2.add(label_playing);
		
		label_samplerate = new JLabel("-");
		label_samplerate.setBounds(113, 25, 172, 14);
		panel_2.add(label_samplerate);
		
		JLabel label_3 = new JLabel("Sample Rate:");
		label_3.setBounds(20, 25, 93, 14);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("Bits per sample:");
		label_4.setBounds(10, 50, 93, 14);
		panel_2.add(label_4);
		
		label_bitssample = new JLabel("-");
		label_bitssample.setBounds(113, 50, 172, 14);
		panel_2.add(label_bitssample);
		
		JLabel label_6 = new JLabel("Mode:");
		label_6.setBounds(52, 75, 51, 14);
		panel_2.add(label_6);
		
		label_mode = new JLabel("-");
		label_mode.setBounds(113, 75, 172, 14);
		panel_2.add(label_mode);
		
		JLabel label_8 = new JLabel("Format:");
		label_8.setBounds(52, 100, 51, 14);
		panel_2.add(label_8);
		
		label_format = new JLabel("-");
		label_format.setBounds(113, 100, 172, 14);
		panel_2.add(label_format);
		
		String[] data = {"Lista vacía"};
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 34, 178, 150);
		frmReproductor.getContentPane().add(scrollPane);

		list = new JList(data);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(list.getSelectedIndex()!=-1){
					reproductor.setIndice(list.getSelectedIndex());
					reproductor.stop();
					reproductor.start();
					actualizaInfo();
				}
			}
		});
		scrollPane.setViewportView(list);
		
	}
	
	public static void actualizaInfo(){
		String nombre = reproductor.getSonidos().get(reproductor.getIndice()).getNombre();
		String samplerate = ""+reproductor.getSonidos().get(reproductor.getIndice()).getClip().getFormat().getSampleRate()+" Hz";
		String[] aux  = reproductor.getSonidos().get(reproductor.getIndice()).getNombre().split("[.]");
		String format = aux[aux.length-1];
		String mode = ""+reproductor.getSonidos().get(reproductor.getIndice()).getClip().getFormat().getEncoding();
		String bitspersample = reproductor.getSonidos().get(reproductor.getIndice()).getClip().getFormat().getSampleSizeInBits()+" - "+reproductor.getSonidos().get(reproductor.getIndice()).getClip().getFormat().getChannels()+"canales";
		label_playing.setText(nombre);
		label_samplerate.setText(samplerate);
		label_format.setText(format);
		label_mode.setText(mode);
		label_bitssample.setText(bitspersample);
		list.setSelectedIndex(reproductor.getIndice());
	}
}
