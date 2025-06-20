package de.eldecker.spring.linkbaum.logik;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.linkbaum.db.LinkBaumRepo;
import de.eldecker.spring.linkbaum.db.model.LinkBaum;
import de.eldecker.spring.linkbaum.db.model.LinkEintrag;

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
        LOG.info( "Anzahl Link-Bäume in Datenbank: {}", anzahlVorher );
        if ( anzahlVorher > 0 ) {

            LOG.info( "Es sind bereits Link-Bäume in der Datenbank, lade deshalb keine Demo-Daten." ); 
            
        } else {

            final LinkEintrag eintrag1 = new LinkEintrag( "Facebook" , "https://www.facebook.com/DHBWKarlsruhe"                   );
            final LinkEintrag eintrag2 = new LinkEintrag( "LinkedIn" , "https://www.linkedin.com/school/dhbwkarlsruhe/posts/"     );
            final LinkEintrag eintrag3 = new LinkEintrag( "Instagram", "https://www.instagram.com/dhbwkarlsruhe/"                 );
            final LinkEintrag eintrag4 = new LinkEintrag( "Web-Seite", "https://www.karlsruhe.dhbw.de/startseite.html"            );
            final LinkEintrag eintrag5 = new LinkEintrag( "YouTube"  , "https://www.youtube.com/channel/UCe5bTJ_lECQ7DiU_NXQMilQ" );
            
            LinkBaum linkBaum1 = new LinkBaum( "dhbw-ka", "DHBW Karlsruhe", "Duale Hochschule Baden-Württemberg Karlsruhe" );
            linkBaum1.addLinkEintraege( eintrag1, eintrag2, eintrag3, eintrag4, eintrag5 );
            
            linkBaum1 = _linkBaumService.saveMitVersion( linkBaum1 );
            
            linkBaum1.setBeschreibung( "Duale Hochschule Baden-Württemberg in Karlsruhe" );
            
            _linkBaumService.saveMitVersion( linkBaum1 );
            
            final long anzahlNachher = _linkBaumRepo.count();
            LOG.info( "Anzahl Link-Bäume in Datenbank nach dem Laden der Demo-Daten: {}", anzahlNachher );
        }
    }
}
