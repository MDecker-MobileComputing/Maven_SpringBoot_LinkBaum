package de.eldecker.spring.linkbaum.web;

import static java.lang.String.format;

import de.eldecker.spring.linkbaum.logik.LinkBaumService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.linkbaum.db.LinkBaumRepo;
import de.eldecker.spring.linkbaum.db.model.LinkBaum;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
public class ThymeleafWebController {

    private Logger LOG = LoggerFactory.getLogger( ThymeleafWebController.class );

    @Autowired
    private LinkBaumRepo _linkBaumRepo;

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
    	
        final Optional<LinkBaum> linkBaumOptional = _linkBaumRepo.findById( linkBaumKey );
            
        if ( linkBaumOptional.isPresent() ) {
            
            final LinkBaum linkBaum = linkBaumOptional.get();
            
            LOG.info( "Link-Baum mit ID \"{}\" gefunden.", linkBaumKey );
            
            model.addAttribute( "linkbaum", linkBaum );

            _linkBaumService.erhoeheZugriffsZaehler( linkBaumKey );
            
            return "linkbaum";
            
        } else {
         
            final String fehlermeldung = 
                    format( "Link-Baum mit ID \"%s\" nicht gefunden.", linkBaumKey );
                   
            LOG.error( fehlermeldung );
            
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
    }

}
