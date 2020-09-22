package MySystem;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import jade.gui.GuiEvent;

public class MainPrencipale extends Applet implements Runnable {
	Image imgCamera, imgFog, imgKey;
	Color ColorCam1 = Color.black, ColorCam2 = Color.black, ColorCam3 = Color.black, ColorCam4 = Color.black,
			ColorCam5 = Color.black, ColorCam6 = Color.black, ColorCam7 = Color.black, ColorCam8 = Color.black,
			ColorCam9 = Color.black, ColorCam10 = Color.black, ColorCam11 = Color.black, ColorCam12 = Color.black,
			ColorCam13 = Color.black, ColorCam14 = Color.black, ColorCam15 = Color.black, ColorCam16 = Color.black,
			ColorCam17 = Color.black, ColorCam18 = Color.black, ColorCam19 = Color.black, ColorCam20 = Color.black,
			ColorCam21 = Color.black, ColorCam22 = Color.black, ColorCam23 = Color.black, ColorCam24 = Color.black;
	Color ColorLabCam1 = Color.black, ColorLabCam2 = Color.black, ColorLabCam3 = Color.black,
			ColorLabCam4 = Color.black, ColorLabCam5 = Color.black, ColorLabCam6 = Color.black,
			ColorLabCam7 = Color.black, ColorLabCam8 = Color.black, ColorLabCam9 = Color.black,
			ColorLabCam10 = Color.black, ColorLabCam11 = Color.black, ColorLabCam12 = Color.black,
			ColorLabCam13 = Color.black, ColorLabCam14 = Color.black, ColorLabCam15 = Color.black,
			ColorLabCam16 = Color.black, ColorLabCam17 = Color.black, ColorLabCam18 = Color.black,
			ColorLabCam19 = Color.black, ColorLabCam20 = Color.black, ColorLabCam21 = Color.black,
			ColorLabCam22 = Color.black, ColorLabCam23 = Color.black, ColorLabCam24 = Color.black;
	Label LabCam1 = new Label(), LabCam2 = new Label(), LabCam3 = new Label(), LabCam4 = new Label(),
			LabCam5 = new Label(), LabCam6 = new Label(), LabCam7 = new Label(), LabCam8 = new Label(),
			LabCam9 = new Label(), LabCam10 = new Label(), LabCam11 = new Label(), LabCam12 = new Label(),
			LabCam13 = new Label(), LabCam14 = new Label(), LabCam15 = new Label(), LabCam16 = new Label(),
			LabCam17 = new Label(), LabCam18 = new Label(), LabCam19 = new Label(), LabCam20 = new Label(),
			LabCam21 = new Label(), LabCam22 = new Label(), LabCam23 = new Label(), LabCam24 = new Label();
	private String ObjCam1 = "", ObjCam2 = "", ObjCam3 = "", ObjCam4 = "", ObjCam5 = "", ObjCam6 = "", ObjCam7 = "",
			ObjCam8 = "", ObjCam9 = "", ObjCam10 = "", ObjCam11 = "", ObjCam12 = "", ObjCam13 = "", ObjCam14 = "",
			ObjCam15 = "", ObjCam16 = "", ObjCam17 = "", ObjCam18 = "", ObjCam19 = "", ObjCam20 = "", ObjCam21 = "",
			ObjCam22 = "", ObjCam23 = "", ObjCam24 = "";
	private String StateCam1 = "", StateCam2 = "", StateCam3 = "", StateCam4 = "", StateCam5 = "", StateCam6 = "",
			StateCam7 = "", StateCam8 = "", StateCam9 = "", StateCam10 = "", StateCam11 = "", StateCam12 = "",
			StateCam13 = "", StateCam14 = "", StateCam15 = "", StateCam16 = "", StateCam17 = "", StateCam18 = "",
			StateCam19 = "", StateCam20 = "", StateCam21 = "", StateCam22 = "", StateCam23 = "", StateCam24 = "";

	private LinkedList<Integer> Liste_Objet_Bloque_origi1 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi2 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi3 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi4 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi5 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi6 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi7 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi8 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi9 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi10 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi11 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi12 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi13 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi14 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi15 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi16 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi17 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi18 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi19 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi20 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi21 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi22 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi23 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_origi24 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer1 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer2 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer3 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer4 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer5 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer6 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer7 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer8 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer9 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer10 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer11 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer12 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer13 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer14 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer15 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer16 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer17 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer18 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer19 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer20 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer21 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer22 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer23 = new LinkedList<Integer>(),
			Liste_Objet_Bloque_estimer24 = new LinkedList<Integer>();

	int CordRect1[] = { 20, 200, 750, 530 };
	int CordRect2[] = { 780, 200, 750, 530 };
	Thread runner;
	Image Buffer;
	Graphics gBuffer;
	ArrayList<Objet> ListeObjet = new ArrayList();
	LinkedList<ObjetAgent> ListeObjetAgent = new LinkedList<ObjetAgent>();
	LinkedList<FogAgent> liste_Fog = new LinkedList<>();
	LinkedList<CameraAgent> Liste_camera = new LinkedList<>();
	GuiEvent gev;
	Color TablColor[] = { Color.blue, Color.yellow, Color.cyan, Color.green, Color.black, Color.red };
	Rectangle bounds;
	Label Nom_Object = new Label();
	//int nombre_objet_Max = 50;
	//int Id_Objet_Suivante = 6;
	int nombre_objet_Max = 50;
	int Id_Objet_Suivante = 5;
	int nombre_objet_detruit = 0;
	LinkedList<Long> Life_Time_Object = new LinkedList<Long>();
	LinkedList<Float> pourcentage_duree_suivi = new LinkedList<Float>();
	Map<Integer, Long> Temps_vie_Objet = new HashMap<>();
	int Objet_Suivi_80 = 0;
	int Objet_Suivi_20 = 0;
	PrintWriter Resultat = null;
	long Temps_debute_Execution = 0;
	long Temps_Fin_Execution = 0;
	boolean Liste_Objet_False_Positive_Fog1[] = new boolean[nombre_objet_Max];
	boolean Liste_Objet_False_Positive_Fog2[] = new boolean[nombre_objet_Max];

	/** Les cameras de la zone 01 **/
	int x1[] = { 20, 450, 320 };
	int y1[] = { 200, 210, 370 };

	int x2[] = { 20, 30, 250 };
	int y2[] = { 400, 720, 720 };

	int x3[] = { 20, 370, 370 };
	int y3[] = { 500, 380, 600 };

	int x4[] = { 20, 30, 250 };
	int y4[] = { 620, 240, 270 };

	int x5[] = { 430, 70, 150 };
	int y5[] = { 730, 650, 470 };

	int x6[] = { 500, 160, 320 };
	int y6[] = { 200, 350, 500 };

	int x7[] = { 520, 350, 550 };
	int y7[] = { 200, 500, 600 };

