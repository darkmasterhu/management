package hu.challenges.management.machine.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.challenges.management.machine.entity.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, UUID> {

	List<Machine> findByActiveOrderByUpdatedAtDesc(Boolean active);

}
