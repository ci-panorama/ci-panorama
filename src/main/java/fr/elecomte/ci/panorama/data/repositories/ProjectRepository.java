package fr.elecomte.ci.panorama.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import fr.elecomte.ci.panorama.data.model.Project;
import fr.elecomte.ci.panorama.data.model.Team;
import fr.elecomte.ci.panorama.data.model.Project.ProjectGroup;

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
	 * Last project version
	 * 
	 * @param codeName
	 * @return
	 */
	Project findFirstByCodeNameOrderBySemverHashDesc(String codeName);

	/**
	 * Fresh project version : the last version with a build update
	 * 
	 * @param codeName
	 * @return
	 */
	Project findFreshProject(String codeName);

	/**
	 * The "fresh" version of the project regarding the specified build type = last tested
	 * version,
	 * 
	 * @param codeName
	 * @param type
	 * @return
	 */
	Project findFreshProjectForResultType(String codeName, String type);

	/**
	 * The "fresh" version of the project without any build of the specified type
	 * 
	 * @param codeName
	 * @param type
	 * @return
	 */
	Project findFreshProjectWithoutResultType(String codeName, String type);

	/**
	 * @return
	 */
	@Query(value = "select count(distinct code_name) from Project", nativeQuery = true)
	int countDifferentProjects();

	/**
	 * @param existingProject
	 * @param newProject
	 * @return
	 * @see fr.elecomte.ci.panorama.data.repositories.LiveCiEntityRepository#merge(fr.elecomte.ci.panorama.data.model.LiveCiEntity,
	 *      fr.elecomte.ci.panorama.data.model.LiveCiEntity)
	 */
	@Override
	default boolean merge(Project existingProject, Project newProject) {

		boolean modified = false;

		if (newProject.getCommonName() != null && !newProject.getCommonName().equals(existingProject.getCommonName())) {
			existingProject.setCommonName(newProject.getCommonName());
			modified = true;
		}

		if (newProject.getLanguage() != null && !newProject.getLanguage().equals(existingProject.getLanguage())) {
			existingProject.setLanguage(newProject.getLanguage());
			modified = true;
		}

		if (newProject.getDescription() != null && !newProject.getDescription().equals(existingProject.getDescription())) {
			existingProject.setDescription(newProject.getDescription());
			modified = true;
		}

		if (newProject.getInceptionDate() != null && !newProject.getInceptionDate().equals(existingProject.getInceptionDate())) {
			existingProject.setInceptionDate(newProject.getInceptionDate());
			modified = true;
		}

		if (newProject.getProductionTool() != null && !newProject.getProductionTool().equals(existingProject.getProductionTool())) {
			existingProject.setProductionTool(newProject.getProductionTool());
			modified = true;
		}

		if (newProject.getTeam() != null && !newProject.getTeam().equals(existingProject.getTeam())) {
			existingProject.setTeam(newProject.getTeam());
			modified = true;
		}

		return modified;
	}

	/**
	 * @param correspondingEntity
	 * @return
	 * @see fr.elecomte.ci.panorama.data.repositories.LiveCiEntityRepository#findExisting(fr.elecomte.ci.panorama.data.model.LiveCiEntity)
	 */
	@Override
	default Project findExisting(Project correspondingEntity) {
		return findByCodeNameAndVersion(correspondingEntity.getCodeName(), correspondingEntity.getVersion());
	}

}
