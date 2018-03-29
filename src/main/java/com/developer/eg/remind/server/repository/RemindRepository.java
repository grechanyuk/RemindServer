package com.developer.eg.remind.server.repository;

import com.developer.eg.remind.server.entity.Remind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemindRepository extends JpaRepository<Remind, Long> { //<Remind> - говорит, что принимаем данные по типу Remind из entity. <Long> - тип id, который нам отдаст Remind
}
