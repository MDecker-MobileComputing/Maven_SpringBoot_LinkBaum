package de.eldecker.spring.linkbaum.web;

import static java.lang.String.format;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.linkbaum.db.model.LinkBaum;
import de.eldecker.spring.linkbaum.logik.LinkBaumService;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
public class ThymeleafWebController {

    private Logger LOG = LoggerFactory.getLogger( ThymeleafWebController.class );

    /** Bean mit Geschäftslogik. */ 
    @Autowired
    private LinkBaumService _linkBaumService;

    
    /**
     * Seite mit Link-Baum anzeigen.
     * 
     * @param linkBaumKey Schlüssel des anzuzeigenden Link-Baums
     * 
     * @param model Objekt um Werte für Platzhalter in Template zu setzen
     * 
     * @return Template-Datei "linkbaum" oder "fehler"
     */
    @GetMapping( "/b/{linkBaumKey}" )
    public String linkBaum( @PathVariable String linkBaumKey,
                            Model model ) {
    	
        final Optional<LinkBaum> linkBaumOptional = 
        				_linkBaumService.erhoeheZugriffsZaehler( linkBaumKey );           
        if ( linkBaumOptional.isPresent() ) {
            
        	LOG.info( "Link-Baum mit Schluessel=\"{}\" gefunden.", linkBaumKey );
        	
            final LinkBaum linkBaum = linkBaumOptional.get();
                                    
            model.addAttribute( "linkbaum", linkBaum );
            
            return "linkbaum";
            
        } else {
         
            final String fehlermeldung = 
                    format( "Keinen Link-Baum mit Schluessel \"%s\" gefunden.", linkBaumKey );
                   
            LOG.error( fehlermeldung );
            
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
    }

}
