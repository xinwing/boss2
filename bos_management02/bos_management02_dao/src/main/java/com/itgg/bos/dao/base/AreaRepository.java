package com.itgg.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itgg.bos.domain.base.Area;

/**
 * ClassName:AreaRepository <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午4:10:49 <br/>
 */
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Query("from Area a where a.province like ?1 or a.city like ?1 or a.district like ?1 or postcode like ?1 or a.citycode like ?1 or a.shortcode like ?1")
    List<Area> findQ(String q);
    
    Area findByProvinceAndCityAndDistrict(String province,String city,String district);

}
