package am.jobspace.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Images {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String picUrl;
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column
  private Date uploadDate;

}
