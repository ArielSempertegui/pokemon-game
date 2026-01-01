package com.ariel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Clase que gestiona el hero o menú principal del juego
 * @author Ariel Sempertegui
 *
 */
class JFrameMenu {

	static JFrame ventana;
	static JPanel panelCardLayout;
	static CardLayout cardLayout;
	
	/**
	 * Create the application.
	 */
	JFrameMenu() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ventana = new JFrame();
		ventana.setBounds(new Rectangle(600, 300, 575, 385));
		ventana.setResizable(false);
		ventana.setTitle("Pokemon Go");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelCardLayout = new JPanel();
		ventana.getContentPane().add(panelCardLayout, BorderLayout.CENTER);
		cardLayout = new CardLayout();
		panelCardLayout.setLayout(cardLayout);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(null);
		
		JButton btnNuevaPartida = new JButton("Nueva Partida");
		btnNuevaPartida.setBounds(217, 202, 124, 23);
		panelMenu.add(btnNuevaPartida);
		
		JButton btnCargarPartida = new JButton("Cargar Partida");
		btnCargarPartida.setEnabled(false);
		btnCargarPartida.setBounds(217, 236, 124, 23);
		panelMenu.add(btnCargarPartida);
		
		JButton btnMostrarStats = new JButton("Mostrar Stats");
		btnMostrarStats.setEnabled(false);
		btnMostrarStats.setBounds(217, 270, 124, 23);
		panelMenu.add(btnMostrarStats);
		
		JLabel lblVersion = new JLabel("version 1.1");
		lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVersion.setBounds(473, 321, 76, 14);
		panelMenu.add(lblVersion);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(162, 58, 235, 121);
		ImageIcon imgTituloJuego = new ImageIcon(getClass().getResource("/img/logo.png"));
		Icon icoTituloJuego = new ImageIcon(imgTituloJuego.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH));
		lblLogo.setIcon(icoTituloJuego);
		panelMenu.add(lblLogo);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondo.setBounds(0, 0, 559, 346);
		ImageIcon imgFondo = new ImageIcon(getClass().getResource("/img/fondoLobby.gif"));
		Icon icoFondo = new ImageIcon(imgFondo.getImage().getScaledInstance(lblFondo.getWidth(), lblFondo.getHeight(), Image.SCALE_DEFAULT));
		lblFondo.setIcon(icoFondo);
		panelMenu.add(lblFondo);
		
		panelCardLayout.add(panelMenu, "JPanelMenu");
		panelCardLayout.add(new JPanelCreacionPersonaje(), "JPanelCreacionPersonaje");
		panelCardLayout.add(new JSplitPaneGame(), "JSplitPanePartida");
		
		//Acciones
		btnNuevaPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardLayout.show(panelCardLayout, "JPanelCreacionPersonaje");
			}
		});
	}

}
