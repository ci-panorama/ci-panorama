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

		if (!newDeveloper.getFullname().equals(existingDeveloper.getFullname()) && newDeveloper.getFullname() != null) {
			existingDeveloper.setFullname(newDeveloper.getFullname());
			modified = true;
		}

		if (!newDeveloper.getTrigram().equals(existingDeveloper.getTrigram()) && newDeveloper.getTrigram() != null) {
			existingDeveloper.setTrigram(newDeveloper.getTrigram());
			modified = true;
		}

		if (!newDeveloper.getImageUrl().equals(existingDeveloper.getImageUrl()) && newDeveloper.getImageUrl() != null) {
			existingDeveloper.setImageUrl(newDeveloper.getImageUrl());
			modified = true;
		}

		if (!newDeveloper.getCompanyName().equals(existingDeveloper.getCompanyName()) && newDeveloper.getCompanyName() != null) {
			existingDeveloper.setCompanyName(newDeveloper.getCompanyName());
			modified = true;
		}

		if (!newDeveloper.getEmail().equals(existingDeveloper.getEmail()) && newDeveloper.getEmail() != null) {
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