	int x8[] = { 750, 420, 520 };
	int y8[] = { 200, 350, 550 };

	int x9[] = { 770, 450, 680 };
	int y9[] = { 280, 580, 600 };

	int x10[] = { 770, 510, 730 };
	int y10[] = { 410, 720, 720 };

	int x11[] = { 770, 520, 765 };
	int y11[] = { 500, 205, 205 };

	int x12[] = { 770, 330, 530 };
	int y12[] = { 730, 630, 490 };

	/** Les cameras de la zone 02 **/
	int x13[] = { 780, 1210, 1080 };
	int y13[] = { 200, 210, 370 };

	int x14[] = { 780, 790, 1010 };
	int y14[] = { 400, 720, 720 };

	int x15[] = { 780, 1130, 1130 };
	int y15[] = { 500, 380, 600 };

	int x16[] = { 780, 790, 1010 };
	int y16[] = { 620, 240, 270 };

	int x17[] = { 1190, 850, 910 };
	int y17[] = { 730, 650, 470 };

	int x18[] = { 1260, 920, 1080 };
	int y18[] = { 200, 350, 500 };

	int x19[] = { 1280, 1110, 1310 };
	int y19[] = { 200, 500, 600 };

	int x20[] = { 1510, 1180, 1280 };
	int y20[] = { 200, 350, 550 };

	int x21[] = { 1530, 1210, 1440 };
	int y21[] = { 280, 580, 600 };

	int x22[] = { 1530, 1270, 1490 };
	int y22[] = { 410, 720, 720 };

	int x23[] = { 1530, 1280, 1525 };
	int y23[] = { 500, 205, 205 };

	int x24[] = { 1530, 1090, 1290 };
	int y24[] = { 730, 630, 490 };

	public MainPrencipale() {

		// Lance le MainContainer
		MainContainer.main(null);

		// Lance les agent de Fog Computing
		FogAgent fogAgent = new FogAgent("1", nombre_objet_Max, 2);
		fogAgent.runAgent();
		liste_Fog.add(fogAgent);

		FogAgent fogAgent2 = new FogAgent("2", nombre_objet_Max, 2);
		fogAgent2.runAgent();
		liste_Fog.add(fogAgent2);

		// Lancer les agents des cameras de la zone 01
		CameraAgent cameraAgent1 = new CameraAgent("1", x1, y1, 435, this, fogAgent);
		cameraAgent1.runAgent();
		Liste_camera.add(cameraAgent1);

		CameraAgent cameraAgent2 = new CameraAgent("2", x2, y2, 505, this, fogAgent);
		cameraAgent2.runAgent();
		Liste_camera.add(cameraAgent2);

		CameraAgent cameraAgent3 = new CameraAgent("3", x3, y3, 430, this, fogAgent);
		cameraAgent3.runAgent();
		Liste_camera.add(cameraAgent3);

		CameraAgent cameraAgent4 = new CameraAgent("4", x4, y4, 480, this, fogAgent);
		cameraAgent4.runAgent();
		Liste_camera.add(cameraAgent4);

		CameraAgent cameraAgent5 = new CameraAgent("5", x5, y5, 470, this, fogAgent);
		cameraAgent5.runAgent();
		Liste_camera.add(cameraAgent5);

		CameraAgent cameraAgent6 = new CameraAgent("6", x6, y6, 415, this, fogAgent);
		cameraAgent6.runAgent();
		Liste_camera.add(cameraAgent6);

		CameraAgent cameraAgent7 = new CameraAgent("7", x7, y7, 380, this, fogAgent);
		cameraAgent7.runAgent();
		Liste_camera.add(cameraAgent7);

		CameraAgent cameraAgent8 = new CameraAgent("8", x8, y8, 405, this, fogAgent);
		cameraAgent8.runAgent();
		Liste_camera.add(cameraAgent8);

		CameraAgent cameraAgent9 = new CameraAgent("9", x9, y9, 435, this, fogAgent);
		cameraAgent9.runAgent();
		Liste_camera.add(cameraAgent9);

		CameraAgent cameraAgent10 = new CameraAgent("10", x10, y10, 420, this, fogAgent);
		cameraAgent10.runAgent();
		Liste_camera.add(cameraAgent10);

		CameraAgent cameraAgent11 = new CameraAgent("11", x11, y11, 425, this, fogAgent);
		cameraAgent11.runAgent();
		Liste_camera.add(cameraAgent11);

		CameraAgent cameraAgent12 = new CameraAgent("12", x12, y12, 510, this, fogAgent);
		cameraAgent12.runAgent();
		Liste_camera.add(cameraAgent12);

		// Lancer les agents des cameras de la zone 02
		CameraAgent cameraAgent13 = new CameraAgent("13", x13, y13, 435, this, fogAgent2);
		cameraAgent13.runAgent();
		Liste_camera.add(cameraAgent13);

		CameraAgent cameraAgent14 = new CameraAgent("14", x14, y14, 505, this, fogAgent2);
		cameraAgent14.runAgent();
		Liste_camera.add(cameraAgent14);

		CameraAgent cameraAgent15 = new CameraAgent("15", x15, y15, 430, this, fogAgent2);
		cameraAgent15.runAgent();
		Liste_camera.add(cameraAgent15);

		CameraAgent cameraAgent16 = new CameraAgent("16", x16, y16, 485, this, fogAgent2);
		cameraAgent16.runAgent();
		Liste_camera.add(cameraAgent16);

		CameraAgent cameraAgent17 = new CameraAgent("17", x17, y17, 470, this, fogAgent2);
		cameraAgent17.runAgent();
		Liste_camera.add(cameraAgent17);

		CameraAgent cameraAgent18 = new CameraAgent("18", x18, y18, 440, this, fogAgent2);
		cameraAgent18.runAgent();
		Liste_camera.add(cameraAgent18);

		CameraAgent cameraAgent19 = new CameraAgent("19", x19, y19, 380, this, fogAgent2);
		cameraAgent19.runAgent();
		Liste_camera.add(cameraAgent19);

		CameraAgent cameraAgent20 = new CameraAgent("20", x20, y20, 430, this, fogAgent2);
		cameraAgent20.runAgent();
		Liste_camera.add(cameraAgent20);

		CameraAgent cameraAgent21 = new CameraAgent("21", x21, y21, 435, this, fogAgent2);
		cameraAgent21.runAgent();
		Liste_camera.add(cameraAgent21);

		CameraAgent cameraAgent22 = new CameraAgent("22", x22, y22, 420, this, fogAgent2);
		cameraAgent22.runAgent();
		Liste_camera.add(cameraAgent22);

		CameraAgent cameraAgent23 = new CameraAgent("23", x23, y23, 425, this, fogAgent2);
		cameraAgent23.runAgent();
		Liste_camera.add(cameraAgent23);

		CameraAgent cameraAgent24 = new CameraAgent("24", x24, y24, 540, this, fogAgent2);
		cameraAgent24.runAgent();
		Liste_camera.add(cameraAgent24);

	}

