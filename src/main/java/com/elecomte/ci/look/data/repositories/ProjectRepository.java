package com.elecomte.ci.look.data.repositories;

import java.util.List;

import com.elecomte.ci.look.data.model.Project;
import com.elecomte.ci.look.data.model.Project.ProjectGroup;
import com.elecomte.ci.look.data.model.Team;

/**
 * @author elecomte
 * @since 0.1.0
 */
public interface ProjectRepository extends LiveCiEntityRepository<Project> {

	/**
	 * @param codeName
	 * @return
	 */
	List<Project> findByCodeNameOrderBySemverHashAsc(String codeName);

	/**
	 * @param team
	 * @return
	 */
	List<Project> findByTeam(Team team);

	/**
	 * @return
	 */
	List<ProjectGroup> findProjectGroups();

	/**
	 * Default existing search from new project values
	 * 
	 * @param codeName
	 * @param version
	 * @return
	 */
	Project findByCodeNameAndVersion(String codeName, String version);

	/**
	 * @param existingProject
	 * @param newProject
	 * @return
	 * @see com.elecomte.ci.look.data.repositories.LiveCiEntityRepository#merge(com.elecomte.ci.look.data.model.LiveCiEntity,
	 *      com.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default boolean merge(Project existingProject, Project newProject) {

		boolean modified = false;

		if (!newProject.getCommonName().equals(existingProject.getCommonName())) {
			existingProject.setCommonName(newProject.getCommonName());
			modified = true;
		}

		if (!newProject.getDescription().equals(existingProject.getDescription())) {
			existingProject.setDescription(newProject.getDescription());
			modified = true;
		}

		if (!newProject.getInceptionDate().equals(existingProject.getInceptionDate())) {
			existingProject.setInceptionDate(newProject.getInceptionDate());
			modified = true;
		}

		if (!newProject.getProductionTool().equals(existingProject.getProductionTool())) {
			existingProject.setProductionTool(newProject.getProductionTool());
			modified = true;
		}

		return modified;
	}

	/**
	 * @param correspondingEntity
	 * @return
	 * @see com.elecomte.ci.look.data.repositories.LiveCiEntityRepository#findExisting(com.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default Project findExisting(Project correspondingEntity) {
		return findByCodeNameAndVersion(correspondingEntity.getCodeName(), correspondingEntity.getVersion());
	}

}
