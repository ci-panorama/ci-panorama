package fr.elecomte.ci.look.data.repositories;

import fr.elecomte.ci.look.data.model.Developer;

public interface DeveloperRepository extends LiveCiEntityRepository<Developer> {

	/**
	 * Default existing search from new developer values
	 * 
	 * @param email
	 * @return
	 */
	Developer findByEmail(String email);

	/**
	 * @param trigram
	 * @return
	 */
	Developer findByTrigram(String trigram);

	/**
	 * @param existingDeveloper
	 * @param newDeveloper
	 * @return
	 * @see fr.elecomte.ci.look.data.repositories.LiveCiEntityRepository#merge(fr.elecomte.ci.look.data.model.LiveCiEntity,
	 *      fr.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default boolean merge(Developer existingDeveloper, Developer newDeveloper) {

		boolean modified = false;

		if (newDeveloper.getFullname() != null && !newDeveloper.getFullname().equals(existingDeveloper.getFullname())) {
			existingDeveloper.setFullname(newDeveloper.getFullname());
			modified = true;
		}

		if (newDeveloper.getTrigram() != null && !newDeveloper.getTrigram().equals(existingDeveloper.getTrigram()) ) {
			existingDeveloper.setTrigram(newDeveloper.getTrigram());
			modified = true;
		}

		if (newDeveloper.getImageUrl() != null && !newDeveloper.getImageUrl().equals(existingDeveloper.getImageUrl()) ) {
			existingDeveloper.setImageUrl(newDeveloper.getImageUrl());
			modified = true;
		}

		if (newDeveloper.getCompanyName() != null && !newDeveloper.getCompanyName().equals(existingDeveloper.getCompanyName()) ) {
			existingDeveloper.setCompanyName(newDeveloper.getCompanyName());
			modified = true;
		}

		if (newDeveloper.getEmail() != null && !newDeveloper.getEmail().equals(existingDeveloper.getEmail()) ) {
			existingDeveloper.setEmail(newDeveloper.getEmail());
			modified = true;
		}

		return modified;
	}

	/**
	 * @param correspondingEntity
	 * @return
	 * @see fr.elecomte.ci.look.data.repositories.LiveCiEntityRepository#findExisting(fr.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default Developer findExisting(Developer correspondingEntity) {

		if (correspondingEntity.getEmail() != null) {
			return findByEmail(correspondingEntity.getEmail());
		}

		return findByTrigram(correspondingEntity.getTrigram());
	}
}
