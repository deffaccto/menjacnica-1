package menjacnica.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {

	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaInterface sistem;

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(
				glavniProzor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
					e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				glavniProzor.prikaziSveValute(sistem.vratiKursnuListu());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
					e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(glavniProzor);
		prozor.setLocationRelativeTo(glavniProzor.getContentPane());
		prozor.setVisible(true);
	}

	private void prikaziObrisiKursGUI(Valuta valuta) {
		if (valuta != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(glavniProzor, valuta);
			prozor.setLocationRelativeTo(glavniProzor.getContentPane());
			prozor.setVisible(true);
		}
	}

	public static void obrisiValutu(Valuta valuta) {
		try {
			glavniProzor.sistem.obrisiValutu(valuta);

			glavniProzor.prikaziSveValute(sistem.vratiKursnuListu());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
					e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra,
			double prodajni, double kupovni, double srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);

			// Dodavanje valute u kursnu listu
			glavniProzor.sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			glavniProzor.prikaziSveValute(sistem.vratiKursnuListu());

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
					e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	
}
