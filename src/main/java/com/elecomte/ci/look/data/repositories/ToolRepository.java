package com.elecomte.ci.look.data.repositories;

import java.util.List;

import com.elecomte.ci.look.data.model.Tool;
import com.elecomte.ci.look.data.model.Tool.ToolGroup;
import com.elecomte.ci.look.data.model.ToolType;

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
	 * @see com.elecomte.ci.look.data.repositories.LiveCiEntityRepository#merge(com.elecomte.ci.look.data.model.LiveCiEntity,
	 *      com.elecomte.ci.look.data.model.LiveCiEntity)
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
	 * @see com.elecomte.ci.look.data.repositories.LiveCiEntityRepository#findExisting(com.elecomte.ci.look.data.model.LiveCiEntity)
	 */
	@Override
	default Tool findExisting(Tool correspondingEntity) {
		return findByNameAndType(correspondingEntity.getName(), correspondingEntity.getType());
	}

}
