package fr.elecomte.ci.look.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.Result;
import fr.elecomte.ci.look.data.model.ResultType;

/**
 * @author elecomte
 * @since 0.1.0
 */
public interface ResultRepository extends JpaRepository<Result, Long> {

	/**
	 * @param project
	 * @return
	 */
	List<Result> findByProjectOrderByResultTimeDesc(Project project);

	/**
	 * @param project
	 * @param type
	 * @return
	 */
	Result findFirstByProjectAndTypeOrderByResultTimeDesc(Project project, ResultType type);

	/**
	 * @param project
	 * @param type
	 * @return
	 */
	List<Result> findByProjectAndTypeOrderByResultTimeDesc(Project project, ResultType type);

}
