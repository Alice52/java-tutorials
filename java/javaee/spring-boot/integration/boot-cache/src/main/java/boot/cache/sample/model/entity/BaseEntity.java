package boot.cache.sample.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zack <br>
 * @create 2021-04-09 10:17 <br>
 * @project integration <br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseEntity<T extends Model> extends Model implements Serializable {
  protected static final long serialVersionUID = 1L;

  @TableLogic private Boolean isDeleted;

  @TableField(value = "inserted_time", fill = FieldFill.INSERT)
  private LocalDateTime insertedTime;

  @TableField(value = "updated_time", fill = FieldFill.UPDATE)
  private LocalDateTime updatedTime;

  @TableField(value = "inserted_by", fill = FieldFill.INSERT)
  private Long insertedBy;

  @TableField(value = "updated_by", fill = FieldFill.UPDATE)
  private Long updatedBy;
}
