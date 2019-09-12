package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.ArchiveComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveCommentRepository extends JpaRepository<ArchiveComment, String> {
}
