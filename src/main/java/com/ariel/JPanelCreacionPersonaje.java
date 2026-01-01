package com.ariel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import placeholder.TextPrompt;

/**
 * Clase con la lógica de ventana para elegir un personaje y crear el nombre
 * @author Ariel Sempertegui
 *
 */
@SuppressWarnings("serial")
class JPanelCreacionPersonaje extends JPanel{
	
	/*CONSTANTES*/
	private static final int LONG_NOMBRE_USUARIO = 7;
	private static final String EXP_REG_NOMBRE_USUARIO = "^[A-Z]\\w{0,6}$";
	
	static String nombreUsuario;
	static int desplazamiento = 0;
	
	private JTextField txtNombreUsuario;
	
	JPanelCreacionPersonaje(){
		initComponents();
		TextPrompt placeHolderNombrePersonaje = new TextPrompt("Introduzca un nombre", txtNombreUsuario);
		placeHolderNombrePersonaje.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	private void initComponents(){
		
		setBackground(new Color(249, 237, 211));
		setLayout(null);
		
		JLabel lblPersonaje = new JLabel("");
		lblPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonaje.setBounds(207, 22, 150, 244);
		ImageIcon imgAshKetchum = new ImageIcon(getClass().getResource("/img/personaje0.png"));  
		Icon icoAshKetchum = new ImageIcon(imgAshKetchum.getImage().getScaledInstance(lblPersonaje.getWidth(), lblPersonaje.getHeight(), Image.SCALE_SMOOTH));
		lblPersonaje.setIcon(icoAshKetchum);
		
		ImageIcon imgBrock = new ImageIcon(getClass().getResource("/img/personaje2.png"));   // Usa el nombre de fichero adecuado
		Icon icoBrock = new ImageIcon(imgBrock.getImage().getScaledInstance(lblPersonaje.getWidth(), lblPersonaje.getHeight(), Image.SCALE_SMOOTH));
		
		ImageIcon imgMisty = new ImageIcon(getClass().getResource("/img/personaje1.png"));   // Usa el nombre de fichero adecuado
		Icon icoMisty = new ImageIcon(imgMisty.getImage().getScaledInstance(lblPersonaje.getWidth(), lblPersonaje.getHeight(), Image.SCALE_SMOOTH));
		
		add(lblPersonaje);
		
		JLabel lblPodio = new JLabel("");
		lblPodio.setHorizontalAlignment(SwingConstants.CENTER);
		lblPodio.setBounds(201, 222, 163, 103);
		ImageIcon imgPodio = new ImageIcon(getClass().getResource("/img/podio.png")); 
		Icon icoPodio =new ImageIcon(imgPodio.getImage().getScaledInstance(lblPodio.getWidth(), lblPodio.getHeight(), Image.SCALE_SMOOTH));
		lblPodio.setIcon(icoPodio);
		add(lblPodio);
		
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombreUsuario.setBounds(192, 304, 180, 25);
		add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);
		
		JButton btnAnterior = new JButton("<-");
		btnAnterior.setBounds(158, 174, 45, 23);
		add(btnAnterior);
		
		JButton btnSiguiente = new JButton("->");
		btnSiguiente.setBounds(361, 174, 45, 23);
		add(btnSiguiente);
		
		JLabel lblMaxCaracteres = new JLabel("");
		lblMaxCaracteres.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMaxCaracteres.setForeground(new Color(255, 0, 0));
		lblMaxCaracteres.setBounds(382, 304, 70, 25);
		add(lblMaxCaracteres);
		
		
		//Acciones
		
		txtNombreUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!Pattern.matches(EXP_REG_NOMBRE_USUARIO, txtNombreUsuario.getText())) {
					
					ImageIcon imgProfesorOak = new ImageIcon(getClass().getResource("/img/profesorOak.png"));
					JOptionPane.showMessageDialog(JFrameMenu.panelCardLayout, "Debe empezar por Mayúscula y sin espacios", "Información", JOptionPane.INFORMATION_MESSAGE, imgProfesorOak);
					
				}else {
					//Agregamos el nombre del personaje que ha puesto el usuario, no es el nombre  a la variable
					nombreUsuario = txtNombreUsuario.getText();
					txtNombreUsuario.setText("");
					
					desplazamiento = 0;
					btnAnterior.setEnabled(true);
					btnSiguiente.setEnabled(true);
					lblPersonaje.setIcon(icoAshKetchum);
					
					//Cambiamos de vista a el panel de combate
					JFrameMenu.cardLayout.show(JFrameMenu.panelCardLayout, "JSplitPanePartida");
					JFrameMenu.ventana.setBounds(new Rectangle(500, 200, 775, 585));
					
				}
			}
		});
		
		txtNombreUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(txtNombreUsuario.getText().length() >= LONG_NOMBRE_USUARIO) {
					e.consume();
					lblMaxCaracteres.setText("Máx.");
				}else {
					lblMaxCaracteres.setText("");
				}
			}
		});
		
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desplazamiento--;
				btnSiguiente.setEnabled(true);
				
				if(desplazamiento == -1 ) {
					btnAnterior.setEnabled(false);
					
					lblPersonaje.setIcon(icoBrock);
				} else {
					lblPersonaje.setIcon(icoAshKetchum);
				}
			}
		});
		
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desplazamiento++;
				btnAnterior.setEnabled(true);
				if(desplazamiento == 1) {
					btnSiguiente.setEnabled(false);
					
					lblPersonaje.setIcon(icoMisty);
				} else {
					lblPersonaje.setIcon(icoAshKetchum);
				}
			}
		});
		
	}
}


