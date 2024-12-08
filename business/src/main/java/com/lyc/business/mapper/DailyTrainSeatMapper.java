package com.lyc.business.mapper;

import com.lyc.business.domain.DailyTrainSeat;
import com.lyc.business.domain.DailyTrainSeatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DailyTrainSeatMapper {
    long countByExample(DailyTrainSeatExample example);

    int deleteByExample(DailyTrainSeatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DailyTrainSeat row);

    int insertSelective(DailyTrainSeat row);

    List<DailyTrainSeat> selectByExample(DailyTrainSeatExample example);

    DailyTrainSeat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") DailyTrainSeat row, @Param("example") DailyTrainSeatExample example);

    int updateByExample(@Param("row") DailyTrainSeat row, @Param("example") DailyTrainSeatExample example);

    int updateByPrimaryKeySelective(DailyTrainSeat row);

    int updateByPrimaryKey(DailyTrainSeat row);
}