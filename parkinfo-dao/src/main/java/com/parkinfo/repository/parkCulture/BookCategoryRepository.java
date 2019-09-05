package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory,String> {
}
