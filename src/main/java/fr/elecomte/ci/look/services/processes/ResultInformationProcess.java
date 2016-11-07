package fr.elecomte.ci.look.services.processes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.Result;
import fr.elecomte.ci.look.data.model.ResultType;
import fr.elecomte.ci.look.data.model.Tool;
import fr.elecomte.ci.look.data.repositories.ResultRepository;
import fr.elecomte.ci.look.services.model.JsonPayload;
import fr.elecomte.ci.look.services.model.ProjectResultListView;
import fr.elecomte.ci.look.services.model.ProjectView;
import fr.elecomte.ci.look.services.model.ResultRecord;
import fr.elecomte.ci.look.services.model.ResultView;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Service
public class ResultInformationProcess extends AbstractRecordProcess {

	@Autowired
	private ResultRepository results;

	@Autowired
	private ProjectInformationProcess projectInfoProcesses;

	/**
	 * @param record
	 */
	public void recordResultInformation(ResultRecord record) {

		Tool tool = getToolForRecord(record);

		Project toAssociate = this.projectInfoProcesses.getProject(record.getProject().getCode(), record.getProject().getVersion());

		// If doesn't exist yet,
		if (toAssociate == null) {

			// Create it anyway
			toAssociate = ProjectInformationProcess.projectFromView(record.getProject());
			toAssociate.setProductionTool(tool);
			toAssociate = this.projectInfoProcesses.refreshProject(toAssociate);
		}

		Result result = resultFromView(record.getPayload(), record.getType());

		// Complete associated
		result.setTool(tool);
		result.setProject(toAssociate);

		this.results.save(result);

		// Update all references (caches)
		this.projectInfoProcesses.markUpdatedProject(toAssociate);
	}

	/**
	 * @param projectCode
	 * @param projectName
	 * @param type
	 * @return
	 */
	public ProjectResultListView getProjectResults(String projectCodeName, String projectVersion, ResultType type) {

		Project project = this.projectInfoProcesses.getProject(projectCodeName, projectVersion);

		if (project == null) {
			return null;
		}

		List<Result> projectResults = type != null ? this.results.findByProjectAndTypeOrderByResultTimeDesc(project, type)
				: this.results.findByProjectOrderByResultTimeDesc(project);

		return projectResultListViewFromResults(projectResults, project, type);
	}

	/**
	 * @param results
	 * @param project
	 * @return
	 */
	static ProjectResultListView projectResultListViewFromResults(List<Result> results, Project project, ResultType type) {

		ProjectResultListView view = new ProjectResultListView();

		view.setResults(results.stream().map(ResultInformationProcess::viewFromResult).collect(Collectors.toList()));
		view.setProject(new ProjectView(project.getCodeName(), project.getVersion()));
		view.setType(type);

		return view;
	}

	/**
	 * @param view
	 * @param type
	 * @return
	 */
	static ResultView viewFromResult(Result result) {

		ResultView view = new ResultView();

		view.setMessage(result.getMessage());
		view.setSuccess(result.isSuccess());
		if (result.getPayload() != null) {
			view.setPayload(new JsonPayload(result.getPayload()));
		}
		view.setResultTime(result.getResultTime());

		return view;
	}

	/**
	 * @param view
	 * @param type
	 * @return
	 */
	static Result resultFromView(ResultView view, ResultType type) {

		Result result = new Result();

		if (view != null) {
			result.setMessage(view.getMessage());
			result.setSuccess(view.isSuccess());

			if (view.getPayload() != null) {
				result.setPayload(view.getPayload().getValue());
			}

			result.setResultTime(view.getResultTime() != null ? view.getResultTime() : LocalDateTime.now());
		} else {
			result.setResultTime(LocalDateTime.now());
		}

		result.setType(type);

		return result;
	}

}
