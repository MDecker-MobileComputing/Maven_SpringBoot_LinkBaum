package de.eldecker.spring.linkbaum.logik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.eldecker.spring.linkbaum.db.LinkBaumRepo;
import de.eldecker.spring.linkbaum.db.model.LinkBaum;


/**
 * Bean-Klasse für Geschäftslogik rund um den LinkBaum.
 */
@Service
public class LinkBaumService {


    /** Repo-Bean für Zugriff auf Link-Baum-Objekte in Redis. */
    @Autowired
    private LinkBaumRepo _linkBaumRepo;


    /**
     * {@code LinkBaum}-Objekt speichern und dabei Version auf {@code 1} setzen oder
     * um {@code +1} erhöhen.
     * <br><br>
     *
     * Diese Methode ist erforderlich, weil <i>Spring Data Redis</i> anscheinend nicht
     * das mit {@code Version} annotierte Feld befüllt/verwaltet; zum Speichern von
     * Änderungen ist es aber erforderlich, dass das geänderte Objekt eine höhere
     * Versionsnummer hat, weil sonst die {@code save()}-Methode aus dem Repo
     * eine {@code DuplicateKeyException} wirft, da intern dann ein Insert statt
     * ein Update ausgeführt wird. 
     *
     * @param linkBaum Objekt, das gespeichert werden soll.
     *
     * @return Objekt mit gesetzter oder erhöhter Version.
     */
    public LinkBaum saveMitVersion( LinkBaum linkBaum ) {

    	final Long versionAlt = linkBaum.getVersion();
    	if ( versionAlt == null ) {

    		linkBaum.setVersion( 1L );

    	} else {

    		final Long versionNeu = versionAlt + 1;
    		linkBaum.setVersion( versionNeu );
    	}

    	return _linkBaumRepo.save( linkBaum );
    }
    
}
