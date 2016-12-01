package fr.elecomte.ci.look.services.processes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elecomte.ci.look.services.model.CacheDetailsView;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Service
public class TechnicalProcess {

	@Autowired
	private BadgesCache cache;

	/**
	 * 
	 */
	public void clearCache() {
		this.cache.dropAll();
	}

	/**
	 * @return
	 */
	public CacheDetailsView getCacheDetails() {
		return new CacheDetailsView(this.cache.totalSize().intValue(), this.cache.totalEntriesLengthSum().intValue() / 1024);
	}
}
