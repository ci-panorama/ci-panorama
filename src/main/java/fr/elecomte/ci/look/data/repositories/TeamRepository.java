package fr.elecomte.ci.look.data.repositories;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.elecomte.ci.look.data.model.Team;

public interface TeamRepository extends LiveCiEntityRepository<Team> {

	/**
	 * @param ids
	 * @return
	 */
	Team findByDevelopers(Collection<Long> ids);

	/**
	 * Default existing search from new tool values
	 * 
	 * @param name
	 * @return
	 */
	Team findByName(String name);

	/**
	 * @param existingTool
	 * @param newTool
	 * @return
	 * @see fr.elecomte.ci.look.data.repositories.LiveCiEntityRepository#merge(fr.elecomte.ci.look.data.model.LiveCiEntity,
	 *      fr.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default boolean merge(Team existingTeam, Team newTeam) {

		boolean modified = false;

		if (!newTeam.getName().equals(existingTeam.getName()) && newTeam.getName() != null) {
			existingTeam.setName(newTeam.getName());
			modified = true;
		}

		// TODO : add developer update

		return modified;
	}

	/**
	 * @param correspondingEntity
	 * @return
	 * @see fr.elecomte.ci.look.data.repositories.LiveCiEntityRepository#findExisting(fr.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default Team findExisting(Team correspondingEntity) {

		if (correspondingEntity.getName() != null) {
			return findByName(correspondingEntity.getName());
		}

		return findByDevelopers(correspondingEntity.getDevelopers().stream().map(d -> d.getId()).collect(Collectors.toSet()));
	}
}
