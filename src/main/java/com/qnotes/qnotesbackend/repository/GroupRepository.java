package com.qnotes.qnotesbackend.repository;

import com.qnotes.qnotesbackend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {}
