package skillbox.javapro11.repository.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Utils {
  public static Pageable getPageable(long offset, int itemPerPage) {
    return getPageable(offset, itemPerPage, null);
  }

  public static Pageable getPageable(long offset, int itemPerPage, Sort sort) {
    itemPerPage = itemPerPage == 0 ? 1 : itemPerPage;
    int page = (int) (offset / itemPerPage);
    if (sort.isEmpty()) {
      return PageRequest.of(page, itemPerPage);
    }else{
      return PageRequest.of(page, itemPerPage, sort);
    }
  }

  public static LocalDateTime getLocalDateTimeFromLong(long timestamp) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
  }

  public static Long getTimestampFromLocalDate(LocalDate date){
    return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public static Long getTimestampFromLocalDateTime(LocalDateTime date) {
    return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

}
