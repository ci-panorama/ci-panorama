package fr.elecomte.ci.panorama.data.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import fr.elecomte.ci.panorama.data.model.LiveCiEntity;

/**
 * <p>
 * Common JPA Repo on LiveCiEntity with some basic features to implement, using "default"
 * methods.
 * </p>
 * 
 * @author elecomte
 * @since 0.1.0
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface LiveCiEntityRepository<T extends LiveCiEntity> extends JpaRepository<T, Long> {

	/**
	 * <p>
	 * Merge associated elements from new entity if required to existing entity. If an
	 * update to existing was done, return true. If the existing is not modified, return
	 * false.
	 * </p>
	 * 
	 * @param existingEntity
	 * @param newEntity
	 * @return true if merge was done, false if no diff was found so the existing is kept
	 */
	boolean merge(T existingEntity, T newEntity);

	/**
	 * Find an existing entity for given new one
	 * 
	 * @param correspondingEntity
	 * @return
	 */
	T findExisting(T correspondingEntity);

	/**
	 * Basic process for proper merging on existing entities using some criterias
	 * 
	 * @param newEntity
	 */
	default T mergeWithExistingAndSave(T newEntity) {

		T existingEntity = findExisting(newEntity);

		if (existingEntity != null) {

			if (merge(existingEntity, newEntity)) {

				// Update live time
				existingEntity.setLastUpdateTime(LocalDateTime.now());

				return save(existingEntity);
			}

			// Keep unchanged existing instead
			return existingEntity;
		}

		// Init times
		newEntity.setInitTime(LocalDateTime.now());
		newEntity.setLastUpdateTime(newEntity.getInitTime());

		return

		save(newEntity);
	}

}
