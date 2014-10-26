//Figura 27.2: SiteSeletor.java
// Carregando um documento de um URL em um navegador.

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.applet.AppletContext;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SiteSelector extends JApplet
{
	private HashMap< String, URL > sites; // nomes e URLs de sites
	private ArrayList< String > siteNames; // nomes dos sites
	private JList siteChooser; // lista de sites a escolher

	//lê os parâmetros e configura a GUI
	public void init()
	{
		sites = new HashMap< String, URL >(); // cria HashMap
		siteNames = new ArrayList< String >(); // cria a ArrayList

		//obtém os parâmetros do documento XHTML
		getSitesFromHTMLParameters();

		//cria componentes GUI e a interface de layout
		add( new JLabel( "Choose a site to browse" ), BorderLayout.NORTH );

		siteChooser = new JList( siteNames.toArray() ); // preenche a JList
		siteChooser.addListSelectionListener(
			new ListSelectionListener() //classe interna anônima
			{
				//vai ao site selecionado pelo usuário
				public void valueChanged( ListSelectionEvent event)
				{
					// obtém o nome do site selecionado
					Object object = siteChooser.getSelectedValue();

					//utiliza o nome d site para localizar o URL correspondente
					URL newDocument  = sites.get( object );
					AppletContext browser = getAppletContext();

					//instrui o contêiner de applets a mudar as páginas
					browser.showDocument( newDocument ); // eu acho que é uma palavra composta, verificar caso dê erro.
				}//fim do método valueChanged
			}//fim da classe interna anônima
		); //fim da chamada para addListSelectionListener

		add( new JScrollPane( siteChooser ), BorderLayout.CENTER );
	} // FIM do método init

	//obtém os parâmetros do documento XHTML
	private void getSitesFromHTMLParameters()
	{
		String title; //titulo do site
		String location; // localização do site
		URL url; // URL da localização
		int counter = 0; //conta número de sites

		title = getParameter( "title" + counter ); // obtém o primeiro título do site

		//faz um loop até que não haja mais parâmetros no documento XTHML
		while( title != null )
		{
			//obtém a localização do site
			location = getParameter( "location" + counter );

			try // coloca titulo/URL no HashMap e título na ArrayList
			{
				url = new URL( location ); // converte a localização em URL
				sites.put( title, url ); // coloca titulo/URL no MashMap
				siteNames.add( title ); // coloca o titulo na ArrayList
			}//fim do try
			catch ( MalformedURLException urlException )
			{
				urlException.printStackTrace();
			}// fim do catch

			++counter;
			title = getParameter( "title" + counter ); // obtém o próximo título do site
		}// fim do while
	} // fim do método getSitesromHTMLParameters
}// fim da classe SiteSelectior
