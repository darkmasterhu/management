package hu.challenges.management.machine.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "machines")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Machine implements Serializable {

	private static final long serialVersionUID = 8817593436603317867L;

	/**
	 * Unique identifier
	 */
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	/**
	 * Creation date of machine
	 */
	@Column
	@CreatedDate
	private OffsetDateTime createdAt;

	/**
	 * Date of update
	 */
	@Column
	private OffsetDateTime updatedAt;

	/**
	 * Name of current machine
	 */
	private String name;

	@Column
	private Boolean active;

}
