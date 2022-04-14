package com.example.notesmaker;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Converters {
    @TypeConverter
    public Long dateToLong(Date date){
        return date.getTime();
    }
    @TypeConverter
    public Date longToDate(Long date){
        return new Date(date);
    }
}
