package fr.elecomte.ci.panorama.data.repositories;

import java.util.List;

import fr.elecomte.ci.panorama.data.model.Tool;
import fr.elecomte.ci.panorama.data.model.ToolType;
import fr.elecomte.ci.panorama.data.model.Tool.ToolGroup;

/**
 * @author elecomte
 * @since 0.1.0
 */
public interface ToolRepository extends LiveCiEntityRepository<Tool> {

	/**
	 * @param name
	 * @return
	 */
	List<Tool> findByNameOrderBySemverHashAsc(String name);

	/**
	 * @param type
	 * @return
	 */
	List<Tool> findByTypeOrderBySemverHashAsc(ToolType type);

	/**
	 * Default existing search from new tool values
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	Tool findByNameAndType(String name, ToolType type);

	/**
	 * @return
	 */
	List<ToolGroup> findToolGroups();

	/**
	 * @param existingTool
	 * @param newTool
	 * @return
	 * @see fr.elecomte.ci.panorama.data.repositories.LiveCiEntityRepository#merge(fr.elecomte.ci.panorama.data.model.LiveCiEntity,
	 *      fr.elecomte.ci.panorama.data.model.LiveCiEntity)
	 */
	@Override
	default boolean merge(Tool existingTool, Tool newTool) {

		boolean modified = false;

		if (!newTool.getVersion().equals(existingTool.getVersion()) && newTool.getVersion() != null) {
			existingTool.setVersion(newTool.getVersion());
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
	default Tool findExisting(Tool correspondingEntity) {
		return findByNameAndType(correspondingEntity.getName(), correspondingEntity.getType());
	}

}
