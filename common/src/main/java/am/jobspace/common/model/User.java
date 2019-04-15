package am.jobspace.common.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements Serializable{
  private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotNull
    @Size(min = 4,max = 10,message = "{name.notempty}")
    private String name;
    @Column
    private String surname;
    @Column
    private String number;
    @Column
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column
    private String password;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "pic_url")
    private String picUrl;

  public User(String name) {
    this.name = name;
  }
}
