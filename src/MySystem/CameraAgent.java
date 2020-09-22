package MySystem;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class CameraAgent extends Agent {
	private int EquationCamera[][] = new int[3][2];
	private int Cam_tempo[][] = new int[3][2];
	// les cordonnées de la caméra
	public int X[];
	public int Y[];
	int cordX, cordY, id_Fog;;
	String State;
	LinkedList<String> NeighborsList = new LinkedList<String>();
	LinkedList<Integer> Assistant = new LinkedList<Integer>();
	LinkedList<Object[]> Liste_Condidat = new LinkedList<Object[]>();
	LinkedList<Integer> Liste_Objet_Bloque_Orig = new LinkedList<Integer>();
	LinkedList<Integer> Liste_Objet_Bloque_Estimer = new LinkedList<Integer>();
	LinkedList<Object[]> Liste_non_Candidat = new LinkedList<Object[]>();
	int Chef, Chef_probable, Nombre_Message_Recu, Id_Objetc_Detecter_Origi, Id_Objetc_Detecter_Estimer, Id_Camera;
	double Vision, Seuil, Distance_Max, Vision_Max, Vision_Complement, Seuil_Max;
	MainPrencipale mySystem;
	LinkedList<Double> Vecteur_Caracteristique;
	FogAgent fogAgent;
	int Nomrbe_fois_chef = 0;
	int Nombre_fois_assistant = 0;
	int Nombre_fois_Echouer = 0;
	int Nombre_fois_Candidat = 0;
	Map<Integer, Long> Liste_Temps_Objet_Echouer = new HashMap<>();
	Map<Integer, Long> Liste_Temps_Objet_Actuel = new HashMap<>();
	Map<Integer, Long> Liste_Temps_Objet_Chef = new HashMap<>();
	Map<Integer, Long> Liste_Temps_Objet_Assistant = new HashMap<>();
	long Temps_Totale_Echouer = 0;
	long Temps_Totale_Active = 0;

	public CameraAgent(String id, int X[], int Y[], double distance, MainPrencipale mySystme, FogAgent fogAgent) {
		this.X = X;
		this.Y = Y;
		this.Id_Camera = Integer.valueOf(id);
		this.Distance_Max = distance;
		this.mySystem = mySystme;
		this.fogAgent = fogAgent;
		// this.sortie = sortie;
		// initialisation des variables
		this.Chef = -1;
		this.Chef_probable = -1;
		this.State = "Libre";
		this.Nombre_Message_Recu = 0;
		this.Vision = 0;
		this.Vision_Max = 0;
		this.Seuil = 0;
		this.Vision_Complement = 0;
		this.Seuil_Max = 80;
	}

	protected void setup() {
		// System.out.println("Démarrage de l'agent : " + this.getAID().getName());
		/***********************************************************
		 ** envoyer message vers le fog pour recuver les voisins **
		 ***********************************************************/

		Object[] obj = { "Request_Neighbors" };
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		// tasti chkon le fog ta3o
		if (Id_Camera == 1 || Id_Camera == 2 || Id_Camera == 3 || Id_Camera == 4 || Id_Camera == 5) {
			aclMessage.addReceiver(new AID("Fog_1", AID.ISLOCALNAME));
			id_Fog = 1;
		} else {
			aclMessage.addReceiver(new AID("Fog_2", AID.ISLOCALNAME));
			id_Fog = 2;
		}

		try {
			aclMessage.setContentObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		send(aclMessage);
		/**********************************************************/

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				ACLMessage aclMessage = receive();
				if (aclMessage != null) {
					try {
						Object[] obj = (Object[]) aclMessage.getContentObject();
						if (obj[0].equals("Neighbors")) {
							// System.out.println("Camera " + Id_Camera + " j'ai réçu mon voisin a partir de
							// Fog");
							NeighborsList = (LinkedList<String>) obj[1];
							// System.out.println("Camera " + Id_Camera + " liste message ==> " +
							// NeighborsList);
						} else if (obj[0].equals("set_coordinates_object")) {
							cordX = Integer.parseInt((String) obj[1]);
							cordY = Integer.parseInt((String) obj[2]);
							int id_obj = Integer.parseInt((String) obj[3]);
							Vecteur_Caracteristique = (LinkedList<Double>) obj[4];
							boolean vecteur_changer = (boolean) obj[5];
							// System.out.println("La camera " + Id_Camera + " le vecteur des
							// caractéristique : "
							// + Vecteur_Caracteristique + " de l'objet " + id_obj);

							Cam_tempo = setCordonnerObjet(Cam_tempo);
							if (resolution(Cam_tempo) == true) {
								detection_Objet(cordX, cordY, id_obj, Vecteur_Caracteristique, vecteur_changer);
							} else {
								if (State.equals("Active")) {
									Detection_Fin_Suivi(id_obj);
								} else if (State.equals("Echouer")) {
									// System.out.println("La camera " + Id_Camera + " : l'objet " + id_obj
									// + " est n'est pas dans mon champs");

									int i = 0;
									while (i < Liste_Objet_Bloque_Orig.size()) {
										if (Liste_Objet_Bloque_Orig.get(i) == id_obj) {
											/**
											 * Arrêtez le calcul du temps
											 **/
											long Temps_depart = Liste_Temps_Objet_Echouer.get(id_obj);
											long Temps_mort = System.currentTimeMillis();
											Temps_Totale_Echouer = Temps_Totale_Echouer + (Temps_mort - Temps_depart);
											Liste_Temps_Objet_Echouer.remove(id_obj);
											// System.out.println("Temps camera Echouer " + Id_Camera + " ==> "
											// + (Temps_mort - Temps_depart));
											// informer le fog que cette objet est quitter mon champs
											// (id_camera,id_objetoriginal)
											fogAgent.supprime_objet_suivi_actuel(Id_Camera, id_obj,
													Liste_Objet_Bloque_Estimer.get(i));
											// suprime l'objet de la liste des bjet bloque
											// System.out.println("CyclicBehaviour : La camera " + Id_Camera
											// + " la liste des objets bloqué riginal est "
											// + Liste_Objet_Bloque_Orig);
											// System.out.println("CyclicBehaviour : La camera " + Id_Camera
											// + " la liste des objets bloqué estimer est "
											// + Liste_Objet_Bloque_Estimer);
											Liste_Objet_Bloque_Orig.remove(i);
											Liste_Objet_Bloque_Estimer.remove(i);
											// System.out.println(
											// "Camera" + Id_Camera + " l'objet " + id_obj + " quitter mn champs");
											// System.out.println("CyclicBehaviour : La camera " + Id_Camera
											// + " la liste des objets bloqué riginal est "
											// + Liste_Objet_Bloque_Orig);
											// System.out.println("CyclicBehaviour : La camera " + Id_Camera
											// + " la liste des objets bloqué estimer est "
											// + Liste_Objet_Bloque_Estimer);
										}
										i++;
									}

									if (Liste_Objet_Bloque_Orig.isEmpty()) {
										State = "Libre";
										setColorCameraInitiale();
										setColorLabCamera(Color.BLACK);
									}
								}
							}
						} else if (obj[0].equals("Election")) {
							int ID_Objet_Recu = (int) obj[1];
							double Vision_Recu = (double) obj[2];
							int id_Emetteur = (int) obj[3];
							// System.out.println("Camera" + Id_Camera + " j'ai recu un message Election
							// par: "
							// + id_Emetteur + ", vision = " + Vision_Recu + " sur l'objet " +
							// ID_Objet_Recu);
							Election(ID_Objet_Recu, Vision_Recu, id_Emetteur);
						} else if (obj[0].equals("Non_Accepter")) {
							// System.out.println("La camera " + Id_Camera + " recever message Non Accepter
							// par "
							// + aclMessage.getSender().getLocalName());
							Non_Accepter(Id_Objetc_Detecter_Origi, Id_Objetc_Detecter_Estimer);
						} else if (obj[0].equals("Accepter")) {
							// System.out.println("La camera " + Id_Camera + " recever message Accepter par
							// "
							// + aclMessage.getSender().getLocalName());
							Accepter();
						} else if (obj[0].equals("Demission")) {
							int id_camera_demissionner = (int) obj[1];
							// System.out.println("La camera " + Id_Camera + " recever message Demission par
							// "
							// + aclMessage.getSender().getLocalName());
							Demission(id_camera_demissionner);
						} else if (obj[0].equals("Reelection")) {

							int id_camera_reelectioner = (int) obj[1];
							int id_objet_estm = (int) obj[2];
							int id_objetOrg = (int) obj[3];
							// System.out.println("La camera " + Id_Camera + " recever message Réélection
							// par "
							// + aclMessage.getSender().getLocalName() + " sur l'objet " + id_objetOrg);
							Reelection(id_camera_reelectioner, id_objet_estm, id_objetOrg);
						} else if (obj[0].equals("Id_Objet")) {
							// System.out.println("Camera " + Id_Camera + " : Envoyer Election vers tous les
							// voisins");
							Informe_Id_Objet((int) obj[1], (int) obj[2]);
						} else {
							block();
						}
					} catch (UnreadableException e1) {
						e1.printStackTrace();
						// System.out.println("Camera " + Id_Camera + " problème");
					}
				}
			}
		});
	}

	public void Informe_Id_Objet(int id_Est, int id_orig) {
		Id_Objetc_Detecter_Estimer = id_Est;
		Id_Objetc_Detecter_Origi = id_orig;
		// System.out.println("Camera " + Id_Camera + " : l'objet tracking est " +
		// Id_Objetc_Detecter_Estimer
		// + " et l'objet original est " + Id_Objetc_Detecter_Origi);
		// bon resultat

		if (Id_Objetc_Detecter_Estimer == Id_Objetc_Detecter_Origi) {
			// setcolor green
			setColorLabCamera(Color.GREEN);
		} else {
			// setcolor red
			setColorLabCamera(Color.RED);
		}
		try {
			int i = 0;
			while (i < NeighborsList.size()) {
				ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
				Object obj1[] = { "Election", Id_Objetc_Detecter_Estimer, Vision, Id_Camera };
				aclMessage.setContentObject(obj1);
				aclMessage.addReceiver(new AID((String) NeighborsList.get(i), AID.ISLOCALNAME));
				send(aclMessage);

				// System.out.println(
				// "la camera " + Id_Camera + " envoyer message Election vers " + (String)
				// NeighborsList.get(i));
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Reelection(int Emiteur, int id_Objet_Estimer, int id_objetOrg) {
		if (State.equals("Echouer")) {
			// System.out.println("********* la camera " + Id_Camera + " ani echouer a jatni
			// relection man3ad " + Emiteur);
			// est ce que cette objet est existe dans la liste ds objet bloques
			boolean objte_existe = false;
			int indice_objet = -1;
			int j = 0;
			while (j < Liste_Objet_Bloque_Orig.size() && objte_existe == false) {
				if (Liste_Objet_Bloque_Orig.get(j) == id_objetOrg
						&& Liste_Objet_Bloque_Estimer.get(j) == id_Objet_Estimer) {
					/**
					 * Arrêtez le calcul du temps
					 **/
					long Temps_depart = Liste_Temps_Objet_Echouer.get(id_objetOrg);
					long Temps_mort = System.currentTimeMillis();
					Temps_Totale_Echouer = Temps_Totale_Echouer + (Temps_mort - Temps_depart);
					Liste_Temps_Objet_Echouer.remove(id_objetOrg);
					// System.out.println("Temps camera Echouer " + Id_Camera + " ==> " +
					// (Temps_mort - Temps_depart));

					objte_existe = true;
					indice_objet = j;
					// System.out.println("La camera " + Id_Camera + "l'objet Org: " + id_objetOrg +
					// " & Est: "
					// + id_Objet_Estimer + " est trouver dans la possition " + j);
				}
				j++;
			}
			// l'objet existe evec les deux coordonenr origenal et estimer
			if (objte_existe == true) {
				// System.out.println("");
				// est ce que l'objet est resta dans mn champs de vision ?
				if (resolution(Cam_tempo)) {
					// System.out.println("La camera " + Id_Camera + " : l'objet " + id_objetOrg
					// + " mazal fe champs de vision ta3i w y existé fe liste bloque Bmoquer Org "
					// + Liste_Objet_Bloque_Orig + " Bloquer Est " + Liste_Objet_Bloque_Estimer);
					Id_Objetc_Detecter_Estimer = id_Objet_Estimer;
					Id_Objetc_Detecter_Origi = id_objetOrg;
					Election_Preparation();
					// System.out.println("Camera " + Id_Camera + " : Envoyer Election vers tous les
					// voisins");
					try {
						int i = 0;
						while (i < NeighborsList.size()) {
							ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
							Object obj[] = { "Election", id_Objet_Estimer, Vision, Id_Camera };
							aclMessage.setContentObject(obj);
							aclMessage.addReceiver(new AID((String) NeighborsList.get(i), AID.ISLOCALNAME));
							send(aclMessage);

							// System.out.println("la camera " + Id_Camera + " envoyer message Election vers
							// "
							// + (String) NeighborsList.get(i));
							i++;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// suprimer cette objet dans touts les cas (rest dans mon champs ou non)
				// System.out.println("Reelection : La camera " + Id_Camera + " la liste des
				// objets bloqué riginal est "
				// + Liste_Objet_Bloque_Orig);
				// System.out.println("Reelection : La camera " + Id_Camera + " la liste des
				// objets bloqué estimer est "
				// + Liste_Objet_Bloque_Estimer);
				if (indice_objet != -1) {
					// l'objet est existe par ce que indice ca change
					Liste_Objet_Bloque_Orig.remove(indice_objet);
					Liste_Objet_Bloque_Estimer.remove(indice_objet);

					// System.out.println("Camera" + Id_Camera + " l'objet " + id_objetOrg + "
					// quitter mn champs");
					// System.out.println("Reelection : La camera " + Id_Camera
					// + " la liste des objets bloqué riginal est " + Liste_Objet_Bloque_Orig);
					// System.out.println("Reelection : La camera " + Id_Camera
					// + " la liste des objets bloqué estimer est " + Liste_Objet_Bloque_Estimer);
				}

				// System.out.println("La camera " + Id_Camera + " la liste des objets bloqué
				// estimer est "
				// + Liste_Objet_Bloque_Estimer);
			}

			/*******************************
			 * 
			 * Ki manalgahach mach ma3natha manswivihach normalment n testi est ce que mazal
			 * 3andi fe champs w nkhdem normal
			 * 
			 *****************************/

		} else if (Chef == Emiteur) {
			// je suis assistante
			Id_Objetc_Detecter_Estimer = id_Objet_Estimer;
			Id_Objetc_Detecter_Origi = id_objetOrg;
			/**
			 * Arrêtez le calcul du temps || Assistante
			 **/

			if (Liste_Temps_Objet_Actuel.containsKey(Id_Objetc_Detecter_Origi)) {
				// System.out.println("Reelection --> " + Liste_Temps_Objet_Active);
				long Temps_mort = System.currentTimeMillis();
				long Temps_depart = Liste_Temps_Objet_Actuel.get(Id_Objetc_Detecter_Origi);
				long temps = Temps_mort - Temps_depart;
				Temps_Totale_Active = Temps_Totale_Active + temps;
				Liste_Temps_Objet_Actuel.remove(Id_Objetc_Detecter_Origi);

				if (Liste_Temps_Objet_Assistant.containsKey(Id_Objetc_Detecter_Origi) == false) {
					Liste_Temps_Objet_Assistant.put(Id_Objetc_Detecter_Origi, temps);
				} else {
					long Temporal = Liste_Temps_Objet_Assistant.get(Id_Objetc_Detecter_Origi);
					Temporal = Temporal + temps;
					Liste_Temps_Objet_Assistant.put(Id_Objetc_Detecter_Origi, Temporal);
				}

				// System.out.println(
				// "1- Temps camera Active || Assistante " + Id_Camera + " ==> " + (Temps_mort -
				// Temps_depart));
			}

			Election_Preparation();
			// System.out
			// .println("Camera " + Id_Camera + " : Envoyer Election vers tous les voisins
			// (methode reelection)");

			try {
				int i = 0;
				while (i < NeighborsList.size()) {
					ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
					Object obj[] = { "Election", id_Objet_Estimer, Vision, Id_Camera };
					aclMessage.setContentObject(obj);
					aclMessage.addReceiver(new AID((String) NeighborsList.get(i), AID.ISLOCALNAME));
					send(aclMessage);

					// System.out.println("la camera " + Id_Camera + " envoyer message Election vers
					// "
					// + (String) NeighborsList.get(i));
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void Demission(int id_camera_demissionner) {
		int i = 0;
		boolean tr = true;
		while (i < Assistant.size() && tr == true) {
			if (Assistant.get(i) == id_camera_demissionner) {
				Assistant.remove(i);
				tr = false;
			}
			i++;
		}
		// System.out.println("La camera " + Id_Camera + " la liste des assistantes est
		// " + Assistant);
	}

	public void Detection_Fin_Suivi(int id_Objet_F_S) {
		// je suis active
		if (Id_Objetc_Detecter_Origi == id_Objet_F_S) {
			// System.out.println("Camera " + Id_Camera + " j'ai perdue l'objet " +
			// id_Objet_F_S);
			// System.out.println("Camera " + Id_Camera + " : Arreter l'enegistrement de
			// video");
			if (Chef == Id_Camera) {
				/**
				 * Arrêtez le calcul du temps || Chef
				 **/
				long Temps_mort = System.currentTimeMillis();
				long Temps_depart = Liste_Temps_Objet_Actuel.get(Id_Objetc_Detecter_Origi);
				long temps = Temps_mort - Temps_depart;
				Temps_Totale_Active = Temps_Totale_Active + temps;
				Liste_Temps_Objet_Actuel.remove(Id_Objetc_Detecter_Origi);

				if (Liste_Temps_Objet_Chef.containsKey(Id_Objetc_Detecter_Origi) == false) {
					Liste_Temps_Objet_Chef.put(Id_Objetc_Detecter_Origi, temps);
				} else {
					long Temporal = Liste_Temps_Objet_Chef.get(Id_Objetc_Detecter_Origi);
					Temporal = Temporal + temps;
					Liste_Temps_Objet_Chef.put(Id_Objetc_Detecter_Origi, Temporal);
				}

				// System.out.println("2- Temps camera Active || Chef " + Id_Camera + " ==> " +
				// (Temps_mort - Temps_depart));

				if (Assistant.size() == 0) {
					// System.out.println("Camera " + Id_Camera + " : Envoyer Objet perdue vers le
					// Fog");
					ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
					Object obj[] = { "Objet_Perdue", Vecteur_Caracteristique, Id_Objetc_Detecter_Estimer, Id_Camera,
							cordX, cordY, Id_Objetc_Detecter_Origi };
					try {
						aclMessage.setContentObject(obj);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					aclMessage.addReceiver(new AID("Fog_".concat(String.valueOf(id_Fog)), AID.ISLOCALNAME));
					send(aclMessage);

					// System.out.println("la camera " + Id_Camera + " envoyer message Objet_Perdue
					// vers "
					// + "Fog_".concat(String.valueOf(id_Fog)));
				} else {
					fogAgent.supprime_objet_suivi_actuel(Id_Camera, Id_Objetc_Detecter_Origi,
							Id_Objetc_Detecter_Estimer);
				}

				// System.out.println("Camera " + Id_Camera + " : Envoyer Réélection vers les
				// voisins ");
				int i = 0;
				while (i < NeighborsList.size()) {
					ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
					Object obj[] = { "Reelection", Id_Camera, Id_Objetc_Detecter_Estimer, Id_Objetc_Detecter_Origi };
					try {
						aclMessage.setContentObject(obj);
						aclMessage.addReceiver(new AID((String) NeighborsList.get(i), AID.ISLOCALNAME));
						send(aclMessage);

						// System.out.println("la camera " + Id_Camera + " envoyer message Reelection
						// vers "
						// + (String) NeighborsList.get(i) + " sur l'objet " +
						// Id_Objetc_Detecter_Origi);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					i++;
				}

			} else {
				// System.out.println("Camera " + Id_Camera + " : Envoyer le message de
				// Demission vers le Chef" + Chef);
				/**
				 * Arrêtez le calcul du temps || Assistante
				 **/
				long Temps_mort = System.currentTimeMillis();
				long Temps_depart = Liste_Temps_Objet_Actuel.get(Id_Objetc_Detecter_Origi);
				long temps = Temps_mort - Temps_depart;
				Temps_Totale_Active = Temps_Totale_Active + temps;
				Liste_Temps_Objet_Actuel.remove(Id_Objetc_Detecter_Origi);

				if (Liste_Temps_Objet_Assistant.containsKey(Id_Objetc_Detecter_Origi) == false) {
					Liste_Temps_Objet_Assistant.put(Id_Objetc_Detecter_Origi, temps);
				} else {
					long Temporal = Liste_Temps_Objet_Assistant.get(Id_Objetc_Detecter_Origi);
					Temporal = Temporal + temps;
					Liste_Temps_Objet_Assistant.put(Id_Objetc_Detecter_Origi, Temporal);
				}

				// System.out.println(
				// "3- Temps camera Active || Assistante " + Id_Camera + " ==> " + (Temps_mort -
				// Temps_depart));

				ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
				Object obj[] = { "Demission", Id_Camera };
				try {
					aclMessage.setContentObject(obj);
					String id_camera_chef = String.valueOf(Chef);
					aclMessage.addReceiver(new AID("Cam_".concat(id_camera_chef), AID.ISLOCALNAME));
					send(aclMessage);

					// System.out.println("la camera " + Id_Camera + " envoyer message Demission
					// vers "
					// + "Cam_".concat(id_camera_chef));

					fogAgent.supprime_objet_suivi_actuel(Id_Camera, Id_Objetc_Detecter_Origi,
							Id_Objetc_Detecter_Estimer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Id_Objetc_Detecter_Estimer = -1;
			// Id_Objetc_Detecter_Origi = -1;
			State = "Libre";
			setColorCameraInitiale();
			setColorLabCamera(Color.black);
		} else {
			// je suis active et j'ai perdue un autre objet != objetSuivi
			int i = 0;
			while (i < Liste_Objet_Bloque_Orig.size()) {
				if (Liste_Objet_Bloque_Orig.get(i) == id_Objet_F_S) {
					// informer le fog que cette objet est quitter mon champs
					// (id_camera,id_objetoriginal)
					fogAgent.supprime_objet_suivi_actuel(Id_Camera, id_Objet_F_S, Liste_Objet_Bloque_Estimer.get(i));
					// suprime l'objet de la liste des bjet bloque
					// System.out.println("CyclicBehaviour : La camera " + Id_Camera
					// + " la liste des objets bloqué riginal est " + Liste_Objet_Bloque_Orig);
					// System.out.println("CyclicBehaviour : La camera " + Id_Camera
					// + " la liste des objets bloqué estimer est " + Liste_Objet_Bloque_Estimer);
					Liste_Objet_Bloque_Orig.remove(i);
					Liste_Objet_Bloque_Estimer.remove(i);
					// System.out.println("Camera" + Id_Camera + " l'objet " + id_Objet_F_S + "
					// quitter mn champs");
					// System.out.println("CyclicBehaviour : La camera " + Id_Camera
					// + " la liste des objets bloqué riginal est " + Liste_Objet_Bloque_Orig);
					// System.out.println("CyclicBehaviour : La camera " + Id_Camera
					// + " la liste des objets bloqué estimer est " + Liste_Objet_Bloque_Estimer);
				}
				i++;
			}
		}
	}

	public void Selection() {
		int K = 0;
		if (Liste_Condidat.isEmpty()) {
			// System.out.println("La camera " + Id_Camera + " la liste des condidats est
			// vide");
		} else {
			// System.out.println("La camera " + Id_Camera + " la liste des condidats est
			// NON vide");
			while (K < Liste_Condidat.size() && Seuil < Seuil_Max) {
				Object Camera[] = Max_Vision_Compement();
				double SeuilCamera = (double) Camera[1];
				Seuil = Seuil + SeuilCamera;
				// System.out.println("La camera " + Id_Camera + " Envoyer Accepter vers la
				// camera " + Camera[0]);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
				Object obj[] = { "Accepter" };
				try {
					aclMessage.setContentObject(obj);
					String idd = String.valueOf(Camera[0]);
					aclMessage.addReceiver(new AID("Cam_".concat(idd), AID.ISLOCALNAME));
					send(aclMessage);

					// System.out
					// .println("la camera " + Id_Camera + " envoyer message Accepter vers " +
					// "Cam_".concat(idd));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Assistant.add((Integer) Camera[0]);
				K++;
			}
			// System.out.println("La camera " + Id_Camera + " la liste des assistantes est
			// " + Assistant
			// + " variable KK = " + K + " nombre des candidat est " +
			// Liste_Condidat.size());
			if (K < Liste_Condidat.size()) {

				while (K < Liste_Condidat.size()) {
					Object Camera[] = Liste_Condidat.get(K);
					// System.out.println(
					// "La camera " + Id_Camera + " Envoyer Non_ACCEPTE vers la camera candidat" +
					// Camera[0]);
					ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
					Object obj[] = { "Non_Accepter" };
					try {
						aclMessage.setContentObject(obj);
						String idd = String.valueOf(Camera[0]);
						aclMessage.addReceiver(new AID("Cam_".concat(idd), AID.ISLOCALNAME));
						send(aclMessage);

						// System.out.println(
						// "la camera " + Id_Camera + " envoyer message Non_Accepter vers " +
						// "Cam_".concat(idd));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					K++;
				}
			}
		}
		K = 0;
		while (K < Liste_non_Candidat.size()) {
			Object Camera[] = (Object[]) Liste_non_Candidat.get(K);
			double vision = (double) Camera[1];
			if (vision != 0) {
				// System.out.println(
				// "La camera " + Id_Camera + " Envoyer Non_accepter vers la camera non
				// candidat" + Camera[0]);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
				Object obj[] = { "Non_Accepter" };
				try {
					aclMessage.setContentObject(obj);
					String idd = (String) Camera[0];
					aclMessage.addReceiver(new AID("Cam_".concat(idd), AID.ISLOCALNAME));
					send(aclMessage);
					// System.out.println(
					// "la camera " + Id_Camera + " envoyer message Non_Accepter vers " +
					// "Cam_".concat(idd));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			K++;
		}
	}

	public Object[] Max_Vision_Compement() {
		Object Camera[] = null, Camera1[];
		double Max, vision;
		int i = 1, indic_max;
		if (Liste_Condidat.isEmpty() == false) {
			Camera = (Object[]) Liste_Condidat.getFirst();
			Max = (double) Camera[1];
			indic_max = 0;

			while (i < Liste_Condidat.size()) {
				Camera1 = (Object[]) Liste_Condidat.get(i);
				vision = (double) Camera1[1];
				if (vision < Max) {
					Camera = (Object[]) Liste_Condidat.get(i);
					indic_max = i;
				}
				i++;
			}
			Liste_Condidat.remove(indic_max);
		}
		return Camera;
	}

	public void Accepter() {
		// System.out.println("La camera " + Id_Camera + " est assistante");
		State = "Active";
		/**
		 * Demarrer le calcul du temps || Assitante
		 **/
		Liste_Temps_Objet_Actuel.put(Id_Objetc_Detecter_Origi, System.currentTimeMillis());
		Chef = Chef_probable;
		Chef_probable = -1;
		Nombre_Message_Recu = 0;
		Nombre_fois_assistant++;
		setColorCameraAssistant();
	}

	public void Non_Accepter(int ID_Objet_Recu_origi, int ID_Objet_Recu_estimer) {
		// System.out.println("La camera " + Id_Camera + " est arriter l'enregistrement
		// de vidéo (recu non_accpter)");
		State = "Echouer";
		/**
		 * Demarrer le calcul du temps
		 **/
		Liste_Temps_Objet_Echouer.put(ID_Objet_Recu_origi, System.currentTimeMillis());

		Nombre_fois_Echouer++;
		Chef = -1;
		Chef_probable = -1;
		// khayaf adirli problème, prq ki n3od echouer mana7awas nafham 3la l compteur
		// Compteur++;
		Nombre_Message_Recu = 0;
		setColorCameraEchoeur();

		int i = 0;
		boolean tr = false;
		while (i < Liste_Objet_Bloque_Orig.size() && tr == false) {
			if (Liste_Objet_Bloque_Estimer.get(i) == ID_Objet_Recu_estimer
					&& Liste_Objet_Bloque_Orig.get(i) == ID_Objet_Recu_origi) {
				tr = true;
			}
			i++;
		}

		// l'objet existe déjà

		if (tr == false) {
			// System.out.println("Non_Accepter : La camera " + Id_Camera + " la liste des
			// objets bloqué riginal est "
			// + Liste_Objet_Bloque_Orig);
			// System.out.println("Non_Accepter : La camera " + Id_Camera + " la liste des
			// objets bloqué estimer est "
			// + Liste_Objet_Bloque_Estimer);

			Liste_Objet_Bloque_Orig.add(ID_Objet_Recu_origi);
			Liste_Objet_Bloque_Estimer.add(ID_Objet_Recu_estimer);

			// System.out.println("Non_Accepter : La camera " + Id_Camera + " la liste des
			// objets bloqué riginal est "
			// + Liste_Objet_Bloque_Orig);
			// System.out.println("Non_Accepter : La camera " + Id_Camera + " la liste des
			// objets bloqué estimer est "
			// + Liste_Objet_Bloque_Estimer);
		}

		// System.out.println("Non_Accepter => La camera " + Id_Camera + " la liste des
		// objets bloqué riginal est "
		// + Liste_Objet_Bloque_Orig);
		// System.out.println("Non_Accepter => La camera " + Id_Camera + " la liste des
		// objets bloqué estimer est "
		// + Liste_Objet_Bloque_Estimer);
	}

	public void Election(int ID_Objet_Estimer_recu, double Vision_recu, int Emiteur) {

		// System.out.println(" Camera " + Id_Camera + " Methode Election : State = " +
		// State);
		// String mesg = "Camera " + Id_Camera + " Methode Election : State = " + State;
		// sortie.println(mesg);
		// sortie.close();
		// System.out.println(mesg);
		if (State.equals("Echouer") == false) {
			// System.out.println("la camera " + Id_Camera + " State = " + State + "
			// Id_Objetc_Detecter_Estimer = "
			// + Id_Objetc_Detecter_Estimer + " ID_Objet_recu = " + ID_Objet_recu);
			if (State.equals("Libre") == false && Id_Objetc_Detecter_Estimer == ID_Objet_Estimer_recu) {
				if (State.equals("Active")) {
					if (Chef == Id_Camera) {
						ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
						Object obj[] = { "Non_Accepter" };
						try {
							aclMessage.setContentObject(obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
						aclMessage.addReceiver(new AID("Cam_".concat(String.valueOf(Emiteur)), AID.ISLOCALNAME));
						send(aclMessage);
						// System.out.println("la camera " + Id_Camera + " envoyer message Non_Accepter
						// vers "
						// + "Cam_".concat(String.valueOf(Emiteur)));
					}
					Nombre_Message_Recu--;
				} else if (Vision_Max < Vision_recu || (Vision_Max == Vision_recu && Chef_probable < Emiteur)) {
					Vision_Max = Vision_recu;
					Chef_probable = Emiteur;
				} else if (Chef_probable == Id_Camera) {
					Vision_Complement = Vision - Vision_recu;
					if (Vision_Complement > 0 && Vision_recu != 0) {
						Object Camera[] = { Emiteur, Vision_Complement };
						Liste_Condidat.add(Camera);
						// System.out.println("Camera " + Id_Camera + " : ajouter la camera " + Emiteur
						// + " dans la liste des candidats");
					} else {
						Object Camera[] = { Emiteur, Vision_recu };
						Liste_non_Candidat.add(Camera);
						// System.out.println("Camera " + Id_Camera + " : ajouter la camera " + Emiteur
						// + " dans la liste Non candidats");
					}
				}
				Nombre_Message_Recu++;
				// System.out.println("Camera " + Id_Camera + " : Compteur = " + Compteur);
				if (State.equals("Active") == false) {
					if (Nombre_Message_Recu == NeighborsList.size()) {
						if (Chef_probable == Id_Camera) {
							/**
							 * Demarrer le calcul du temps || Chef
							 **/
							Liste_Temps_Objet_Actuel.put(Id_Objetc_Detecter_Origi, System.currentTimeMillis());

							Chef = Chef_probable;
							State = "Active";
							Nombre_Message_Recu = 0;
							// System.out.println("Camera " + Id_Camera
							// + " : je suis le chef, j'ai execute l'algorithme de sélection");
							setColorCameraChef();
							Selection();
							Nomrbe_fois_chef++;
						} else {
							Chef = Chef_probable;
							Nombre_Message_Recu = 0;
							Liste_Condidat.clear();
							Liste_non_Candidat.clear();
							// System.out.println("Camera " + Id_Camera + " je suis n'est pas le chef");
						}
					}
				}
			} else {
				// System.out.println("Camera " + Id_Camera + " : Envoyer Election (vision =
				// 0.0) vers camera " + Emiteur);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
				Object obj[] = { "Election", ID_Objet_Estimer_recu, 0.0, Id_Camera, };
				try {
					aclMessage.setContentObject(obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
				aclMessage.addReceiver(new AID("Cam_".concat(String.valueOf(Emiteur)), AID.ISLOCALNAME));
				send(aclMessage);
				// System.out.println("la camera " + Id_Camera + " (" + State + " id_org " +
				// Id_Objetc_Detecter_Origi
				// + ") envoyer message Election (vision = 0.0, l'objet electionner " +
				// ID_Objet_recu + ") vers "
				// + "Cam_".concat(String.valueOf(Emiteur)) + " Compteur = " + Compteur);
			}
		} else {
			/*
			 * envoyer message election vers l'emiteur avec une vision 0 si le message
			 * contient un objet qui existe dans la liste ds objets bloque, envoyer eussi
			 * message election vers l'emiteur par ce que il existe un chef qui se bloque ce
			 * dernier || madamni mazelt mabloque 3la l'objet hada c à d mazal kayan ou moin
			 * le chef li ga3ad yataba3 fih donc dans tout les cas, j'ai envoyer selement le
			 * message election avec vision 0
			 */
			if (Vision_recu != 0) {
				// System.out.println(Compteur + " **** Camera " + Id_Camera
				// + " : Envoyer Election (vision = 0.0) vers camera " + Emiteur);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
				Object obj[] = { "Election", ID_Objet_Estimer_recu, 0.0, Id_Camera };
				try {
					aclMessage.setContentObject(obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
				aclMessage.addReceiver(new AID("Cam_".concat(String.valueOf(Emiteur)), AID.ISLOCALNAME));
				send(aclMessage);
				// System.out.println("** la camera " + Id_Camera + " (" + State
				// + ") envoyer message Election (vision = 0.0, l'objet electionner " +
				// ID_Objet_recu + ") vers "
				// + "Cam_".concat(String.valueOf(Emiteur)) + " Compteur = " + Compteur);
			}
		}
	}

	public void Election_Preparation() {
		// System.out.println("Camera " + Id_Camera + " : execute preparation
		// d'election");
		this.Chef = -1;
		this.Chef_probable = Id_Camera;
		this.State = "Candidat";
		Nombre_fois_Candidat++;
		this.Nombre_Message_Recu = 0;
		this.Liste_Condidat.clear();
		this.Liste_non_Candidat.clear();
		this.Assistant.clear();
		this.Vision = Get_Vision(cordX, cordY);
		this.Vision_Max = Vision;
		this.Seuil = Vision;
	}

	public double Get_Vision(int x, int y) {
		double distance = Math.sqrt(Math.pow((X[0] - x), 2.0) + Math.pow((Y[0] - y), 2.0));
		// System.out.println("la distance max est " + Distance_Max);
		double prctg = 100 - (distance * 100) / Distance_Max;
		// System.out.println(
		// "Camera " + Id_Camera + " : la vision de cette objet est :" + prctg + "% x: "
		// + x + " y: " + y);
		return prctg;
	}

	public void detection_Objet(int x, int y, int id_objet, LinkedList<Double> VecteurCar, boolean vecteur_changer) {

		// normalment j'ai utilise pas l'identificateur original de l'objt
		// mais dans cette etape, l'algorithme de suivi qui travaille cette opération
		// (filtre de kalman)
		if (State.equals("Echouer") && Liste_Objet_Bloque_Orig.contains(id_objet) == true) {
			// j'ai rien fair pour le moment psq il existe déjà
		} else {
			if (State.equals("Libre") || State.equals("Echouer")
					|| (State.equals("Active") && Id_Camera != Chef && Id_Objetc_Detecter_Origi != id_objet)) {
				if (State.equals("Active")) {
					/**
					 * Arrêtez le calcul du temps || Assistante
					 **/
					long Temps_mort = System.currentTimeMillis();
					long Temps_depart = Liste_Temps_Objet_Actuel.get(Id_Objetc_Detecter_Origi);
					long temps = Temps_mort - Temps_depart;
					Temps_Totale_Active = Temps_Totale_Active + temps;
					Liste_Temps_Objet_Actuel.remove(Id_Objetc_Detecter_Origi);
					// System.out.println("4- Temps camera Active || Assistante " + Id_Camera + "
					// ==> "
					// + (Temps_mort - Temps_depart));

					if (Liste_Temps_Objet_Assistant.containsKey(Id_Objetc_Detecter_Origi) == false) {
						Liste_Temps_Objet_Assistant.put(Id_Objetc_Detecter_Origi, temps);
					} else {
						long Temporal = Liste_Temps_Objet_Assistant.get(Id_Objetc_Detecter_Origi);
						Temporal = Temporal + temps;
						Liste_Temps_Objet_Assistant.put(Id_Objetc_Detecter_Origi, Temporal);
					}
					// System.out
					// .println("Camera " + Id_Camera + " : Envoyer le message de Demission vers le
					// Chef" + Chef);
					ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
					Object obj[] = { "Demission", Id_Camera };
					try {
						aclMessage.setContentObject(obj);
						String id_camera_chef = String.valueOf(Chef);
						aclMessage.addReceiver(new AID("Cam_".concat(id_camera_chef), AID.ISLOCALNAME));
						send(aclMessage);
						// System.out.println("la camera " + Id_Camera + " envoyer message Demission
						// vers "
						// + "Cam_".concat(id_camera_chef));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Election_Preparation();
				// envoyer message vers le FOG
				// System.out.println(
				// "Camera " + Id_Camera + " : Envoyer le message de Request Id Objet vers le
				// Fog" + id_Fog);
				ACLMessage aclMessage1 = new ACLMessage(ACLMessage.REQUEST);
				Object obj1[] = { "Request_Id_Objet", VecteurCar, Id_Camera, cordX, cordY, id_objet };
				try {
					aclMessage1.setContentObject(obj1);
					aclMessage1.addReceiver(new AID("Fog_".concat(String.valueOf(id_Fog)), AID.ISLOCALNAME));
					send(aclMessage1);

					// System.out.println("la camera " + Id_Camera + " envoyer message
					// Request_Id_Objet vers "
					// + "Fog_".concat(String.valueOf(id_Fog)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Id_Objetc_Detecter_Origi = id_objet;
			} else {
				// envoyer le vecteur vers le fog si le vecteur de l'objet qui j'ai suivi ca
				// change
				if (vecteur_changer && Id_Objetc_Detecter_Origi == id_objet) {
					ACLMessage aclMessage1 = new ACLMessage(ACLMessage.REQUEST);
					Object obj1[] = { "Update_Vecteur", VecteurCar, Id_Objetc_Detecter_Estimer, Id_Camera, cordX, cordY,
							Id_Objetc_Detecter_Origi };
					try {
						aclMessage1.setContentObject(obj1);
						aclMessage1.addReceiver(new AID("Fog_".concat(String.valueOf(id_Fog)), AID.ISLOCALNAME));
						send(aclMessage1);
						// System.out.println("la camera " + Id_Camera + " envoyer message
						// Update_Vecteur vers "
						// + "Fog_".concat(String.valueOf(id_Fog)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

	public void setColorCameraChef() {
		Color color = Color.green;
		if (Id_Camera == 1) {
			mySystem.setColorCam1(color);
			mySystem.setObjCam1("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 2) {
			mySystem.setColorCam2(color);
			mySystem.setObjCam2("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 3) {
			mySystem.setColorCam3(color);
			mySystem.setObjCam3("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 4) {
			mySystem.setColorCam4(color);
			mySystem.setObjCam4("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 5) {
			mySystem.setColorCam5(color);
			mySystem.setObjCam5("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 6) {
			mySystem.setColorCam6(color);
			mySystem.setObjCam6("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 7) {
			mySystem.setColorCam7(color);
			mySystem.setObjCam7("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 8) {
			mySystem.setColorCam8(color);
			mySystem.setObjCam8("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 9) {
			mySystem.setColorCam9(color);
			mySystem.setObjCam9("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 10) {
			mySystem.setColorCam10(color);
			mySystem.setObjCam10("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 11) {
			mySystem.setColorCam11(color);
			mySystem.setObjCam11("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 12) {
			mySystem.setColorCam12(color);
			mySystem.setObjCam12("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 13) {
			mySystem.setColorCam13(color);
			mySystem.setObjCam13("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 14) {
			mySystem.setColorCam14(color);
			mySystem.setObjCam14("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 15) {
			mySystem.setColorCam15(color);
			mySystem.setObjCam15("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 16) {
			mySystem.setColorCam16(color);
			mySystem.setObjCam16("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 17) {
			mySystem.setColorCam17(color);
			mySystem.setObjCam17("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 18) {
			mySystem.setColorCam18(color);
			mySystem.setObjCam18("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 19) {
			mySystem.setColorCam19(color);
			mySystem.setObjCam19("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 20) {
			mySystem.setColorCam20(color);
			mySystem.setObjCam20("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 21) {
			mySystem.setColorCam21(color);
			mySystem.setObjCam21("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 22) {
			mySystem.setColorCam22(color);
			mySystem.setObjCam22("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 23) {
			mySystem.setColorCam23(color);
			mySystem.setObjCam23("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 24) {
			mySystem.setColorCam24(color);
			mySystem.setObjCam24("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		}
	}

	public void setColorCameraAssistant() {
		Color color = Color.ORANGE;
		if (Id_Camera == 1) {
			mySystem.setColorCam1(color);
			mySystem.setObjCam1("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 2) {
			mySystem.setColorCam2(color);
			mySystem.setObjCam2("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 3) {
			mySystem.setColorCam3(color);
			mySystem.setObjCam3("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 4) {
			mySystem.setColorCam4(color);
			mySystem.setObjCam4("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 5) {
			mySystem.setColorCam5(color);
			mySystem.setObjCam5("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 6) {
			mySystem.setColorCam6(color);
			mySystem.setObjCam6("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 7) {
			mySystem.setColorCam7(color);
			mySystem.setObjCam7("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 8) {
			mySystem.setColorCam8(color);
			mySystem.setObjCam8("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 9) {
			mySystem.setColorCam9(color);
			mySystem.setObjCam9("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State, Liste_Objet_Bloque_Orig,
					Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 10) {
			mySystem.setColorCam10(color);
			mySystem.setObjCam10("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 11) {
			mySystem.setColorCam11(color);
			mySystem.setObjCam11("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 12) {
			mySystem.setColorCam12(color);
			mySystem.setObjCam12("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 13) {
			mySystem.setColorCam13(color);
			mySystem.setObjCam13("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 14) {
			mySystem.setColorCam14(color);
			mySystem.setObjCam14("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 15) {
			mySystem.setColorCam15(color);
			mySystem.setObjCam15("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 16) {
			mySystem.setColorCam16(color);
			mySystem.setObjCam16("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 17) {
			mySystem.setColorCam17(color);
			mySystem.setObjCam17("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 18) {
			mySystem.setColorCam18(color);
			mySystem.setObjCam18("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 19) {
			mySystem.setColorCam19(color);
			mySystem.setObjCam19("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 20) {
			mySystem.setColorCam20(color);
			mySystem.setObjCam20("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 21) {
			mySystem.setColorCam21(color);
			mySystem.setObjCam21("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 22) {
			mySystem.setColorCam22(color);
			mySystem.setObjCam22("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 23) {
			mySystem.setColorCam23(color);
			mySystem.setObjCam23("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 24) {
			mySystem.setColorCam24(color);
			mySystem.setObjCam24("O ".concat(String.valueOf(Id_Objetc_Detecter_Estimer)), State,
					Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		}
	}

	public void setColorCameraEchoeur() {
		Color color = Color.RED;
		if (Id_Camera == 1) {
			mySystem.setColorCam1(color);
			mySystem.setObjCam1("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 2) {
			mySystem.setColorCam2(color);
			mySystem.setObjCam2("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 3) {
			mySystem.setColorCam3(color);
			mySystem.setObjCam3("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 4) {
			mySystem.setColorCam4(color);
			mySystem.setObjCam4("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 5) {
			mySystem.setColorCam5(color);
			mySystem.setObjCam5("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 6) {
			mySystem.setColorCam6(color);
			mySystem.setObjCam6("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 7) {
			mySystem.setColorCam7(color);
			mySystem.setObjCam7("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 8) {
			mySystem.setColorCam8(color);
			mySystem.setObjCam8("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 9) {
			mySystem.setColorCam9(color);
			mySystem.setObjCam9("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 10) {
			mySystem.setColorCam10(color);
			mySystem.setObjCam10("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 11) {
			mySystem.setColorCam11(color);
			mySystem.setObjCam11("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 12) {
			mySystem.setColorCam12(color);
			mySystem.setObjCam12("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 13) {
			mySystem.setColorCam13(color);
			mySystem.setObjCam13("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 14) {
			mySystem.setColorCam14(color);
			mySystem.setObjCam14("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 15) {
			mySystem.setColorCam15(color);
			mySystem.setObjCam15("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 16) {
			mySystem.setColorCam16(color);
			mySystem.setObjCam16("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 17) {
			mySystem.setColorCam17(color);
			mySystem.setObjCam17("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 18) {
			mySystem.setColorCam18(color);
			mySystem.setObjCam18("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 19) {
			mySystem.setColorCam19(color);
			mySystem.setObjCam19("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 20) {
			mySystem.setColorCam20(color);
			mySystem.setObjCam20("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 21) {
			mySystem.setColorCam21(color);
			mySystem.setObjCam21("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 22) {
			mySystem.setColorCam22(color);
			mySystem.setObjCam22("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 23) {
			mySystem.setColorCam23(color);
			mySystem.setObjCam23("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 24) {
			mySystem.setColorCam24(color);
			mySystem.setObjCam24("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		}
	}

	public void setColorCameraInitiale() {
		Color color = Color.black;
		if (Id_Camera == 1) {
			mySystem.setColorCam1(color);
			mySystem.setObjCam1("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 2) {
			mySystem.setColorCam2(color);
			mySystem.setObjCam2("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 3) {
			mySystem.setColorCam3(color);
			mySystem.setObjCam3("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 4) {
			mySystem.setColorCam4(color);
			mySystem.setObjCam4("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 5) {
			mySystem.setColorCam5(color);
			mySystem.setObjCam5("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 6) {
			mySystem.setColorCam6(color);
			mySystem.setObjCam6("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 7) {
			mySystem.setColorCam7(color);
			mySystem.setObjCam7("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 8) {
			mySystem.setColorCam8(color);
			mySystem.setObjCam8("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 9) {
			mySystem.setColorCam9(color);
			mySystem.setObjCam9("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 10) {
			mySystem.setColorCam10(color);
			mySystem.setObjCam10("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 11) {
			mySystem.setColorCam11(color);
			mySystem.setObjCam11("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 12) {
			mySystem.setColorCam12(color);
			mySystem.setObjCam12("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 13) {
			mySystem.setColorCam13(color);
			mySystem.setObjCam13("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 14) {
			mySystem.setColorCam14(color);
			mySystem.setObjCam14("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 15) {
			mySystem.setColorCam15(color);
			mySystem.setObjCam15("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 16) {
			mySystem.setColorCam16(color);
			mySystem.setObjCam16("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 17) {
			mySystem.setColorCam17(color);
			mySystem.setObjCam17("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 18) {
			mySystem.setColorCam18(color);
			mySystem.setObjCam18("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 19) {
			mySystem.setColorCam19(color);
			mySystem.setObjCam19("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 20) {
			mySystem.setColorCam20(color);
			mySystem.setObjCam20("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 21) {
			mySystem.setColorCam21(color);
			mySystem.setObjCam21("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 22) {
			mySystem.setColorCam22(color);
			mySystem.setObjCam22("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 23) {
			mySystem.setColorCam23(color);
			mySystem.setObjCam23("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		} else if (Id_Camera == 24) {
			mySystem.setColorCam24(color);
			mySystem.setObjCam24("", State, Liste_Objet_Bloque_Orig, Liste_Objet_Bloque_Estimer);
		}
	}

	public void setColorLabCamera(Color color) {
		if (Id_Camera == 1) {
			mySystem.setColorLabCam1(color);
		} else if (Id_Camera == 2) {
			mySystem.setColorLabCam2(color);
		} else if (Id_Camera == 3) {
			mySystem.setColorLabCam3(color);
		} else if (Id_Camera == 4) {
			mySystem.setColorLabCam4(color);
		} else if (Id_Camera == 5) {
			mySystem.setColorLabCam5(color);
		} else if (Id_Camera == 6) {
			mySystem.setColorLabCam6(color);
		} else if (Id_Camera == 7) {
			mySystem.setColorLabCam7(color);
		} else if (Id_Camera == 8) {
			mySystem.setColorLabCam8(color);
		} else if (Id_Camera == 9) {
			mySystem.setColorLabCam9(color);
		} else if (Id_Camera == 10) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 11) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 12) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 13) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 14) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 15) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 16) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 17) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 18) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 19) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 20) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 21) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 22) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 23) {
			mySystem.setColorLabCam10(color);
		} else if (Id_Camera == 24) {
			mySystem.setColorLabCam10(color);
		}
	}

	public int[][] setCordCamera(int x[], int y[], int Cord_Cam[][]) {
		Cord_Cam[0][0] = x[1] - x[0];
		Cord_Cam[0][1] = y[1] - y[0];
		Cord_Cam[1][0] = x[2] - x[0];
		Cord_Cam[1][1] = y[2] - y[0];
		Cord_Cam[2][0] = x[0];
		Cord_Cam[2][1] = y[0];
		return Cord_Cam;
	}

	public int[][] setCordonnerObjet(int Cord_Cam[][]) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				Cord_Cam[i][j] = EquationCamera[i][j];
			}
		}

		Cord_Cam[2][0] = cordX - Cord_Cam[2][0];
		Cord_Cam[2][1] = cordY - Cord_Cam[2][1];
		return Cord_Cam;
	}

	public double[][] Converst(int e1[][]) {
		double[][] M = new double[3][2];
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j < 2; j++) {
				M[i][j] = e1[i][j];
			}
		}
		return M;
	}

	public boolean resolution(int e1[][]) {
		double temp;
		double[] s = new double[2];
		double[][] e = Converst(e1);
		int a, b;
		for (int k = 0; k < 1; k++) {
			for (a = 1 + k; a < 2; a++) {
				temp = e[k][a];
				for (b = k; b < 3; b++) {
					e[b][a] = e[b][a] * e[k][k] - e[b][k] * temp;
				}
			}
		}
		s[1] = e[2][1] / e[1][1];

		for (int i = 1; i < 2; i++) {
			for (int j = 2; j <= 2; j++) {
				e[2 - i][2 - j] *= s[2 - i];
				e[2][2 - j] -= e[2 - i][2 - j];
				e[2 - i][2 - j] = 0;
			}
			s[2 - (i + 1)] = e[2][2 - (i + 1)] / e[2 - (i + 1)][2 - (i + 1)]; // on met   jour le vecteur
		}
		double som = 0;
		for (int i = 0; i < 2; i++) {
			som = som + s[i];
		}
		if (som > 1 || s[0] < 0 || s[1] < 0) {
			return false;
		} else {
			return true;
		}
	}

	public void KillAgent() {
		this.takeDown();
	}

	public void runAgent() {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
			AgentController agentController = agentContainer.acceptNewAgent("Cam_".concat(String.valueOf(Id_Camera)),
					this);
			agentController.start();
			EquationCamera = setCordCamera(X, Y, EquationCamera);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}