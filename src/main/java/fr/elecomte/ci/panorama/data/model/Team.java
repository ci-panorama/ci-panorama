package fr.elecomte.ci.panorama.data.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
@NamedNativeQueries({
		// Team by members
		@NamedNativeQuery(
				name = "Team.findByDevelopers",
				query = "select t.* from Team t "
						+ "inner join "
						+ "( "
						+ "   select td.team_id from Team_Developers td "
						+ "   where td.developers_id in (?1)"
						+ "   group by td.team_id"
						+ ") m on m.team_id = t.id",
				resultClass = Team.class)
})
public class Team extends LiveCiEntity {

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Developer> developers = new HashSet<>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Developer> getDevelopers() {
		return this.developers;
	}

	public void setDevelopers(Collection<Developer> developers) {
		this.developers = developers;
	}
}
