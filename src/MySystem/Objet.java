package MySystem;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.util.LinkedList;
import java.util.Random;
import jade.gui.GuiEvent;

class Objet extends Applet {
	int Id_Objet;
	Color ColorObjetct;
	int x, y, r;
	Color color;
	Graphics g;
	ObjetAgent objetAgent;
	GuiEvent gev;
	boolean G_D = true;
	int Derection;
	MainPrencipale mainPrencipale;
	// int TempSleep;
	// Label Nom_Object = new Label();

	public int getId() {
		return Id_Objet;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Objet(int Id_Objet, int x, int y, int r, Color C, LinkedList<FogAgent> liste_Fog, int Derection,
			MainPrencipale mainPrencipale) {
		this.Id_Objet = Id_Objet;
		this.x = x;
		this.y = y;
		this.r = r;
		this.ColorObjetct = C;
		this.Derection = Derection;
		this.mainPrencipale = mainPrencipale;
		String id_Objet_Agent = String.valueOf(Id_Objet);
		ObjetAgent objetAgent = new ObjetAgent(id_Objet_Agent, liste_Fog);
		objetAgent.runAgent();
		this.objetAgent = objetAgent;
		// this.TempSleep = temps;
	}

	public int Randome(int Min, int Max) {
		Random r = new Random();
		int valeur = Min + (r.nextInt(Max - Min));
		return valeur;
	}

	public void move(int TempSleep) {

		if (Derection == 1) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					x++;
					y++;
				} else if (valeur == 2) {
					x++;
					y++;
				} else if (valeur == 3) {
					x++;
					y--;
				} else if (valeur == 4) {
					x++;
					y--;
				} else if (valeur == 5) {
					x++;
					y++;
				} else if (valeur == 6) {
					x++;
					y--;
				}
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {

				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				// mainPrencipale.ListeObjet.remove(this);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			}
		} else if (Derection == 2) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					x--;
					y--;
				} else if (valeur == 2) {
					x--;
					y++;
				} else if (valeur == 3) {
					x--;
					y++;
				} else if (valeur == 4) {
					x--;
					y--;
				} else if (valeur == 5) {
					x--;
					y--;
				} else if (valeur == 6) {
					x--;
					y++;
				}
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				// mainPrencipale.ListeObjet.remove(this);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			}
		} else if (Derection == 3) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					y++;
					x++;
				} else if (valeur == 2) {
					x--;
					y++;
				} else if (valeur == 3) {
					y++;
					x--;
				} else if (valeur == 4) {
					x++;
					y++;
				} else if (valeur == 5) {
					x--;
					y++;
				} else if (valeur == 6) {
					x++;
					y++;
				}
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				// mainPrencipale.ListeObjet.remove(this);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			}
		} else if (Derection == 4) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					x++;
				} else if (valeur == 2) {
					x++;
					y++;
				} else if (valeur == 3) {
					x++;
				} else if (valeur == 4) {
					x++;
					y++;
				} else if (valeur == 5) {
					x++;
					y++;
					// y++;
				} else if (valeur == 6) {
					x++;
				}
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				// mainPrencipale.ListeObjet.remove(this);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			}

		} else if (Derection == 5) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					x++;
				} else if (valeur == 2) {
					x++;
					// y++;
				} else if (valeur == 3) {
					x++;
				} else if (valeur == 4) {
					x++;
					y--;
				} else if (valeur == 5) {
					x++;
					y--;
				} else if (valeur == 6) {
					x++;
					y--;
				}
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				// mainPrencipale.ListeObjet.remove(this);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
					// System.out.println("Objet "+Id_Objet+" temps sleep = "+TempSleep);
				} catch (Exception e) {
				}
			}
		} else if (Derection == 6) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					x--;
				} else if (valeur == 2) {
					x--;
					// y++;
				} else if (valeur == 3) {
					x--;
				} else if (valeur == 4) {
					x--;
					y--;
				} else if (valeur == 5) {
					x--;
					y--;
				} else if (valeur == 6) {
					x--;
					y--;
				}
				try {
					Thread.sleep(TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
				} catch (Exception e) {
				}
			}
		} else if (Derection == 7) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					x--;
				} else if (valeur == 2) {
					x--;
					// y++;
				} else if (valeur == 3) {
					x--;
				} else if (valeur == 4) {
					x--;
					y++;
				} else if (valeur == 5) {
					x--;
					y++;
				} else if (valeur == 6) {
					x--;
					y++;
				}
				try {
					Thread.sleep(TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
				} catch (Exception e) {
				}
			}
		} else if (Derection == 8) {
			if (x <= 1530 && x >= 20 && y <= 730 && y >= 200) {
				int valeur = Randome(1, 7);
				if (valeur == 1) {
					y--;
					x--;
				} else if (valeur == 2) {
					x++;
					y--;
				} else if (valeur == 3) {
					y--;
					x--;
				} else if (valeur == 4) {
					x++;
					y--;
				} else if (valeur == 5) {
					x--;
					y--;
				} else if (valeur == 6) {
					x++;
					y--;
				}
				try {
					Thread.sleep(TempSleep);
				} catch (Exception e) {
				}
			} else {
				mainPrencipale.calcule_temps_vie_objet(Id_Objet, System.currentTimeMillis());
				objetAgent.KillAgent();
				mainPrencipale.delete_objet(Id_Objet);
				remove(this);
				mainPrencipale.crer_objet();
				try {
					Thread.sleep(TempSleep);
				} catch (Exception e) {
				}
			}
		}

		// envoyer les cordonnées vers l'agent coresspandent
		// System.out.println("X = "+x+"|| Y = "+y);
		gev = new GuiEvent(this, 1);
		gev.addParameter(x);
		gev.addParameter(y);
		objetAgent.onGuiEvent(gev);
	}

	public void paint(Graphics gr) {
		g = gr;
		g.setColor(ColorObjetct);
		g.fillOval(x, y, r, r);

	}
}