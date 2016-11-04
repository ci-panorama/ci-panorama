package fr.elecomte.ci.look.data.model;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

/**
 * @author elecomte
 * @since 0.1.0
 */
@MappedSuperclass
public abstract class LiveCiEntity extends CiEntity {

	private LocalDateTime initTime;

	private LocalDateTime lastUpdateTime;

	public LocalDateTime getInitTime() {
		return this.initTime;
	}

	public void setInitTime(LocalDateTime initTime) {
		this.initTime = initTime;
	}

	public LocalDateTime getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = prime * result + ((this.initTime == null) ? 0 : this.initTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiveCiEntity other = (LiveCiEntity) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		if (this.initTime == null) {
			if (other.initTime != null)
				return false;
		} else if (!this.initTime.equals(other.initTime))
			return false;
		return true;
	}

}
