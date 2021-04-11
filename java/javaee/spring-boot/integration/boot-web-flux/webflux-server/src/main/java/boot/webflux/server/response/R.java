package boot.webflux.server.response;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zack <br>
 * @create 2021-04-11 15:00 <br>
 * @project springboot <br>
 */
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Getter @Setter private int code = 1;

  @Getter @Setter private String msg = "success";

  @Getter @Setter private T data;

  public R() {
    super();
  }

  public R(T data) {
    super();
    this.data = data;
  }

  public R(T data, String msg) {
    super();
    this.data = data;
    this.msg = msg;
  }

  public R(Throwable e) {
    super();
    this.msg = e.getMessage();
    this.code = 1;
  }
}
