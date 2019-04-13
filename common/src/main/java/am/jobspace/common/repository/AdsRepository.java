package am.jobspace.common.repository;


import am.jobspace.common.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads,Integer> {
    List<Ads> findAllByCategoryId(int categoryId);
}
