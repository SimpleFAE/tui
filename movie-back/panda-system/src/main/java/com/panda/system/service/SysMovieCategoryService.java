package com.panda.system.service;

import com.panda.system.domin.SysMovieCategory;
import com.panda.system.domin.SysMovieToCategory;

import java.util.List;
import java.util.Map;


public interface SysMovieCategoryService {
    List<SysMovieCategory> findAllCategorys();

    SysMovieCategory findCategoryById(Long id);

    int addCategory(SysMovieCategory sysMovieCategory);

    int updateCategory(SysMovieCategory sysMovieCategory);

    int deleteCategory(Long[] ids);

    int addMovieToCategory(SysMovieToCategory sysMovieToCategory);

    int deleteMovieToCategory(SysMovieToCategory sysMovieToCategory);

    List<Map<String, Object>> getCategoryStatistics();
}
