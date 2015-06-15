package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.List;
import java.awt.TextField;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.sistemske_operacije.SODodajValutu;
import menjacnica.sistemske_operacije.SOIzvrsiTransakciju;
import menjacnica.sistemske_operacije.SOObrisiValutu;

public class GUIKontroler {

	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaInterface sistem;
	//private static Valuta valuta;
	//private static LinkedList<Valuta> kursnaLista;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem=new Menjacnica();	
					
					
					glavniProzor=new MenjacnicaGUI();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(
				glavniProzor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz aplikacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void sacuvajUFajl() {
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

	public static void prikaziObrisiKursGUI(Valuta valuta) {
		if (valuta != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(glavniProzor, valuta);
			prozor.setLocationRelativeTo(glavniProzor.getContentPane());
			prozor.setVisible(true);
		}
	}

	public static void obrisiValutu(Valuta valuta) {
		try {
			
			sistem.obrisiValutu(valuta);

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
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			glavniProzor.prikaziSveValute(sistem.vratiKursnuListu());

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
					e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(){
		Valuta valuta=new Valuta();
		
		IzvrsiZamenuGUI frame=new IzvrsiZamenuGUI(glavniProzor, valuta);
		frame.setLocationRelativeTo(glavniProzor);
		frame.setVisible(true);
	}
	
	public static void izvrsiZamenu(JTextField textFieldIznos,JRadioButton rdbtnProdaja,JTextField textFieldKonacniIznos){
		try{
			Valuta valuta=new Valuta();
			double konacniIznos = 
					sistem.izvrsiTransakciju(valuta,rdbtnProdaja.isSelected(), 
							Double.parseDouble(textFieldIznos.getText()));
		
			textFieldKonacniIznos.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}

	
	
	

	
}
