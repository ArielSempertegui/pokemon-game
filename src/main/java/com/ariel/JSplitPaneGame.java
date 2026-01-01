package com.ariel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.ariel.model.Personaje;
/**
 * Clase que gestiona toda la lógica del juego
 * @author Ariel Sempertegui
 */
@SuppressWarnings("serial")
class JSplitPaneGame extends JSplitPane{

	private static final int VITALIDAD_BASE_AMIGO = 1000;
	private static final int VITALIDAD_BASE_ENEMIGO = 500;
	
	private static final int AUMENTO_VITALIDAD_ENEMIGO7 = 300;
	private static final int AUMENTO_VITALIDAD_ENEMIGO8 = 480;
	private static final int AUMENTO_VITALIDAD_ENEMIGO9 = 1500;
	
	private static final int AUMENTO_ATAQUE_AMIGO = 40;
	private static final int AUMENTO_ATAQUE_ENEMIGO8 = 50;
	private static final int AUMENTO_ATAQUE_ENEMIGO9 = 100;
	
	private static final int VITALIDAD_POCION = 200;
	private static final int NUM_INICIAL_POCIONES = 3;
	private static final int NUM_TOTAL_ENEMIGOS = 10;
	
	private static String[] nombresEnemigos = {"Wartortle","Bulbasur","Charmander","Lapras","Tentacruel","Empoleon","Onix","Vaporeon","Milotic","Gyarados"};
	
	private int numTotalAtaques;
	private int contAtaques;
	private int contEnemigo;
	private int numPociones;
	
	private static Personaje amigo;
	private static Personaje enemigo;
	private JButton btnCurarse;
	private JLabel lblAmigo;
	private JLabel lblAmigoPixel;
	private JLabel lblNombreAmigo;
	private JLabel lblVersus;
	private JLabel lblEnemigo;
	private JLabel lblEnemigoPixel;
	private JLabel lblNombreEnemigo;
	private JLabel lblNumAtaque1;
	private JLabel lblNumDefensa1;
	private JLabel lblNumDanyo1;
	private JLabel lblNumAtaque2;
	private JLabel lblNumDefensa2;
	private JLabel lblNumDanyo2;
	private JTextArea txtACombate;
	
	/**
	 * Metodo Constructor
	 */
	public JSplitPaneGame() {
		initComponents();
		initCombate(); //Se inicializar o reiniciar al primer combate
	}
	
	/**
	 * Genera un número de Ataque al azar de entre 51 y 100 
	 * @return el ataque
	 */
	private static int generarAtaqueRandom() {
		return new Random().nextInt(50) + 51;
	}
	
	/**
	 * Genera un número de Defensa al azar de entre 1 y 50
	 * @return la defensa
	 */
	private static int generarDefensaRandom() {
		return new Random().nextInt(50) + 1;
	}
	
	/**
	 * Genera las estadisticas de ataque y defensa para cada personaje
	 * @param amigo
	 * @param enemigo
	 */
	private void generarEstadisticas(Personaje amigo, Personaje enemigo) {
		
		amigo.setAtaque(generarAtaqueRandom());
		amigo.setDefensa(generarDefensaRandom());
		
		enemigo.setAtaque(generarAtaqueRandom());
		enemigo.setDefensa(generarDefensaRandom());
		
		if(amigo.haEvolucionado(numTotalAtaques)) {
			amigo.setAtaque(amigo.getAtaque()+AUMENTO_ATAQUE_AMIGO);
		}
	}
	
