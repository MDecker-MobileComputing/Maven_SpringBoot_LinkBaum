package de.eldecker.spring.linkbaum.logik;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.linkbaum.db.LinkBaumRepo;
import de.eldecker.spring.linkbaum.db.model.LinkBaum;
import de.eldecker.spring.linkbaum.db.model.LinkEintrag;


/**
 * Bean erzeugt direkt nach Start der Anwendung neue Link-Bäume, wenn noch kein
 * einziger Link-Baum vorhanden ist.
 */
@Component
public class DemoDatenLader implements ApplicationRunner {

    private final static Logger LOG = LoggerFactory.getLogger( DemoDatenLader.class );
    
    @Autowired
    private LinkBaumRepo _linkBaumRepo;
    
    @Autowired
    private LinkBaumService _linkBaumService;
    
    @Override
    public void run( ApplicationArguments args ) throws Exception {
        
        final long anzahlVorher = _linkBaumRepo.count();
        LOG.info( "Anzahl Link-Baeume in Datenbank: {}", anzahlVorher );
        if ( anzahlVorher > 0 ) {

            LOG.info( "Es sind bereits Link-Baeume in der Datenbank, lade deshalb keine Demo-Daten." ); 
            
        } else {
            
        	erzeugeLinkBaumDHBW();
        	erzeugeLinkBaumAndroidDev();
        	
            final long anzahlNachher = _linkBaumRepo.count();
            LOG.info( "Anzahl Link-Baeume in Datenbank nach dem Laden der Demo-Daten: {}", anzahlNachher );
        }
    }
    
    private void erzeugeLinkBaumDHBW() {

        final LinkEintrag eintrag1 = new LinkEintrag( "Facebook" , "https://www.facebook.com/DHBWKarlsruhe"                   );
        final LinkEintrag eintrag2 = new LinkEintrag( "LinkedIn" , "https://www.linkedin.com/school/dhbwkarlsruhe/posts/"     );
        final LinkEintrag eintrag3 = new LinkEintrag( "Instagram", "https://www.instagram.com/dhbwkarlsruhe/"                 );
        final LinkEintrag eintrag4 = new LinkEintrag( "Web-Seite", "https://www.karlsruhe.dhbw.de/startseite.html"            );
        final LinkEintrag eintrag5 = new LinkEintrag( "YouTube"  , "https://www.youtube.com/channel/UCe5bTJ_lECQ7DiU_NXQMilQ" );
        
        final LinkBaum linkBaum = new LinkBaum( "dhbw-ka", "DHBW Karlsruhe", "Duale Hochschule Baden-Württemberg Karlsruhe" );
        linkBaum.addLinkEintraege( eintrag1, eintrag2, eintrag3, eintrag4, eintrag5 );
        
        _linkBaumService.saveMitVersion( linkBaum );
    }
    
    private void erzeugeLinkBaumAndroidDev() {
    	
    	final LinkEintrag eintrag1 = new LinkEintrag( "Blog"       , "https://android-developers.googleblog.com/"       );
    	final LinkEintrag eintrag2 = new LinkEintrag( "Nachrichten", "https://www.androidpolice.com/"                   );  
    	final LinkEintrag eintrag3 = new LinkEintrag( "API-Level"  , "https://apilevels.com/"                           );
    	final LinkEintrag eintrag4 = new LinkEintrag( "API-Doku"   , "https://developer.android.com/reference/packages" );
    	
        final LinkBaum linkBaum = new LinkBaum( "droidev", "Android-Entwicklung", "Info-Quellen für Android-Entwickler" );
        linkBaum.addLinkEintraege( eintrag1, eintrag2, eintrag3, eintrag4 );
        
        _linkBaumService.saveMitVersion( linkBaum );
    }

}
