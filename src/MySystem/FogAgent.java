package MySystem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class FogAgent extends Agent {
	String id;
	double distance_Max = 806.4;
	double distance_Min = 0;
	double seuil_distance = 0.2;
	String[] Groupe11 = { "Cam_1", "Cam_2", "Cam_3", "Cam_4", "Cam_5", "Cam_6" };
	String[] Groupe12 = { "Cam_7", "Cam_8", "Cam_9", "Cam_10", "Cam_11", "Cam_12" };
	String[] Groupe21 = { "Cam_13", "Cam_14", "Cam_15", "Cam_16", "Cam_17", "Cam_18" };
	String[] Groupe22 = { "Cam_19", "Cam_20", "Cam_21", "Cam_22", "Cam_23", "Cam_24" };
	static int id_objet_suivant;
	int Nombre_Fog;
	int Nmbre_Objet_Max;
	LinkedList Liste_Objet_Suivi_Actuel = new LinkedList();
	LinkedList Liste_Objet_Suivi_Historique = new LinkedList();
	LinkedList Liste_Objet_Recu = new LinkedList();
	int Nombre_Switche = 0;
	int Numbre_Fragmented_Objet[];
	int Nombre_Request_ID_total = 0;
	int Nombre_false_alarme = 0;
	boolean Liste_Objet[];
	boolean section_creteque = true;

	public void Copier_Liste_Fichier() {
		PrintWriter Suivi_Actuel = null;
		PrintWriter Suivi_Historique = null;
		PrintWriter Recu = null;
		try {
			Suivi_Actuel = new PrintWriter(new FileWriter("Liste_Objet_Suivi_Actuel_Fog_" + id + ".txt", true));
			Suivi_Historique = new PrintWriter(new FileWriter("Liste_Objet_Suivi_Historique_Fog_" + id + ".txt", true));
			Recu = new PrintWriter(new FileWriter("Liste_Objet_Recu_Fog_" + id + ".txt", true));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < Liste_Objet_Suivi_Actuel.size(); i++) {
			// Suivi_Actuel.println(Liste_Objet_Suivi_Actuel.get(i));
			LinkedList ListeTempo = new LinkedList();
			ListeTempo = (LinkedList) Liste_Objet_Suivi_Actuel.get(i);
			for (int j = 0; j < ListeTempo.size(); j++) {
				Suivi_Actuel.print(ListeTempo.get(j) + " ");
			}
			Suivi_Actuel.print("\n");
		}
		Suivi_Actuel.close();

		for (int i = 0; i < Liste_Objet_Suivi_Historique.size(); i++) {
			// Suivi_Historique.println(Liste_Objet_Suivi_Historique.get(i));
			LinkedList ListeTempo = new LinkedList();
			ListeTempo = (LinkedList) Liste_Objet_Suivi_Historique.get(i);
			for (int j = 0; j < ListeTempo.size(); j++) {
				Suivi_Historique.print(ListeTempo.get(j) + " ");
			}
			Suivi_Historique.print("\n");
		}
		Suivi_Historique.close();

		for (int i = 0; i < Liste_Objet_Recu.size(); i++) {
			// Recu.println(Liste_Objet_Recu.get(i));
			LinkedList ListeTempo = new LinkedList();
			ListeTempo = (LinkedList) Liste_Objet_Recu.get(i);
			for (int j = 0; j < ListeTempo.size(); j++) {
				Recu.print(ListeTempo.get(j) + " ");
			}
			Recu.print("\n");
		}
		Recu.close();
	}

	public FogAgent(String id, int nombre_Objet, int nombre_Fog) {
		this.id = id;
		id_objet_suivant = nombre_Objet;
		this.Nombre_Fog = nombre_Fog;
		this.Nmbre_Objet_Max = nombre_Objet;
	}

	public boolean[] Calcule_False_Positive_case_2() {
		int nbr_F_P = 0;
		boolean Liste_Objet[] = new boolean[Nmbre_Objet_Max];
		// initialiser le tableau par false
		for (int i = 0; i < Liste_Objet.length; i++) {
			Liste_Objet[i] = false;
		}

		for (int i = 0; i < Liste_Objet_Suivi_Historique.size(); i++) {
			LinkedList vecteur_objet = (LinkedList) Liste_Objet_Suivi_Historique.get(i);
			int id_objet_suivi_estimer = (int) vecteur_objet.get(1);
			int id_objet_suivi_origi = (int) vecteur_objet.get(0);
			//System.out.println(id_objet_suivi_estimer + " -**- " + id_objet_suivi_origi);
			if (id_objet_suivi_estimer == id_objet_suivi_origi) {
				Liste_Objet[id_objet_suivi_origi - 1] = true;
				//System.out.println(id_objet_suivi_origi + " -**- " + Liste_Objet[id_objet_suivi_origi - 1]);
			}
		}

		// Calcule le nombre des elements qui possede la valeur False
		/*
		 * => False : l'objet n'a pas suivi
		 * 
		 * => True : l'objet est suivi ou moin par un caméra
		 */
		// for (int i = 0; i < Liste_Objet.length; i++) {
		// if (Liste_Objet[i] == false) {
		// nbr_F_P++;
		// }
		// }
		// for (int i = 0; i < Liste_Objet.length; i++) {
		// System.out.println("Fog " + id + " : " + Liste_Objet[i]);
		// }
		// return nbr_F_P;
		return Liste_Objet;
	}

	public void add_Vecteur(LinkedList vecteur, int type) {
		if (type == 1) {
			Liste_Objet_Recu.add(vecteur);
		}
		// else if (type == 2) {
		// Liste_Objet_Suivi_Historique.add(vecteur);
		// System.out.println("Fog "+id+" : 1) Liste O S Historique : ajuter le vecteur
		// ==> "+vecteur);
		// }

		// System.out.println("Fog_" + id + ": (add man 3and l'objet ) la liste des
		// objets suivi histrique " + Liste_Objet_Suivi_Historique);
	}

	public LinkedList<String> Get_neighbors(String camera) {
		LinkedList<String> neighbors = new LinkedList<String>();
		if (camera.equals("Cam_1") || camera.equals("Cam_2") || camera.equals("Cam_3") || camera.equals("Cam_4")
				|| camera.equals("Cam_5") || camera.equals("Cam_6")) {
			for (int i = 0; i < Groupe11.length; i++) {
				if (Groupe11[i].equals(camera) == false) {
					neighbors.add(Groupe11[i]);
				}
			}
		} else if (camera.equals("Cam_7") || camera.equals("Cam_8") || camera.equals("Cam_9") || camera.equals("Cam_10")
				|| camera.equals("Cam_11") || camera.equals("Cam_12")) {
			for (int i = 0; i < Groupe12.length; i++) {
				if (Groupe12[i].equals(camera) == false) {
					neighbors.add(Groupe12[i]);
				}
			}
		}
		if (camera.equals("Cam_13") || camera.equals("Cam_14") || camera.equals("Cam_15") || camera.equals("Cam_16")
				|| camera.equals("Cam_17") || camera.equals("Cam_18")) {
			for (int i = 0; i < Groupe21.length; i++) {
				if (Groupe21[i].equals(camera) == false) {
					neighbors.add(Groupe21[i]);
				}
			}
		} else if (camera.equals("Cam_19") || camera.equals("Cam_20") || camera.equals("Cam_21")
				|| camera.equals("Cam_22") || camera.equals("Cam_23") || camera.equals("Cam_24")) {
			for (int i = 0; i < Groupe22.length; i++) {
				if (Groupe22[i].equals(camera) == false) {
					neighbors.add(Groupe22[i]);
				}
			}
		}
		return neighbors;
	}

	/**
	 * Les méthodes de recherche retournent l'identifiant d'objet le plus similaire,
	 * si l'objet n'existe pas, il renvoie la valeur 0 car aucun objet n'a l'ID 0
	 **/

	public int Recherche_Objet_Suivi_Actuel(int cordX, int cordY) {
		int i = 0;
		int id_objet_extraire = 0;
		boolean tr = true;
		while (i < Liste_Objet_Suivi_Actuel.size() && tr == true) {
			LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(i);
			int cX = (int) Noued_Objet.get(3);
			int cY = (int) Noued_Objet.get(4);
			// System.out.println("(x,y)= ( " + cX + ", " + cY + " )");
			// si il ya un objet qui contient les meme cordonnéess (x, y)
			if (cordX == cX && cordY == cY) {
				tr = false;
				id_objet_extraire = (int) Noued_Objet.get(1);
			}
			i++;
		}
		return id_objet_extraire;
	}

	public int Recherche_Objet_suivi_histrique(LinkedList<Double> vecteur) {
		int i = 0;
		int id_objet_extraire = 0;
		boolean tr = true;
		while (i < Liste_Objet_Suivi_Historique.size() && tr == true) {
			LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Historique.get(i);
			LinkedList<Double> Vecteur_Objet = new LinkedList<>();
			// le vectur commance de l'indice 5
			for (int j = 5; j < Noued_Objet.size(); j++) {
				Vecteur_Objet.addLast((double) Noued_Objet.get(j));
			}
			// calcula la distance
			double distanse = Calcule_distanse(Vecteur_Objet, vecteur);
			if (distanse < seuil_distance) {
				tr = false;
				id_objet_extraire = (int) Noued_Objet.get(1);
			}
			i++;
		}
		return id_objet_extraire;
	}

	/**
	 * pour cette méthode, normalement je testait sur les coordonnées géographiques
	 * suivant de l'objet qui introduire par l'algorithme de filtre de Kalman
	 **/

	public int Recherche_Objet_Rcue(LinkedList<Double> vecteur) {
		int i = 0;
		int id_objet_extraire = 0;
		boolean tr = true;
		while (i < Liste_Objet_Recu.size() && tr == true) {
			LinkedList Noued_Objet = (LinkedList) Liste_Objet_Recu.get(i);
			LinkedList<Double> Vecteur_Objet = new LinkedList<>();
			// le vectur commance de l'indice 5
			for (int j = 5; j < Noued_Objet.size(); j++) {
				Vecteur_Objet.addLast((double) Noued_Objet.get(j));
			}
			// calcula la distance
			double distanse = Calcule_distanse(Vecteur_Objet, vecteur);
			if (distanse <= seuil_distance) {
				tr = false;
				id_objet_extraire = (int) Noued_Objet.get(1);
			}
			i++;
		}
		return id_objet_extraire;
	}

	public double Calcule_distanse(LinkedList<Double> V1, LinkedList<Double> V2) {
		double distnse = 0;
		for (int i = 0; i < V1.size(); i++) {
			distnse = distnse + Math.pow((V1.get(i) - V2.get(i)), 2);
		}
		distnse = Math.sqrt(distnse);
		// la normalisation
		double distance_normer = ((distnse - distance_Min) / (distance_Max + distance_Min));
		// System.out.println("la distance est : " + distnse + " & la distance normer "
		// + distance_normer);
		return distance_normer;
	}

	public double[] Convertire_table(String[] T) {
		double T1[] = new double[T.length];
		for (int i = 0; i < T.length; i++) {
			T1[i] = Double.valueOf(T[i]);
		}
		return T1;
	}

	// cette methode est appler par les camera Echouer lorsque l'objet sorti leur
	// champs
	public void supprime_objet_suivi_actuel(int id_camera_suivi, int id_objet_Original, int id_objet_Estimer) {
		// System.out.println("Fog "+id+" : Liste avant => "+Liste_Objet_Suivi_Actuel);
		// couper le vecteur qui possed les deux id (orig & estim) avec l id de camera
		// de la liste des objet suivi vers la liste des bjet suivi hitorique
		// System.out.println("la camera " + id_camera_suivi + " demande supprimer
		// l'objet " + id_objet_Original);
		int i = 0;
		boolean trouve = false;
		int indic_objet_suprime = -1;
		// System.out.println("1- Liste Objet Suivi : " + Liste_Objet_Suivi_Actuel);
		while (section_creteque == false) {
			System.out.println("la de mande de la caméra " + id_camera_suivi + " est en attante");
		}
		while (i < Liste_Objet_Suivi_Actuel.size() && trouve == false) {
			section_creteque = false;
			LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(i);
			int id_Ob_Or = (int) Noued_Objet.get(0);
			int id_Ob_Es = (int) Noued_Objet.get(1);
			int id_camr = (int) Noued_Objet.get(2);
			if (id_Ob_Or == id_objet_Original && id_Ob_Es == id_objet_Estimer && id_camr == id_camera_suivi) {
				// Couper
				Liste_Objet_Suivi_Historique.add(Noued_Objet);
				// System.out.println("Fog " + id + " : 2) Liste O S Historique : ajuter le
				// vecteur ==> " + Noued_Objet);
				// System.out.println("Fog_" + id + ":(add) la liste des objets suivi historique
				// "
				// + Liste_Objet_Suivi_Historique);

				trouve = true;
				indic_objet_suprime = i;
				// System.out.println("1- l'indice de l'objet supprime est " +
				// indic_objet_suprime
				// + " la taille de la liste est " + Liste_Objet_Suivi_Actuel);
			}
			i++;
		}

		if (trouve == true) {
			if (indic_objet_suprime > Liste_Objet_Suivi_Actuel.size() - 1) {
				// System.out.println("2- -- indice objet supprimer");
				indic_objet_suprime--;
			}
			// System.out.println("1- l'indice de l'objet supprime est " +
			// indic_objet_suprime
			// + " la taille de la liste est " + Liste_Objet_Suivi_Actuel);
			Liste_Objet_Suivi_Actuel.remove(indic_objet_suprime);
			// System.out.println("1- Liste Objet Suivi : " + Liste_Objet_Suivi_Actuel);

		}
		section_creteque = true;
	}

	protected void setup() {
		System.out.println("Démarrage de l'agent : " + this.getAID().getName());

		Numbre_Fragmented_Objet = new int[Nmbre_Objet_Max];
		for (int i = 0; i < Numbre_Fragmented_Objet.length; i++) {
			Numbre_Fragmented_Objet[i] = 0;
		}

		Liste_Objet = new boolean[Nmbre_Objet_Max];

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {

				ACLMessage messageRecu = receive();
				if (messageRecu != null) {
					Object[] obj = null;
					try {
						obj = (Object[]) messageRecu.getContentObject();
						String Demand = (String) obj[0];
						if (Demand.equals("Request_Neighbors")) {
							String Nom_Camera = messageRecu.getSender().getLocalName();
							LinkedList neighbors = Get_neighbors(Nom_Camera);

							// replay le messge
							ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
							aclMessage.addReceiver(messageRecu.getSender());
							Object[] obj1 = { "Neighbors", neighbors };
							aclMessage.setContentObject(obj1);
							send(aclMessage);
							// System.out.println("Fog" + id + " : envoyer message Neighbors vers "
							// + messageRecu.getSender().getLocalName());

						} else if (Demand.equals("Request_Id_Objet")) {
							// Detection d'un objet pour le premier fois
							Nombre_Request_ID_total++;
							LinkedList<Double> Vecteur = (LinkedList<Double>) obj[1];
							int id_camera = (int) obj[2];
							int cordX = (int) obj[3];
							int cordY = (int) obj[4];
							int id_Objet_Original = (int) obj[5];
							// System.out.println(
							// "Fog " + id + " : j'ai recu un messsage requet id objet ==> " +
							// id_Objet_Original);
							int id_Objet_Estimer = Recherche_Objet_Suivi_Actuel(cordX, cordY);
							if (id_Objet_Estimer == 0) {
								// c.à.d l'objet n'est existe pas dans la liste des objets suivi actuel
								id_Objet_Estimer = Recherche_Objet_Rcue(Vecteur);
								if (id_Objet_Estimer == 0) {
									// c.à.d l'objet n'est existe pas dans la liste des objets recu
									id_Objet_Estimer = Recherche_Objet_suivi_histrique(Vecteur);
									if (id_Objet_Estimer == 0) {
										// c.à.d l'objet n'est existe pas dans la liste des objets suivi Histrique
										// cration nouvelle identificateur pour cette objets
										id_objet_suivant++;
										id_Objet_Estimer = id_objet_suivant;
									} else {
										// System.out.println("Fog" + id + " : j'ai trouver l'objet " + id_Objet_Estimer
										// + " dans la liste des Objets Suivi Historique|| demande de la camera "
										// + id_camera);
									}
								} else {
									// System.out.println("Fog" + id + " : j'ai trouver l'objet " + id_Objet_Estimer
									// + " dans la liste des Objets Recu|| demande de la camera " + id_camera);
								}
							} else {
								// System.out.println("Fog" + id + " : j'ai trouver l'objet " + id_Objet_Estimer
								// + " dans la liste des Objets Suivi Actuel|| demande de la camera " +
								// id_camera);
							}

							LinkedList newVecteur = new LinkedList<>();
							newVecteur.add(0, id_Objet_Original);
							newVecteur.add(1, id_Objet_Estimer);
							newVecteur.add(2, id_camera);
							newVecteur.add(3, cordX);
							newVecteur.add(4, cordY);
							for (int i = 0; i < Vecteur.size(); i++) {
								newVecteur.addLast(Vecteur.get(i));
							}

							// avant d'ajouter cet objet il faut testé est ce que il existe ou non
							// prq il le cas d'un camera Echouer ajouter deux fois le meme vecteur
							boolean trouve = false;
							for (int i = 0; i < Liste_Objet_Suivi_Actuel.size(); i++) {
								LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(i);
								int id_Ob_Or = (int) Noued_Objet.get(0);
								int id_Ob_Es = (int) Noued_Objet.get(1);
								int id_camr = (int) Noued_Objet.get(2);
								if (id_Ob_Or == id_Objet_Original && id_Ob_Es == id_Objet_Estimer
										&& id_camr == id_camera) {
									trouve = true;
								}
							}
							if (trouve == false) {
								Liste_Objet_Suivi_Actuel.add(newVecteur);
								// System.out.println("55555");
							}

							// System.out.println("Fog_" + id + ": (add) la liste des objets suivi actuel "
							// + Liste_Objet_Suivi_Actuel.size() + " ==>" + Liste_Objet_Suivi_Actuel);

							// System.out.println("Fog_" + id + ": le vecteur reçu est : " + Vecteur + " par
							// la camera "
							// + messageRecu.getSender().getLocalName());

							ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
							aclMessage.addReceiver(messageRecu.getSender());
							Object[] obj1 = { "Id_Objet", id_Objet_Estimer, id_Objet_Original };
							aclMessage.setContentObject(obj1);
							send(aclMessage);
							// System.out.println("Fog" + id + " : envoyer message Id_Objet vers "
							// + messageRecu.getSender().getLocalName());
							if (id_Objet_Original != id_Objet_Estimer) {
								Nombre_Switche++;
							}

							/*
							 * parcourez la liste des objet suivi si il y a une false alarme c.à.d. est ce
							 * que il plus de deux objets suiver un seul bjet avec identificatur diférent
							 */
							int nombre_camera_suivi_actuel = 1;
							for (int j = 0; j < Liste_Objet_Suivi_Actuel.size(); j++) {
								LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(j);
								int id_Ob_Or = (int) Noued_Objet.get(0);
								int id_Ob_Es = (int) Noued_Objet.get(1);
								if (id_Ob_Or == id_Objet_Original && id_Ob_Es != id_Objet_Estimer) {
									// il y a un false alarme sur cette objet
									nombre_camera_suivi_actuel++;
								}
							}
							if (nombre_camera_suivi_actuel >= 2) {
								Nombre_false_alarme++;
								// System.out.println(
								// "Fog_" + id + ": il y a false alarme sur l'objet " + id_Objet_Original+" ||
								// "+nombre_camera_suivi_actuel+" suivi cette objet avec des IDs différent");
							} else {
								// System.out.println("Fog_" + id + ": il y a false alarme sur l'objet "
								// + id_Objet_Original +" || "+nombre_camera_suivi_actuel+" suivi cette objet
								// avec des IDs différent, mais elle est déjà calculer");
							}

						} else if (Demand.equals("Update_Vecteur")) {

							LinkedList<Double> Vecteur = (LinkedList<Double>) obj[1];
							// System.out.println("Fog_" + id + ": Update le vecteur reçu est : " +
							// Vecteur);
							int id_objet_Estimer = (int) obj[2];
							int id_objet_Original = (int) obj[6];
							int id_camera_suivi = (int) obj[3];
							int cordX = (int) obj[4];
							int cordY = (int) obj[5];

							// couper le vecteur qui possed les deux id (orig & estim) avec l id de camera
							// de la liste des objet suivi vers la liste des bjet suivi hitorique
							int i = 0;
							boolean trouve = false;
							int indic_objet_suprime = -1;
							// while (section_creteque == false) {
							// System.out
							// .println("2 - la de mande de la caméra " + id_camera_suivi + " est en
							// attante");
							// }
							// System.out.println("2- Liste Objet Suivi : " + Liste_Objet_Suivi_Actuel);
							while (i < Liste_Objet_Suivi_Actuel.size() && trouve == false) {
								// section_creteque = false;
								LinkedList Noued_Objet = new LinkedList<>();
								Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(i);
								// System.out.println(" ==> " + Noued_Objet);
								int id_Ob_Or = (int) Noued_Objet.get(0);
								int id_Ob_Es = (int) Noued_Objet.get(1);
								int id_camr = (int) Noued_Objet.get(2);
								if (id_Ob_Or == id_objet_Original && id_Ob_Es == id_objet_Estimer
										&& id_camr == id_camera_suivi) {
									// Couper
									Liste_Objet_Suivi_Historique.add(Noued_Objet);
									// System.out.println("Fog " + id
									// + " : 3) Liste O S Historique : ajuter le vecteur ==> " + Noued_Objet);
									trouve = true;
									indic_objet_suprime = i;
									// System.out.println("2- l'indice de l'objet supprime est " +
									// indic_objet_suprime
									// + " la taille de la liste est " + Liste_Objet_Suivi_Actuel);
									// System.out.println("Fog_" + id + ":(add) la liste des objets suivi historique
									// "
									// + Liste_Objet_Suivi_Historique);
								}
								i++;
							}
							if (trouve == true) {
								if (indic_objet_suprime > Liste_Objet_Suivi_Actuel.size() - 1) {
									// System.out.println("2- -- indice objet supprimer");
									indic_objet_suprime--;
								}
								// System.out.println("2- l'indice de l'objet supprime est " +
								// indic_objet_suprime
								// + " la taille de la liste est " + Liste_Objet_Suivi_Actuel);
								Liste_Objet_Suivi_Actuel.remove(indic_objet_suprime);

								// System.out.println("2- Liste Objet Suivi : " + Liste_Objet_Suivi_Actuel);
							}
							// section_creteque = true;
							// System.out.println("Fog "+id+" : Liste (Update remove) =>
							// "+Liste_Objet_Suivi_Actuel);

							// ajoute le dernier vecteur de ce objet qui suivi par cette camera
							LinkedList newVecteur = new LinkedList<>();
							newVecteur.add(0, id_objet_Original);
							newVecteur.add(1, id_objet_Estimer);
							newVecteur.add(2, id_camera_suivi);
							newVecteur.add(3, cordX);
							newVecteur.add(4, cordY);
							for (int i1 = 0; i1 < Vecteur.size(); i1++) {
								newVecteur.addLast(Vecteur.get(i1));
							}
							Liste_Objet_Suivi_Actuel.add(newVecteur);
							// System.out.println("Fog "+id+" : Liste (Upadate add) =>
							// "+Liste_Objet_Suivi_Actuel);
							// System.out.println("Fog_" + id + ": (add) la liste des objets suivi actuel "
							// + Liste_Objet_Suivi_Actuel.size() + " ==>" + Liste_Objet_Suivi_Actuel);

						} else if (Demand.equals("Objet_Perdue")) {
							boolean Quitter = true;
							LinkedList<Double> Vecteur = (LinkedList<Double>) obj[1];
							int id_objet_Estimer = (int) obj[2];
							int id_camera_suivi = (int) obj[3];
							int cordX = (int) obj[4];
							int cordY = (int) obj[5];
							int id_objet_Original = (int) obj[6];
							// System.out.println("Fog_" + id + ": j'ai reçu un message objet perdue la
							// camera "
							// + messageRecu.getSender().getLocalName() + " perdue l'objet " + id_objet);
							// envoyer vers le fog

							// couper le vecteur qui possed les deux id (orig & estim) avec l id de camera
							// de la liste des objet suivi vers la liste des objets historique
							int i = 0;
							boolean trouve = false;
							int indic_objet_suprime = -1;
							// System.out.println("3- Liste Objet Suivi : " + Liste_Objet_Suivi_Actuel);
							while (i < Liste_Objet_Suivi_Actuel.size() && trouve == false) {
								LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(i);
								int id_Ob_Or = (int) Noued_Objet.get(0);
								int id_Ob_Es = (int) Noued_Objet.get(1);
								int id_camr = (int) Noued_Objet.get(2);
								// int cordXX = (int) Noued_Objet.get(3);
								// int cordYY = (int) Noued_Objet.get(4);

								// il y a un objet dans les meme coordonnées (nécessairement le meme objet)

								if (id_Ob_Or == id_objet_Original && id_Ob_Es == id_objet_Estimer
										&& id_camr == id_camera_suivi) {

									// Couper le vecteur qui existe dans la liste ectuel vers Historique
									Liste_Objet_Suivi_Historique.add(Noued_Objet);
									// System.out.println("3- l'indice de l'objet supprime est " +
									// indic_objet_suprime
									// + " la taille de la liste est " + Liste_Objet_Suivi_Actuel);
									// System.out.println(
									// "Fog_" + id + ": (add) la liste des objets perdue " + Liste_Objet_Perdue);
									trouve = true;
									indic_objet_suprime = i;
								}
								i++;
							}
							if (trouve) {
								if (indic_objet_suprime > Liste_Objet_Suivi_Actuel.size() - 1) {
									// System.out.println("3- -- indice objet supprimer");
									indic_objet_suprime--;
								}
								// System.out.println("3- l'indice de l'objet supprime est " +
								// indic_objet_suprime
								// + " la taille de la liste est " + Liste_Objet_Suivi_Actuel);
								Liste_Objet_Suivi_Actuel.remove(indic_objet_suprime);
								// System.out.println("3- Liste Objet Suivi : " + Liste_Objet_Suivi_Actuel);
							}
							/********************************************
							 * Statistique
							 ********************************************/
							for (int j = 0; j < Liste_Objet_Suivi_Actuel.size(); j++) {
								LinkedList Noued_Objet = (LinkedList) Liste_Objet_Suivi_Actuel.get(j);
								int id_Ob_Or = (int) Noued_Objet.get(0);
								int id_Ob_Es = (int) Noued_Objet.get(1);
								// System.out.println("objet ectuel " + Noued_Objet);
								if (id_Ob_Or == id_objet_Original && id_Ob_Es == id_objet_Estimer) {
									Quitter = false;
								}
							}
							// l'objet est quitter tout les champs de visions
							if (Quitter) {
								// System.out.println("Fog_" + id + ": l'objet " + id_objet_Original + " quitter
								// || camera"
								// + id_camera_suivi);
								int valuer = Numbre_Fragmented_Objet[id_objet_Original - 1];
								Numbre_Fragmented_Objet[id_objet_Original - 1] = valuer + 1;
							} else {
								/*************************************************
								 * l'objet est quitter un champs d'un camera mais elle reste dans un autre
								 * champs c.à.d. l'objet n'a pas quitter
								 *************************************************/
								// System.out.println("Fog_" + id + ": l'objet " + id_objet_Original
								// + " n'a pas quitter || camera " + id_camera_suivi);
							}

							// couper le dernier vecteur de l'objet perdue dans la liste historique
							LinkedList Noued_Objet = new LinkedList<>();
							Noued_Objet.add(0, id_objet_Original);
							Noued_Objet.add(1, id_objet_Estimer);
							Noued_Objet.add(2, id_camera_suivi);
							Noued_Objet.add(3, cordX);
							Noued_Objet.add(4, cordY);

							for (int i1 = 0; i1 < Vecteur.size(); i1++) {
								Noued_Objet.addLast(Vecteur.get(i1));
							}
							Liste_Objet_Suivi_Historique.add(Noued_Objet);
							// System.out.println(
							// "Fog " + id + " : 5) Liste O S Historique : ajuter le vecteur ==> " +
							// Noued_Objet);
							// System.out
							// .println("Fog_" + id + ": (add) la liste des objets perdue " +
							// Liste_Objet_Perdue);

							// envoyer le dernier vecteur vers les autres Fog
							int i1 = 1;
							while (i1 <= Nombre_Fog) {
								if (Integer.valueOf(id) != i1) {
									ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
									aclMessage.addReceiver(new AID("Fog_".concat(String.valueOf(i1)), AID.ISLOCALNAME));
									Object[] obj1 = { "Informe_Fog", Noued_Objet };
									aclMessage.setContentObject(obj1);
									send(aclMessage);

									// System.out.println("Fog" + id + " : envoyer message Informe_Fog vers "
									// + messageRecu.getSender().getLocalName());
								}
								i1++;
							}

						} else if (Demand.equals("Informe_Fog")) {
							LinkedList vercteur_recu = (LinkedList) obj[1];
							// ajouter cette vecteur dans la liste des objets recus et supprimer l'encien si
							// il existe
							int id_objet_Original = (int) vercteur_recu.get(0);
							int id_objet_Estimer = (int) vercteur_recu.get(1);

							// parcoure de la liste pour le recherch
							int i = 0;
							boolean trouve = false;
							int indic_objet_suprime = -1;
							while (trouve == false && i < Liste_Objet_Recu.size()) {
								LinkedList Noued_Objet = (LinkedList) Liste_Objet_Recu.get(i);
								int id_Ob_Or = (int) Noued_Objet.get(0);
								int id_Ob_Es = (int) Noued_Objet.get(1);

								if (id_Ob_Or == id_objet_Original && id_Ob_Es == id_objet_Estimer) {
									// l'objet est existe
									trouve = true;
									indic_objet_suprime = i;
								}
								i++;
							}
							if (trouve == true) {
								Liste_Objet_Recu.remove(indic_objet_suprime);
							}
							Liste_Objet_Recu.add(vercteur_recu);
							// System.out.println("Fog_" + id + ": la liste des objets Recu " +
							// Liste_Objet_Recu);
						} else {
							block();
						}
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}

	public void runAgent() {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
			AgentController agentController = agentContainer.acceptNewAgent("Fog_".concat(id), this);
			agentController.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