	/**
	 * Muestra las estadisticas en los Jlabels de la esquina inferior derecha
	 * @param p1
	 * @param p2
	 */
	private void mostrarEstadisticas(Personaje amigo, Personaje enemigo) {
		
		lblNumAtaque1.setText("" +amigo.getAtaque());
		lblNumDefensa1.setText("" +amigo.getDefensa());
		lblNumDanyo1.setText("" +(amigo.getAtaque() - enemigo.getDefensa()));		
		lblNumAtaque2.setText("" +enemigo.getAtaque());
		lblNumDefensa2.setText("" +enemigo.getDefensa());		
		lblNumDanyo2.setText("" +(enemigo.getAtaque() - amigo.getDefensa()));
		
	}
	/**
	 * Calcula el aumento de vida del personaje1 que consigue al derrotar a un enemigo, 
	 * según vaya avanzado se le sumará más vida
	 * @param amigo
	 * @param contEnemigos
	 * @return
	 */
	private int calcularAumentoVitalidad(Personaje amigo, int contEnemigos) {
		
		int aumentoVitalidad = 0;
		
		switch(contEnemigos) {
		
			case 1: aumentoVitalidad = (int) ((amigo.getVitalidad() + 150)*0.2); break;
			case 2: aumentoVitalidad = (int) ((amigo.getVitalidad() + 250)*0.2); break;
			case 3: aumentoVitalidad = (int) ((amigo.getVitalidad() + 300)*0.25); break;
			case 4: aumentoVitalidad = (int) ((amigo.getVitalidad() + 380)*0.3); break;
			case 5: aumentoVitalidad = (int) ((amigo.getVitalidad() + 410)*0.3); break;
			case 6: aumentoVitalidad = (int) ((amigo.getVitalidad() + 500)*0.38); break;
			case 7: aumentoVitalidad = (int) ((amigo.getVitalidad() + 590)*0.41); break;
			case 8: aumentoVitalidad = (int) ((amigo.getVitalidad() + 600)*0.5); break;
			case 9: aumentoVitalidad = (int) ((amigo.getVitalidad() + 900)*0.6); break;
				
		}
		return aumentoVitalidad;
	}
	
	/**
	 * Este método sirve para reiniciar el combate
	 */
	private void initCombate() {
		
		numTotalAtaques = 0;
		contAtaques = 0;
		contEnemigo = 0;
		numPociones = NUM_INICIAL_POCIONES;
		
		amigo = new Personaje("Pikachu", VITALIDAD_BASE_AMIGO);
		enemigo = new Personaje(nombresEnemigos[contEnemigo], VITALIDAD_BASE_ENEMIGO);
		
		txtACombate.setText("      》 ---------------------- BATALLA ---------------------- 《\n");
		
		ImageIcon imgAmigo = new ImageIcon(getClass().getResource("/img/amigo0.png"));   //Usa el nombre de fichero adecuado
		Icon icoAmigo = new ImageIcon(imgAmigo.getImage().getScaledInstance(lblAmigo.getWidth(), lblAmigo.getHeight(), Image.SCALE_SMOOTH));
		lblAmigo.setIcon(icoAmigo);
		
		ImageIcon imgAmigoPixel = new ImageIcon(getClass().getResource("/img/amigo0Pixel.png"));   //Usa el nombre de fichero adecuado
		Icon icoAmigoPixel = new ImageIcon(imgAmigoPixel.getImage().getScaledInstance(lblAmigoPixel.getWidth(), lblAmigoPixel.getHeight(), Image.SCALE_SMOOTH));
		lblAmigoPixel.setIcon(icoAmigoPixel);
		
		lblNombreAmigo.setText(amigo.getNombre());
		
		ImageIcon imgVs = new ImageIcon(getClass().getResource("/img/vs.png"));   //Usa el nombre de fichero adecuado
		Icon icoVs = new ImageIcon(imgVs.getImage().getScaledInstance(lblVersus.getWidth(), lblVersus.getHeight(), Image.SCALE_SMOOTH));
		lblVersus.setIcon(icoVs);
		
		ImageIcon imgEnemigo = new ImageIcon(getClass().getResource("/img/enemigo"+contEnemigo+".png"));   //Usa el nombre de fichero adecuado
		Icon icoEnemigo = new ImageIcon(imgEnemigo.getImage().getScaledInstance(lblEnemigo.getWidth(), lblEnemigo.getHeight(), Image.SCALE_SMOOTH));
		lblEnemigo.setIcon(icoEnemigo);
		
		ImageIcon imgEnemigoPixel = new ImageIcon(getClass().getResource("/img/enemigo"+contEnemigo+"Pixel.png"));   //Usa el nombre de fichero adecuado
		Icon icoEnemigoPixel = new ImageIcon(imgEnemigoPixel.getImage().getScaledInstance(lblEnemigoPixel.getWidth(), lblEnemigoPixel.getHeight(), Image.SCALE_SMOOTH));
		lblEnemigoPixel.setIcon(icoEnemigoPixel);
		
		lblNombreEnemigo.setText(enemigo.getNombre());
		
		btnCurarse.setEnabled(true);
		
		generarEstadisticas(amigo, enemigo);
		mostrarEstadisticas(amigo, enemigo);
	}
	