	public void calcule_temps_vie_objet(int ID_Objet, long Temps_mort) {
		// System.out.println("Objet " + ID_Objet + " => Life_Time " +
		// Life_Time_Object.get(ID_Objet - 1) + " Temps mort "
		// + Temps_mort);
		long Temps_depart = Life_Time_Object.get(ID_Objet - 1);
		Life_Time_Object.set((ID_Objet - 1), (Temps_mort - Temps_depart));
	}

	public void init() {
		Temps_debute_Execution = System.currentTimeMillis();
		setSize(2200, 750);
		Buffer = createImage(2200, 750);
		gBuffer = Buffer.getGraphics();
		for (int i = 1; i < Id_Objet_Suivante; i++) {
			int type_trajevtoire = Randome(9, 1);
			// int type_trajevtoire = 3;
			if (type_trajevtoire == 1) {
				int positinY = Randome(729, 201);
				ListeObjet.add(new Objet(i, 21, positinY, 5, TablColor[i % 5], liste_Fog, 1, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 2) {
				int positinY = Randome(729, 201);
				ListeObjet.add(new Objet(i, 1529, positinY, 5, TablColor[i % 5], liste_Fog, 2, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 3) {
				int positionX = Randome(1529, 21);
				ListeObjet.add(new Objet(i, positionX, 201, 5, TablColor[i % 5], liste_Fog, 3, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 4) {
				ListeObjet.add(new Objet(i, 21, 201, 5, TablColor[i % 5], liste_Fog, 4, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 5) {
				ListeObjet.add(new Objet(i, 21, 729, 5, TablColor[i % 5], liste_Fog, 5, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 6) {
				ListeObjet.add(new Objet(i, 1529, 729, 5, TablColor[i % 5], liste_Fog, 6, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 7) {
				ListeObjet.add(new Objet(i, 1529, 201, 5, TablColor[i % 5], liste_Fog, 7, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 8) {
				int positionX = Randome(1529, 21);
				ListeObjet.add(new Objet(i, positionX, 729, 5, TablColor[i % 5], liste_Fog, 8, this));
				Life_Time_Object.add((i - 1), System.currentTimeMillis());
			}
		}
	}

	public int Randome(int Max, int Min) {
		Random r = new Random();
		int valeur = Min + (r.nextInt(Max - Min));
		return valeur;
	}

	public void delete_objet(int id_objet) {
		for (int i = 0; i < ListeObjet.size(); i++) {
			if (ListeObjet.get(i).Id_Objet == id_objet) {
				ListeObjet.remove(i);
			}
		}
	}

	public void crer_objet() {
		if (Id_Objet_Suivante <= nombre_objet_Max) {
			nombre_objet_detruit++;
			// System.out.println("nombre des objets detruit ==> " + nombre_objet_detruit);
			int type_trajevtoire = Randome(9, 1);
			// int type_trajevtoire = 3;
			if (type_trajevtoire == 1) {
				int positinY = Randome(729, 201);
				ListeObjet.add(new Objet(Id_Objet_Suivante, 21, positinY, 5, TablColor[Id_Objet_Suivante % 5],
						liste_Fog, 1, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 2) {
				int positinY = Randome(729, 201);
				ListeObjet.add(new Objet(Id_Objet_Suivante, 1529, positinY, 5, TablColor[Id_Objet_Suivante % 5],
						liste_Fog, 2, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 3) {
				int positionX = Randome(1529, 21);
				ListeObjet.add(new Objet(Id_Objet_Suivante, positionX, 201, 5, TablColor[Id_Objet_Suivante % 5],
						liste_Fog, 3, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 4) {
				ListeObjet.add(
						new Objet(Id_Objet_Suivante, 21, 201, 5, TablColor[Id_Objet_Suivante % 5], liste_Fog, 4, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 5) {
				ListeObjet.add(
						new Objet(Id_Objet_Suivante, 21, 729, 5, TablColor[Id_Objet_Suivante % 5], liste_Fog, 5, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 6) {
				ListeObjet.add(new Objet(Id_Objet_Suivante, 1529, 729, 5, TablColor[Id_Objet_Suivante % 5], liste_Fog,
						6, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 7) {
				ListeObjet.add(new Objet(Id_Objet_Suivante, 1529, 201, 5, TablColor[Id_Objet_Suivante % 5], liste_Fog,
						7, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			} else if (type_trajevtoire == 8) {
				int positionX = Randome(1529, 21);
				ListeObjet.add(new Objet(Id_Objet_Suivante, positionX, 729, 5, TablColor[Id_Objet_Suivante % 5],
						liste_Fog, 8, this));
				Life_Time_Object.add((Id_Objet_Suivante - 1), System.currentTimeMillis());
			}
			Id_Objet_Suivante++;
		} else {
			nombre_objet_detruit++;

			if (nombre_objet_detruit == nombre_objet_Max) {
				Temps_Fin_Execution = System.currentTimeMillis();
				Affichage_Resultat();
			}
		}
	}

	public void Affichage_Resultat() {
		try {
			Resultat = new PrintWriter(new FileWriter("Resultat.txt", true));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// le programme est terminer
		long Temps_execution = Temps_Fin_Execution - Temps_debute_Execution;
		Resultat.println(" Le temps de l'experience est : " + Temps_execution);
		System.out.println(" ************************  l'executin est terminer  ************************");
		System.out.println("");
		System.out.println("");

		/*
		 * parcoure les Fog(s)
		 */
		int Somme_detection = 0, Somme_Nombre_Fragment = 0, Somme_False_Positive = 0, Somme_false_alarme = 0,
				Somme_Nombre_False_Nigative = 0;
		for (int i = 0; i < liste_Fog.size(); i++) {
			Somme_detection = Somme_detection + liste_Fog.get(i).Nombre_Request_ID_total;
			System.out.println("        ********************************************************************");
			System.out.println(
					"        **************************** < Fog " + (i + 1) + " > *****************************");
			System.out.println("        ********************************************************************");
			System.out.println("");

			/*
			 * Couper les données des listes dans les fichiers
			 */
			liste_Fog.get(i).Copier_Liste_Fichier();

			// System.out.println("Somme totat de detection est : " +
			// liste_Fog.get(i).Nombre_Request_ID_total);
			// System.out.println("");
			// System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			// System.out.println("");
			int x = 0;
			for (int j = 0; j < liste_Fog.get(i).Numbre_Fragmented_Objet.length; j++) {
				Somme_Nombre_Fragment = Somme_Nombre_Fragment + liste_Fog.get(i).Numbre_Fragmented_Objet[j];
				x = x + liste_Fog.get(i).Numbre_Fragmented_Objet[j];
			}
			System.out.println("Nmbre de fragmentation Fog " + (i + 1) + " ==> " + x);

			System.out.println("");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("");

			// System.out.println("nombre false possitive (case 1) dans le Fog " + (i + 1) +
			// " ==> "
			// + liste_Fog.get(i).Calcule_False_Positive_case_1());
			// Somme_False_Positive_C1 = Somme_False_Positive_C1 +
			// liste_Fog.get(i).Calcule_False_Positive_case_1();

			System.out.println("");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("");

			if (i == 0) { // fog 1
				Liste_Objet_False_Positive_Fog1 = liste_Fog.get(i).Calcule_False_Positive_case_2();
			} else if (i == 1) { // fog 2
				Liste_Objet_False_Positive_Fog2 = liste_Fog.get(i).Calcule_False_Positive_case_2();
			}

			System.out.println("");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("");

			Somme_Nombre_False_Nigative = Somme_Nombre_False_Nigative + liste_Fog.get(i).Nombre_Switche;
			System.out.println(
					"La somme total de nombre false Nigative (Nbr Switche) ==> " + liste_Fog.get(i).Nombre_Switche);

			System.out.println("");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("");

			System.out.println(
					"Nmbre de false Alarme dans le Fog " + (i + 1) + " ==> " + liste_Fog.get(i).Nombre_false_alarme);
			Somme_false_alarme = Somme_false_alarme + liste_Fog.get(i).Nombre_false_alarme;

			System.out.println("");
			System.out.println("");
		}

		for (int j = 0; j < Liste_Objet_False_Positive_Fog1.length; j++) {
			System.out.println(Liste_Objet_False_Positive_Fog1[j] + " -*- " + Liste_Objet_False_Positive_Fog2[j]);
			if (Liste_Objet_False_Positive_Fog1[j] == false && Liste_Objet_False_Positive_Fog2[j] == false) {
				Somme_False_Positive++;
			}
		}

		Resultat.println("False Alarme Total : " + Somme_false_alarme);
		Resultat.println("False Nigative (Nbr Switche) Totatl : " + Somme_Nombre_False_Nigative);
		Resultat.println("False Possitive (Case 2) Total : " + Somme_False_Positive);
		Resultat.println("Nombre de Fragmentation Total : " + Somme_Nombre_Fragment);

		/**
		 * parcoure les caméras
		 **/
		int Somme_Echouer = 0, Somme_Candidat = 0, Somme_Chef = 0, Somme_Assistant = 0;
		long Total_Echouer = 0, Total_Active = 0;
		for (int j = 0; j < Liste_camera.size(); j++) {
			// System.out.println("Camera " + (j + 1) + " Chef = " +
			// Liste_camera.get(j).Nomrbe_fois_chef
			// + " || Assistant = " + Liste_camera.get(j).Nombre_fois_assistant + " ||
			// Echouer = "
			// + Liste_camera.get(j).Nombre_fois_Echouer + " || Candidat = "
			// + Liste_camera.get(j).Nombre_fois_Candidat);
			Somme_Echouer = Somme_Echouer + Liste_camera.get(j).Nombre_fois_Echouer;
			Somme_Candidat = Somme_Candidat + Liste_camera.get(j).Nombre_fois_Candidat;
			Somme_Chef = Somme_Chef + Liste_camera.get(j).Nomrbe_fois_chef;
			Somme_Assistant = Somme_Assistant + Liste_camera.get(j).Nombre_fois_assistant;
			Total_Echouer = Total_Echouer + Liste_camera.get(j).Temps_Totale_Echouer;
			Total_Active = Total_Active + Liste_camera.get(j).Temps_Totale_Active;

			// System.out.println("Camera " + (j + 1) + " Temps Chef : " +
			// Liste_camera.get(j).Liste_Temps_Objet_Chef);
			// System.out.println(
			// "Camera " + (j + 1) + " Temps Assistant : " +
			// Liste_camera.get(j).Liste_Temps_Objet_Assistant);

			Fusion_temps_objet(Liste_camera.get(j).Liste_Temps_Objet_Chef);
		}
		System.out.println("");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("");

		System.out.println("Duree de suivi pour chaque objet");
		Resultat.println("Duree de suivi pour chaque objet");
		System.out.println(Temps_vie_Objet);
		Resultat.println(Temps_vie_Objet);

		System.out.println("");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("");

		System.out.println("Nombre de detection total : " + Somme_detection);
		Resultat.println("Nombre de detection total : " + Somme_detection);

		System.out.println("Nombre des caméras Chef : " + Somme_Chef);
		Resultat.println("Nombre des caméras Chef : " + Somme_Chef);

		System.out.println("Nombre des caméras Assistant : " + Somme_Assistant);
		Resultat.println("Nombre des caméras Assistant : " + Somme_Assistant);

		System.out.println("Nombre des caméras Echouer : " + Somme_Echouer);
		Resultat.println("Nombre des caméras Echouer : " + Somme_Echouer);

		System.out.println("Nombre des caméras Candidat : " + Somme_Candidat);
		Resultat.println("Nombre des caméras Candidat : " + Somme_Candidat);

		System.out.println("");
		float pourcentage_Camera = (float) (Somme_Echouer * 100) / Somme_Candidat;
		System.out.println("Pourcentage de redection (sur les caméras) : " + pourcentage_Camera + " %");
		Resultat.println("Pourcentage de redection (sur les caméras) : " + pourcentage_Camera + " %");

		System.out.println("");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("");

		System.out.println("** Durée de vie de chaque Objet **");
		Resultat.println("** Durée de vie de chaque Objet **");
		// initailisé la liste
		for (int i = 0; i < nombre_objet_Max; i++) {
			float val = 0;
			pourcentage_duree_suivi.add(val);
		}
		for (int j = 0; j < Life_Time_Object.size(); j++) {
			System.out.println("Temps de vie de l'Objet " + (j + 1) + " : " + Life_Time_Object.get(j) + " ms");
			Resultat.println("Temps de vie de l'Objet " + (j + 1) + " : " + Life_Time_Object.get(j) + " ms");
			int id_objet = j + 1;
			if (Temps_vie_Objet.containsKey(id_objet)) {
				float porcentage_suivi = (float) (Temps_vie_Objet.get(id_objet) * 100) / Life_Time_Object.get(j);
				pourcentage_duree_suivi.set(j, porcentage_suivi);
				System.out.println("Le pourcentage de suivi de l'Objet " + (j + 1) + " : " + porcentage_suivi + " %");
				Resultat.println("Le pourcentage de suivi de l'Objet " + (j + 1) + " : " + porcentage_suivi + " %");
				if (porcentage_suivi >= 80) {
					Objet_Suivi_80++;
				} else if (porcentage_suivi < 20) {
					Objet_Suivi_20++;
				}
			} else {
				float porcentage_suivi = 0;
				pourcentage_duree_suivi.set(j, porcentage_suivi);
				System.out.println("Le pourcentage de suivi de l'Objet " + (j + 1) + " : " + porcentage_suivi + " %");
				Resultat.println("Le pourcentage de suivi de l'Objet " + (j + 1) + " : " + porcentage_suivi + " %");
				Objet_Suivi_20++;
			}
			// pourcentage_duree_vie
		}
		System.out.println();
		System.out.println("Durée Totale Echouer (gagné) de toutes les caméras : " + Total_Echouer + " ms");
		Resultat.println("Durée Totale Echouer (gagné) de toutes les caméras : " + Total_Echouer + " ms");
		System.out.println();
		System.out.println("Durée Totale Active de toutes les caméras : " + Total_Active + " ms");
		Resultat.println("Durée Totale Active de toutes les caméras : " + Total_Active + " ms");
		System.out.println();
		float pourcentage_Temps = (float) (Total_Echouer * 100) / (Total_Echouer + Total_Active);
		System.out.println("Le pourcentage de la durée gagné est : " + pourcentage_Temps + " %");
		Resultat.println("Le pourcentage de la durée gagné est : " + pourcentage_Temps + " %");
		System.out.println();
		System.out.println("Nombre des objets qui suivi plus de 80% est : " + Objet_Suivi_80);
		Resultat.println("Nombre des objets qui suivi plus de 80% est : " + Objet_Suivi_80);
		System.out.println();
		System.out.println("Nombre des objets qui suivi plus de 20% est : " + Objet_Suivi_20);
		Resultat.println("Nombre des objets qui suivi plus de 20% est : " + Objet_Suivi_20);
		System.out.println("");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("");
		System.out.println(
				" ************************  NB: N'oublie pas de couper les fichiers produits par cette exécution. ************************");
		Resultat.close();
		impeEcran.screenShot(new Rectangle(0, 0, 1600, 900), new Dimension(1600, 900), "CapteurEcrant.png",
				impeEcran.IMAGE_TYPE_PNG);
		impeEcran.screenShot(new Rectangle(0, 0, 1600, 900), new Dimension(1600, 900), "CapteurEcrant1.png",
				impeEcran.IMAGE_TYPE_PNG);
		impeEcran.screenShot(new Rectangle(0, 0, 1600, 900), new Dimension(1600, 900), "CapteurEcrant2.png",
				impeEcran.IMAGE_TYPE_PNG);
		try {
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void Fusion_temps_objet(Map<Integer, Long> hm) {
		Set<Entry<Integer, Long>> setHm = hm.entrySet();
		Iterator<Entry<Integer, Long>> it = setHm.iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> e = it.next();
			// System.out.println(e.getKey() + " : " + e.getValue());
			if (Temps_vie_Objet.containsKey(e.getKey()) == true) {
				long Temps = Temps_vie_Objet.get(e.getKey());
				Temps = Temps + e.getValue();
				Temps_vie_Objet.put(e.getKey(), Temps);
			} else {
				Temps_vie_Objet.put(e.getKey(), e.getValue());
			}
		}

	}

	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	public void run() {
		while (true) {
			for (int i = 0; i < ListeObjet.size(); i++) {
				Objet e = ListeObjet.get(i);
				e.move(30);
				Nom_Object.setText("O".concat(String.valueOf(e.getId())));
				Nom_Object.setBounds(e.getX() - 20, e.getY() + 10, 30, 20);
				Nom_Object.setForeground(Color.black);
				Nom_Object.setFont(new Font("Serif", Font.BOLD, 12));
				this.add(Nom_Object);
				repaint();
			}
		}
	}

	public void paint(Graphics g) {
		for (Objet e : ListeObjet) {
			e.paint(gBuffer);
		}
		g.drawImage(Buffer, 0, 0, this);
		gBuffer.setColor(Color.BLACK);

		// Icone camera de la zone 01
		imgCamera = getImage(getCodeBase(), "Camera.jpg");

		// Les cameras zone (01)
		gBuffer.setColor(ColorCam1);
		gBuffer.drawPolygon(x1, y1, 3);
		LabCam1.setText("C1 => ".concat(ObjCam1) + " || Stt = " + StateCam1 + "||Og " + Liste_Objet_Bloque_origi1
				+ "||Es " + Liste_Objet_Bloque_estimer1);
		LabCam1.setBounds(20, 180, 230, 20);
		LabCam1.setForeground(ColorLabCam1);
		LabCam1.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam1);
		// gBuffer.drawImage(imgCamera, 50, 100, 20, 20, this);

		gBuffer.setColor(ColorCam2);
		gBuffer.drawPolygon(x2, y2, 3);
		LabCam2.setText("C2 => ".concat(ObjCam2) + " || Stt = " + StateCam2 + "||Og " + Liste_Objet_Bloque_origi2
				+ "||Es " + Liste_Objet_Bloque_estimer2);
		LabCam2.setBounds(0, 370, 230, 20);
		LabCam2.setForeground(ColorCam2);
		LabCam2.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam2);
		// gBuffer.drawImage(imgCamera, 30, 580, 20, 20, this);

		gBuffer.setColor(ColorCam3);
		gBuffer.drawPolygon(x3, y3, 3);
		LabCam3.setText("C3 => ".concat(ObjCam3) + " || Stt = " + StateCam3 + "||Og " + Liste_Objet_Bloque_origi3
				+ "||Es " + Liste_Objet_Bloque_estimer3);
		LabCam3.setBounds(0, 470, 230, 20);
		LabCam3.setForeground(ColorCam3);
		LabCam3.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam3);
		// gBuffer.drawImage(imgCamera, 250, 80, 20, 20, this);

		gBuffer.setColor(ColorCam4);
		gBuffer.drawPolygon(x4, y4, 3);
		LabCam4.setText("C4 => ".concat(ObjCam4) + " || Stt = " + StateCam4 + "||Og " + Liste_Objet_Bloque_origi4
				+ "||Es " + Liste_Objet_Bloque_estimer4);
		LabCam4.setBounds(0, 620, 230, 20);
		LabCam4.setForeground(ColorCam4);
		LabCam4.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam4);
		// gBuffer.drawImage(imgCamera, 240, 580, 20, 20, this);

		gBuffer.setColor(ColorCam5);
		gBuffer.drawPolygon(x5, y5, 3);
		LabCam5.setText("C5 => ".concat(ObjCam5) + " || Stt = " + StateCam5 + "||Og " + Liste_Objet_Bloque_origi5
				+ "||Es " + Liste_Objet_Bloque_estimer5);
		LabCam5.setBounds(400, 735, 230, 20);
		LabCam5.setForeground(ColorCam5);
		LabCam5.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam5);
		// gBuffer.drawImage(imgCamera, 550, 100, 20, 20, this);

		gBuffer.setColor(ColorCam6);
		gBuffer.drawPolygon(x6, y6, 3);
		LabCam6.setText("C6 => ".concat(ObjCam6) + " || Stt = " + StateCam6 + "||Og " + Liste_Objet_Bloque_origi6
				+ "||Es " + Liste_Objet_Bloque_estimer6);
		LabCam6.setBounds(350, 180, 230, 20);
		LabCam6.setForeground(ColorCam6);
		LabCam6.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam6);
		// gBuffer.drawImage(imgCamera, 750, 100, 20, 20, this);

		gBuffer.setColor(ColorCam7);
		gBuffer.drawPolygon(x7, y7, 3);
		LabCam7.setText("C7 => ".concat(ObjCam7) + " || Stt = " + StateCam7 + "||Og " + Liste_Objet_Bloque_origi7
				+ "||Es " + Liste_Objet_Bloque_estimer7);
		LabCam7.setBounds(500, 160, 230, 20);
		LabCam7.setForeground(ColorCam7);
		LabCam7.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam7);
		// gBuffer.drawImage(imgCamera, 730, 580, 20, 20, this);

		gBuffer.setColor(ColorCam8);
		gBuffer.drawPolygon(x8, y8, 3);
		LabCam8.setText("C8 => ".concat(ObjCam8) + " || Stt = " + StateCam8 + "||Og " + Liste_Objet_Bloque_origi8
				+ "||Es " + Liste_Objet_Bloque_estimer8);
		LabCam8.setBounds(640, 180, 230, 20);
		LabCam8.setForeground(ColorCam8);
		LabCam8.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam8);
		// gBuffer.drawImage(imgCamera, 950, 80, 20, 20, this);

		gBuffer.setColor(ColorCam9);
		gBuffer.drawPolygon(x9, y9, 3);
		LabCam9.setText("C9 => ".concat(ObjCam9) + " || Stt = " + StateCam9 + "||Og " + Liste_Objet_Bloque_origi9
				+ "||Es " + Liste_Objet_Bloque_estimer9);
		LabCam9.setBounds(720, 260, 230, 20);
		LabCam9.setForeground(ColorCam9);
		LabCam9.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam9);
		// gBuffer.drawImage(imgCamera, 940, 580, 20, 20, this);

		gBuffer.setColor(ColorCam10);
		gBuffer.drawPolygon(x10, y10, 3);
		LabCam10.setText("C10 => ".concat(ObjCam10) + " || Stt = " + StateCam10 + "||Og " + Liste_Objet_Bloque_origi10
				+ "||Es " + Liste_Objet_Bloque_estimer10);
		LabCam10.setBounds(720, 390, 230, 20);
		LabCam10.setForeground(ColorCam10);
		LabCam10.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam10);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam11);
		gBuffer.drawPolygon(x11, y11, 3);
		LabCam11.setText("C11 => ".concat(ObjCam11) + " || Stt = " + StateCam11 + "||Og " + Liste_Objet_Bloque_origi11
				+ "||Es " + Liste_Objet_Bloque_estimer11);
		LabCam11.setBounds(720, 505, 230, 20);
		LabCam11.setForeground(ColorCam11);
		LabCam11.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam11);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam12);
		gBuffer.drawPolygon(x12, y12, 3);
		LabCam12.setText("C12 => ".concat(ObjCam12) + " || Stt = " + StateCam12 + "||Og " + Liste_Objet_Bloque_origi12
				+ "||Es " + Liste_Objet_Bloque_estimer12);
		LabCam12.setBounds(720, 735, 230, 20);
		LabCam12.setForeground(ColorCam12);
		LabCam12.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam12);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		// Les cameras zone (02)

		gBuffer.setColor(ColorCam13);
		gBuffer.drawPolygon(x13, y13, 3);
		LabCam13.setText("C13 => ".concat(ObjCam13) + " || Stt = " + StateCam13 + "||Og " + Liste_Objet_Bloque_origi13
				+ "||Es " + Liste_Objet_Bloque_estimer13);
		LabCam13.setBounds(820, 180, 230, 20);
		LabCam13.setForeground(ColorCam13);
		LabCam13.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam13);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam14);
		gBuffer.drawPolygon(x14, y14, 3);
		LabCam14.setText("C14 => ".concat(ObjCam14) + " || Stt = " + StateCam14 + "||Og " + Liste_Objet_Bloque_origi14
				+ "||Es " + Liste_Objet_Bloque_estimer14);
		LabCam14.setBounds(760, 370, 230, 20);
		LabCam14.setForeground(ColorCam14);
		LabCam14.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam14);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam15);
		gBuffer.drawPolygon(x15, y15, 3);
		LabCam15.setText("C15 => ".concat(ObjCam15) + " || Stt = " + StateCam15 + "||Og " + Liste_Objet_Bloque_origi15
				+ "||Es " + Liste_Objet_Bloque_estimer15);
		LabCam15.setBounds(760, 470, 230, 20);
		LabCam15.setForeground(ColorCam15);
		LabCam15.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam15);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam16);
		gBuffer.drawPolygon(x16, y16, 3);
		LabCam16.setText("C16 => ".concat(ObjCam16) + " || Stt = " + StateCam16 + "||Og " + Liste_Objet_Bloque_origi16
				+ "||Es " + Liste_Objet_Bloque_estimer16);
		LabCam16.setBounds(760, 620, 230, 20);
		LabCam16.setForeground(ColorCam16);
		LabCam16.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam16);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam17);
		gBuffer.drawPolygon(x17, y17, 3);
		LabCam17.setText("C17 => ".concat(ObjCam17) + " || Stt = " + StateCam17 + "||Og " + Liste_Objet_Bloque_origi17
				+ "||Es " + Liste_Objet_Bloque_estimer17);
		LabCam17.setBounds(1160, 735, 230, 20);
		LabCam17.setForeground(ColorCam17);
		LabCam17.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam17);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam18);
		gBuffer.drawPolygon(x18, y18, 3);
		LabCam18.setText("C18 => ".concat(ObjCam18) + " || Stt = " + StateCam18 + "||Og " + Liste_Objet_Bloque_origi18
				+ "||Es " + Liste_Objet_Bloque_estimer18);
		LabCam18.setBounds(1110, 180, 230, 20);
		LabCam18.setForeground(ColorCam18);
		LabCam18.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam18);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam19);
		gBuffer.drawPolygon(x19, y19, 3);
		LabCam19.setText("C19 => ".concat(ObjCam19) + " || Stt = " + StateCam19 + "||Og " + Liste_Objet_Bloque_origi19
				+ "||Es " + Liste_Objet_Bloque_estimer19);
		LabCam19.setBounds(1260, 160, 230, 20);
		LabCam19.setForeground(ColorCam19);
		LabCam19.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam19);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam20);
		gBuffer.drawPolygon(x20, y20, 3);
		LabCam20.setText("C20 => ".concat(ObjCam20) + " || Stt = " + StateCam20 + "||Og " + Liste_Objet_Bloque_origi20
				+ "||Es " + Liste_Objet_Bloque_estimer20);
		LabCam20.setBounds(1430, 180, 230, 20);
		LabCam20.setForeground(ColorCam20);
		LabCam20.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam20);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam21);
		gBuffer.drawPolygon(x21, y21, 3);
		LabCam21.setText("C21 => ".concat(ObjCam21) + " || Stt = " + StateCam21 + "||Og " + Liste_Objet_Bloque_origi21
				+ "||Es " + Liste_Objet_Bloque_estimer21);
		LabCam21.setBounds(1480, 260, 230, 20);
		LabCam21.setForeground(ColorCam21);
		LabCam21.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam21);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam22);
		gBuffer.drawPolygon(x22, y22, 3);
		LabCam22.setText("C22 => ".concat(ObjCam22) + " || Stt = " + StateCam22 + "||Og " + Liste_Objet_Bloque_origi22
				+ "||Es " + Liste_Objet_Bloque_estimer22);
		LabCam22.setBounds(1480, 505, 230, 20);
		LabCam22.setForeground(ColorCam22);
		LabCam22.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam22);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam23);
		gBuffer.drawPolygon(x23, y23, 3);
		LabCam23.setText("C23 => ".concat(ObjCam23) + " || Stt = " + StateCam23 + "||Og " + Liste_Objet_Bloque_origi23
				+ "||Es " + Liste_Objet_Bloque_estimer23);
		LabCam23.setBounds(1480, 505, 230, 20);
		LabCam23.setForeground(ColorCam23);
		LabCam23.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam23);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		gBuffer.setColor(ColorCam24);
		gBuffer.drawPolygon(x24, y24, 3);
		LabCam24.setText("C24 => ".concat(ObjCam24) + " || Stt = " + StateCam24 + "||Og " + Liste_Objet_Bloque_origi24
				+ "||Es " + Liste_Objet_Bloque_estimer24);
		LabCam24.setBounds(1480, 735, 230, 20);
		LabCam24.setForeground(ColorCam24);
		LabCam24.setFont(new Font("Serif", Font.BOLD, 13));
		add(LabCam24);
		// gBuffer.drawImage(imgCamera, 1250, 100, 20, 20, this);

		// Les zones
		gBuffer.setColor(Color.black);
		gBuffer.drawRect(CordRect1[0], CordRect1[1], CordRect1[2], CordRect1[3]);
		gBuffer.drawRect(CordRect2[0], CordRect2[1], CordRect2[2], CordRect2[3]);

		// Icone Fog
		imgFog = getImage(getCodeBase(), "Fog.png");
		gBuffer.drawImage(imgFog, 300, 1, 70, 65, this);
		gBuffer.drawImage(imgFog, 1000, 1, 70, 65, this);

	}

	public void setColorLabCam1(Color colorLabCam1) {
		ColorLabCam1 = colorLabCam1;
	}

	public void setColorLabCam2(Color colorLabCam2) {
		ColorLabCam2 = colorLabCam2;
	}

	public void setColorLabCam3(Color colorLabCam3) {
		ColorLabCam3 = colorLabCam3;
	}

	public void setColorLabCam4(Color colorLabCam4) {
		ColorLabCam4 = colorLabCam4;
	}

	public void setColorLabCam5(Color colorLabCam5) {
		ColorLabCam5 = colorLabCam5;
	}

	public void setColorLabCam6(Color colorLabCam6) {
		ColorLabCam6 = colorLabCam6;
	}

	public void setColorLabCam7(Color colorLabCam7) {
		ColorLabCam7 = colorLabCam7;
	}

	public void setColorLabCam8(Color colorLabCam8) {
		ColorLabCam8 = colorLabCam8;
	}

	public void setColorLabCam9(Color colorLabCam9) {
		ColorLabCam9 = colorLabCam9;
	}

	public void setColorLabCam10(Color colorLabCam10) {
		ColorLabCam10 = colorLabCam10;
	}

	public void setColorLabCam11(Color colorLabCam11) {
		ColorLabCam11 = colorLabCam11;
	}

	public void setColorLabCam12(Color colorLabCam12) {
		ColorLabCam12 = colorLabCam12;
	}

	public void setColorLabCam13(Color colorLabCam13) {
		ColorLabCam13 = colorLabCam13;
	}

	public void setColorLabCam14(Color colorLabCam14) {
		ColorLabCam14 = colorLabCam14;
	}

	public void setColorLabCam15(Color colorLabCam15) {
		ColorLabCam15 = colorLabCam15;
	}

	public void setColorLabCam16(Color colorLabCam16) {
		ColorLabCam16 = colorLabCam16;
	}

	public void setColorLabCam17(Color colorLabCam17) {
		ColorLabCam17 = colorLabCam17;
	}

	public void setColorLabCam18(Color colorLabCam18) {
		ColorLabCam18 = colorLabCam18;
	}

	public void setColorLabCam19(Color colorLabCam19) {
		ColorLabCam19 = colorLabCam19;
	}

	public void setColorLabCam20(Color colorLabCam20) {
		ColorLabCam20 = colorLabCam20;
	}

	public void setColorLabCam21(Color colorLabCam21) {
		ColorLabCam21 = colorLabCam21;
	}

	public void setColorLabCam22(Color colorLabCam22) {
		ColorLabCam22 = colorLabCam22;
	}

	public void setColorLabCam23(Color colorLabCam23) {
		ColorLabCam23 = colorLabCam23;
	}

	public void setColorLabCam24(Color colorLabCam24) {
		ColorLabCam24 = colorLabCam24;
	}

	public void setColorCam1(Color colorCam1) {
		ColorCam1 = colorCam1;
	}

	public void setColorCam2(Color colorCam2) {
		ColorCam2 = colorCam2;
	}

	public void setColorCam3(Color colorCam3) {
		ColorCam3 = colorCam3;
	}

	public void setColorCam4(Color colorCam4) {
		ColorCam4 = colorCam4;
	}

	public void setColorCam5(Color colorCam5) {
		ColorCam5 = colorCam5;
	}

	public void setColorCam6(Color colorCam6) {
		ColorCam6 = colorCam6;
	}

	public void setColorCam7(Color colorCam7) {
		ColorCam7 = colorCam7;
	}

	public void setColorCam8(Color colorCam8) {
		ColorCam8 = colorCam8;
	}

	public void setColorCam9(Color colorCam9) {
		ColorCam9 = colorCam9;
	}

	public void setColorCam10(Color colorCam10) {
		ColorCam10 = colorCam10;
	}

	public void setColorCam11(Color colorCam11) {
		ColorCam11 = colorCam11;
	}

	public void setColorCam12(Color colorCam12) {
		ColorCam12 = colorCam12;
	}

	public void setColorCam13(Color colorCam13) {
		ColorCam13 = colorCam13;
	}

	public void setColorCam14(Color colorCam14) {
		ColorCam14 = colorCam14;
	}

	public void setColorCam15(Color colorCam15) {
		ColorCam15 = colorCam15;
	}

	public void setColorCam16(Color colorCam16) {
		ColorCam16 = colorCam16;
	}

	public void setColorCam17(Color colorCam17) {
		ColorCam17 = colorCam17;
	}

	public void setColorCam18(Color colorCam18) {
		ColorCam18 = colorCam18;
	}

	public void setColorCam19(Color colorCam19) {
		ColorCam19 = colorCam19;
	}

	public void setColorCam20(Color colorCam20) {
		ColorCam20 = colorCam20;
	}

	public void setColorCam21(Color colorCam21) {
		ColorCam21 = colorCam21;
	}

	public void setColorCam22(Color colorCam22) {
		ColorCam22 = colorCam22;
	}

	public void setColorCam23(Color colorCam23) {
		ColorCam23 = colorCam23;
	}

	public void setColorCam24(Color colorCam24) {
		ColorCam24 = colorCam24;
	}

	public void setObjCam1(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam1 = objCam;
		StateCam1 = State;
		Liste_Objet_Bloque_origi1 = list_objet_bloc;
		Liste_Objet_Bloque_estimer1 = list_objet_bloc_estim;
	}

	public void setObjCam2(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam2 = objCam;
		StateCam2 = State;
		Liste_Objet_Bloque_origi2 = list_objet_bloc;
		Liste_Objet_Bloque_estimer2 = list_objet_bloc_estim;
	}

	public void setObjCam3(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam3 = objCam;
		StateCam3 = State;
		Liste_Objet_Bloque_origi3 = list_objet_bloc;
		Liste_Objet_Bloque_estimer3 = list_objet_bloc_estim;
	}

	public void setObjCam4(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam4 = objCam;
		StateCam4 = State;
		Liste_Objet_Bloque_origi4 = list_objet_bloc;
		Liste_Objet_Bloque_estimer4 = list_objet_bloc_estim;
	}

	public void setObjCam5(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam5 = objCam;
		StateCam5 = State;
		Liste_Objet_Bloque_origi5 = list_objet_bloc;
		Liste_Objet_Bloque_estimer5 = list_objet_bloc_estim;
	}

	public void setObjCam6(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam6 = objCam;
		StateCam6 = State;
		Liste_Objet_Bloque_origi6 = list_objet_bloc;
		Liste_Objet_Bloque_estimer6 = list_objet_bloc_estim;
	}

	public void setObjCam7(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam7 = objCam;
		StateCam7 = State;
		Liste_Objet_Bloque_origi7 = list_objet_bloc;
		Liste_Objet_Bloque_estimer7 = list_objet_bloc_estim;
	}

	public void setObjCam8(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam8 = objCam;
		StateCam8 = State;
		Liste_Objet_Bloque_origi8 = list_objet_bloc;
		Liste_Objet_Bloque_estimer8 = list_objet_bloc_estim;
	}

	public void setObjCam9(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam9 = objCam;
		StateCam9 = State;
		Liste_Objet_Bloque_origi9 = list_objet_bloc;
		Liste_Objet_Bloque_estimer9 = list_objet_bloc_estim;
	}

	public void setObjCam10(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam10 = objCam;
		StateCam10 = State;
		Liste_Objet_Bloque_origi10 = list_objet_bloc;
		Liste_Objet_Bloque_estimer10 = list_objet_bloc_estim;
	}

	public void setObjCam11(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam11 = objCam;
		StateCam11 = State;
		Liste_Objet_Bloque_origi11 = list_objet_bloc;
		Liste_Objet_Bloque_estimer11 = list_objet_bloc_estim;
	}

	public void setObjCam12(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam12 = objCam;
		StateCam12 = State;
		Liste_Objet_Bloque_origi12 = list_objet_bloc;
		Liste_Objet_Bloque_estimer12 = list_objet_bloc_estim;
	}

	public void setObjCam13(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam13 = objCam;
		StateCam13 = State;
		Liste_Objet_Bloque_origi13 = list_objet_bloc;
		Liste_Objet_Bloque_estimer13 = list_objet_bloc_estim;
	}

	public void setObjCam14(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam14 = objCam;
		StateCam14 = State;
		Liste_Objet_Bloque_origi14 = list_objet_bloc;
		Liste_Objet_Bloque_estimer14 = list_objet_bloc_estim;
	}

	public void setObjCam15(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam15 = objCam;
		StateCam15 = State;
		Liste_Objet_Bloque_origi15 = list_objet_bloc;
		Liste_Objet_Bloque_estimer15 = list_objet_bloc_estim;
	}

	public void setObjCam16(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam16 = objCam;
		StateCam16 = State;
		Liste_Objet_Bloque_origi16 = list_objet_bloc;
		Liste_Objet_Bloque_estimer16 = list_objet_bloc_estim;
	}

	public void setObjCam17(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam17 = objCam;
		StateCam17 = State;
		Liste_Objet_Bloque_origi17 = list_objet_bloc;
		Liste_Objet_Bloque_estimer17 = list_objet_bloc_estim;
	}

	public void setObjCam18(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam18 = objCam;
		StateCam18 = State;
		Liste_Objet_Bloque_origi18 = list_objet_bloc;
		Liste_Objet_Bloque_estimer18 = list_objet_bloc_estim;
	}

	public void setObjCam19(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam19 = objCam;
		StateCam19 = State;
		Liste_Objet_Bloque_origi19 = list_objet_bloc;
		Liste_Objet_Bloque_estimer19 = list_objet_bloc_estim;
	}

	public void setObjCam20(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam20 = objCam;
		StateCam20 = State;
		Liste_Objet_Bloque_origi20 = list_objet_bloc;
		Liste_Objet_Bloque_estimer20 = list_objet_bloc_estim;
	}

	public void setObjCam21(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam21 = objCam;
		StateCam21 = State;
		Liste_Objet_Bloque_origi21 = list_objet_bloc;
		Liste_Objet_Bloque_estimer21 = list_objet_bloc_estim;
	}

	public void setObjCam22(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam22 = objCam;
		StateCam22 = State;
		Liste_Objet_Bloque_origi22 = list_objet_bloc;
		Liste_Objet_Bloque_estimer22 = list_objet_bloc_estim;
	}

	public void setObjCam23(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam23 = objCam;
		StateCam23 = State;
		Liste_Objet_Bloque_origi23 = list_objet_bloc;
		Liste_Objet_Bloque_estimer23 = list_objet_bloc_estim;
	}

	public void setObjCam24(String objCam, String State, LinkedList<Integer> list_objet_bloc,
			LinkedList<Integer> list_objet_bloc_estim) {
		ObjCam24 = objCam;
		StateCam24 = State;
		Liste_Objet_Bloque_origi24 = list_objet_bloc;
		Liste_Objet_Bloque_estimer24 = list_objet_bloc_estim;
	}
}