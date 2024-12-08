package com.lyc.business.mapper;

import com.lyc.business.domain.DailyTrainCarriage;
import com.lyc.business.domain.DailyTrainCarriageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DailyTrainCarriageMapper {
    long countByExample(DailyTrainCarriageExample example);

    int deleteByExample(DailyTrainCarriageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DailyTrainCarriage row);

    int insertSelective(DailyTrainCarriage row);

    List<DailyTrainCarriage> selectByExample(DailyTrainCarriageExample example);

    DailyTrainCarriage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") DailyTrainCarriage row, @Param("example") DailyTrainCarriageExample example);

    int updateByExample(@Param("row") DailyTrainCarriage row, @Param("example") DailyTrainCarriageExample example);

    int updateByPrimaryKeySelective(DailyTrainCarriage row);

    int updateByPrimaryKey(DailyTrainCarriage row);
}