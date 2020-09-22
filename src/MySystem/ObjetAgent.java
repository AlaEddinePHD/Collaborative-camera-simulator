package MySystem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import jade.core.AID;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class ObjetAgent extends GuiAgent {
	String id;
	// int CordRect1[] = { 20, 200, 750, 530 };
	// int CordRect2[] = { 780, 200, 750, 530 };

	int Triangle1_X[] = { 20, 770, 20 };
	int Triangle1_Y[] = { 200, 200, 730 };

	int Triangle2_X[] = { 20, 770, 770 };
	int Triangle2_Y[] = { 730, 200, 730 };

	int Triangle3_X[] = { 780, 1530, 780 };
	int Triangle3_Y[] = { 200, 200, 730 };

	int Triangle4_X[] = { 1530, 780, 1530 };
	int Triangle4_Y[] = { 200, 730, 730 };

	private int EquationTriangle1[][] = new int[3][2];
	private int EquationTriangle2[][] = new int[3][2];
	private int EquationTriangle3[][] = new int[3][2];
	private int EquationTriangle4[][] = new int[3][2];
	private int EquationTriangle_Tempo[][] = new int[3][2];

	LinkedList<Double> Vecteur_Caracteristique = new LinkedList<Double>();
	LinkedList<FogAgent> liste_Fog = new LinkedList<>();
	// boolean changer = true;
	boolean autorise = true;
	int cordX, cordY;

	public int Decision_Zone(int cordX, int cordY) {
		int Zone = 0;
		EquationTriangle_Tempo = setCordonnerObjet(EquationTriangle_Tempo, EquationTriangle1, cordX, cordY);
		if (resolution(EquationTriangle_Tempo)) {
			// System.out.println("L'objet : " + id + " est dans la zone 1 dans le traingle
			// 1");
			Zone = 1;
		} else {
			EquationTriangle_Tempo = setCordonnerObjet(EquationTriangle_Tempo, EquationTriangle2, cordX, cordY);
			if (resolution(EquationTriangle_Tempo)) {
				// System.out.println("L'objet : " + id + " est dans la zone 1 dans le traingle
				// 2");
				Zone = 1;
			} else {
				EquationTriangle_Tempo = setCordonnerObjet(EquationTriangle_Tempo, EquationTriangle3, cordX, cordY);
				if (resolution(EquationTriangle_Tempo)) {
					// System.out.println("L'objet : " + id + " est dans la zone 2 dans le traingle
					// 3");
					Zone = 2;
				} else {
					EquationTriangle_Tempo = setCordonnerObjet(EquationTriangle_Tempo, EquationTriangle4, cordX, cordY);
					if (resolution(EquationTriangle_Tempo)) {
						// System.out.println("L'objet : " + id + " est dans la zone 2 dans le traingle
						// 4");
						Zone = 2;
					}
				}
			}
		}
		return Zone;
	}

	public ObjetAgent(String id, LinkedList<FogAgent> liste_Fog) {
		this.id = id;
		this.liste_Fog = liste_Fog;
	}

	public int[][] setCordonnerObjet(int Cord_Cam[][], int EquationCamera[][], int cordX, int cordY) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				Cord_Cam[i][j] = EquationCamera[i][j];
			}
		}

		Cord_Cam[2][0] = cordX - Cord_Cam[2][0];
		Cord_Cam[2][1] = cordY - Cord_Cam[2][1];
		return Cord_Cam;
	}

	public int[][] setCordTraingle(int x[], int y[], int Cord_Triangle[][]) {
		Cord_Triangle[0][0] = x[1] - x[0];
		Cord_Triangle[0][1] = y[1] - y[0];
		Cord_Triangle[1][0] = x[2] - x[0];
		Cord_Triangle[1][1] = y[2] - y[0];
		Cord_Triangle[2][0] = x[0];
		Cord_Triangle[2][1] = y[0];
		return Cord_Triangle;
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

	public int Randome(int Min, int Max) {
		Random r = new Random();
		int valeur = Min + (r.nextInt(Max - Min));
		return valeur;
	}

	public LinkedList<Double> New_Vecteur() {
		LinkedList<Double> new_vecteur = new LinkedList<>();
		int i = 0;
		int Case_vecteur = Randome(1, Vecteur_Caracteristique.size());
		while (i < Vecteur_Caracteristique.size()) {

			if (i == Case_vecteur) {
				new_vecteur.add((double) Randome(1, 255));
			} else {
				new_vecteur.add(Vecteur_Caracteristique.get(i));
			}
			i++;
		}
		return new_vecteur;
	}

	protected void setup() {
		System.out.println("Démarrage de l'agent : " + this.getAID().getName());

		EquationTriangle1 = setCordTraingle(Triangle1_X, Triangle1_Y, EquationTriangle1);
		EquationTriangle2 = setCordTraingle(Triangle2_X, Triangle2_Y, EquationTriangle2);
		EquationTriangle3 = setCordTraingle(Triangle3_X, Triangle3_Y, EquationTriangle3);
		EquationTriangle4 = setCordTraingle(Triangle4_X, Triangle4_Y, EquationTriangle4);

		// création des vecteurs des caractéristiques de façon aléatoire
		int i = 0;
		while (i < 15) {
			Vecteur_Caracteristique.add((double) Randome(1, 255));
			i++;
		}
	}

	protected void onGuiEvent(GuiEvent ev) {
		switch (ev.getType()) {
		case 1:
			int cordX = (int) ev.getParameter(0);
			int cordY = (int) ev.getParameter(1);
			this.cordX = cordX;
			this.cordY = cordY;
			// sauvgarder les vercteurs original das les listes des objets recu de
			// chaque Fog (comme un repaire)
			// creer le vecteur
			int Zone = Decision_Zone(cordX, cordY);
			LinkedList vecteurEnvoyer = new LinkedList<>();
			vecteurEnvoyer.add(0, Integer.valueOf(id));
			vecteurEnvoyer.add(1, Integer.valueOf(id));
			vecteurEnvoyer.add(2, 0);
			vecteurEnvoyer.add(3, cordX);
			vecteurEnvoyer.add(4, cordY);

			for (int j = 0; j < Vecteur_Caracteristique.size(); j++) {
				vecteurEnvoyer.addLast(Vecteur_Caracteristique.get(j));
			}
			if (autorise) {
				if (Zone == 1) {
					liste_Fog.get(0).add_Vecteur(vecteurEnvoyer, 1);
				} else if (Zone == 2) {
					liste_Fog.get(1).add_Vecteur(vecteurEnvoyer, 1);
				}
				autorise = false;
			}
			// je sais pas 3lah dertha !!!
			// else {
			// liste_Fog.get(0).add_Vecteur(vecteurEnvoyer, 2);
			// liste_Fog.get(1).add_Vecteur(vecteurEnvoyer, 2);
			// }

			/**********************************************
			 * Eenvoyer message vers tous les agents camera
			 *********************************************/
			String X = String.valueOf(cordX);
			String Y = String.valueOf(cordY);

			int randome = Randome(1, 200);
			if (randome == 5) {
				Vecteur_Caracteristique = New_Vecteur();
			}

			Object[] obj = { "set_coordinates_object", X, Y, id, Vecteur_Caracteristique, true };
			int Zone1 = Decision_Zone(cordX, cordY);
			// teste la zone destinée
			if (Zone1 == 1) {
				// System.out.println("zone 1");
				// l'objet est dans la zone 1
				for (int i = 1; i <= 12; i++) {
					String j = String.valueOf(i);
					ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
					aclMessage.addReceiver(new AID("Cam_".concat(j), AID.ISLOCALNAME));
					try {
						aclMessage.setContentObject(obj);
					} catch (IOException e) {
						e.printStackTrace();
					}
					send(aclMessage);
					// System.out
					// .println("l'objet " + id + " envoyer message set_coordinates_object vers
					// Cam_".concat(j));
				}
			} else if (Zone1 == 2) {
				// System.out.println("zone 2");
				// l'objet est dans la zone 2
				for (int i = 13; i <= 24; i++) {
					String j = String.valueOf(i);
					ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
					aclMessage.addReceiver(new AID("Cam_".concat(j), AID.ISLOCALNAME));
					try {
						aclMessage.setContentObject(obj);
					} catch (IOException e) {
						e.printStackTrace();
					}
					send(aclMessage);
					// System.out
					// .println("l'objet " + id + " envoyer message set_coordinates_object vers
					// Cam_".concat(j));
				}
			} else {
				// System.out.println("L'objet : " + id + " pas trouvé dans aucun champ");
			}
			break;
		}
	}

	public void KillAgent() {
		this.doDelete();
	}

	public void runAgent() {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
			AgentController agentController = agentContainer.acceptNewAgent("Objet_".concat(id), this);
			agentController.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}