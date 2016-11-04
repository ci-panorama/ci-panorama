package com.elecomte.ci.look.services.processes;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;

import com.elecomte.ci.look.data.model.Tool;
import com.elecomte.ci.look.data.model.ToolType;
import com.elecomte.ci.look.data.repositories.ToolRepository;
import com.elecomte.ci.look.services.model.Record;
import com.elecomte.ci.look.services.model.ToolView;

/**
 * @author elecomte
 * @since 0.1.0
 */
public abstract class AbstractRecordProcess {

	@Autowired
	protected ToolRepository tools;

	@Autowired
	protected SemverHashGenerator semverHashGenerator;

	/**
	 * @param record
	 * @return
	 */
	protected Tool getToolForRecord(Record<?> record) {

		if(record.getSource() == null){
			return null;
		}
		
		Tool tool = toolFromView(record.getSource());

		if (tool.getVersion() != null) {
			tool.setSemverHash(this.semverHashGenerator.hashVersion(tool.getVersion()));
		}

		return this.tools.mergeWithExistingAndSave(tool);
	}

	/**
	 * @param ldt
	 * @return
	 */
	protected static long ldt(LocalDateTime ldt) {
		return ldt.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * @param value
	 * @return
	 */
	protected static LocalDateTime ldt(long value) {
		return LocalDateTime.ofInstant(new Date(value).toInstant(), ZoneId.systemDefault());
	}

	/**
	 * @param view
	 * @return
	 */
	private static Tool toolFromView(ToolView view) {
		Tool tool = new Tool();

		tool.setName(view.getName());
		tool.setVendorName(view.getVendorName());
		tool.setType(detectToolType(view));
		tool.setVersion(view.getVersion());

		return tool;
	}

	/**
	 * @param view
	 * @param production
	 * @return
	 */
	private static ToolType detectToolType(ToolView view) {

		// Basic search
		return ToolType.detectToolTypeFromValue((view.getName() + " " + view.getVendorName()).toLowerCase());
	}

}