	private void initComponents() {
		
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel panelBatalla = new JPanel();
		setLeftComponent(panelBatalla);
		panelBatalla.setLayout(null);
		
		lblAmigo = new JLabel("");
		lblAmigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmigo.setBounds(43, 68, 200, 180);
		panelBatalla.add(lblAmigo);
		
		lblVersus = new JLabel("");
		lblVersus.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersus.setBounds(286, 68, 200, 180);
		panelBatalla.add(lblVersus);
		
		lblEnemigo = new JLabel("");
		lblEnemigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemigo.setBounds(529, 68, 200, 180);
		panelBatalla.add(lblEnemigo);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondo.setBounds(0, 0, 813, 399);
		ImageIcon imgFondo = new ImageIcon(getClass().getResource("/img/fondoBatalla.jpg"));  
		Icon icoFondo = new ImageIcon(imgFondo.getImage().getScaledInstance(lblFondo.getWidth(), lblFondo.getHeight(), Image.SCALE_SMOOTH));
		lblFondo.setIcon(icoFondo);
		panelBatalla.add(lblFondo);
		
		JPanel panelControl = new JPanel();
		panelControl.setBackground(new Color(249, 237, 211));
		setRightComponent(panelControl);
		panelControl.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(145, 11, 326, 179);
		panelControl.add(scrollPane);
		
		txtACombate = new JTextArea();
		txtACombate.setEditable(false);
		scrollPane.setViewportView(txtACombate);
		
		JButton btnAtacar = new JButton("Atacar");
		btnAtacar.setBounds(18, 46, 117, 37);
		panelControl.add(btnAtacar);
		
		btnCurarse = new JButton("Curarse");
		btnCurarse.setBounds(18, 128, 117, 37);
		panelControl.add(btnCurarse);
		
		lblAmigoPixel = new JLabel("");
		lblAmigoPixel.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmigoPixel.setBounds(494, 11, 106, 71);
		panelControl.add(lblAmigoPixel);
		
		lblEnemigoPixel = new JLabel("");
		lblEnemigoPixel.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemigoPixel.setBounds(631, 11, 108, 73);
		panelControl.add(lblEnemigoPixel);
		
		lblNombreAmigo = new JLabel();
		lblNombreAmigo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombreAmigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreAmigo.setBounds(515, 93, 65, 14);
		panelControl.add(lblNombreAmigo);
		
		lblNombreEnemigo = new JLabel();
		lblNombreEnemigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreEnemigo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombreEnemigo.setBounds(621, 93, 128, 14);
		panelControl.add(lblNombreEnemigo);
		
		JLabel lblAtaque1 = new JLabel("Ataque:");
		lblAtaque1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtaque1.setBounds(494, 126, 46, 14);
		panelControl.add(lblAtaque1);
		
		JLabel lblDefensa1 = new JLabel("Defensa:");
		lblDefensa1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDefensa1.setBounds(481, 151, 59, 14);
		panelControl.add(lblDefensa1);
		
		JLabel lblDanyo1 = new JLabel("Daño:");
		lblDanyo1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDanyo1.setBounds(494, 176, 46, 14);
		panelControl.add(lblDanyo1);
		
		JLabel lblAtaque2 = new JLabel("Ataque:");
		lblAtaque2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtaque2.setBounds(637, 126, 46, 14);
		panelControl.add(lblAtaque2);
		
		JLabel lblDefensa2 = new JLabel("Defensa:");
		lblDefensa2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDefensa2.setBounds(618, 151, 65, 14);
		panelControl.add(lblDefensa2);
		
		JLabel lblDanyo2 = new JLabel("Daño:");
		lblDanyo2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDanyo2.setBounds(637, 176, 46, 14);
		panelControl.add(lblDanyo2);
		
		lblNumAtaque1 = new JLabel("");
		lblNumAtaque1.setBounds(550, 126, 46, 14);
		panelControl.add(lblNumAtaque1);
		
		lblNumDefensa1 = new JLabel("");
		lblNumDefensa1.setBounds(550, 151, 46, 14);
		panelControl.add(lblNumDefensa1);
		
		lblNumDanyo1 = new JLabel("");
		lblNumDanyo1.setBounds(550, 176, 46, 14);
		panelControl.add(lblNumDanyo1);
		
		lblNumAtaque2 = new JLabel("");
		lblNumAtaque2.setBounds(693, 128, 46, 14);
		panelControl.add(lblNumAtaque2);
		
		lblNumDefensa2 = new JLabel("");
		lblNumDefensa2.setBounds(693, 151, 46, 14);
		panelControl.add(lblNumDefensa2);
		
		lblNumDanyo2 = new JLabel("");
		lblNumDanyo2.setBounds(693, 176, 46, 14);
		panelControl.add(lblNumDanyo2);
		
		JLabel lblVentanaCombate = new JLabel("");
		lblVentanaCombate.setEnabled(false);
		lblVentanaCombate.setBounds(145, 11, 326, 179);
		panelControl.add(lblVentanaCombate);
		
		setDividerLocation(350);
		
		//Acciones
		btnAtacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				contAtaques++;
				numTotalAtaques++;
				
				//Contador de ataques de cada combate
				txtACombate.append("\nATAQUE "+contAtaques+"\n");
				
				//Personaje1 ataca a Personaje2
				txtACombate.append(" - "+amigo.getNombre()+" ataca a "+enemigo.getNombre()+"\n");
				enemigo.setVitalidad(enemigo.getVitalidad() - (amigo.getAtaque() - enemigo.getDefensa()));
				
				if(enemigo.getVitalidad() > 0) {
					txtACombate.append("   Vitalidad de "+enemigo.getNombre()+": "+enemigo.getVitalidad()+"\n");
					
					//Personaje2 ataca a Personaje1
					txtACombate.append(" - "+enemigo.getNombre()+" ataca a "+amigo.getNombre()+"\n");
					amigo.setVitalidad(amigo.getVitalidad() - (enemigo.getAtaque() - amigo.getDefensa()));
					
					if(amigo.getVitalidad() > 0) {
						txtACombate.append("   Vitalidad de "+amigo.getNombre()+": "+amigo.getVitalidad()+"\n");
						
						/**
						 * Dado las características del juego que al derrotar a un enemigo y pasar al siguiente, depende
						 * de un número de clicks distinto para cada enemigo puesto que las estadisticas se vuelven aleatoriamente a favor o en contra del jugador,
						 * sumando al uso de pociones, creo conveniente que según un número determinado de clicks de ataque (haciendo alusión a la experiencia
						 * y para simplificarlo) el personaje1 evolucione. La gracia está en que cuando pj1 recibe el golpe del enemigo evolucione para ser más fuerte
						 * dandole un poco más de ataque.
						 */
						
						if(amigo.evoluciona(numTotalAtaques)) {
							
							String nombreAnterior = amigo.getNombre();
							
							amigo.setNombre("Raichu");
							amigo.setAtaque(amigo.getAtaque()+AUMENTO_ATAQUE_AMIGO);
							
							ImageIcon imgAmigoEvolucionado = new ImageIcon(getClass().getResource("/img/amigo0Evolucionado.png"));   //Usa el nombre de fichero adecuado
							Icon icoAmigoEvolucionado = new ImageIcon(imgAmigoEvolucionado.getImage().getScaledInstance(lblAmigo.getWidth(), lblAmigo.getHeight(), Image.SCALE_SMOOTH));
							lblAmigo.setIcon(icoAmigoEvolucionado);
							
							ImageIcon imgAmigoEvolucionadoPixel = new ImageIcon(getClass().getResource("/img/amigo0EvolucionadoPixel.png"));   //Usa el nombre de fichero adecuado
							Icon icoAmigoEvolucionadoPixel = new ImageIcon(imgAmigoEvolucionadoPixel.getImage().getScaledInstance(lblAmigoPixel.getWidth(), lblAmigoPixel.getHeight(), Image.SCALE_SMOOTH));
							lblAmigoPixel.setIcon(icoAmigoEvolucionadoPixel);
							
							lblNombreAmigo.setText(amigo.getNombre());
							
							txtACombate.append("\n   "+nombreAnterior+" ha evolucionado a "+amigo.getNombre()+" !\n");
							txtACombate.append("   Ahora tienes +"+AUMENTO_ATAQUE_AMIGO+" de ataque\n");
							
							lblNumAtaque1.setText("" +amigo.getAtaque());
							lblNumDanyo1.setText("" +(amigo.getAtaque() - enemigo.getDefensa()));	
						}
					}
					else {
						txtACombate.append("   "+amigo.getNombre()+" no tiene vitalidad. Has Perdido :(\n");
						
						String[] opciones = {"Reintentar", "Salir"}; 
						ImageIcon imgDerrota = new ImageIcon(getClass().getResource("/img/triste.jpg"));
						int valorReinicio = JOptionPane.showOptionDialog(JFrameMenu.panelCardLayout, JPanelCreacionPersonaje.nombreUsuario+", tu "+amigo.getNombre()+" no pudo con la destreza de "+enemigo.getNombre()+" :(", "Derrota", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, imgDerrota, opciones, opciones[0]);
						
						/**
						 * Si el usuario le da a reintentar se reinician los componentes y el combate 
						 */
						if(valorReinicio == JOptionPane.YES_OPTION) {
							initCombate();
						}
						else {
							initCombate();
							JFrameMenu.cardLayout.show(JFrameMenu.panelCardLayout, "JPanelMenu");
							JFrameMenu.ventana.setBounds(new Rectangle(600, 300, 575, 385));
						}
					}
				}
				else {
					contEnemigo++;
					txtACombate.append("   "+enemigo.getNombre()+" ha caido !\n");
					
					if(contEnemigo < NUM_TOTAL_ENEMIGOS) {
						int aumentoVitalidad = calcularAumentoVitalidad(amigo, contEnemigo);
						amigo.setVitalidad(amigo.getVitalidad() + aumentoVitalidad);
						txtACombate.append("\n   "+amigo.getNombre()+" ha subido de vitalidad: "+amigo.getVitalidad()+" (+"+aumentoVitalidad+")\n");
						
						ImageIcon imgEnemigo = new ImageIcon(getClass().getResource("/img/enemigo"+contEnemigo+".png"));   //Usa el nombre de fichero adecuado
						Icon icoEnemigo = new ImageIcon(imgEnemigo.getImage().getScaledInstance(lblEnemigo.getWidth(), lblEnemigo.getHeight(), Image.SCALE_SMOOTH));
						lblEnemigo.setIcon(icoEnemigo);
						
						ImageIcon imgEnemigoPixel = new ImageIcon(getClass().getResource("/img/enemigo"+contEnemigo+"Pixel.png"));   //Usa el nombre de fichero adecuado
						Icon icoEnemigoPixel = new ImageIcon(imgEnemigoPixel.getImage().getScaledInstance(lblEnemigoPixel.getWidth(), lblEnemigoPixel.getHeight(), Image.SCALE_SMOOTH));
						lblEnemigoPixel.setIcon(icoEnemigoPixel);
						
						enemigo = new Personaje(nombresEnemigos[contEnemigo], VITALIDAD_BASE_ENEMIGO);
						lblNombreEnemigo.setText(enemigo.getNombre());
	
						generarEstadisticas(amigo, enemigo);
					}
					
					switch(contEnemigo){
					
						case 5:
							numPociones++;
							btnCurarse.setEnabled(true);
							txtACombate.append("   Has ganado +1 poción !\n");
							
							break;
							
						case 7:
							enemigo.setVitalidad(enemigo.getVitalidad()+AUMENTO_VITALIDAD_ENEMIGO7);
							
							txtACombate.append("   "+enemigo.getNombre()+" tiene "+enemigo.getVitalidad()+" de vitalidad\n");

							break;
							
						case 8:
							numPociones++;
							btnCurarse.setEnabled(true);
							txtACombate.append("   Has ganado +1 poción !\n");
							
							enemigo.setVitalidad(enemigo.getVitalidad()+AUMENTO_VITALIDAD_ENEMIGO8);
							enemigo.setAtaque(enemigo.getAtaque()+AUMENTO_ATAQUE_ENEMIGO8);
							
							txtACombate.append("\n   "+enemigo.getNombre()+" tiene "+enemigo.getVitalidad()+" de vitalidad y +"+AUMENTO_ATAQUE_ENEMIGO8+" de ataque\n");
							
							break;
							
						case 9:
							numPociones++;
							btnCurarse.setEnabled(true);
							txtACombate.append("   Has ganado +1 poción !\n");
							
							enemigo.setVitalidad(enemigo.getVitalidad()+AUMENTO_VITALIDAD_ENEMIGO9);
							enemigo.setAtaque(enemigo.getAtaque()+AUMENTO_ATAQUE_ENEMIGO9);
							
							txtACombate.append("\n   "+enemigo.getNombre()+" tiene "+enemigo.getVitalidad()+" de vitalidad y +"+AUMENTO_ATAQUE_ENEMIGO9+" de ataque\n");
							
							ImageIcon imgVsJefeFinal = new ImageIcon(getClass().getResource("/img/vsJefeFinal.png"));
							Icon icoVsJefeFinal = new ImageIcon(imgVsJefeFinal.getImage().getScaledInstance(lblVersus.getWidth(), lblVersus.getHeight(), Image.SCALE_SMOOTH));
							lblVersus.setIcon(icoVsJefeFinal);
							
							break;
							
						case 10: 
							String[] opciones = {"Reintentar", "Salir"}; 
							ImageIcon imgVictoria = new ImageIcon(getClass().getResource("/img/corona.png"));
							int valorReinicio = JOptionPane.showOptionDialog(JFrameMenu.panelCardLayout, "!Enhorabuena "+JPanelCreacionPersonaje.nombreUsuario+", tú y "+amigo.getNombre()+" habeis Ganado!", "Victoria", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, imgVictoria, opciones, opciones[0]);
							
							/**
							 * Lo hago de esa manera para que solo cuando el usuario le dé a reintentar se reinicie el fondo y si le da a salir a parte
							 * de volver a la pantalla de inicio tambien se reinicien los componentes para una nueva partida 
							 */
							if(valorReinicio == JOptionPane.YES_OPTION) {
								initCombate();
							}
							else {
								initCombate();
								JFrameMenu.cardLayout.show(JFrameMenu.panelCardLayout, "JPanelMenu");
								JFrameMenu.ventana.setBounds(new Rectangle(600, 300, 575, 385));
							}
					}
					mostrarEstadisticas(amigo, enemigo);
					contAtaques = 0; //Cuando se pase al siguiente enemigo el contador de ataques de esa partida vuelve a cero
				}
			}
		});
		
		btnCurarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(numPociones > 0) {
					numPociones--;
					
					amigo.setVitalidad(amigo.getVitalidad() + VITALIDAD_POCION);
					txtACombate.append("\n"+amigo.getNombre()+" se ha curado! Tienes "+numPociones+" restantes.\n");
					txtACombate.append("\n   Vitalidad de "+enemigo.getNombre()+": "+enemigo.getVitalidad()+"\n");
					txtACombate.append("   Vitalidad de "+amigo.getNombre()+": "+amigo.getVitalidad()+" (+"+VITALIDAD_POCION+")\n");
					
					if(numPociones == 0) {
						btnCurarse.setEnabled(false);
					}
				}
				
			}
		});
	}
}
