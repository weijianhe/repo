package com.jt.web.pojo;

import java.util.Date;

/**
 * @author si
 *
 */
public class Seckill {
/*
 * -------------  ------------  ---------------  ------  ------  -------------------  --------------  -------------------------------  ------------------
seckill_id     BIGINT(20)    (NULL)           NO      PRI     (NULL)               AUTO_INCREMENT  SELECT,INSERT,UPDATE,REFERENCES  商品库存id    
NAME           VARCHAR(120)  utf8_general_ci  NO              (NULL)                               SELECT,INSERT,UPDATE,REFERENCES  商品名称      
number         INT(11)       (NULL)           NO              (NULL)                               SELECT,INSERT,UPDATE,REFERENCES  库存数量      
initial_price  BIGINT(20)    (NULL)           NO              (NULL)                               SELECT,INSERT,UPDATE,REFERENCES  原价            
seckill_price  BIGINT(20)    (NULL)           NO              (NULL)                               SELECT,INSERT,UPDATE,REFERENCES  秒杀价         
sell_point     VARCHAR(500)  utf8_general_ci  NO              (NULL)                               SELECT,INSERT,UPDATE,REFERENCES  卖点            
create_time    TIMESTAMP     (NULL)           NO      MUL     CURRENT_TIMESTAMP                    SELECT,INSERT,UPDATE,REFERENCES  秒杀创建时间
start_time     TIMESTAMP     (NULL)           NO      MUL     0000-00-00 00:00:00                  SELECT,INSERT,UPDATE,REFERENCES  秒杀开始时间
end_time       TIMESTAMP     (NULL)           NO      MUL     0000-00-00 00:00:00                  SELECT,INSERT,UPDATE,REFERENCES  秒杀结束时间

 */
	private Long seckillId;
	private String name;
	private Integer number;
	private Long initialPrice;
	private Long seckillPrice;
	private String sellPoint;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	public Long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Long getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(Long initialPrice) {
		this.initialPrice = initialPrice;
	}
	public Long getSeckillPrice() {
		return seckillPrice;
	}
	public void setSeckillPrice(Long seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
	
}
