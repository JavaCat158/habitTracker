package ru.yalab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yalab.dto.HabitDetailsDTO;
import ru.yalab.dto.HabitDto;
import ru.yalab.entity.Habit;

@Mapper
public interface HabitMapper {
    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    HabitDto habitToHabitDto(Habit habit);

    HabitDetailsDTO habitToHabitDetailDto(Habit habit);

    Habit habitDtoToHabit(HabitDto habitDto);
}
